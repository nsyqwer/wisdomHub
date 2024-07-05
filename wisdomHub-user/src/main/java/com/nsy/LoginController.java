package com.nsy;

import com.nsy.model.BaseResult;
import com.nsy.model.vo.UserInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: LoginController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/19 14:38
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public BaseResult<UserInfoVO> Login(@RequestParam String account,@RequestParam String password){
        UserInfoVO userInfoVO =new UserInfoVO();
        return new BaseResult<>(200,"登录成功",userInfoVO);
    }
}
