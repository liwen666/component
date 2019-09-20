package vip.dcpay.validate.sdk.demand;


import vip.dcpay.log.sdk.MyLogManager;
import vip.dcpay.validate.sdk.bean.config.SystemConfig;
import vip.dcpay.validate.sdk.bean.config.UserConfig;
import vip.dcpay.validate.sdk.enums.BusinessTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: liq
 * @Date: 2019/6/18 22:54
 * @Description:
 */
public class DemandCollection {

    private static volatile DemandCollection collection;
    private static Lock cleanLock = new ReentrantLock();

    private Map<String, BaseDemand> demandMap = new ConcurrentHashMap<>();

    private DemandCollection() {
    }

    public static DemandCollection getInstance() {
        if (null == collection) {
            synchronized (DemandCollection.class) {
                if (null == collection) {
                    collection = new DemandCollection();
                }
            }
        }
        return collection;
    }

    public BaseDemand createDemand(BusinessTypeEnum businessType, SystemConfig systemConfig, UserConfig userConfig) {
        return DemandFactory.getDemand(businessType, systemConfig, userConfig);
    }

    public BaseDemand getDemand(String validateToken) {
        return demandMap.get(validateToken);
    }

    public void saveDemand(String validateToken, BaseDemand baseDemand) {
        demandMap.put(validateToken, baseDemand);
    }

    public void clean(String validateToken) {
        demandMap.remove(validateToken);
    }

    public void cleanAbandonToken() {
        if (!cleanLock.tryLock()) {
            MyLogManager.info(">>>token清理进行中<<<");
        }

        //MyLogManager.info(">>>开始清理失效token");
        long begin = System.currentTimeMillis();

        List<String> tokenList = new ArrayList<>();

        try {
          //  MyLogManager.info("当前token数量：" + demandMap.size() + " 条");
            if (demandMap.size() < 1) {
                return;
            }

            for (BaseDemand demand : demandMap.values()) {
                if (demand.checkExpire()) {
                    if(demand.validateInfo!=null){
                        tokenList.add(demand.validateInfo.getToken());
                    }
                }
            }

            if (tokenList.size() > 0) {
                for (String token : tokenList) {
                    clean(token);
                }
            }

        } finally {
            long end = System.currentTimeMillis();
         //   MyLogManager.info("本次共清理失效token数量：" + tokenList.size() + " 条");
         //   MyLogManager.info(">>>清理失效token执行完毕。耗时：" + (end - begin) + "毫秒");

            cleanLock.unlock();
        }

    }

}
