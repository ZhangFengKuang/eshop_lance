package com.roncoo.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 2018/10/26
 * Author: Lance
 * Class action:请求内存队列
 */
public class RequestQueue {
    /**
     * 内存队列
     * 队列集合
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

    public int queueSize() {
        return queues.size();
    }

    /**
     * 静态内部类(单例)
     */
    private static class Singleton {

        private static RequestQueue instance;

        static {
            //静态代码块只会执行一次
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }

    /**
     * Jvm的机制去保证多线程并发安全
     * <p>
     * 内部类的初始化，一定只会发生一次，不管多少个线程去并发初始化
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 添加一个内存队列到内存队列集合
     */
    public void addQueue(ArrayBlockingQueue queue) {
        this.queues.add(queue);
    }

    public ArrayBlockingQueue<Request> getQueue(Integer productId) {
        return this.queues.get(productId);
    }
}
