package com.roncoo.eshop.inventory.mapper;

import com.roncoo.eshop.inventory.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Date: 2018/10/25
 * Author: Lance
 */
public interface UserMapper {

    //@Select("select userName,mobile from user")
    public User queryUserInfo();

}
