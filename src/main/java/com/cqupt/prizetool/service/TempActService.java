package com.cqupt.prizetool.service;


import com.cqupt.prizetool.bean.PrizeList;
import com.cqupt.prizetool.bean.RewardList;
import com.cqupt.prizetool.bean.TempAct;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.ActivityMapper;
import com.cqupt.prizetool.pojo.response.NSpecifiedActResponse;
import com.cqupt.prizetool.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TempActService {

    @Autowired
    private RedisTemplate<Object, TempAct> tempActRedisTemplate;
    @Autowired
    private ActivityMapper activityMapper;

    public NSpecifiedActResponse getTempAct(List<PrizeList> typeA, List<RewardList> typeB, String activity, HttpServletRequest request) throws ValidException {
        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = f_date.format(new Date());
        long time = System.currentTimeMillis();
        HttpSession session = request.getSession();
        String founder = (String) session.getAttribute("SESSIONNAME");
        TempAct tempAct = new TempAct();
        tempAct.setActivity(activity);
        tempAct.setTypeA(typeA);
        tempAct.setTypeB(typeB);
        tempAct.setFounder(founder);
        String actid = getID(activity);
//        if(typeA.size()!=0||typeB.size()!=0) {
//            int num = 0;
//            for (int i = 0; i < typeA.size(); i++) {
//
//                PrizeList prizeList = typeA.get(i);
//                num += activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), 2, date, actid, prizeList.getReward(), "", ""));
//            }
//
//            for (int j = 0; j < typeB.size(); j++) {
//                RewardList rewardList = typeB.get(j);
//                num += activityMapper.insert(new Activity(activity, (String) session.getAttribute("SESSIONNAME"), 2, date, actid, rewardList.getReward(), "", ""));
//            }

//            if (num == 0) {
//                throw new ValidException("插入数据库失败");
//            }
//        }
        tempActRedisTemplate.opsForValue().set("CACHE_"+founder+"_"+actid,tempAct);
        tempActRedisTemplate.expire(activity,7, TimeUnit.DAYS);

       return new NSpecifiedActResponse(200,"success",getID(activity));

    }

    private  static String  getID(String activity){
        String longID = SessionUtil.getMD5(activity);
        String actID = longID.substring(0,6);
        return actID;
    }
}
