package com.cqupt.prizetool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecifiedAct {
    private String token;
    private String activity;
    private String acturl;
    private List<PrizeList> typeA;
    private List<RewardList> typeB;

}
