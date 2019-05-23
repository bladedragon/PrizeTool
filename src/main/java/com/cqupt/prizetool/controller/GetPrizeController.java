package com.cqupt.prizetool.controller;

import com.cqupt.prizetool.model.WXAccount;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.response.GetPrizeResponse;
import com.cqupt.prizetool.service.GetprizeAService;
import com.cqupt.prizetool.service.WXAuthorizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


@Controller
@Slf4j
public class GetPrizeController {


    /**
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaf05acd677a29f25&redirect_uri=http://65v7bh.natappfree.cc/getPrizeB/93e3ad/xbox&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect
     * https://wx.idsbllp.cn/game/api/index.php?redirect=http://hxmvcn.natappfree.cc/getPrizeB/3b454a/0b3f45
     * @return
     */

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


    @GetMapping("/prize/wx_operate/redirect_A/{actid}/{rewardid}")
    public void redirectPrizeA(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "actid") String actid,@PathVariable(name = "rewardid") String rewardid) throws IOException {
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APPID
                +"&redirect_uri="+REDIRECT_URI+"/getPrizeA/"+actid+"/"+rewardid
                +"&response_type=code&scope=snsapi_userinfo"
                +"&state=STAT#wechat_redirect");

    }

    @GetMapping("/prize/wx_operate/redirect_B/{actid}/{rewardid}")
    public void redirectPrizeB(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable(name = "actid") String actid,@PathVariable(name = "rewardid") String rewardid) throws IOException {
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APPID
                +"&redirect_uri="+REDIRECT_URI+"/getPrizeB/"+actid+"/"+rewardid
                +"&response_type=code&scope=snsapi_userinfo"
                +"&state=STAT#wechat_redirect");

    }

    @GetMapping("/getPrizeA/{actid}/{rewardid}")
    public String getPrizeA(@RequestParam(name = "code", required = false) String code,
                                      @RequestParam(name = "state") String state, @PathVariable(name = "actid") String actid, @PathVariable(name = "rewardid")String rewardid) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {

        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);
        if(wxAccount==null){
            throw new ValidException("Fail to authorize");
        }

        GetPrizeResponse response =  getprizeAService.getPrizeA(wxAccount.getOpenid(),actid,rewardid);
        log.info("getPrizeA's Response:"+response);
        if(response.getStatus()==200){
            return "success.html";
        }
        return "fail.html";

    }

    @GetMapping("/getPrizeB/{actid}/{rewardid}")
    public String getPrizeB(@RequestParam(name = "code", required = false) String code,
                            @RequestParam(name = "state") String state,@PathVariable(name = "actid") String actid,@PathVariable(name = "rewardid") String rewardid) throws NoSuchProviderException, NoSuchAlgorithmException, ValidException {

        //这里写获取openid的方法
        WXAccount wxAccount = wxAuthorizeServicew.getWxInfo(code,state);

        if(wxAccount==null){
            log.error("ZLOG==>Fail to authorize");
            throw new ValidException("Fail to authorize");
        }

       GetPrizeResponse response= getprizeAService.getPrizeB(wxAccount.getOpenid(),actid,rewardid);
        log.info("getPrizeB's Response:"+response);


        if(response.getStatus()==200){
                return "success.html";
            }
            return "fail.html";

    }

//        @GetMapping("/getPrizeA/{actid}/{reward}")
//        public String getPrizeA(WxUser wxUser, @PathVariable(name = "actid") String actid,
//                                @PathVariable(name = "reward")String reward) throws ValidException {
//
//            if(wxUser==null){
//                log.error("ZLOG==>Fail to authorize");
//                throw new ValidException("Fail to authorize");
//            }
//
//        String openid = wxUser.getOpenid();
//            log.info("ZLOG==>getObject："+wxUser.equals(""));
////        log.info("接受到的openid==》"+openid);
////        log.info("接受到的昵称==》"+wxUser.getNickname());
////        log.info("接受到的城市==》"+wxUser.getCity());
////        log.info("接受到的国家==》"+wxUser.getCountry());
////        log.info("接受到的性别==》"+wxUser.getSex());
////        log.info("接受到的unionid==》"+wxUser.getUnionid());
////        log.info("接受到的头像地址=》"+wxUser.getHeadimgurl());
//
//        GetPrizeResponse response = getprizeAService.getPrizeA(openid,actid,reward);
//
//            if(response.getStatus()==200){
//
//                return "success.html";
//            }
//
//            return "fail.html";
//    }
//
//    @GetMapping("/getPrizeB/{actid}/{reward}")
//    public String getPrizeB(WxUser wxUser, @PathVariable(name = "actid") String actid,
//                                      @PathVariable(name = "reward")String reward) throws ValidException {
//        if(wxUser==null){
//            throw new ValidException("Fail to authorize");
//        }
//
//
//       GetPrizeResponse response= getprizeAService.getPrizeB(wxUser.getOpenid(),actid,reward);
//
//        if(response.getStatus()==200){
//
//            return "success.html";
//        }
//
//        return "fail.html";
//
//
//    }


}
