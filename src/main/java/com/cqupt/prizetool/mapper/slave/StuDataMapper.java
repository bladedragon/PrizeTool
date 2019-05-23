package com.cqupt.prizetool.mapper.slave;


import com.cqupt.prizetool.model.ReqStudent;
import com.cqupt.prizetool.model.StuInfo;
import com.cqupt.prizetool.model.WXAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface StuDataMapper {

@Select("SELECT openid, nickname FROM student_data WHERE stuid = #{stuid} ")
WXAccount getOpenid(String stuid);

@Select("SELECT stuname,stuid,collage,telephone FROM student_data WHERE openid = #{openid}")
ReqStudent getStuData(String openid);
@Insert("INSERT INTO student_data (openid,nickname,sutid,stuname,collage,telephone) value(#{openid},#{nickname},#{stuid},#{stuname},#{collage},#{telephone})"
        + "ON DUPLICATE KEY UPDATE openid = #{openid},nickname = #{nickname},stuid = #{stuid},stuname = #{stuname}, collage = #{collage},telephone = #{telephone}")
void insertData(StuInfo stuInfo);


}
