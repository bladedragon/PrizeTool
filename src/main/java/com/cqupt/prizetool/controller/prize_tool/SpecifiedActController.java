package com.cqupt.prizetool.controller.prize_tool;

import com.cqupt.prizetool.model.PrizeList;
import com.cqupt.prizetool.model.RewardList;
import com.cqupt.prizetool.model.SpecifiedAct;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.response.SpecifiedActResponse;
import com.cqupt.prizetool.service.SpecifiedActService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j

public class SpecifiedActController {

    @Autowired
    SpecifiedActService specifiedActService;
//    @Autowired
//    AsyncTaskService asyncService;

 @PostMapping(value = "/prize/specifiedAct")
public SpecifiedActResponse SpecifiedActOperation(@RequestBody SpecifiedAct specifiedAct, HttpServletRequest request) throws ValidException {
if(specifiedAct==null){
    throw new ValidException("Param cannnot be null");
}


     String token = specifiedAct.getToken();
     if(null==token||(!request.getSession().getAttribute("SESSIONID").equals(token))){
         throw new ValidException("token验证无效");
     }
     List<PrizeList> typeA = specifiedAct.getTypeA();
     List<RewardList>  typeB = specifiedAct.getTypeB();
     String activity = specifiedAct.getActivity();



     if(activity==null||activity.isEmpty()){
         throw new  ValidException("活动名称不能为空");
     }



     SpecifiedActResponse response = null;


     try {
         response = specifiedActService.createSpecifiedAct(typeA,typeB, activity, request);


     } catch (SQLException e) {
         e.printStackTrace();
         log.error("ZLOG==>Occur SQL_Ecxption!!");
     }

     return response;
 }
}