package com.temp.common.base.singleton;

public class PersionService {
    private Integer id=1;
    private PersionService() {
        System.out.println(100);
    }
    {
        System.out.println("2");//会先于构造方法执行
    }
    public static PersionService  getPersionService(){
        return PersionServiceHolder.persionService;
    }

    public static class PersionServiceHolder {
        {
            System.out.println("创建单例");//不会执行
        }
        private static PersionService persionService = new PersionService();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
