package test;

import org.apache.flink.table.functions.ScalarFunction;
import com.riveretech.est.common.utils.JsonUtils;

import java.util.Map;


public class GetSourceMetaFunction extends ScalarFunction {
    public String eval(String debeziumJson,String key ) {
        return (String) ((Map)JsonUtils.parseObject(debeziumJson, Map.class).get("source")).get(key);
    }
}