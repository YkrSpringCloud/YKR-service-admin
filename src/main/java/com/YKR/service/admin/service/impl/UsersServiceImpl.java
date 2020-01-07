package com.YKR.service.admin.service.impl;

import com.YKR.service.admin.domain.Users;
import com.YKR.service.admin.mapper.UsersMapper;
import com.YKR.service.admin.service.UsersService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * Author:ykr
 * Date:2020/1/7
 * Description:
 */
@Service
@Transactional(readOnly = true)  //声明式事务
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Override  //体现用户的注册功能
    @Transactional(readOnly = false) //声明式事务
    public void insertAll(Users users) {
        users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes())); //对密码进行加密
        usersMapper.insertAll(users);
    }

    /*体现用户的登录功能*/
    public Users login(@Param("username") String username,@Param("plantPassword") String plantPassword){
            Users users=  usersMapper.getAllByUsername(username); //根据用户名查询用户信息
            String password=DigestUtils.md5DigestAsHex(plantPassword.getBytes()); //对前端用户输入的密码进行明文加密。
            if (password.equals(users.getPassword())){  //两次输入的密码相匹配
                return users;
            }
            return null;

    }


}
