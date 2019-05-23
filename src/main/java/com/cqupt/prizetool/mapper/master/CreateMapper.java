package com.cqupt.prizetool.mapper.master;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreateMapper {
    void createActivity();
    void createAdmin();
    void createNon_specified_type();
    void createSpecified_type();
    void dropActivity();
    void dropAdmin();
    void dropNon_specified_type();
    void dropSpecified_type();
}
