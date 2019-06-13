package com.cqupt.prizetool.controller.prize_tool;


import com.cqupt.prizetool.model.UserInfo;
import com.cqupt.prizetool.mapper.master.CreateMapper;
import com.cqupt.prizetool.mapper.master.UserMapper;
import com.cqupt.prizetool.model.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DBController {
    @Autowired
    private CreateMapper createMapper;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/prize/init")
    public ErrorResponse init(){
        createMapper.dropActivity();
        createMapper.dropAdmin();
        createMapper.dropNon_specified_type();
        createMapper.dropSpecified_type();
        createMapper.createActivity();
        createMapper.createAdmin();
        createMapper.createNon_specified_type();
        createMapper.createSpecified_type();
        userMapper.insert(new UserInfo("xc","zzz"));
        userMapper.insert(new UserInfo("redrock","redrock123456"));
        userMapper.insert(new UserInfo("zzz","zzz"));
        return new ErrorResponse(200,"success");
    }
}
