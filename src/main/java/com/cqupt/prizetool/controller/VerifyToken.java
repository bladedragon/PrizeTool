package com.cqupt.prizetool.controller;


import com.cqupt.prizetool.utils.verify.CheckUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class VerifyToken {

    @RequestMapping("/prize/wx/token")
    @ResponseBody
    public String verifyWXToken(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if (CheckUtils.checkSignature(msgSignature, msgTimestamp, msgNonce)) {

            return echostr;
        }

        return null;
    }
}
