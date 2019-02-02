package com.temp.common.mybatis.geneator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Map;

public class MyFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = super.getObjectMap(tableInfo);
        objectMap.put("自定义参数","aaaaaaaaaaaaaaaaaaaaa");
        return objectMap;
    }
}
