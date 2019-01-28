package com.temp.common.transaction.service.impl;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;

import com.temp.common.transaction.dao.TestMasterDao;
import com.temp.common.transaction.dao.TestSlaveDao;
import com.temp.common.transaction.service.TestService;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@Service
public class TestServiceImpl implements TestService {
    @Resource(name = "springTransactionManager")
    private JtaTransactionManager txManager;
    @Autowired
    private TestMasterDao testMasterDao;
//    @Autowired
//    private final ThreadLocal<TestSlaveDao> testSlaveDao = new ThreadLocal<TestSlaveDao>();
    @Autowired
    private  TestSlaveDao testSlaveDao ;

    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;  
    //编程式
    @Override
    public String test() {
         UserTransaction userTx = txManager.getUserTransaction();
         try {               
             userTx.begin();     
             testMasterDao.master(); 
//             testSlaveDao.get().slave();
             testSlaveDao.slave();
//             int a=1/0;
//             System.out.println(a);
             userTx.commit();
         } catch (Exception e) {
             System.out.println("捕获到异常，进行回滚" + e.getMessage());
             e.printStackTrace();
             try {
                 userTx.rollback();
             } catch (IllegalStateException e1) {
                System.out.println("IllegalStateException:" + e1.getMessage());
             } catch (SecurityException e1) {
                 System.out.println("SecurityException:" + e1.getMessage());
             } catch (SystemException e1) {
                 System.out.println("SystemException:" + e1.getMessage());
             } catch (javax.transaction.SystemException e1) {
                 e1.printStackTrace();
             }
         }
        return null;
    }
    //声明式
    @Override
    @Transactional
    public String update(){
         testMasterDao.master();        
         testSlaveDao.slave();
         int a=1/0;
         System.out.println(a);
        return null;
    }
    //事务模板方式
    @Override
     public void test3() {  

            transactionTemplate.execute(new TransactionCallbackWithoutResult(){  
                @Override  
                protected void doInTransactionWithoutResult(TransactionStatus status) {  
                    try {  
                         testMasterDao.master();        
                         testSlaveDao.slave();
                         int a=1/0;
                        System.out.println(a);
                    } catch (Exception ex) {  
                        // 通过调用 TransactionStatus 对象的 setRollbackOnly() 方法来回滚事务。  
                        status.setRollbackOnly();  
                        ex.printStackTrace();  
                    }  
                }  
            });         


               /* 
                //有返回值的回调
                 Object obj=transactionTemplate.execute(new TransactionCallback(){
                    @Override
                    public Object doInTransaction(TransactionStatus status) {

                        return 1;
                    }  
                });  */
        }  


}