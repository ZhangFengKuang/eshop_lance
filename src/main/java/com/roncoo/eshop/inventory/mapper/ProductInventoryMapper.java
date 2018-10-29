package com.roncoo.eshop.inventory.mapper;

import com.roncoo.eshop.inventory.model.ProductInventory; /**
 * Date: 2018/10/27
 * Author: Lance
 * Class action:操作商品库存在db数据的mapper
 */
public interface ProductInventoryMapper {

    void updateProductInventory(ProductInventory productInventory);

    ProductInventory findProductInventory(Integer productInventoryId);
}
