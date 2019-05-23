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
public class EndActController {

    @Autowired
    ActivityMapper activityMapper;

    @PostMapping("/prize/EndActivity")
    public GetPrizeResponse EndAct(@RequestParam(value = "token",required = false)String token,
                                   @RequestParam(value = "actid",defaultValue = "") String actid,
                                   HttpServletRequest request) throws ValidException {

        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }
        if(actid.equals("")){
            return new GetPrizeResponse(-2,"Param cannnot be null");
        }

        int result = activityMapper.UpdateActStatus(actid,3);

        if(result == 0){
            return new GetPrizeResponse(-2,"no modify");
        }

        return new GetPrizeResponse(200,"success");
    }
}
