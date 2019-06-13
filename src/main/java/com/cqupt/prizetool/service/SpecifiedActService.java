package com.cqupt.prizetool.service;

import com.cqupt.prizetool.config.TaskExecutorConfig;
import com.cqupt.prizetool.model.*;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.master.ActivityMapper;
import com.cqupt.prizetool.mapper.master.SpecifiedTypeMapper;
import com.cqupt.prizetool.mapper.slave.StuDataMapper;
import com.cqupt.prizetool.model.response.SpecifiedActResponse;

import com.cqupt.prizetool.model.SendThread;
import com.cqupt.prizetool.utils.UnicodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SpecifiedActService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    StuDataMapper stuDataMapper;
    @Autowired
    TemplateMessageService templateMessageService;
    @Autowired
    AsyncTaskService asyncTaskService;
    @Autowired
    RedisTemplate<Object, TempAct> tempActRedisTemplate;
    Pattern pattern = Pattern.compile("\\\"errmsg\\\":\\\"(.*?)\\\"");
    @Autowired
    TaskExecutorConfig taskExecutorConfig;

    public SpecifiedActResponse createSpecifiedAct(List<PrizeList> typeA, List<RewardList> typeB, String activity, HttpServletRequest request) throws SQLException, ValidException, ExecutionException, InterruptedException {
          final int[] result = {-1};
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = f_date.format(new Date());
        HttpSession session = request.getSession();
        String actid;
        String brewardID;
        List<String>  actids = activityMapper.SelectActivityId(activity);

          if(actids.isEmpty()){
            actid = UnicodeUtil.getID(activity);
          }else{
               actid = actids.get(0);
          }
          if(actid.equals("0")){
              actid = UnicodeUtil.getID(activity);
          }
          final String finalActid = actid;

          int CountSum = 0;
          for(int k=0;k<typeA.size();k++){
              PrizeList prizeList = typeA.get(k);
            CountSum += prizeList.getReqStudents().size();
          }
        log.info("ZLOG=>CountSum:"+CountSum);
          Map<String,String> Arewards = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(CountSum);
       Vector<Map<String, String>> failedMsg = new Vector<>();

       if(tempActRedisTemplate.delete("CACHE_"+session.getAttribute("SESSIONNAME")+"_"+actid)){
            log.error("成功删除缓存");
        }

        for (int i = 0; i < typeA.size(); i++) {

            PrizeList prizeList = typeA.get(i);
           String  arewardID = UnicodeUtil.getID(prizeList.getReward());

            activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"),1, date, actid, prizeList.getReward(),arewardID,prizeList.getMark()));

            for (int j = 0; j < prizeList.getReqStudents().size(); j++) {
                ReqStudent reqStudent = prizeList.getReqStudents().get(j);
                String msg = prizeList.getSendmsg();

                WXAccount wxAccount =  stuDataMapper.getOpenid(reqStudent.getStuid());
                if(wxAccount==null){
                    throw new ValidException("Fail to get openid");
                }
                String openid = wxAccount.getOpenid();

                if (openid.equals("")) {
                    throw new ValidException("openid error");
                }

                BigInteger telephone = null;
                if(reqStudent.getTelephone()!=null){
                     telephone= new BigInteger(reqStudent.getTelephone());
                }else{
                    telephone = BigInteger.valueOf(0);
                }

                StudentA student = new StudentA(openid, reqStudent.getStuname(), reqStudent.getCollege(), reqStudent.getStuid(), telephone, actid, date, prizeList.getReward(), 0,arewardID,1);

                specifiedTypeMapper.insert(student);
                Arewards.put(prizeList.getReward(),arewardID);

                Future<Map<String,String>> thread_result = taskExecutorConfig.asyncExecutor().submit(
                            new SendThread(specifiedTypeMapper,templateMessageService,openid,msg,activity,prizeList.getReward(),date,prizeList.getPrizeDate(), prizeList.getRemark()
                    ,student.getStuname(),student.getCollege(),student.getStuid(),reqStudent.getTelephone(),finalActid,arewardID,countDownLatch));

                System.out.println("thread_result = "+thread_result.get());
                    if(thread_result.get()!=null&&!thread_result.get().isEmpty()){
                        failedMsg.add(thread_result.get());
                            }

            }
    }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        Map<String,String> Brewards = new HashMap<>();
            for(int m =0;m<typeB.size();m++ ){
                RewardList rewardList = typeB.get(m);
                brewardID = UnicodeUtil.getID(rewardList.getReward());
                activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"),1, date, actid,rewardList.getReward(),brewardID,rewardList.getMark()));
                Brewards.put(rewardList.getReward(),brewardID);
            }


        if (failedMsg.isEmpty()) {
            return new SpecifiedActResponse(200, "success", actid, Arewards,Brewards,failedMsg);
        }

        return new SpecifiedActResponse(200,"have fail sending!",actid,Arewards,Brewards,failedMsg);
    }


    public int getFailedSend(String httpResponse){
        String text = null;

        Matcher matcher = pattern.matcher(httpResponse);
        while (matcher.find()) {
            text = matcher.group(1);
            System.out.println(matcher.group(1));
        }
       log.warn("ZLOG=>errorMsg:"+text);

        if(text==null||!text.equals("ok")){

            return 1;
        }
        return 0;
    }

}
