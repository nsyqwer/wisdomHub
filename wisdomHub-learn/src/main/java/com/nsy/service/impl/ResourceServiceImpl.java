package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsy.pojo.Resource;
import com.nsy.service.ResourceService;
import com.nsy.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author 宁舒意
* @description 针对表【resource】的数据库操作Service实现
* @createDate 2024-05-27 09:02:16
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService {
}




