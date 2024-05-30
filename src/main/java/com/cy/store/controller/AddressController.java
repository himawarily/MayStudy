package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(address, uid, username);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public  JsonResult<List<Address>> getByUid(HttpSession session){
        Integer uid = getuidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return  new JsonResult<>(data,OK);
    }
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(HttpSession session,
                                       @PathVariable("aid") Integer aid){
        addressService.setDefault(aid,
                getuidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(HttpSession session,
                                   @PathVariable("aid") Integer aid){
        addressService.delete(aid,
                              getuidFromSession(session),
                              getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/update")
    public JsonResult<Void> update(String address,
                                   @PathVariable("aid")Integer aid,
                                   HttpSession session){
        addressService.update(address,
                              aid,
                              getuidFromSession(session),
                              getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}

