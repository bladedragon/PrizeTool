package com.cqupt.prizetool.service;

import com.cqupt.prizetool.bean.TempAct;
import com.cqupt.prizetool.pojo.response.ShowTempReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ShowTempService {
    @Autowired
    RedisTemplate<Object, TempAct> tempActRedisTemplate;

    public ShowTempReponse showTemp(String actid, HttpServletRequest request){
        HttpSession session = request.getSession();

    TempAct tempAct = tempActRedisTemplate.opsForValue().get("CACHE_"+session.getAttribute("SESSIONNAME")+"_"+actid);

    if(tempAct==null){

        return new ShowTempReponse(-2,"cache is not exist",null);
    }
    return new ShowTempReponse(0,"success",tempAct);
    }

}
