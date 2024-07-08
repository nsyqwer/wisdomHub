package com.nsy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsy.model.pojo.User;
import com.nsy.model.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

/**
* @author 宁舒意
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-07-07 15:42:39
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select user.id,r.binding_id,user.*,r.identity from user " +
            "join role r on user.id = r.user_id " +
            "where account=#{account} and password =#{password}")
    @Result(property = "roleId", column = "binding_id")
    UserInfoVO login(String account,String password);

}




