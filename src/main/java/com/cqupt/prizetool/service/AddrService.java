package com.cqupt.prizetool.service;

import com.cqupt.prizetool.mapper.slave.StuDataMapper;
import com.cqupt.prizetool.utils.SpringContextUtil;
import com.cqupt.prizetool.utils.UnicodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class AddrService {
    @Autowired
    StuDataMapper stuDataMapper;

    @Transactional(transactionManager = "slaveTransactionManager",rollbackFor = SQLIntegrityConstraintViolationException.class)
    public int setAddr(String openid,String address,String prov,String city,String dist){



        stuDataMapper.UpdatePrior(openid);
        int addrID = UnicodeUtil.getAddrID();
        stuDataMapper.insertAddr(openid,addrID,address,prov,city,dist,1);
        return 0;
    }
}
