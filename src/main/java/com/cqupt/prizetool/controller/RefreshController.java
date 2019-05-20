package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.access_token.Scheduler;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.pojo.response.GetPrizeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class RefreshController {

    @Autowired
    Scheduler scheduler;
    @PostMapping("/refresh")
    public GetPrizeResponse refreshToken(@RequestParam(value = "token",required = false)String token, HttpServletRequest request) throws ValidException {

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        String accesstoken = scheduler.getAccessTokenApi();

       if(null==accesstoken||accesstoken.equals("")){
           return new GetPrizeResponse(-2,"获取accestoken失败");
       }

        return new GetPrizeResponse(200,"accesstoken刷新成功！");
    }
}
