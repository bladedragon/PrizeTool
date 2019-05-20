package com.cqupt.prizetool.pojo.response;

import com.cqupt.prizetool.bean.ShowAct;
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
