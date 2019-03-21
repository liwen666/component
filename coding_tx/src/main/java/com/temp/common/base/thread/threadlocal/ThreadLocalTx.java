package com.temp.common.base.thread.threadlocal;

import lombok.Data;

public class ThreadLocalTx {
    public static void main(String[] args) throws InterruptedException {
        Pession ds = new DataDomin();
        ds.setName("wo");
        Thread th = new Thread(new BusinessPay(ds));
        Thread th2 = new Thread(new BusinessPay(ds));
        th.start();
        th2.start();
        th.join();
        th2.join();
        System.out.println(ds.getName()+"---"+ds.getMoney());
        //下面是ThreadLocal的结果
        DataDominLocal local = new DataDominLocal();
        local.setName("local");
        Thread th3 = new Thread(new BusinessPayLocalTx(local));
        Thread th4 = new Thread(new BusinessPayLocalTx(local));
        Thread th5 = new Thread(new BusinessPayLocalTx(local));
        th3.start();
        th4.start();
        th5.start();
        th3.join();
        th4.join();
        th5.join();
        System.out.println(local.getName()+"---"+((DataDominLocal)local).get());
        System.out.println(local.getName()+"---"+((DataDominLocal)local).getMoney());
    }
}
class DataDomin extends Pession{

}
@Data
class DataDominLocal extends Pession{

//    属性名称一样子类可以覆盖父类属性
//    private ThreadLocal<Integer>money = new ThreadLocal<>();
    private ThreadLocal<Integer>money2 = new ThreadLocal<Integer>(){
    @Override
    protected Integer initialValue() {
//        return 10;
        return super.initialValue();
    }
};
    public int get(){
        return money2.get();
    }
    public  void set(int num){

        money2.set(money2.get()==null?0:1+1);
    }

}
class BusinessPay implements Runnable{
    private Pession pession;

    public BusinessPay(Pession ds) {
        this.pession=ds;
    }

    @Override
    public void run() {
        for(int i=0;i<10000;i++){
            pession.setMoney(pession.getMoney()+1);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class BusinessPayLocalTx implements Runnable{
    private Pession pession;

    public BusinessPayLocalTx(DataDominLocal ds) {
        this.pession=ds;
    }

    @Override
    public void run() {
        for(int i=0;i<5000;i++){

            DataDominLocal pession = null;
            try {
                pession = (DataDominLocal) this.pession;
                pession.set(i);
                System.out.println(Thread.currentThread().getName()+"    "+pession.get());
                pession.setMoney(pession.getMoney()+1);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                pession.getMoney2().remove();
            }
            System.out.println(Thread.currentThread().getName()+"  ***  "+pession.getMoney());

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class TX{
    public static void main(String[] args) {
        Pession p = new Pession();
        int money = p.getMoney();
        money++;
        System.out.println(p.getMoney());
        Integer integer = p.getInteger();
        integer++;
        System.out.println(p.getInteger());
        DataDominLocal dl = new DataDominLocal();

    }
}