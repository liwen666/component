package com.temp.common.base.nmr;

/**
 * Created by kaufmannkr on 1/18/16.
 */
public class HashFunctionDefault implements HashFunction {
	@Override
	public int hashCode(String s) {
		return s.hashCode();
	}
}
