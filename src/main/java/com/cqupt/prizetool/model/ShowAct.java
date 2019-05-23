package com.cqupt.prizetool.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowAct {

    private String actname;
    private String founder;

    private int status;
    private String time;
    private String actid;
    private List<Acturl> urls;

}
