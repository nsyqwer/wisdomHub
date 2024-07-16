package com.nsy.util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.HashMap;
import java.util.Map;


public class MybatisPlusUtil {

    public static Map<String,Object> getMap(Object ...object) {
        if(object.length%2==1) {
            throw new IllegalArgumentException();
        }else{
            Map<String,Object> mp = new HashMap<>();
            String key = null;
            for (int i = 0; i < object.length; i++) {
                if(i%2==0){
                    if(object[i] instanceof String){
                        key = (String) object[i];
                    }else{
                        throw new IllegalArgumentException();
                    }
                }else{
                    mp.put(key, object[i]);
                }
            }
            return mp;
        }
    }

    /**
     * 简化利用QueryWrapperEq查询操作
     * @param object
     * @return
     */
    public static <T> QueryWrapper<T> queryWrapperEq(Object ...object) {
        if(object.length%2==1) {
            throw new IllegalArgumentException();
        }else{
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            String key = null;
            for (int i = 0; i < object.length; i++) {
                if(i%2==0){
                    if(object[i] instanceof String){
                        key = (String) object[i];
                    }else{
                        throw new IllegalArgumentException();
                    }
                }else{
                    queryWrapper.eq(key, object[i]);
                }
            }
            return queryWrapper;
        }
    }
}
