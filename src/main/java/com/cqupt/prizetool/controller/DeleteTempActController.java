package com.cqupt.prizetool.controller;


import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.pojo.response.GetPrizeResponse;
import com.cqupt.prizetool.service.DeleteTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeleteTempActController {

    @Autowired
    DeleteTempService deleteTempService;

    @PostMapping(value = "/deleteTemp")
    public GetPrizeResponse deleteTemp(@RequestParam(value = "token",required = false)String token,
                                       @RequestParam(value = "actid",defaultValue = "") String actid, HttpServletRequest request) throws ValidException {
        if(actid.equals("")){
            throw new ValidException("Param cannnot be null");
        }
        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        int result = deleteTempService.DeleteTemp("CACHE_"+actid);

        switch(result){
            case 1:return new GetPrizeResponse(200,"success");
            case 0:return new GetPrizeResponse(200,"no modify");
        }

        return new GetPrizeResponse(-2,"Operation failed");

    }
}
