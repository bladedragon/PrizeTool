package com.cqupt.prizetool.mapper;


import com.cqupt.prizetool.bean.ReqStudent;
import com.cqupt.prizetool.bean.WXAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface StuDataMapper {

@Select("SELECT openid, nickname FROM prize_data_db WHERE stuid = #{stuid} ")
WXAccount getOpenid(String stuid);

@Select("SELECT stuname,stuid,collage,telephone FROM prize_data_db WHERE openid = #{openid}")
ReqStudent getStuData(String openid);



}
