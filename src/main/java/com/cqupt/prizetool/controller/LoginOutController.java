package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.pojo.response.GetPrizeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginOutController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/loginOut")
    public GetPrizeResponse loginOut(@RequestParam(value = "token",required = false)String token, HttpServletRequest request, HttpServletResponse response) throws ValidException {


        if(null==token||!request.getSession().getAttribute("SESSIONID").equals(token)){
            throw new ValidException("token验证无效");
        }

        HttpSession session = request.getSession();

        stringRedisTemplate.opsForHash().delete("SESSIONID",session.getAttribute("SESSIONNAME"));

        session.removeAttribute("SESSIONID");
        session.removeAttribute("SESSIONNAME");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            switch(cookie.getName()){
                case "isLogined":
                    System.out.println("获取到cookie");

                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie); //重新写入，将覆盖之前的
                    break;
                default:
                    break;
            }
        }
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






      return new GetPrizeResponse(200,"sucess");


    }
}
