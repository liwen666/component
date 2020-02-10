package com.temp.common.base.excel.table;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
@Data
@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TableModel {
    private String[] firtLine = {"教育系统新冠肺炎疫情防控期学生信息登记表"};
    private String[] secoundLine = {"学校", "一小", "年级：四年级", "班级：一班", "填表日期： 2020年 2   月   9 日"};
    private String[] thridLine = {"姓名", "性别", "身份证号", "家庭住址", "联系电话", "学校意见与建议："};

    private String[] line4 = {"冯灿", "女", "411526200910070185", "彭畈村大梓树组", "1569198173"};
    private String[] line5 = {"本人近期身体状况","", "正常","","", "负责人\t\n签  字："};
    private String[] line6 = {"本人近十四天活动轨迹","", "居家","",""};
    private String[] line7 = {"居家其他亲属","", "正常","",""};
    private String[] line8 = {"居家其他亲属近期身体状况","", "正常","","", "2020年    月    日"};
    private String[] line9 = {"（此表全校学生一人一份）"};
    private String[] line10 = {"填表人：", "冯灿", "本人（家长）签字：", "审核人："};
    private String[] line11 = {"说明：1、“近十四天活动轨迹”填2月1日后生活的地点和走过的地方；"};
    private String[] line12 = {"      2、“身体状况”填有无确诊病例、疑似病例、发热或正常等情况；"};
    private String[] line13 = {"      3、“学校意见与建议”填可以入班、在家隔离、到医院就诊等。"};

    public static void main(String[] args) {
        Field[] declaredFields = TableModel.class.getDeclaredFields();
        System.out.println(declaredFields.length);
        int line = declaredFields.length;
        for (Field f : declaredFields) {
            if (f.getName().equals("log")) {
                line -= 1;
                continue;
            }
            System.out.println(f.getName());

        }
        System.out.println(line);
    }
}
