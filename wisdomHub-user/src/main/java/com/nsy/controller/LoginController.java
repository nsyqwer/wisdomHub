package com.nsy.controller;

import com.nsy.model.BaseResult;
import com.nsy.model.vo.UserInfoVO;
import com.nsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: LoginController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/19 14:38
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public BaseResult<UserInfoVO> Login(@RequestParam String account,@RequestParam String password){
        UserInfoVO userInfoVO =userService.login(account,password);
        return new BaseResult<>(200,"登录成功",userInfoVO);
    }


}
