package com.cqupt.prizetool.pojo.response;


import com.cqupt.prizetool.bean.StudentB;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@Data
@AllArgsConstructor
public class ShowPrizerBResponse {

    private int status;
    private String info;
    private int total;
    private String actId;
    private List<StudentB> data;
}