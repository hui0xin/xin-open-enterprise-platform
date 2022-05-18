package com.xin.commons.multi.shardingjdbc.controller;

import com.xin.commons.multi.shardingjdbc.bean.UserAppDo;
import com.xin.commons.multi.shardingjdbc.bean.UserDeptDo;
import com.xin.commons.multi.shardingjdbc.bean.UserOtherDo;
import com.xin.commons.multi.shardingjdbc.service.UserAppService;
import com.xin.commons.multi.shardingjdbc.service.UserDeptService;
import com.xin.commons.multi.shardingjdbc.service.UserOtherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/test")
@Slf4j
public class TestContrlloer {

    //1
    @Autowired
    private UserOtherService userOtherService;

    //2
    @Autowired
    private UserAppService userAppService;

    //2
    @Autowired
    private UserDeptService userDeptService;

    @GetMapping(value = "/get1")
    public List<UserOtherDo> get() {
        UserOtherDo userOtherDo = new UserOtherDo();
        return userOtherService.listByObject(userOtherDo);
    }


    @GetMapping(value = "/get2")
    public List<UserAppDo> get2() {
        UserAppDo userAppDo = new UserAppDo();
        return userAppService.listByObject(userAppDo);
    }

    @GetMapping(value = "/get3")
    public List<UserDeptDo> get3() {
        UserDeptDo userDeptDo = new UserDeptDo();
        return userDeptService.listByObject(userDeptDo);
    }

    @GetMapping(value = "/get4")
    public String get4() {
        UserOtherDo userOtherDo = new UserOtherDo();
        System.out.println(userOtherService.listByObject(userOtherDo));

        UserAppDo userAppDo = new UserAppDo();
        System.out.println(userAppService.listByObject(userAppDo));


        UserDeptDo userDeptDo = new UserDeptDo();
        System.out.println(userDeptService.listByObject(userDeptDo));

        return "success";
    }

}