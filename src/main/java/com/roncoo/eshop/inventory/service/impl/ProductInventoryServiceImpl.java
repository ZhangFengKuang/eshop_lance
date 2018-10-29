package com.roncoo.eshop.inventory.service.impl;

import com.roncoo.eshop.inventory.dao.RedisDAO;
import com.roncoo.eshop.inventory.mapper.ProductInventoryMapper;
import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Date: 2018/10/27
 * Author: Lance
 * Class action:操作库存的service实现类
 */
@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private RedisDAO redisDAO;
    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String productInventoryId = "product:inventory:"+productInventory.getProductId();
        redisDAO.removeProductInventoryCache(productInventoryId);
    }

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public ProductInventory findProductInventory(Integer productInventoryId) {
        return productInventoryMapper.findProductInventory(productInventoryId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.set(key, String.valueOf(productInventory.getInventoryCnt()));
    }
}
