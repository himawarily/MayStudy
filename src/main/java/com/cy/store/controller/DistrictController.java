package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.impl.DistrictServiceImpl;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    //district开头的请求都被拦截到getByParent()方法
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getByParent(parent);
        return new  JsonResult<>(data,OK);
    }
}
