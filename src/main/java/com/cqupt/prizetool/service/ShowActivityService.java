package com.cqupt.prizetool.service;

import com.cqupt.prizetool.model.ShowAct;
import com.cqupt.prizetool.model.TempAct;
import com.cqupt.prizetool.mapper.master.ActivityMapper;
import com.cqupt.prizetool.model.response.ShowActivityResponse;
import com.cqupt.prizetool.utils.UnicodeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ShowActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    private RedisTemplate<String, TempAct> tempActStringRedisTemplate;
    @Autowired
    private RedisTemplate<Object,TempAct> tempActRedisTemplate;



    public ShowActivityResponse showActivity(int start, int pagesize, HttpServletRequest request){

        HttpSession session = request.getSession();

        PageHelper.startPage(start,pagesize);
        List<ShowAct> activities =activityMapper.SelectActAll((String) session.getAttribute("SESSIONNAME"));


        Set<String> keys = tempActRedisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match("*").count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            }
        });
        for(String key:keys){
            String matcherStr = getMatcher(key, (String) session.getAttribute("SESSIONNAME"));
            if(!matcherStr.equals("")) {
                TempAct tempAct = tempActRedisTemplate.opsForValue().get(matcherStr);
                String activity = tempAct.getActivity();
                activities.add(new ShowAct(activity, tempAct.getFounder(), 2, "", UnicodeUtil.getID(activity), null));
            }
        }

        PageInfo<ShowAct> page = new PageInfo<>(activities);

        int sum = (int) page.getTotal();
        int total = 0;
        if(sum%pagesize!=0){
            total = sum/pagesize+1;
        }else{
            total = sum/pagesize;
        }

        return new ShowActivityResponse(200,"success",total ,page.getList());
    }

    private static String getMatcher(String str,String founder){
        Pattern pattern = Pattern.compile("CACHE_"+founder+"_(.*)");
        Matcher matcher = pattern.matcher(str);
        String result = "";
        String reStr = "";
        while(matcher.find()){
            reStr = matcher.group(0);
           result = reStr.substring(0,reStr.length()-1);

        }

        return result;
    }

//    public static void main(String[] args) {
//        String str = "\"CACHE_1\"";
//
//        System.out.println(getMatcher(str));
//    }

}
