package com.temp.common.base.kafka_partition;

import com.fasterxml.jackson.core.JsonGenerator;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class RowMap implements Serializable {


	public enum KeyFormat { HASH, ARRAY }

	static final Logger LOGGER = LoggerFactory.getLogger(RowMap.class);

	private String rowQuery;
	private final String rowType;
	private final String database;
	private final String table;
	private final Long timestampMillis;
	private final Long timestampSeconds;
	private final Position position;
	private Position nextPosition;
	private String kafkaTopic;
	private String partitionString;
	protected boolean suppressed;
	private String comment;

	private Long xid;
	private Long xoffset;
	private boolean txCommit;
	private Long serverId;
	private Long threadId;
	private Long schemaId;

	private final LinkedHashMap<String, Object> data;
	private final LinkedHashMap<String, Object> oldData;

	private final LinkedHashMap<String, Object> extraAttributes;

	private final List<String> pkColumns;
	private RowIdentity rowIdentity;

	private long approximateSize;

	public RowMap(String type, String database, String table, Long timestampMillis, List<String> pkColumns,
			Position position, Position nextPosition, String rowQuery) {
		this.rowQuery = rowQuery;
		this.rowType = type;
		this.database = database;
		this.table = table;
		this.timestampMillis = timestampMillis;
		this.timestampSeconds = timestampMillis / 1000;
		this.data = new LinkedHashMap<>();
		this.oldData = new LinkedHashMap<>();
		this.extraAttributes = new LinkedHashMap<>();
		this.position = position;
		this.nextPosition = nextPosition;
		this.pkColumns = pkColumns;
		this.suppressed = false;
		this.approximateSize = 100L; // more or less 100 bytes of overhead
	}

	public RowMap(String type, String database, String table, Long timestampMillis, List<String> pkColumns,
				  Position nextPosition, String rowQuery) {
		this(type, database, table, timestampMillis, pkColumns, nextPosition, nextPosition, rowQuery);
	}

	public RowMap(String type, String database, String table, Long timestampMillis, List<String> pkColumns,
				  Position nextPosition) {
		this(type, database, table, timestampMillis, pkColumns, nextPosition, null);
	}

	public RowIdentity getRowIdentity() {
		if (rowIdentity == null) {
			List<Pair<String, Object>> entries = new ArrayList<>(pkColumns.size());
			for (String pk: pkColumns) {
				entries.add(Pair.of(pk, data.get(pk)));
			}
			rowIdentity = new RowIdentity(database, table, rowType, entries);
		}

		return rowIdentity;
	}

	public String buildPartitionKey(List<String> partitionColumns) {
		StringBuilder partitionKey= new StringBuilder();
		for (String pc : partitionColumns) {
			Object pcValue = null;
			if (data.containsKey(pc)) {
				pcValue = data.get(pc);
			}
			if (pcValue != null) {
				partitionKey.append(pcValue.toString());
			}
		}

		return partitionKey.toString();
	}

	private void writeMapToJSON(
			String jsonMapName,
			LinkedHashMap<String, Object> data,
			JsonGenerator g,
			boolean includeNullField
	) throws IOException, NoSuchAlgorithmException {
		g.writeObjectFieldStart(jsonMapName);

		for (String key : data.keySet()) {
			Object value = data.get(key);

		}

		g.writeEndObject(); // end of 'jsonMapName: { }'
	}

}
