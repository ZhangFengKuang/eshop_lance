package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

/**
 * Date: 2018/10/27
 * Author: Lance
 * Class action:cache aside pattern 删除缓存 更新数据库
 */
public class ProductInventoryDBUpdateRequest implements Request{

    /**
     * 商品库存
     */
    private ProductInventory productInventory;

    /**
     * 商品库存服务
     */
    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public Boolean isForceRefresh() {
        return false;
    }

    @Override
    public void process() {
        //删除缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        //更新数据库
        productInventoryService.updateProductInventory(productInventory);
    }
}
