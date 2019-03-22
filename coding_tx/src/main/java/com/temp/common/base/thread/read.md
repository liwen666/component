# join 
  线程结束  才会继续进行主线程
  
  
  多线程情况下  HASHMAP  会有hash冲突 导致数据丢失问题
                         2.  hashmap还有死循环现象
                         
     多线程情况下使用 concurrentHashMap  可以避免以上两种问题   