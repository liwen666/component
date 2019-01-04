##ClassLoad的意义
    加载class   通过loadclass（）方法就行  返回一个被加载的class
     此方法相当用findClass  如何想调用方法可以通过
     loadclass("className")获取class

##自定义类加载器
    重写findclass方法  将类加载到自定义的classload中  用同样的
    方法将类取出  
    
    如果调用的事load方法加载类会被加载到父类中 再从父类中拿出来
    这就是双亲委托模式，默认类都是由父类来加载。
    Class.forname = classload.loadclass
    
## 价值 
     可以指定目录加载class文件，并通过反射，调用其各种方法以及属性
     
     
    