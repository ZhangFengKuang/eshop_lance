package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

/**
 * Date: 2018/10/27
 * Author: Lance
 * Class action: 读取数据库数据 写入缓存redis
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    /**
     * 商品库存id
     */
    private Integer productInventoryId;
    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh;
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productInventoryId, ProductInventoryService productInventoryService,boolean forceRefresh) {
        this.productInventoryId = productInventoryId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public Integer getProductId() {
        return productInventoryId;
    }

    @Override
    public Boolean isForceRefresh() {
        return forceRefresh;
    }

    @Override
    public void process() {
        //查询db中的库存信息
        ProductInventory productInventory = productInventoryService.findProductInventory(productInventoryId);
        //更新redis缓存
        productInventoryService.setProductInventoryCache(productInventory);
    }
}
