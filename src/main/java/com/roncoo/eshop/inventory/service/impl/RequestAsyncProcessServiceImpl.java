package com.roncoo.eshop.inventory.service.impl;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Date: 2018/10/28
 * Author: Lance
 * Class action:
 */
@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) {
        try {
            //ArrayBlockingQueue是多线程并发安全的
            //做请求路由，根据库存id将请求路由到对应的内存队列中
            ArrayBlockingQueue<Request> blockingQueue = getBlockingQueue(request.getProductId());
            //将请求放入对应的内存队列中
            blockingQueue.put(request);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    /**
     * 将请求路由到对应的队列中去
     * @param productId 商品id
     * @return 对应的内存队列中
     */
    private ArrayBlockingQueue<Request> getBlockingQueue(Integer productId) {
        RequestQueue queue = RequestQueue.getInstance();
        // 先获取productId的hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定路由到同样的一个内存队列中去的
        int index = (queue.queueSize() - 1) & hash;
        return queue.getQueue(index);
    }
}
