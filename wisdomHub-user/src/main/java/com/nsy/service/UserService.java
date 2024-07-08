package com.nsy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.pojo.User;
import com.nsy.model.vo.UserInfoVO;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-07 15:42:39
*/
@Service
public interface UserService extends IService<User> {

    UserInfoVO login(String account, String password);
}
