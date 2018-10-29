package com.roncoo.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.inventory.dao.RedisDAO;
import com.roncoo.eshop.inventory.mapper.UserMapper;
import com.roncoo.eshop.inventory.model.User;
import com.roncoo.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Date: 2018/10/25
 * Author: Lance
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource(name = "redisDAO")
    RedisDAO redisDAO;

    @Override
    public User queryUserInfo() {
        return userMapper.queryUserInfo();
    }

    @Override
    public User queryCachedUserInfo() {
        redisDAO.set("cached_user", "{\"name\": \"xxxboy\", \"mobile\": 1783123213123}");

        String json = redisDAO.get("cached_user");
        JSONObject jsonObject = JSONObject.parseObject(json);

        User user = new User();
        user.setMobile(jsonObject.getString("mobile"));
        user.setName(jsonObject.getString("name"));
        return user;
    }
}
