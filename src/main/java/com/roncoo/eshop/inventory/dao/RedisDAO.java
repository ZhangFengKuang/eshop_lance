package com.roncoo.eshop.inventory.dao;

import com.roncoo.eshop.inventory.model.ProductInventory; /**
 * Date: 2018/10/25
 * Author: Lance
 */
public interface RedisDAO {
    public void set(String key, String value);

    public String get(String key);

    void removeProductInventoryCache(String productInventoryId);
}
