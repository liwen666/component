<bean id="job1" class="org.test.job.TestJob2" />     
        
    <!-- 定义触发器来管理任务bean -->    
    <bean id="cronTriggerJob1"     
        class="org.springframework.scheduling.quartz.CronTriggerFactoryBean"    
        p:cronExpression="0 52 17 * * ? *" >    
        <property name="jobDetail">    
            <bean class="org.springframework.scheduling.quartz.JobDetailFactoryBean"    
            p:durability="true"    
            p:jobClass="org.test.job.TestJob1"    
             />    
        </property>    
    </bean>    
        
    
    </bean>    
    <!-- 执行实际的调度 -->    
    <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">    
        <property name="triggers">    
            <list>    
                <ref bean="cronTriggerJob1" />    
            </list>    
        </property>    
    </bean>    