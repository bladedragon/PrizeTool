package com.cqupt.prizetool.service;

import com.cqupt.prizetool.model.ReqStudent;
import com.cqupt.prizetool.model.StudentA;
import com.cqupt.prizetool.model.StudentB;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.master.ActivityMapper;
import com.cqupt.prizetool.mapper.master.GetPrizerMapper;
import com.cqupt.prizetool.mapper.master.SpecifiedTypeMapper;
import com.cqupt.prizetool.mapper.slave.StuDataMapper;
import com.cqupt.prizetool.model.response.GetPrizeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GetprizeAService {


    @Autowired
    GetPrizerMapper getPrizerMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    StuDataMapper stuDataMapper;

    public GetPrizeResponse getPrizeA(String openid, String actid, String rewardid ){

        System.out.println(rewardid);
        List<StudentA> students = getPrizerMapper.findStudentA(openid,actid,rewardid);



        String reward = activityMapper.SelectReward(actid,rewardid);

        if(null==reward||reward.equals("")){
            return new GetPrizeResponse(-4,"Can't find activity or reward");
        }

        List<Integer> statuses = activityMapper.SelectStatus(actid);

        if(statuses.get(0)==3){
            return new GetPrizeResponse(-5,"Activity has ended");
        }

        if(null==students||students.size()==0){
            return new GetPrizeResponse(-3,"No prizer's info");
        }else {

            if(students.size()!=1){
                log.error("ZLOG==>Data has repeated insert!" );
            }

            StudentA student = students.get(0);
            if(student.getStatus()!=0){
                System.out.println(student.getStatus());
                return new GetPrizeResponse(1,"Fail to get again");

            }else{
                getPrizerMapper.updateSpecified_type(openid,1);
                return new GetPrizeResponse(200,"success");
            }
        }

    }

    public GetPrizeResponse getPrizeB(String openid, String actid,String rewardid) throws ValidException {
//        String openid = wxAccount.getOpenid();            //利用openid获取学生的真实姓名
        if(null==openid||openid.equals("")){

            return new GetPrizeResponse(-2, "Fail to authorize");
        }

        String reward = activityMapper.SelectReward(actid,rewardid);

        if(null==reward||reward.equals("")){
            return new GetPrizeResponse(-4,"Can't find activity or reward");
        }

        List<Integer> statuses = activityMapper.SelectStatus(actid);

        if(statuses.get(0)==3){
            return new GetPrizeResponse(-5,"Activity has ended");
        }


        StudentB studentB = getPrizerMapper.findStudentB(openid,actid,rewardid);
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());

        if(studentB!=null){
            return new GetPrizeResponse(1,"Fail to get again ");
        }else{
            ReqStudent reqStudent =  stuDataMapper.getStuData(openid);
            if(null==reqStudent||reqStudent.equals("")){
                log.error("ZLOG==>Fail to get StuData");
                throw new ValidException("Fail to get StuData");
            }


            getPrizerMapper.insertNonSpecified_type(new StudentB(reqStudent.getStuname(),reqStudent.getStuid(),openid,actid,date,reward,rewardid,1,0));

            return new GetPrizeResponse(200,"success");
        }
    }
}
