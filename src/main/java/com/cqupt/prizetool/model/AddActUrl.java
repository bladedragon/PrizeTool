package com.cqupt.prizetool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddActUrl {
    private String token;
    private String actid;
    private List<Acturl> acturls;
}
