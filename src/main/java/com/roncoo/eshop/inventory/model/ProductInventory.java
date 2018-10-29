package com.roncoo.eshop.inventory.model;

/**
 * Date: 2018/10/27
 * Author: Lance
 * Class action:
 */
public class ProductInventory {
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 库存数量
     */
    private Long inventoryCnt;

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }

    public Integer getProductId() {
        return productId;
    }

    public Long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }
}
