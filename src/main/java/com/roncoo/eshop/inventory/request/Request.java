package com.roncoo.eshop.inventory.request;

/**
 * Date: 2018/10/26
 * Author: Lance
 * Class action:请求接口
 */
public interface Request {
    /**
     * 获取商品id
     * @return 商品id
     */
    Integer getProductId();
    /**
     * 是否强制刷新
     * @return true or false  强制 | 不强制
     */
    Boolean isForceRefresh();
    /**
     * 执行业务代码
     */
    void process();
}
