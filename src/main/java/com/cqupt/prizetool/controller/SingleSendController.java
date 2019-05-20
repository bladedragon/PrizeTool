package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.bean.SingleSendStudent;
import com.cqupt.prizetool.bean.WXAccount;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.SpecifiedTypeMapper;
import com.cqupt.prizetool.mapper.StuDataMapper;
import com.cqupt.prizetool.pojo.response.GetPrizeResponse;
import com.cqupt.prizetool.service.TemplateMessageService;
import com.cqupt.prizetool.utils.PosterUtil;
import com.cqupt.prizetool.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class SingleSendController {

    @Autowired
    TemplateMessageService templateMessageService;
    @Autowired
    PosterUtil posterUtil;
    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;
    @Autowired
    StuDataMapper stuDataMapper;
    Pattern pattern = Pattern.compile("\\\"errmsg\\\":\\\"(.*?)\\\"");

    @PostMapping("/sendOne")
    public GetPrizeResponse SingleSend(
            @RequestBody SingleSendStudent singleSendStudent,
            HttpServletRequest request) throws ValidException {

        if(singleSendStudent==null){
            throw new ValidException("Param cannnot be null");
        }
        String token = singleSendStudent.getToken();
        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        String stuid = singleSendStudent.getStuid();
        if(null==stuid||stuid.equals("")){
            throw new ValidException("stuid不能为空");
        }
        WXAccount wxAccount = stuDataMapper.getOpenid(stuid);
        if(wxAccount == null){
            throw new ValidException("Fail to get openid");
        }
        String openid = wxAccount.getOpenid();

        String msg = singleSendStudent.getMsg();
        String activity = singleSendStudent.getActivity();
        String reward = singleSendStudent.getReward();
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = f_date.format(new Date());
        String prize_date = singleSendStudent.getPrizeDate();
        String remark = singleSendStudent.getRemark();
        String result = ";";
        String text =  null;
        try {
             result = templateMessageService.sendMsg(openid,msg,activity,reward,date,prize_date,remark);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            text = matcher.group(1);
            System.out.println(matcher.group(1));
        }
        log.warn("ZLOG=>errorMsg:"+text);

        if(text==null||!text.equals("ok")){

            return new GetPrizeResponse(-2,"发送失败");
        }
        specifiedTypeMapper.updatePush_status(0,getID(activity),getID(reward),stuid);
        return new GetPrizeResponse(200,"发送成功");


    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
