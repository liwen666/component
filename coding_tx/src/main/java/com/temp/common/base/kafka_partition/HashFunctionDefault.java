package com.temp.common.base.kafka_partition;

/**
 * Created by kaufmannkr on 1/18/16.
 */
public class HashFunctionDefault implements HashFunction {
	@Override
	public int hashCode(String s) {
		return s.hashCode();
	}
}
