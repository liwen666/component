package com.temp.common.base.excel.table;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.data.Json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NomalTableModel {
    private String id = "序号";
    private String name = "姓名";
    private String gender = null;
    private String idcard = "身份证号";
    private String leave = "年级";
    private String stuclass = "班级";
    private String status = "身体状况";
    private String schoole ;
    private String advince = "学校意见";
    private String check = "审核人";
    private String address;
    private String mobile;

    public static void main(String[] args) throws IllegalAccessException {

        NomalTableModel nomalTableModel = new NomalTableModel();
        Field[] declaredFields = nomalTableModel.getClass().getDeclaredFields();
        List<String> data = new ArrayList<>();
        for (Field f : declaredFields) {
            if (f.getName().equals("log")) {
                continue;
            }
            f.setAccessible(true);
            String o = (String) f.get(nomalTableModel);
            if(null!=o){
                data.add(o);
            }

        }
        System.out.println(JSON.toJSONString(data.toArray()));
    }
}
