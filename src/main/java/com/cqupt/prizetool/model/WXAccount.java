package com.cqupt.prizetool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXAccount {
    private String nickname;
    private String openid;
}
