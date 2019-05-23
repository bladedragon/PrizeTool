package com.cqupt.prizetool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuInfo {
    private String openid;
    private String nickname;
    private String stuid;
    private String stuname;
    private int collage;
    private BigInteger telephone;
}
