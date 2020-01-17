package com.YKR.service.admin.mapper;


import java.util.List;

import com.YKR.common.domain.Users;

import com.YKR.service.admin.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@CacheNamespace(implementation = RedisCache.class)
@Mapper
public interface UsersMapper {
    List<Users> allUser();

    void  insertAll(Users users);
    Users getAllByUsername(@Param("username") String username);
}