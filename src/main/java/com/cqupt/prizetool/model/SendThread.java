package com.cqupt.prizetool.model;

import com.cqupt.prizetool.mapper.master.SpecifiedTypeMapper;
import com.cqupt.prizetool.service.TemplateMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Async("asyncExecutor")
@Slf4j
public class SendThread implements Callable<Map<String,String>> {
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    TemplateMessageService templateMessageService;
    Vector<Map<String, String>> failedMsg = new Vector<>();

    final int[] result = {-1};
    private String openid;
    private String msg;
    private String activity;
    private String reward;
    private String date;
    private String prizeDate;
    private String remark;
    private String stuname;
    private String collage;
    private String stuid;
    private String telephone;
    private String finalActid;
    private String arewardID;
    private CountDownLatch latch;


    public SendThread(SpecifiedTypeMapper specifiedTypeMapper, TemplateMessageService templateMessageService,
                      String openid, String msg, String activity, String reward, String date, String prizeDate, String rewark,
                      String stuname, String collage, String stuid, String telephone, String finalActid, String arewardID, CountDownLatch latch) {
        this.specifiedTypeMapper = specifiedTypeMapper;
        this.templateMessageService = templateMessageService;
        this.openid = openid;
        this.msg = msg;
        this.activity = activity;
        this.reward = reward;
        this.date = date;
        this.prizeDate = prizeDate;
        this.remark = rewark;
        this.stuname = stuname;
        this.stuid = stuid;
        this.collage = collage;
        this.telephone = telephone;
        this.finalActid = finalActid;
        this.arewardID = arewardID;
        this.latch = latch;
    }

    private static int getFailedSend(String httpResponse) {
        String text = null;
        Pattern pattern = Pattern.compile("\\\"errmsg\\\":\\\"(.*?)\\\"");
        Matcher matcher = pattern.matcher(httpResponse);
        while (matcher.find()) {
            text = matcher.group(1);
        }
        log.error("ZLOG=>errorMsg:" + text);

        if (text == null || !text.equals("ok")) {

            return 1;
        }
        return 0;
    }

//    @Override
//    public void run() {
//        try {
//            result[0] = getFailedSend(templateMessageService.sendMsg(openid, msg, activity, reward, date, prizeDate, remark));
//            if (result[0] == 1) {
//                Map<String, String> stuMsg = new HashMap<>();
//                stuMsg.put("stuname", stuname);
//                stuMsg.put("college", collage);
//                stuMsg.put("stuid", stuid);
//                stuMsg.put("telephone", telephone);
//                stuMsg.put("reward", reward);
//                failedMsg.add(stuMsg);
//            }
//            specifiedTypeMapper.updatePush_status(result[0], finalActid, arewardID, stuid);
//            Thread.sleep(1000);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        latch.countDown();
//    }


    @Override
    public Map<String, String> call(){
        Map<String, String> stuMsg = new HashMap<>();
        try {
            result[0] = getFailedSend(templateMessageService.sendMsg(openid, msg, activity, reward, date, prizeDate, remark));

            System.out.println("SendThread里的result[0]"+result[0]);
            if (result[0] == 1) {

                stuMsg.put("stuname", stuname);
                stuMsg.put("college", collage);
                stuMsg.put("stuid", stuid);
                stuMsg.put("telephone", telephone);
                stuMsg.put("reward", reward);
            }
            specifiedTypeMapper.updatePush_status(result[0], finalActid, arewardID, stuid);
//            Thread.sleep(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        latch.countDown();

        System.out.println(stuMsg);
        return stuMsg;
    }
}
