package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Date: 2018/10/26
 * Author: Lance
 * Class action: 请求处理线程池、单例
 */
public class RequestProcessorThreadPool {

    //获取一个线程池 设置线程池内线程数量
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    /**
     * 在构造函数中实例化想做的事情
     */
    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int x = 0; x < 10; x++){
            ArrayBlockingQueue<Request> queues = new ArrayBlockingQueue<>(100);//一个队列进入一百个请求
            requestQueue.addQueue(queues);
            threadPool.submit(new RequestProcessorThread(queues));//将线程丢入线程池
        }
    }

    /**
     * 静态内部类、单例
     */
    private static class Singleton {
        private static RequestProcessorThreadPool singleton;

        static {
            //静态代码块只会执行一次
            singleton = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getSingleton() {
            return singleton;
        }
    }

    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getSingleton();
    }

    /**
     * 初始化编写法
     */
    public static void init() {
        getInstance();
    }
}
