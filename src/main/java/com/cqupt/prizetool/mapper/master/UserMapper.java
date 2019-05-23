package com.cqupt.prizetool.mapper.master;

import com.cqupt.prizetool.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Mapper
@Repository

public interface UserMapper {

    @Insert("Insert into admin (username,password,sessionid) value(#{username},#{password},#{sessionid})")
    void insert(UserInfo userInfo);

    @Select("Select * from admin where username = #{username}")
    UserInfo selectByUsername(@Param("username") String username);
}
