package vip.dcpay.validate.sdk.sender;

import org.apache.commons.lang3.StringUtils;
import vip.dcpay.validate.sdk.bean.record.SendRecord;
import vip.dcpay.validate.sdk.util.DateUtil;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Auther: liq
 * @Date: 2019/6/19 14:25
 * @Description:
 */
public class AdapterSendRecordCollection {

    /**
     * 发送记录缓存
     */
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<SendRecord>> sendRecordMap = new ConcurrentHashMap<>();

    /**
     * 发送间隔
     */
    private int INTERVAL = 30;

    /**
     * 当日最大发送条数
     */
    private int MAX_NUM_ONEDAY = 100;

    private byte[] saveLock = new byte[0];

    private static final String PATTERN = "yyyy-MM-dd";

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 获取 receiver 的最后一条发送记录
     *
     * @param receiver
     * @return
     */
    public SendRecord last(String receiver) {
        if (StringUtils.isBlank(receiver)) {
            return null;
        }

        ConcurrentLinkedDeque<SendRecord> deque = sendRecordMap.get(receiver);
        if (null == deque) {
            return null;
        }

        return deque.peekLast();
    }

    /**
     * 保存发送记录
     *
     * @param record
     * @return
     */
    public boolean add(SendRecord record) {
        if (null == record) {
            return false;
        }

        ConcurrentLinkedDeque<SendRecord> deque = sendRecordMap.get(record.getReceiver());
        if (null == deque) {
            synchronized (saveLock) {
                deque = sendRecordMap.get(record.getReceiver());
                if (null == deque) {
                    ConcurrentLinkedDeque<SendRecord> newDeque = new ConcurrentLinkedDeque<>();
                    sendRecordMap.put(record.getReceiver(), newDeque);

                    deque = sendRecordMap.get(record.getReceiver());
                }
            }
        }

        return deque.offer(record);
    }

    /**
     * 获取 receiver 发送记录数量（并发情况下，其结果不一定是准确的，只能供参考）
     *
     * @param receiver
     * @return
     */
    public int size(String receiver) {
        if (StringUtils.isBlank(receiver)) {
            return 0;
        }

        ConcurrentLinkedDeque<SendRecord> deque = sendRecordMap.get(receiver);
        if (null == deque) {
            return 0;
        }

        return deque.size();
    }

    /**
     * 清空全部发送记录
     *
     * @return
     */
    public boolean clean() {
        sendRecordMap = new ConcurrentHashMap<>();

        return true;
    }

    /**
     * 清空 receiver 的发送记录
     *
     * @return
     */
    public boolean clean(String receiver) {
        if (StringUtils.isBlank(receiver)) {
            return true;
        }

        ConcurrentLinkedDeque<SendRecord> deque = sendRecordMap.get(receiver);
        if (null == deque) {
            return true;
        }

        sendRecordMap.remove(receiver);

        return true;
    }

    /**
     * 是否发送太频繁
     *
     * @return
     */
    public boolean checkSendFrequency(String receiver) {
        // 获取最后一条发送记录
        SendRecord record = last(receiver);
        if (null == record) {
            return false;
        }

        Date curDate = new Date();
        long intervalTime = curDate.getTime() - record.getCreateTime().getTime() - INTERVAL * 1000;

        return intervalTime < 0;
    }

    /**
     * 是否超出当日允许发送条数
     *
     * @param receiver
     * @return
     */
    public boolean checkOnedayMaxNum(String receiver) {
        // 清理非当日发送记录
        cleanOnedayRecord(receiver);

        // 获取当日发送记录条数
        int size = size(receiver);

        return size > MAX_NUM_ONEDAY;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    /**
     * 清理非当日发送记录
     *
     * @param receiver
     */
    private void cleanOnedayRecord(String receiver) {
        if (StringUtils.isBlank(receiver)) {
            return;
        }

        ConcurrentLinkedDeque<SendRecord> deque = sendRecordMap.get(receiver);
        if (null == deque) {
            return;
        }

        String curDate = DateUtil.dateToString(new Date(), PATTERN);

        SendRecord record;
        String recordDate;
        while (!deque.isEmpty()) {
            record = deque.peekFirst();
            if (null == record) {
                break;
            }

            recordDate = DateUtil.dateToString(record.getCreateTime(), PATTERN);
            if (!curDate.equals(recordDate)) {
                // 日期非当日，记录丢弃
                deque.pollFirst();
            } else {
                // 队列中，发现一条当日记录，后续不用检查
                break;
            }
        }
    }

}
