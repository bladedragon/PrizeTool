package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.bean.NSpecifiedAct;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.pojo.response.NSpecifiedActResponse;
import com.cqupt.prizetool.service.NSpecifiedActService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;

/**
 * 已废弃
 */

@Controller
@Slf4j
public class NSpecifiedActController {

    @Autowired
    NSpecifiedActService specifiedActService;

    @PostMapping(value = "/noSpecifiedAct")
    @ResponseBody
    public NSpecifiedActResponse NSpecifiedActOperation(@RequestBody NSpecifiedAct nSpecifiedAct, HttpServletRequest request) throws ValidException {

        String token = nSpecifiedAct.getToken();
        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        String activity = nSpecifiedAct.getActivity();
        String url = nSpecifiedAct.getActurl();
        if(activity==null||activity.isEmpty()){
            throw new ValidException("活动名称不能为空");
        }
        if(url==null||url.isEmpty()){
            throw new  ValidException("活动路由不能为空");
        }

        NSpecifiedActResponse response = null;

        response = specifiedActService.createNSpecifiedAct(activity, url, request);

        return response;
    }
}
