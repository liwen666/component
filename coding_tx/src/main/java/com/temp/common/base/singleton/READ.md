#初级
/**
* Created by forever on 2017/9/20.
*/
public class Singleton {
  private Singleton singleton;

  private Singleton() {

  }

  public Singleton getInstance() {
      if (singleton == null) {
          singleton = new Singleton();
      }
      return singleton;
  }
}
#改进
public synchronized Singleton getInstance() {
      if (singleton == null) {
          singleton = new Singleton();
      }
      return singleton;
  }
  
#升级
public Singleton getInstance() {
      if (singleton == null) {
          synchronized (Singleton.class){
              if(singleton == null){
                  singleton = new Singleton();
              }
          }
      }
      return singleton;
  }
 #恶汉式防止多线程问题对内存有影响
 /**
 * Created by forever on 2017/9/20.
 */
 public class Singleton {
   public Singleton singleton = new Singleton();
 
   private Singleton() {
   }
 
   public Singleton getInstance() {
       return singleton;
   }
 }
 
 #最理想方法
 public class Singleton {
 
   private Singleton(){
 
   }
 
   public static Singleton getInstance(){
       return Nested.singleton;
   }
 
   public static class Nested{//声明成静态防止内部类持有外部类的
   //引用造成内存泄漏
       static {
           System.out.println("创建实例");
       }
       private static Singleton singleton = new Singleton();
   }
 }