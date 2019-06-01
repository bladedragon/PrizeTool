package com.cqupt.prizetool.mapper.master;

import com.cqupt.prizetool.model.Activity;
import com.cqupt.prizetool.model.Acturl;
import com.cqupt.prizetool.model.ShowAct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface ActivityMapper {

    @Options(keyProperty="actid", keyColumn="id")
    @Insert("Insert into activity (actname,founder,status,time,actid,reward,rewardID,mark) value(#{actname},#{founder},#{status},#{time},#{actid},#{reward},#{rewardID},#{mark})"
            +"ON DUPLICATE KEY UPDATE actname=#{actname},founder=#{founder},status=#{status},actid=#{actid},time=#{time},rewardID=#{rewardID},mark=#{mark}")
    int insert(Activity activity);

    @Delete("Delete from activity where actid = #{actid}")
    void deleteAct(String actid);

    @Delete("Delete from activity where status = 2 and actid = #{actid}")
    void deleteActTemp(String actid);

    @Select("Select actid from activity where actname = #{actname}")
    List<String> SelectActivityId(@Param("actname") String actname);

//    @Select("Select  ifnull((Select actname from activity where actid = #{actid}),'')")
//    String SelectActname(@Param("actid") String actnid);


    @Results({
            @Result(property = "actname", column = "actname"),
            @Result(property = "urls",column = "actname",
                    many =@Many(select = "com.cqupt.prizetool.mapper.master.ActivityMapper.SelectUrl"))
    })
    @Select("Select actname,founder,status,time,actid from activity where founder = #{founder} group by actname,founder,status,actid ")
    List<ShowAct> SelectActAll(String founder);

    @Delete("Delete from specified_type where actid = #{actid}")
    void deleteSpecifiedType(String actid);

    @Delete("Delete from non_specified_type where actid = #{actid}")
    void deleteNoSpecifiedType(String actid);

    @Select("Select ifnull((Select reward from activity where actid = #{actid}and rewardID = #{rewardID}),'')")
    String SelectReward(@Param("actid") String actid, @Param("rewardID") String rewardID);

    @Update("update activity set url =#{url} where actid = #{actid} and reward=#{reward}")
    int UpdateActUrl(@Param("actid") String actid, @Param("url") String acturl, @Param("reward") String reward);

    @Update("update activity set status = #{status} where actid = #{actid}")
    int UpdateActStatus(@Param("actid") String actid, @Param("status") int status);

    @Select("select reward ,url from activity where actname = #{actname}")
    Acturl SelectUrl(@Param("actname") String actname);

    @Select("select status from activity where actid = #{actid}")
    List<Integer> SelectStatus(@Param("actid") String actid);

}
