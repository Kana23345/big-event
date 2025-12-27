package com.myproject.service;

import com.myproject.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {
    //查询用户
     User findByUserName(String   username);

    //注册
    void register(String username, String password);

    void update(User user);

    void updateAvatar(@URL String avatarUrl);

    void updatePwd(String newPwd);
}
