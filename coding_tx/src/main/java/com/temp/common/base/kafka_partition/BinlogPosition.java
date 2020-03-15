package com.temp.common.base.kafka_partition;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BinlogPosition implements Serializable {
    static final Logger LOGGER = LoggerFactory.getLogger(BinlogPosition.class);

    private static final String FILE_COLUMN = "File";
    private static final String POSITION_COLUMN = "Position";
    private static final String GTID_COLUMN = "Executed_Gtid_Set";

    private final String gtidSetStr;
    private final String gtid;
    private final long offset;
    private final String file;

    public BinlogPosition(String gtidSetStr, String gtid, long l, String file) {
        this.gtidSetStr = gtidSetStr;
        this.gtid = gtid;
        this.offset = l;
        this.file = file;
    }

    public BinlogPosition(long l, String file) {
        this(null, null, l, file);
    }

    public static BinlogPosition capture(Connection c, boolean gtidMode) throws SQLException {
        ResultSet rs;
        rs = c.createStatement().executeQuery("SHOW MASTER STATUS");
        rs.next();
        long l = rs.getInt(POSITION_COLUMN);
        String file = rs.getString(FILE_COLUMN);
        String gtidSetStr = null;
        if (gtidMode) {
            gtidSetStr = rs.getString(GTID_COLUMN);
        }
        return new BinlogPosition(gtidSetStr, null, l, file);
    }

    public static BinlogPosition at(BinlogPosition position) {
        return new BinlogPosition(position.gtidSetStr, position.gtid, position.offset, position.file);
    }

    public static BinlogPosition at(String gtidSetStr, long offset, String file) {
        return new BinlogPosition(gtidSetStr, null, offset, file);
    }

    public static BinlogPosition at(long offset, String file) {
        return new BinlogPosition(null, null, offset, file);
    }

    public long getOffset() {
        return offset;
    }

    public String getFile() {
        return file;
    }

    public String getGtid() {
        return gtid;
    }

    public String getGtidSetStr() {
        return gtidSetStr;
    }


    @Override
    public String toString() {
        return "BinlogPosition["
                + (gtidSetStr == null ? file + ":" + offset : gtidSetStr)
                + "]";
    }

    public String fullPosition() {
        String pos = file + ":" + offset;
        if (gtidSetStr != null) {
            pos += "[" + gtidSetStr + "]";
        }
        return pos;
    }

    public boolean newerThan(BinlogPosition other) {
        if (other == null) {
        }
        return false;
    }
}
