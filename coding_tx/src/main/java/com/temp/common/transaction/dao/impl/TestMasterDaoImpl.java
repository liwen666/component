package com.temp.common.transaction.dao.impl;

import javax.annotation.Resource;

import com.temp.common.transaction.dao.TestMasterDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class TestMasterDaoImpl implements TestMasterDao {

    @Resource(name="masterJdbcTemplate")
    JdbcTemplate masterJdbcTemplate;
    @Override
    public String master() {
//        masterJdbcTemplate.execute("update master set name='master' where id=1");
        masterJdbcTemplate.execute("insert into  master (name) values('master')");
        return "success";
    }

    @Override
    public String update() {
        masterJdbcTemplate.execute("update teacher set name='8' where id=1");
        System.out.println("update");
        masterJdbcTemplate.execute("fff teacher set name=''6' where id=1");

        return null;
    }

}