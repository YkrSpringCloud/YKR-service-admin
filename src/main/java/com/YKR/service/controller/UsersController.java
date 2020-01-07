package com.YKR.service.controller;

import com.YKR.service.admin.domain.Users;
import com.YKR.service.admin.mapper.UsersMapper;
import com.YKR.service.admin.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(String username,String plantPassword){
        List<Users> usersList= usersMapper.allUser();//测试数据库连接，此处不需要
        Users users1=   usersService.login(username,plantPassword);
        return "登录成功";

    }
}
