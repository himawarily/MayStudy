package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/** 收货地址业务层接口*/
public interface IAddressService {
    /** mapper层定义了两个抽象方法，不代表Service层也要定义两个抽象方法，
     * 定义多少个方法这取决于这两个接口是依赖关系还是独立的关系
     */
    void addNewAddress(Address address,Integer uid,String username);

    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址数据 并设为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户的id
     * @param username 表示修改地址执行的人
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户的id
     * @param username 用户名
     */
    void delete(Integer aid,Integer uid,String username);

    void update(String address,Integer aid,Integer uid, String username);
}
