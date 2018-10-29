package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.request.Request;

/**
 * Date: 2018/10/28
 * Author: Lance
 * Class action:内存队列
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
