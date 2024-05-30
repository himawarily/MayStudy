package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith:表示启动这个单元测试类，否则这个测试类是不能运行的，需要传递一个参数，必须是SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNesAddress(){
        Address address = new Address();
        address.setPhone("13078883927");
        address.setName("修改者02");
        addressService.addNewAddress(address,10,"管理员");
    }

    @Test
    public void setDefault(){
        addressService.setDefault(1,10,"管理员");
    }

    @Test
    public void delete(){
        addressService.delete(19,13,"管理员");
    }

    @Test
    public void update(){
        addressService.update("家庭第地址修改成功",18,13,"管理者");
    }
}
