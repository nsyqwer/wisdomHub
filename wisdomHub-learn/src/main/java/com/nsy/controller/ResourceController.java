package com.nsy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsy.model.BaseResult;
import com.nsy.model.pojo.Resource;
import com.nsy.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: ResourceController
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/6/10 21:30
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    /**
     * 学生：获取所有资料
     * @author 宁舒意
     * @date 21:28 2024/5/16
     * @param courseId 课程id
     * @return com.nsy.model.BaseResult
     **/
    @GetMapping("/{courseId}")
    public BaseResult<List<Resource>> resource(@PathVariable int courseId){
        QueryWrapper<Resource> resourceQueryWrapper =new QueryWrapper<Resource>().eq("course_id",courseId);
        List<Resource> resourceList =resourceService.list(resourceQueryWrapper);
        return new BaseResult(200,"获取所有课程资源成功",resourceList);
    }

    //TODO 下载资料
}
