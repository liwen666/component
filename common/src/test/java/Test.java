import com.alibaba.fastjson.JSON;
import com.temp.common.common.entry.MerchantFullDto;
import com.temp.common.common.entry.MerchantPaymentChoiceDto;
import com.temp.common.common.util.SqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author lw
 * date 2019/8/29  10:43
 * discribe
 */
public class Test {
    public static void main(String[] args) {
        ArrayList ls = new ArrayList();
        System.out.println(ls.isEmpty());

        System.out.println(JSON.parseArray(null, SqlUtil.class));


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list= list.stream().filter(e->e!=1).collect(Collectors.toList());
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
}
