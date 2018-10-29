package com.roncoo.eshop.inventory.service;
import com.roncoo.eshop.inventory.model.User;

/**
 * Date: 2018/10/25
 * Author: Lance
 */
public interface UserService {

    public User queryUserInfo();

    public User queryCachedUserInfo();
}
