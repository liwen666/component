package com.temp.common.base.kafka_partition;

import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class RowIdentity implements Serializable {
	private final String database;
	private final String table;
	private final String rowType;
	private final List<Pair<String, Object>> primaryKeyColumns;

	public RowIdentity(String database, String table, String rowType, List<Pair<String, Object>> primaryKeyColumns) {
		this.database = database;
		this.table = table;
		this.primaryKeyColumns = primaryKeyColumns;
		this.rowType = rowType;
	}

	public String getDatabase() {
		return database;
	}

	public String getTable() {
		return table;
	}

	public String toKeyJson(RowMap.KeyFormat keyFormat) throws IOException {
		return null;

	}

	public String toConcatString() {
		// Generates a concise, lossy representation of this identity (i.e you can't easily parse it).
		// Simpler than generating real JSON, used as a hash input for partition calculation.
		if (primaryKeyColumns.isEmpty()) {
			return database + table;
		}
		StringBuilder keys = new StringBuilder();
		for (Map.Entry<String, Object> pk : primaryKeyColumns) {
			Object pkValue = pk.getValue();
			if (pkValue != null) {
				keys.append(pkValue.toString());
			}
		}
		if (keys.length() == 0) {
			return "None";
		}
		return keys.toString();
	}

	@Override
	public String toString() {
		return database + ":" + table + ":" + primaryKeyColumns;
	}

}
