package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.roncoo.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * Date: 2018/10/26
 * Author: Lance
 * Class action:执行请求的工作
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                Request request = queue.take();//如果那边put进来一个线程，这边就可以接收到
                Boolean forceRefresh = request.isForceRefresh();//false
                if(!forceRefresh){//true 需要强制刷新
                    RequestQueue requestQueue = RequestQueue.getInstance();//获取到一个内存队列
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();//标识位
                    if(request instanceof ProductInventoryDBUpdateRequest) {//如果是写请求
                        flagMap.put(request.getProductId(), true);//写请求就是true
                    } else if(request instanceof ProductInventoryCacheRefreshRequest) {//如果是读请求
                        Boolean flag = flagMap.get(request.getProductId());
                        if(flag == null) {//如果flag是空 就是前面没有读请求也没有写请求
                            flagMap.put(request.getProductId(), false);//标识有个读请求
                        }
                        if(flag != null && flag) {//说明前面有个更新请求
                            flagMap.put(request.getProductId(), false);
                        }
                        if(flag != null && !flag) {//说明前面有个读请求
                            return true; //排除重复读请求进行优化
                        }
                    }
                }
                //执行request的process方法
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
