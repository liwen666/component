package com.temp.common.base.singleton.base;

public class PersionService {
    private  String name = "a";
   private static PersionService persionService;
    private PersionService() {
    }
    public static PersionService getInstance() {
        if (persionService == null) {
            synchronized (PersionService.class){
                if(persionService == null){
                    persionService = new PersionService();
                }
            }
        }
        return persionService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
