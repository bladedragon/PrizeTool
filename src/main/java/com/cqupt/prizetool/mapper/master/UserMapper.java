package com.cqupt.prizetool.mapper.master;

import com.cqupt.prizetool.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


@Mapper
@Repository

public interface UserMapper {

    @Insert("Insert into organization (username,password) value(#{username},#{password})")
    void insert(UserInfo userInfo);

    @Select("Select username , password from organization where username = #{username}")
    UserInfo selectByUsername(@Param("username") String username);


}
