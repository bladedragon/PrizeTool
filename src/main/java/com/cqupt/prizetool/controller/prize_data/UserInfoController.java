package com.cqupt.prizetool.controller.prize_data;

import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.slave.StuDataMapper;
import com.cqupt.prizetool.model.StuInfo;
import com.cqupt.prizetool.model.UserInfo;
import com.cqupt.prizetool.model.WXAccount;
import com.cqupt.prizetool.model.response.ErrorResponse;
import com.cqupt.prizetool.model.response.GetPrizeResponse;
import com.cqupt.prizetool.service.GetprizeAService;
import com.cqupt.prizetool.service.WXAuthorizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Slf4j
@Controller
public class UserInfoController {


    @Value("${WX_APPID}")
    private String WX_APPID;
    @Value("${WX_APPSECRET}")
    private String WX_APPSECRET;
    @Value("${REDIRECT_URI}")
    private String REDIRECT_URI;

    @Autowired
    WXAuthorizeService wxAuthorizeServicew;
    @Autowired
    GetprizeAService getprizeAService;
    @Autowired
    StuDataMapper stuDataMapper;


    @ResponseBody
    @GetMapping("/prize/wx_operate/redirect_wxinfo")
    public void redirectPrizeA(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APPID
                +"&redirect_uri="+REDIRECT_URI+"/addr/WxInfo"
                +"&response_type=code&scope=snsapi_userinfo"
                +"&state=STAT#wechat_redirect");

    }


    @GetMapping("/prize/addr/WxInfo")
    public String getWXInfo(HttpServletRequest request,@RequestParam(name = "code", required = false) String code,
                            @RequestParam(name = "state") String state) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {

        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);
        if(wxAccount==null){
            throw new ValidException("Fail to authorize");
        }

        HttpSession session = request.getSession();
        session.setAttribute("openid",wxAccount.getOpenid());
        session.setAttribute("nickname",wxAccount.getNickname());

            return "successpage";
    }


    @PostMapping("/prize/addr/addUser")
    @ResponseBody
    public ErrorResponse addUserInfo(HttpServletRequest request,String stuname, String stuid,
                                     int collage,long telephone){
        ErrorResponse errorResponse = new ErrorResponse();
        HttpSession session = request.getSession();
        int flag = 0;
        StuInfo stuInfo = new StuInfo();
        stuInfo.setStuname(stuname);
        stuInfo.setStuid(stuid);
        stuInfo.setCollage(collage);
        stuInfo.setTelephone(BigInteger.valueOf(telephone));

        if(session.getAttribute("nickname")== null||session.getAttribute("openid")== null){
            flag = 1;
        }
            System.out.println(session.getAttribute("nickname"));
            stuInfo.setOpenid((String) session.getAttribute("nickname"));
            stuInfo.setOpenid((String) session.getAttribute("openid"));



        int i = stuDataMapper.insertData(stuInfo);
        switch (i){
            case 0 :errorResponse.setMsg("You have bound it");
                    errorResponse.setStatus(200);
                    break;
            case 1:errorResponse.setMsg("Success");
                    errorResponse.setStatus(200);
                    break;
            default:errorResponse.setMsg("Binding has met some mistake");
                    errorResponse.setStatus(-2);
                    break;
        }

        switch (flag){
            case 1:errorResponse.setMsg("cannot bind wxUser with student");
                break;
            default:
                break;
        }

        return errorResponse;

    }
}
