package com.cqupt.prizetool.model.response;

import com.cqupt.prizetool.model.ShowAct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowActivityResponse {

    private  int status;
    private String info ;
    private int total;
    private List<ShowAct> data;
}
