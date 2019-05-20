package com.cqupt.prizetool.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {

    private String sessionid;
    private String username;
    private String password;


}
