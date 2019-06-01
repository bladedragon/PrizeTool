package com.cqupt.prizetool.controller.prize_tool;

import com.cqupt.prizetool.model.response.ErrorResponse;
import com.cqupt.prizetool.service.AddrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class SetAddressController {

    @Autowired
    AddrService addrService;
    @ResponseBody
    @PostMapping("/prize/addr/setAddr")
    public ErrorResponse setAddr(HttpServletRequest request,
            @RequestParam("address") String address, @RequestParam("prov") String prov,
                                 @RequestParam(value = "city",defaultValue = "") String city, @RequestParam(value = "static/dist",defaultValue = "") String dist) {

        System.out.println("获取地址："+address);
        HttpSession session = request.getSession();
        System.out.println("获取session："+session.getAttribute("openid"));

        if (address == null || address.equals("")) {
            return new ErrorResponse(-1, "内容不能为空");
        }
        addrService.setAddr((String) session.getAttribute("openid"),address,prov,city,dist);
        return new ErrorResponse(0, "ok");
    }


    @GetMapping("/prize/addr/success")
    public String showPage() {
        return "successpage.html";
    }

}
