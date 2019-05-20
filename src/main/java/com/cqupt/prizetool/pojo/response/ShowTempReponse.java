package com.cqupt.prizetool.pojo.response;

import com.cqupt.prizetool.bean.TempAct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTempReponse {

    private int status;
    private String info;
    private TempAct data;
}
