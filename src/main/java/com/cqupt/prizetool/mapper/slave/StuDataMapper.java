package com.cqupt.prizetool.mapper.slave;


import com.cqupt.prizetool.model.ReqStudent;
import com.cqupt.prizetool.model.StuInfo;
import com.cqupt.prizetool.model.WXAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface StuDataMapper {

    @Select("SELECT openid, nickname FROM student_data WHERE stuid = #{stuid} ")
    WXAccount getOpenid(String stuid);

    @Select("SELECT stuname,stuid,collage,telephone FROM student_data WHERE openid = #{openid}")
    ReqStudent getStuData(String openid);

    @Insert("INSERT INTO student_data (openid,nickname,stuid,stuname,collage,telephone) VALUES(#{openid},#{nickname},#{stuid},#{stuname},#{collage},#{telephone})"
        + "ON DUPLICATE KEY UPDATE openid = #{openid},nickname = #{nickname},stuid = #{stuid},stuname = #{stuname}, collage = #{collage},telephone = #{telephone}")
    int  insertData(StuInfo stuInfo);

    @Insert("INSERT INTO student_address (openid,addr_id,address,province,city,district,prior) VALUES(#{openid},#{addr_id},#{address},#{prov},"
            +"#{city},#{dist},#{prior})")
    int insertAddr(@Param("openid") String openid, @Param("addr_id") int addr_id, @Param("address") String address, @Param("prov") String prov,
                   @Param("city") String city, @Param("static/dist") String dist, @Param("prior") int prior);
    @Select("SELECT prior FROM student_address WHERE openid = #{openid}")
    List<Integer> getAddrsByOpenid(String openid);

    @Update("UPDATE student_address SET prior = prior+1 WHERE openid = #{openid}")
    int UpdatePrior(@Param("openid") String openid);
}
