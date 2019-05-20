package com.cqupt.prizetool.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NSpecifiedActResponse {
    private int status;
    private String info;
    private String actId;
}
