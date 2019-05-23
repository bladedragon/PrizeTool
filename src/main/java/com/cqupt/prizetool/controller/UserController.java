package com.cqupt.prizetool.controller;


import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.model.response.UserResponse;
import com.cqupt.prizetool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController

class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/prize/login")
    public UserResponse userLogin(@RequestParam(value = "username",defaultValue = "") String username, @RequestParam(value = "password",defaultValue = "") String password, HttpServletRequest request, HttpServletResponse response) throws ValidException {



        UserResponse userResponse = userService.Login(username,password,request,response);
//        int status = userResponse.getStatus();
//        switch(status){
//            case -1:
//                throw new ValidException("Username checks invalid");
//            case  -2:
//                throw new ValidException("Password cannot be null");
//            case  -3:
//                throw new ValidException("Username doesn't exist");
//            case  -4:
//                throw new ValidException("Password checks invalid");
//            default:
//                break;
//        }
        return userResponse;

    }


}
