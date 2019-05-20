package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.bean.Acturl;
import com.cqupt.prizetool.bean.AddActUrl;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.ActivityMapper;
import com.cqupt.prizetool.pojo.response.AddActUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AddActUrlController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/addActUrl")
    public AddActUrlResponse actUrl(@RequestBody AddActUrl addActUrl,
                                    HttpServletRequest request) throws ValidException {

        String token = addActUrl.getToken();
        String actid = addActUrl.getActid();
        List<Acturl> acturls = addActUrl.getActurls();
        String reward = null;
        String url =  null;
        int result = 0;
        List<String> failMsg =new ArrayList<>();

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        if(actid==null||actid.isEmpty()){
            throw new  ValidException("活动ID不能为空");
        }

        for(int i =0;i<acturls.size();i++){
            Acturl acturl = acturls.get(i);
            reward = acturl.getReward();
            url = acturl.getUrl();
              result = activityMapper.UpdateActUrl(actid,url,reward);
              if(result==0){
                  failMsg.add(reward);
              }
        }

        if(!failMsg.isEmpty()){
            return new AddActUrlResponse(-2,"has failed addition",failMsg);
        }
            return new AddActUrlResponse(200,"success!",failMsg);




    }
}
