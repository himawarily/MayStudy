package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Controller
@RestController//@Controller+@ResponseBody注解的功能
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    /**
     * 约定大于配置：开发理念来完成，省略了大量的配置，注解的编写
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型来接收前端的数据；
     * Spring Boot会将前端的url地址中的参数名和pojo类的属性名进行比较，如果这俩个名称相同，则将值注入到pojo类中的对应的属性上
     */
    @RequestMapping("reg")
    //@ResponseBody表示此方法的响应结果以Json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
            userService.reg(user);
            return new JsonResult<Void>(OK);
    }

    /**
     * 2.接收数据方式：请求处理方法的参数列表设置为非pojo类型（非自己封装）
     * Spring Boot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同，则自动完成值的依赖注入
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password,
                                  HttpSession session){
        User data = userService.login(username, password);
        //向session对象中（session全局的）完成数据的绑定
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(data, OK);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword,
                                           HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data=
                userService.getByUid(getuidFromSession(session));
        return new  JsonResult<>(data,OK);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,
                                       HttpSession session){
        //user对象中有四部分的数据:username,phone,email,gender
        //uid需要再次封装到user对象中
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    /**设置上传文件的最大值 */
    public static final  int AVATAR_MAX_SIZE = 10 *1024*1024;

    /** 限制上传文件的类型*/ //static静态模块
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
        static {
            AVATAR_TYPE.add("image/jpeg");
            AVATAR_TYPE.add("image/png");
            AVATAR_TYPE.add("image/bmp");
            AVATAR_TYPE.add("image/gif");
        }

    /**
     * MultipartFile接口是由SpringMVC提供的一个接口，
     * 这个接口为我们包装了获取文件类型的数据（任何类型的file都可以接收）
     * ，SpringBoot它又整合了SpringMVC,
     * 只需要在处理请求的方法参数列表上声明一个参数类型MultipartFile的参数，然后SpringBoot自动将文件数据复制给这个参数
     *
     * @RequestParam 表示将请求中的参数，注入到请求处理的某个参数上
     * ，如果名称不一致则可以使用@RequestParam注解进行标记和映射
     * 
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                    @RequestParam("file") MultipartFile file){
        //判断文件是否为空null
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超出限制");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String ContentType = file.getContentType();
        //如果集合包含某个元素则返回true,这里取反为true
        if (!AVATAR_TYPE.contains(ContentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件：.../upload/文件.png
        //getServletContext 获取上下文的路径,getRealPath 真实路径
        String parent =
                session.getServletContext().
                        getRealPath("upload");
        //File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()){//➡检测目录是否存在
            dir.mkdirs();//➡创建当前的目录
        }
        //获取到这个文件名称，UUID工具来生成一个新的字符串作为新的文件名
        //例如；原文件名：avatar001.png
        //substring截取字符串的子串
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename="+originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //实现了类似于：SERHF-SMUS45-DCXR213-WVCYUC-SDGREF12.png
        String filename =
                UUID.randomUUID().toString().toUpperCase()
                + suffix;
        File dest = new File(dir, filename);//➡此时是空文件
        //参数file中的数据写入到这个空文件dest中
        try {
            file.transferTo(dest);//➡将file文件中的数据写入到dest文件中
        }catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径，相对路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid,avatar,username);

        //返回用户头像的路径给前端页面，将来用于头像的展示使用
        return new JsonResult<>(avatar,OK);
    }
/*
    @RequestMapping("reg")
    //@ResponseBody表示此方法的响应结果以Json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user){
        /* 创建响应结果对象 * /
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户名注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }
*/
}



