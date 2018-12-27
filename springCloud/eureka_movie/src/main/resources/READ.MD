#访问movie  服务地址
     http://localhost:8083/movie/1
     
#通过controller调用user微服务 
     http://localhost:8083/router
     
     访问controller 后台返回
     String json = tpl.getForObject("http://microservice-provider-user/hello/1", String.class);
     microservice-provider-user这个就是微服务的注册名