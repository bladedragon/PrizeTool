package com.cqupt.prizetool.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeList {
    private String mark;
    private String reward;
    private String prizeDate;
    private String sendmsg;
    private String remark;
    private List<ReqStudent> reqStudents;

}
