package com.temp.common.mybatis.tx.dao;

import com.temp.common.mybatis.tx.domain.ImUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tx
 * @since 2019-02-01
 */
@Mapper
@Component
public interface ImUserMapper extends BaseMapper<ImUser> {
    ImUser selectMy();

}
