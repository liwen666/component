线程的创建和销毁占用的资源太多
			1.线程的复用性，提高系统性能
***********************************************
       I  Executor   
       I  ExecutorService
       I	 ScheduledExecutorService
       A  AbstractExecutorService
       C  ThreadPoolExecutor
       C  Executors
***************************************************			
                  商用线程池
              Executors  使用工厂方法创建    不同的 ThreadPoolExecutor
              由构造器的参数决定是什么样的线程池
              
              public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      /**
                                      *固定大小的线程池当任务被排满后会将任务放到  阻塞队列里面
                                      */
                                      
                                      new LinkedBlockingQueue<Runnable>());
    }
              
               public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        							线程会保持60秒      线程最少为0个线程
                                      60L, TimeUnit.SECONDS,
                                      一个同步队列  这个队列并不会真正的将任务放到里面保存 
                                      	  而是起到交换的作用    一个线程放数据一个个线程同时取数据
                                      new SynchronousQueue<Runnable>());
    }
    
    
    ***********************************************************************
    
              