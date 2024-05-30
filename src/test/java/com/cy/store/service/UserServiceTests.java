package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith:表示启动这个单元测试类，否则这个测试类是不能运行的，需要传递一个参数，必须是SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;
    /**
     * 单元测试方法:满足以下条件就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表 不能指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("text02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user =
                userService.login("text02","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(9,"管理员","321","123");
    }

    @Test
    public void getByUid() {
        System.err.println(userService.getByUid(9));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("15022220001");
        user.setEmail("text02@qq.com");
        user.setGender(0);
        userService.changeInfo(9,"管理者",user);
    }
    @Test
    public void changeAvatar(){
        userService.changeAvatar(10,"/upload/test.png","管理者2");
    }
}
