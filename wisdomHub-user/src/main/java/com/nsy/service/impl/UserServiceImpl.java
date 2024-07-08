package com.nsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.model.pojo.User;
import com.nsy.model.vo.UserInfoVO;
import com.nsy.service.UserService;
import com.nsy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-07 15:42:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInfoVO login(String account, String password) {
        UserInfoVO userInfoVO = userMapper.login(account,password);

        return userInfoVO;
    }
}




