package com.YKR.service.admin.controller;


import com.YKR.common.domain.Api;
import com.YKR.common.domain.Users;
import com.YKR.service.admin.mapper.UsersMapper;
import com.YKR.service.admin.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:ykr
 * Date:2020/1/6
 * Description:
 */
@RestController  //提供服务注册和服务登录的功能  提供者，只提供rest接口
public class UsersController {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UsersService usersService;

    /*服务注册*/
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(String username,String password,String perms){
        Users users=new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setPerms(perms);
/*        users.setUsername("kkk");
        users.setPassword("123456");
        users.setPerms("xxx");*/
        usersService.insertAll(users);
        return String.format("用户信息注册成功");
    }
    /*服务登录*/
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public Api login(@RequestParam(value = "username") String username, @RequestParam(value = "plantPassword") String plantPassword){
        Api api=new Api();
        List<Users> usersList= usersMapper.allUser();//测试数据库连接，此处不需要
        Users users1=   usersService.login(username,plantPassword);
        if (users1!=null){
            api.setCode(200);
            api.setMessage("登录成功");
            api.setData(users1);
            return api;
        }
        else{
            api.setCode(500);
            api.setMessage("登录信息有误");
            api.setData(users1);
            return api;
        }
    }


}
