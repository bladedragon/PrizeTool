package com.cqupt.prizetool.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetPrizeResponse {
    private int status;
    private String info;
}
