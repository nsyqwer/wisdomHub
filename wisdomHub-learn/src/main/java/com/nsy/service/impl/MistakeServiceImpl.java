package com.nsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nsy.mapper.MistakeMapper;
import com.nsy.model.pojo.Mistake;

import com.nsy.model.vo.MistakeDetailVO;
import com.nsy.model.vo.MistakeVo;
import com.nsy.service.MistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【mistake】的数据库操作Service实现
* @createDate 2024-07-03 10:12:57
*/
@Service
public class MistakeServiceImpl extends ServiceImpl<MistakeMapper, Mistake>
    implements MistakeService {

    @Autowired
    private MistakeMapper mistakeMapper;

    @Override
    public List<MistakeVo> listBySidCid(int studentId, int courseId) {

        return mistakeMapper.getMistakeVo(studentId,courseId);
    }

    @Override
    public MistakeDetailVO getDetail(int mistakeId) {

        return mistakeMapper.getDetail(mistakeId);
    }

    @Override
    public List<MistakeVo> listBySid(int studentId) {
        return mistakeMapper.getMistakeVoBySid(studentId);
    }
}




