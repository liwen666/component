import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import com.temp.common.common.entry.MerchantFullDto;
import com.temp.common.common.entry.MerchantPaymentChoiceDto;
import com.temp.common.common.util.DateUtils;
import com.temp.common.common.util.SqlUtil;

import java.util.*;
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
        while (true) {
            if (executorService.isTerminated()) {
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

    @org.junit.Test
    public void arrayTest() {
        String[] aaa = new String[0];
        aaa[0] = "ddd";
        System.out.println(aaa[0]);

    }


    @org.junit.Test
    public void testCaseFormat() {
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));
    }


    @org.junit.Test
    public void datefinal() {
        Date parse = DateUtils.parse("9999-12-10");
        System.out.println(parse.getTime());

    }


    @org.junit.Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        Set s = new HashSet();
        s.add("aaa");
        System.out.println(list.containsAll(s));

    }
}
