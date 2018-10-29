package com.roncoo.eshop.inventory.controller;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.roncoo.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import com.roncoo.eshop.inventory.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Date: 2018/10/28
 * Author: Lance
 * Class action:商品库存Controller
 */
@Controller("productInventoryController")
public class ProductInventoryController {

    @Resource
    private ProductInventoryService productInventoryService;
    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;
    /**
     * 更新商品库存
     * @param productInventory 商品库存信息
     * @return 响应信息
     */
    @RequestMapping("/updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        Response response;
        try {
            //获得请求操作服务
            Request request = new ProductInventoryDBUpdateRequest(productInventory,productInventoryService);
            //将其放入队列路由,路由到对应的内存队列中去执行操作
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存
     * @return 商品库存
     */
    @RequestMapping("/getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId){
        try {
            //将需要的参数和调用服务的类给封装好的请求类  然后可以实现读写 删改 分离操作
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService,false);
            requestAsyncProcessService.process(request);//将其丢入内存队列
            //将请求扔给service异步去处理后，这里要while(true) 一会 可能会hang个20ms
            //去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true) {
                if(waitTime > 200) //一个线程操作基本上只需要 20ms 如果等待时间大于200ms 就给他停掉
                    break;
                //尝试去redis中读取一次商品库存的缓存数据
                ProductInventory productInventory = productInventoryService.findProductInventory(productId);
                //如果读到了结果就直接返回
                if(productInventory != null) {
                    return productInventory;
                } else {
                    //如果没有请求到数据 线程睡20ms 这种就是可能前面有个线程在进行更新到数据库操作，20ms之后可能会有数据库
                    Thread.sleep(20);
                    //过去了多长时间 40 - 20 = 20 || 60 - 20 = 40
                    endTime = System.currentTimeMillis() - startTime;
                    //等待了 20 - 20 = 0  || 40 - 20 = 20  > 200 就等了十次请求时间
                    waitTime = endTime - startTime;
                }
            }
            //如果超过了 200ms 没有请求到数据 就直接请求db
            ProductInventory productInventory = productInventoryService.findProductInventory(productId);
            if(productInventory != null) {
                // 这个过程，实际上是一个读操作的过程，但是没有放在队列中串行去处理，还是有数据不一致的问题
                // return productInventory;

                //这里就是直接将这个请求打入队列中 不进入 if(!forceRefresh){} 直接执行process 去更新redis库存的最新数据，与db一致
                request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, true);//如果redis中没有查询出来，就去mysql中查找，如果这个商品在redis中没有更新操作，这个商品就一直为null
                requestAsyncProcessService.process(request);//
                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果都没获取到 就返回个空的库存
        return new ProductInventory(productId,-1L);
    }
}
