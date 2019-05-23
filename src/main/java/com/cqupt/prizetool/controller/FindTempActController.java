package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.TempAct;
import com.cqupt.prizetool.model.response.ErrorResponse;
import com.cqupt.prizetool.model.response.ShowTempReponse;
import com.cqupt.prizetool.service.ShowTempService;
import com.cqupt.prizetool.utils.UnicodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class FindTempActController {
    @Autowired
    RedisTemplate<Object, TempAct> tempActRedisTemplate;

    @PostMapping("/prize/findTemp")
    public ErrorResponse findAct(@RequestParam(value = "token",required = false) String token, @RequestParam(value = "actname",defaultValue = "") String actName, HttpServletRequest request) throws ValidException {
        if (null == token || !request.getSession().getAttribute("SESSIONID").equals(token)) {
            throw new ValidException("token验证无效");
        }
        if (actName.equals("")) {
            throw new ValidException("actName is null");
        }

        TempAct tempAct = tempActRedisTemplate.opsForValue().get("CACHE_"+request.getSession().getAttribute("SESSIONNAME")+"_"+ UnicodeUtil.getID(actName));
        if(tempAct!=null){
            return new ErrorResponse(-1,"Cache is exist");
        }
        return new ErrorResponse(200,"no errors");
    }
}
