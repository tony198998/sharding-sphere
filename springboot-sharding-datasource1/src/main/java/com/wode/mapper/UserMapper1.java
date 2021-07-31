package com.wode.mapper;

import com.wode.entity.User;

import java.util.List;

/**
 * @description:
 * @author: jitao
 * @createDate: 2021/7/28
 */
public interface UserMapper1 {

    Long addUser(User user);

    List<User> list();

    User findById(Long id);

    User findByName(String name);
}
