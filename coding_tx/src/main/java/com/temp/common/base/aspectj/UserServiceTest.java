package com.temp.common.base.aspectj;

import com.temp.common.base.aspectj.high.ControllerTx;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


@ComponentScan(basePackages = "com.temp.common.base.aspectj")
public class UserServiceTest {
  public static void main(String[] args) {

      AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
      context.register(UserServiceTest.class);
      context.refresh();
      UserService service = (UserService)context.getBean("userService");
      System.out.println(service.getClass());
      service.add(new User());
      System.out.println("###");

      context.destroy();
}


}
class  UserServiceTx2{
    public static void main(String[] args) {


        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/temp/common/base/aspectj/springContextTest.xml");
        System.out.println(context.getBean("beanService"));
//        AnnotationConfigWebApplicationContext context2 = context;
        UserService service = (UserService)context.getBean("userService");
        System.out.println(service.getClass());
        service.add(new User());
        System.out.println("###");
        Object beanService = context.getBean("beanService");
        System.out.println(beanService);
        ControllerTx tx= (ControllerTx) context.getBean("controllerTx");
        tx.say();
        context.destroy();
    }

}