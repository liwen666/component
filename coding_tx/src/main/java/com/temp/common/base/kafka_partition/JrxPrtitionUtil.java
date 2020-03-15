package com.temp.common.base.kafka_partition;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class JrxPrtitionUtil {
    public static void main(String[] args) {
        MaxwellKafkaPartitioner maxwellKafkaPartitioner = new MaxwellKafkaPartitioner("murmur3","primary_key",null,PartitionBy.PRIMARY_KEY.name());

    }
}
