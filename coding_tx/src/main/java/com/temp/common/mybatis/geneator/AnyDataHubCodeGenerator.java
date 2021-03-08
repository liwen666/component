package com.temp.common.mybatis.geneator;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mybatis-plus 代码生成
 *
 * @author 乐天
 * @since 2018-04-10
 */
@Slf4j
public class AnyDataHubCodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 是否覆盖已有文件
        gc.setFileOverride(false);
        String projectPath = System.getProperty("user.dir");
//        String projectPath = "D:\\component\\component\\coding_tx";
        gc.setOutputDir(projectPath + "/coding_tx/src/main/java");
        gc.setAuthor("lw");
        gc.setOpen(false);

        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
//        gc.setControllerName("SSSSScontroller");//设置生成的controller名称

        mpg.setGlobalConfig(gc);



        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();


//		 //配置自定义输出模板
        // 不需要其他的类型时，直接设置为null就不会成对应的模版了
        //templateConfig.setEntity("...");
//        templateConfig.setService(null);
//        templateConfig.setController(null);
//        templateConfig.setServiceImpl(null);
// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也
        // 可以自定义模板名称 只要放到目录下，名字不变 就会采用这个模版 下面这句有没有无所谓
        // 模版去github上看地址：
        /**https://github.com/baomidou/mybatis-plus/tree/3.0/mybatis-plus-generator/src/main/resources/templates*/
        //templateConfig.setEntity("/templates/entity.java");
//                templateConfig.setXml(null);// 是否在mapper下生成xml文件
        mpg.setTemplate(templateConfig);


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://192.168.42.136:3306/data_flow_test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setUrl("jdbc:mysql://11.11.1.79:3306/any_data_hub2.0?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName("hub");
        pc.setParent("jrx.data");
        //修改包结构
        pc.setEntity("infrastructure.model");
        pc.setService("domain.service");
        pc.setController("application.controller");
        pc.setServiceImpl("domain.service.impl");
        pc.setMapper("infrastructure.dao");
        pc.setXml("infrastructure.dao.mapper");

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing

                ConfigBuilder config = this.getConfig();
                TemplateConfig template = config.getTemplate();
//                template.setEntity("D:\\component\\component\\coding_tx\\src\\main\\java\\com\\temp\\common\\mybatis\\temp.java");
                //设置自定义模板
                templateConfig.setEntity("com/temp/common/mybatis/template/entity.java");
                templateConfig.setXml("com/temp/common/mybatis/template/mapper.xml");
                templateConfig.setController("com/temp/common/mybatis/template/controller.java");

            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/coding_tx/src/main/resources/mapperaaa/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);  //不设置表示加上@controller
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        strategy.setInclude(scanner("表名"));
//        strategy.setInclude("mc_user","meta_category","meta_data_object","meta_data_object_info","meta_data_source_info","meta_function","meta_function_info","meta_job_info","meta_job_object","meta_object_field","meta_work_info","meta_relation_info");
//        strategy.setInclude("meta_tenant");
        strategy.setInclude("debezium_analysis_version","debezium_binlog_analysis_info");
//        strategy.setExclude(..);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());
        mpg.execute();
    }
}
