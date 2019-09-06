import com.alibaba.fastjson.JSON;
import com.temp.common.common.entry.MerchantFullDto;
import com.temp.common.common.entry.MerchantPaymentChoiceDto;
import com.temp.common.common.util.SqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * author lw
 * date 2019/8/29  10:43
 * discribe
 */
public class Test {
    final Semaphore semaphore = new Semaphore(10);
//    ExecutorService executorService = Executors.newFixedThreadPool(10);
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ArrayList ls = new ArrayList();
        System.out.println(ls.isEmpty());

        System.out.println(JSON.parseArray(null, SqlUtil.class));


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list = list.stream().filter(e -> e != 1).collect(Collectors.toList());
        System.out.println(list);
    }

    @org.junit.Test
    public void testStream() {
        MerchantFullDto merchantFullDto = new MerchantFullDto();
        merchantFullDto.setPaymentChoices(Arrays.asList(new MerchantPaymentChoiceDto()));
        List<String> collect = merchantFullDto.getPaymentChoices().stream().map(MerchantPaymentChoiceDto::getPayWay).collect(Collectors.toList());
        String s = JSON.toJSONString(null);
        System.out.println(collect.size());

    }

    @org.junit.Test
    public void name() {
        List<String> a = new ArrayList<>();
        a.add("aaaa");
        a.add("aaaa");
        System.out.println(JSON.toJSONString(a));
        System.out.println(JSON.toJSONString(new HashSet<>(a)));
        System.out.println(new HashSet<>(null));
    }

    @org.junit.Test
    public void threadpool() {
        long l = System.currentTimeMillis();


        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
//            add();
        }
        executorService.shutdown();
        while(true){
            if(executorService.isTerminated()){
                System.out.println("所有的子线程都结束了！");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(System.currentTimeMillis() - l);

    }

    private void add() {
        for (int j = 0; j < 3; j++) {
            try {
                System.out.println("=========================================================================================");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
