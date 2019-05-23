package com.cqupt.prizetool.service;

import com.cqupt.prizetool.model.TempAct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class DeleteTempService {

    @Autowired
    private RedisTemplate<Object, TempAct> tempActRedisTemplate;

    public int DeleteTemp(String actid){
          if(tempActRedisTemplate.delete(actid)){
              return  1;
          }else{
              return 0;
          }
    }
}
