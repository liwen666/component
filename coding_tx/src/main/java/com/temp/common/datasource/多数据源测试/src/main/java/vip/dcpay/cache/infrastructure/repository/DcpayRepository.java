package com.temp.common.datasource.多数据源测试.src.main.java.vip.dcpay.cache.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vip.dcpay.cache.domain.repository.IDcpayRepository;
import vip.dcpay.cache.infrastructure.model.ExDigitalMoneyAsset;

import java.util.List;

@Repository
public class DcpayRepository implements IDcpayRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<ExDigitalMoneyAsset> listAssets(Long id, int code) {
        List<ExDigitalMoneyAsset> query = jdbcTemplate.query("select * from ex_digitalmoney_asset where accountId = "+id+" and accountType = "+code, new BeanPropertyRowMapper(ExDigitalMoneyAsset.class));
        return query;
    }
}
