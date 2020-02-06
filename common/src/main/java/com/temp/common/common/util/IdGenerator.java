package com.temp.common.common.util;


import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * activiti主键生成策略工具
 *
 * @author xinre
 */
public class IdGenerator {
    public static void main(String[] args) {
        IdGenerator pkid = new IdGenerator();
        long l = System.currentTimeMillis();
        System.out.println(pkid.nextId());
        System.out.println(IdGenerator.getNext());
    }

    /**
     * 基准时间戳（确定后不可变动）
     */
//    private static final long TWEPOCH = 1213000000L;
    private static final long TWEPOCH = 591984000000L;
    /**
     * 机器标识位数
     */
    private final long workerIdBits = 5L;
    /**
     * 数据中心标识位数
     */
    private final long datacenterIdBits = 5L;
    /**
     * 毫秒内自增位
     */
    private final long sequenceBits = 12L;
    /**
     * 机器ID最大值
     */
    private final long maxWorkerId = ~(-1L << workerIdBits);
    /**
     * 数据中心ID最大值
     */
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);

    private final long sequenceMask = ~(-1L << sequenceBits);
    /**
     * 上次生产id时间戳
     */
    private long lastTimestamp = -1L;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;
    /**
     * 机器标识部分
     */
    private final long workerId;
    /**
     * 数据标识id部分
     */
    private final long datacenterId;

    public IdGenerator() {
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public IdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public String getNextId() {
        //return UUID.randomUUID().toString().replace("-", "");
        return String.valueOf(nextId());
    }

    /**
     * 获取下一个ID
     *
     * @return long类型id值
     */
    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {

            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 机器ID偏左移12位
        long workerIdShift = sequenceBits;
        // 数据中心ID左移17位
        long datacenterIdShift = sequenceBits + workerIdBits;
        // 时间毫秒左移22位
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - TWEPOCH) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    public static IdGenerator getPrimaryKeyGeneratorByUuid() {
        return IdGenerator.PrimaryKeyGeneratorByUuidHolder.primaryKeyGeneratorByUuid;
    }

    public static class PrimaryKeyGeneratorByUuidHolder {
        private static IdGenerator primaryKeyGeneratorByUuid = new IdGenerator();
    }

    public static String getNext() {
        return String.valueOf(getPrimaryKeyGeneratorByUuid().nextId());
    }
}
