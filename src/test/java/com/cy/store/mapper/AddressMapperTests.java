package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith:表示启动这个单元测试类，否则这个测试类是不能运行的，需要传递一个参数，必须是SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(10);
        address.setPhone("13055550001");
        address.setName("地址修改者");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(10);
        System.out.println(count);
    }
    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(8);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.err.println(addressMapper.findByAid(6));
    }

    @Test
    public void updateNoDefault(){
        addressMapper.updateNoDefault(10);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(3,"红红",new Date());
    }
    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(15);
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(13));
    }

    @Test
    public void updateByAid(){
        addressMapper.updateByAid("红红的地址修改",18,"管理者",new Date());
    }
}
