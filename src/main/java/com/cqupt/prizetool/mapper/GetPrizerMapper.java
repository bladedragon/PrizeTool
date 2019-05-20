package com.cqupt.prizetool.mapper;


import com.cqupt.prizetool.bean.StudentA;
import com.cqupt.prizetool.bean.StudentB;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Mapper
@Repository

public interface GetPrizerMapper {


    @Update("update specified_type set status =#{status} where openid = #{openid}")
    Boolean updateSpecified_type(@Param(value = "openid") String openid, @Param(value = "status") int status);


    @Select("Select * from specified_type where (openid = #{openid}and actid = #{actid}and rewardID =#{rewardID})")
    List<StudentA> findStudentA(@Param(value = "openid") String openid, @Param(value = "actid") String actid, @Param(value = "rewardID") String rewardID);

    @Insert("Insert into non_specified_type (stuname,stuid,actid,openid,add_time,reward,rewardID,status,push_status) value(#{stuname},#{stuid},"+
            "#{actid},#{openid},#{add_time},#{reward},#{rewardID},#{status},#{push_status})")
    void insertNonSpecified_type(StudentB student);

    @Select("Select * from non_specified_type where openid = #{openid} and actid = #{actid}and rewardID = #{rewardID}")
    StudentB findStudentB(@Param(value = "openid") String openid, @Param(value = "actid") String actid, @Param(value = "rewardID") String rewardID);





}



