package com.temp.common.common.schedule.quaze;

//import org.quartz.impl.triggers.CronTriggerImpl;
import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.List;

public class TestMain {
    public  static Logger logger= LoggerFactory.getLogger(TestMain.class);
    public static void main(String[] args) throws Exception {
        System.out.println("start");
        logger.debug("kkkkkk");
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetClass(WorkSchedul.class);
        jobDetailFactoryBean.setTargetMethod("say");
        WorkSchedul workSchedul = new WorkSchedul();
//        jobDetailFactoryBean.setTargetBeanName("sayName");
//        jobDetailFactoryBean.setBeanFactory(new ClassPathXmlApplicationContext());
        jobDetailFactoryBean.setTargetObject(workSchedul);
        jobDetailFactoryBean.setName("name");
        jobDetailFactoryBean.afterPropertiesSet();
        JobDetail detail1 = jobDetailFactoryBean.getObject();

        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean2 = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean2.setTargetObject(workSchedul);
        jobDetailFactoryBean2.setTargetMethod("see");
        jobDetailFactoryBean2.setName("name2");
        jobDetailFactoryBean2.afterPropertiesSet();
        JobDetail detail2 = jobDetailFactoryBean2.getObject();

        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setCronExpression("0/2 * 0-23 * * ?");
        ctfb.setJobDetail(detail1);
        ctfb.setName("trigger1");
        ctfb.afterPropertiesSet();
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        CronTrigger trigger1 = ctfb.getObject();
        /********************************/
        CronTriggerFactoryBean ctfb2 = new CronTriggerFactoryBean();
        ctfb2.setCronExpression("0/5 * 0-23 * * ?");
        ctfb2.setJobDetail(detail2);
        ctfb2.setName("trigger2");
        ctfb2.afterPropertiesSet();
        CronTrigger trigger2 = ctfb2.getObject();
        sfb.setTriggers(trigger2,trigger1);
        sfb.afterPropertiesSet();
        sfb.start();
        Scheduler scheduler = sfb.getObject();


        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        System.out.println(JSON.toJSONString(triggerGroupNames));
        Trigger trigger11 = scheduler.getTrigger(new TriggerKey("trigger1"));
        System.out.println(JSON.toJSONString(trigger11));
        Thread.sleep(5000);
        System.out.println("停止定时器！");

        sfb.stop();
        Thread.sleep(1000);
        System.out.println("开启定时器");
        sfb.start();
        Thread.sleep(1000);
        System.out.println("停止trigger1定时器！");
        scheduler.pauseTrigger(trigger1.getKey());
        Thread.sleep(5000);
        System.out.println("恢复trigger1定时器！");
        scheduler.resumeTrigger(trigger1.getKey());
//        scheduler.unscheduleJob()

    }
}
