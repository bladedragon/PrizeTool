package com.cqupt.prizetool.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMsgResponse {
    private int status;
    private  String info;
    private Object FailMsg;
}
