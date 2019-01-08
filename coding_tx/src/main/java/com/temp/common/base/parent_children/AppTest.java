package com.temp.common.base.parent_children;

import com.temp.common.base.parent_children.extend.ClassUtil;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 根据接口为所有接口实现类赋值
 */
public class AppTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ParentInterfaceHolder bean = ParentTempBeanFactory.getBean(ParentInterfaceHolder.class);
        System.out.println(bean.getContext());
        /**
         * 指定包名就加载指定包下所有类，不指定就加载指定接口就接口子包下的多有类
         */
        ArrayList<Class<ParentInterface>> allClassByInterfaces = ClassUtil.getAllClassByInterface(ParentInterface.class,"com.temp.common");
        for (Class<ParentInterface> p : allClassByInterfaces) {
            ParentInterface bean1 = ParentTempBeanFactory.getBean(p);
            bean1.setApplicationContext(new TargetContext());
        }
        System.out.println(bean.getContext());
    }
}
