package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.master.ActivityMapper;
import com.cqupt.prizetool.model.response.GetPrizeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class DeleteActController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/prize/deleteActivity")
    public GetPrizeResponse deleteActivity(@RequestParam(value = "token",required = false)String token,
                                           @RequestParam(value = "actid",defaultValue = "") String actid, HttpServletRequest request) throws ValidException {

        if(actid.equals("")){
            throw new ValidException("Param cannnot be null");
        }
        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        activityMapper.deleteAct(actid);
        activityMapper.deleteSpecifiedType(actid);
        activityMapper.deleteNoSpecifiedType(actid);

        return new GetPrizeResponse(200,"success");
    }
}
