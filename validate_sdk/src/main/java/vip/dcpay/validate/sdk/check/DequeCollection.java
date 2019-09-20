package vip.dcpay.validate.sdk.check;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Auther: liq
 * @Date: 2019/6/19 11:03
 * @Description:
 */
public class DequeCollection<T> {

    protected ConcurrentLinkedDeque<T> recordList = new ConcurrentLinkedDeque<>();

    /**
     * 获取最后一条缓存数据
     *
     * @return
     */
    public T last() {
        // 检索但不删除此deque的最后一个元素，如果此deque为空，则返回 null
        return recordList.peekLast();
    }

    /**
     * 保存缓存数据
     *
     * @param record
     * @return
     */
    public boolean add(T record) {
        // deque尾部插入数据
        return recordList.offer(record);
    }

    /**
     * 获取缓存记录数量（并发情况下，其结果不一定是准确的，只能供参考）
     *
     * @return
     */
    public int size() {
        return recordList.size();
    }

}
