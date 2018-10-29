package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.model.ProductInventory;

/**
 * Date: 2018/10/27
 * Author: Lance
 * Class action:商品库存，缓存，db，操作service
 */
public interface ProductInventoryService {
    /**
     * 删除缓存的商品库存
     * @param productInventory 商品库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 修改db库存数量
     * @param productInventory 商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据库存id查询db中库存数据
     * @param productInventoryId 库存id
     * @return 根据id查询出来的库存
     */
    ProductInventory findProductInventory(Integer productInventoryId);

    /**
     * 给redis中添加值
     * @param productInventory 库存
     */
    void setProductInventoryCache(ProductInventory productInventory);
}
