package com.cqupt.prizetool.service;

import com.alibaba.fastjson.JSON;
import com.cqupt.prizetool.utils.access_token.Scheduler;
import com.cqupt.prizetool.model.TemplateData;
import com.cqupt.prizetool.model.TemplateMsg;
import com.cqupt.prizetool.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TemplateMessageService {



    @Value("${TEMPLATE_ID}")
    private String template_id;
    @Value("${SENDURL}")
    private String sendurl;

    @Autowired
   private Scheduler scheduler;
    @Autowired
    private HttpUtil httpUtil;



    private TemplateMsg getTemplate(String openid, String msg, String activity, String award, String addtime, String prizetime, String remark) throws SQLException {

        TemplateMsg templateMsg = new TemplateMsg();

        Map<String,TemplateData>  dataMap = new HashMap<>();
        dataMap.put("first",new TemplateData(msg,"#173177"));
            dataMap.put("keyword1",new TemplateData(activity,"#173177"));
            dataMap.put("keyword2",new TemplateData(award,"#173177"));
            dataMap.put("keyword3",new TemplateData(addtime,"#173177"));
            dataMap.put("keyword4",new TemplateData(prizetime,"#173177"));
            dataMap.put("remark",new TemplateData(remark,"#173177"));
            templateMsg.setData(dataMap);
            templateMsg.setTemplate_id(template_id);
            templateMsg.setTopcolor("#173177");
        templateMsg.setTouser(openid);


        return templateMsg;
    }

    public String  sendMsg(String openid,String msg,String activity,String award,String addtime,String prizetime,String remark) throws SQLException{

        String accesstoken = "";

        accesstoken= scheduler.getAccess_Token();
        String url = sendurl+accesstoken;
        String HttpResponse  = "0";

        TemplateMsg templateMsg = getTemplate(openid,msg,activity, award, addtime, prizetime, remark);

        synchronized (this) {
            try {
                HttpResponse = httpUtil.httpRequestToString(url, "POST", JSON.toJSONString(templateMsg));
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
                log.error("ZLOG==> Request error");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                log.error("ZLOG==> Request error");
            }
        }

        return HttpResponse;

    }




}
