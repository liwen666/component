package com.temp.common.base.JDBC.jrx.jrx_bi_demo.est_interface;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public enum CityEnum {

    BEIJING("北京市", "11"),
    TIANJIN("天津市", "12"),
    HEBEI("河北省", "13"),
    SHANGXI("山西省", "14"),
    NEIMENGGU("内蒙古自治区", "15"),
    LIAOLIN("辽宁省", "21"),
    JILIN("吉林省", "22"),
    HEILONGJIAN("黑龙江省", "23"),
    SHANGHAI("上海市", "31"),
    JIANGSHU("江苏省", "32"),
    ZHEJIAN("浙江省", "33"),
    ANHUI("安徽省", "34"),
    FUJIAN("福建省", "35"),
    JIANGXI("江西省", "36"),
    SHANDONG("山东省", "37"),
    HENAN("河南省", "41"),
    HUBEI("湖北省", "42"),
    HUNAN("湖南省", "43"),
    GUANGDONG("广东省", "44"),
    GUANGXIZHUANGZHU("广西壮族自治区", "45"),
    HAINAN("海南省", "46"),
    CHONGQING("重庆市", "50"),
    SHICHUAN("四川省", "51"),
    GUIZHOU("贵州省", "52"),
    YUNNAN("云南省", "53"),
    XIZHANGZHIZHIQU("西藏自治区", "54"),
    SANXISHENG("陕西省", "61"),
    GANXU("甘肃省", "62"),
    QINHAI("青海省", "63"),
    NINGXIA("宁夏回族自治区", "64"),
    XINGJIANG("新疆维吾尔自治区", "65"),
    TAIWAN("台湾省", "71"),
    XIANGGUANG("香港", "81"),
    AOMENG("澳门", "82");

    CityEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String code;

    public String getCode() {
        return code;
    }


    public static void main(String[] args) {
        String name = CityEnum.SANXISHENG.getName();
        System.out.println(name);
        CityEnum[] values = CityEnum.values();
    }
    public void setCode(String code) {
        this.code = code;
    }}
