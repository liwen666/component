package com.temp.common.base.kafka_partition;


/**
 * Created by kaufmannkr on 1/18/16.
 */
public class HashFunctionMurmur3 implements HashFunction {
	private int seed;
	public HashFunctionMurmur3(int seed){
		this.seed = seed;
	}
	@Override
	public int hashCode(String s) {
		return MurmurHash3.murmurhash3_x86_32(s, 0, s.length(), seed);
	}


}
