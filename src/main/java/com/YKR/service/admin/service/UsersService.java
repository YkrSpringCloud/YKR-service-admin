package com.YKR.service.admin.service;

import com.YKR.service.admin.domain.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Author:ykr
 * Date:2020/1/7
 * Description:
 */
@Service
public interface UsersService {
    void  insertAll(Users users);
    Users login(@Param("username") String username,@Param("plantPassword") String plantPassword);

}
