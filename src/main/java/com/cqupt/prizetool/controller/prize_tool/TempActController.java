package com.cqupt.prizetool.controller.prize_tool;


import com.cqupt.prizetool.model.PrizeList;
import com.cqupt.prizetool.model.RewardList;
import com.cqupt.prizetool.model.SpecifiedAct;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.response.NSpecifiedActResponse;
import com.cqupt.prizetool.service.TempActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TempActController {

    @Autowired
    TempActService tempActService;

    @PostMapping("/prize/tempAct")
    public NSpecifiedActResponse modifyAct(@RequestBody SpecifiedAct specifiedAct, HttpServletRequest request) throws ValidException {

        String token = specifiedAct.getToken();
        if(null==token||(!request.getSession().getAttribute("SESSIONID").equals(token))){
            System.out.println(token);
            throw new ValidException("token验证无效");
        }

        if(specifiedAct==null){
            throw new ValidException("Param cannnot be null");
        }
        List<PrizeList> typeA = specifiedAct.getTypeA();
        List<RewardList>  typeB = specifiedAct.getTypeB();
        String activity = specifiedAct.getActivity();

        NSpecifiedActResponse response = tempActService.getTempAct(typeA,typeB,activity,request);

        return response;
    }


}
