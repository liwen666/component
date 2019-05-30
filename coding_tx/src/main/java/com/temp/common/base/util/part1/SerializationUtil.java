package com.temp.common.base.util.part1;

import java.io.*;

public class SerializationUtil {
	public static Object clone(Serializable object) {
		return deserialize(serialize(object));
	}

	public static void serialize(Serializable obj, OutputStream outputStream) throws Exception {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(outputStream);
			out.writeObject(obj);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		try {
			serialize(obj, baos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	public static Object deserialize(InputStream inputStream) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(inputStream);
			return in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object deserialize(byte[] objectData) {
		if (objectData == null) {
			throw new IllegalArgumentException("The byte[] must not be null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
		return deserialize(bais);
	}
}