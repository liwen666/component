package com.temp.common.transaction.dao.impl;

import javax.annotation.Resource;

import com.temp.common.transaction.dao.TestSlaveDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class TestSlaveDaoImpl implements TestSlaveDao {

    @Resource(name="slaveJdbcTemplate")
    JdbcTemplate slaveJdbcTemplate;
    @Override
    public String slave() {
        slaveJdbcTemplate.execute("insert into  salver (name) values('salver')");            
        return "success";
    }   

}