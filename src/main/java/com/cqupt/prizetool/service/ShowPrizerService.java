package com.cqupt.prizetool.service;

import com.cqupt.prizetool.model.StudentA;
import com.cqupt.prizetool.model.StudentB;
import com.cqupt.prizetool.exception.ValidException;
import com.cqupt.prizetool.mapper.master.SpecifiedTypeMapper;
import com.cqupt.prizetool.model.response.ShowPrizerAResponse;
import com.cqupt.prizetool.model.response.ShowPrizerBResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class ShowPrizerService {

    @Autowired
    SpecifiedTypeMapper specifiedTypeMapper;

    public ShowPrizerAResponse showPrizerA(String actid, int start, int pagesize) throws ValidException {
        int total;
        PageHelper.startPage(start, pagesize);
        List<StudentA> studentAS = null;


        studentAS = specifiedTypeMapper.findStudentA(actid);


                PageInfo<StudentA> page = new PageInfo(studentAS);
                int sum = (int) page.getTotal();

                if (sum % pagesize != 0) {
                    total = sum / pagesize + 1;
                } else {
                    total = sum / pagesize;
                }
                return new ShowPrizerAResponse(200, "success", total, actid, page.getList());
        }


    public ShowPrizerBResponse showPrizerB(String actid, int start, int pagesize) throws ValidException {
        int total;
        PageHelper.startPage(start, pagesize);
        List<StudentB> studentBS = null;


        studentBS = specifiedTypeMapper.findStudentB(actid);


        PageInfo<StudentB> page = new PageInfo(studentBS);
        int sum = (int) page.getTotal();

        if (sum % pagesize != 0) {
            total = sum / pagesize + 1;
        } else {
            total = sum / pagesize;
        }
        return new ShowPrizerBResponse(200, "success", total, actid, page.getList());
    }


}
