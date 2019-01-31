package com.temp.common.mybatis.tx.service.impl;

import com.temp.common.mybatis.tx.domain.ImUser;
import com.temp.common.mybatis.tx.dao.ImUserMapper;
import com.temp.common.mybatis.tx.service.IImUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tx
 * @since 2019-01-31
 */
@Service
public class ImUserServiceImpl extends ServiceImpl<ImUserMapper, ImUser> implements IImUserService {

}
