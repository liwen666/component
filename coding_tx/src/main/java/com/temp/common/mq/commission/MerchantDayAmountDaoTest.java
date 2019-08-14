//package com.temp.common.mq.commission;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import vip.dcpay.commission.RunApplication;
//import vip.dcpay.commission.infrastructure.repository.model.MerchantDayAmount;
//import vip.dcpay.util.date.DateUtils;
//
//import java.math.BigDecimal;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = RunApplication.class)
//public class MerchantDayAmountDaoTest {
//    @Autowired(required = false)
//    private MerchantDayAmountDao merchantDayAmountDao;
//
//    @Test
//    public void insert() {
//        MerchantDayAmount build = MerchantDayAmount.builder().merchantId(1l).createTime(DateUtils.parse("2019-08-14")).remark(1).dayAmountSum(new BigDecimal(100)).build();
//        merchantDayAmountDao.insert(build);
//    }
//
//    @Test
//    public void update() {
//        BigDecimal amount = new BigDecimal(10);
////        MerchantDayAmount build = MerchantDayAmount.builder().merchantId(1l).createTime(DateUtils.parse("2019-08-14")).remark(1).dayAmountSum(new BigDecimal(400)).build();
//        for (int i = 0; i < 10; i++) {
//            new Thread() {
//                @Override
//                public void run() {
//                    MerchantDayAmount merchantDayAmount = merchantDayAmountDao.selectOne(Wrappers.<MerchantDayAmount>lambdaQuery().eq(MerchantDayAmount::getCreateTime, "2019-08-14").eq(MerchantDayAmount::getMerchantId, 1));
//                    merchantDayAmount.setDayAmountSum(merchantDayAmount.getDayAmountSum().add(amount));
//                    merchantDayAmount.setRemark(merchantDayAmount.getRemark()+1);
//                    int update = merchantDayAmountDao.update(merchantDayAmount, Wrappers.<MerchantDayAmount>lambdaUpdate().eq(MerchantDayAmount::getMerchantId, merchantDayAmount.getMerchantId()).eq(MerchantDayAmount::getCreateTime, merchantDayAmount.getCreateTime()).eq(MerchantDayAmount::getRemark, merchantDayAmount.getRemark() - 1));
//                    while (update==0){
//                        MerchantDayAmount merchantDayAmount1 = merchantDayAmountDao.selectOne(Wrappers.<MerchantDayAmount>lambdaQuery().eq(MerchantDayAmount::getCreateTime, "2019-08-14").eq(MerchantDayAmount::getMerchantId, 1));
//                        merchantDayAmount1.setDayAmountSum(merchantDayAmount1.getDayAmountSum().add(amount));
//                        merchantDayAmount1.setRemark(merchantDayAmount1.getRemark()+1);
//                        update = merchantDayAmountDao.update(merchantDayAmount1, Wrappers.<MerchantDayAmount>lambdaUpdate().eq(MerchantDayAmount::getMerchantId, merchantDayAmount1.getMerchantId()).eq(MerchantDayAmount::getCreateTime, merchantDayAmount1.getCreateTime()).eq(MerchantDayAmount::getRemark, merchantDayAmount1.getRemark() - 1));
//                    }
//
//                    super.run();
//                }
//            }.start();
//        }
//    }
//}