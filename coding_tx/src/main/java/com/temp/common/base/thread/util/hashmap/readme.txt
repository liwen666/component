高并发容器
	1  ConcurrentHashMap

Collection  中hashmap  list  set

集合不是线程安全的   collections类中提供的有方法是  这些集合变成线程安全的  但是性能不高 适合低并发量

java.util.concurrent  包中有 并发性能高的map   
concurrentHashMap   内部的实现 是多线程  访问被切割的小的hashMap 保证数据的正确级并发量



hashmap  相当于一个数组
一个hashmap 有不同的entry  储存key  和  value
当两个不同的key 指向同一个entry 时   可以将他们用  entry<k,v>next   来包装区分

trylock  是的使用可以放在while循环中    给定条件  如果失败可以使用lock（）   break结束循环

	2. BlockingQueue 阻塞队列
	
	2. ConcurrentLinkedQueue 高并发队列

