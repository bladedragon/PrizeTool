package com.cqupt.prizetool.controller.prize_tool;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.response.ShowTempReponse;
import com.cqupt.prizetool.service.ShowTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class ShowTempActController {

    @Autowired
    private ShowTempService showTempService;

    @PostMapping("/prize/showTemp")
    public ShowTempReponse showAct(@RequestParam(value = "token",required = false) String token, @RequestParam(value = "actid",defaultValue = "") String actId, HttpServletRequest request) throws ValidException {
        if (null == token || !request.getSession().getAttribute("SESSIONID").equals(token)) {
            throw new ValidException("token验证无效");
        }
            if (actId.equals("")) {
                throw new ValidException("actId is null");
            }
            ShowTempReponse response = showTempService.showTemp(actId,request);

            return response;
        }

}
