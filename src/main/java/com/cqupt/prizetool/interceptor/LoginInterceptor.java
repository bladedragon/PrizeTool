package com.cqupt.prizetool.interceptor;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.filter.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisTemplate<String,String> stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ValidException {


        HttpSession session = request.getSession();

        // 初始化拦截器，设置不拦截路径
        String noMatchPath = ".*/(login).*";
        String resourcePath = ".*/(file).*";
        String cssPath = ".*/(css).*";
        String distPath = ".*/(dist).*";
        String jsPath = ".*/(js).*";
        String path = request.getServletPath();
        String errorPath = ".*/(error).*";

        if(path.matches(errorPath)){
            throw new ValidException("Internal Server Error");
        }
        if (path.matches(noMatchPath)||path.matches(resourcePath)
                ||path.matches(distPath)||path.matches(cssPath)||path.matches(jsPath)) {

            // 授权路径，不拦截
            return true;
        } else if (session.getAttribute("SESSIONID") != null) {

            String token = request.getParameter("token");
            if(token==null||token.equals("")){
                RequestWrapper requestWrapper = new RequestWrapper(request);
                String body = requestWrapper.getBody();
            }

            Map<Object, Object> sessionMap =  stringRedisTemplate.opsForHash().entries("SESSIONID");
            for (Object sessionId:sessionMap.values() ) {
                    if (sessionId != null && sessionId.equals(session.getAttribute("SESSIONID"))) {
                        System.out.println("sessionId =="+sessionId);
                        stringRedisTemplate.expire("SESSIONID",30, TimeUnit.MINUTES);
                        return true;
                    }
            }

        }
//        return true;
        log.info("ZLOG==>Path:"+path);
        throw new ValidException("token验证无效");
    }

}
