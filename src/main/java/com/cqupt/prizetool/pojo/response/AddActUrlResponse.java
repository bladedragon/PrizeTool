package com.cqupt.prizetool.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddActUrlResponse {
    private int status;
    private String info;
    private List<String> failSend;
}
