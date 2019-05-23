package com.cqupt.prizetool.model.response;

import com.cqupt.prizetool.model.TempAct;
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
