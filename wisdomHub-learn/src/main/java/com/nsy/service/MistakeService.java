package com.nsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nsy.model.pojo.Mistake;
import com.nsy.model.vo.MistakeDetailVO;
import com.nsy.model.vo.MistakeVo;

import java.util.List;

/**
* @author 宁舒意
* @description 针对表【mistake】的数据库操作Service
* @createDate 2024-07-03 10:12:57
*/
public interface MistakeService extends IService<Mistake> {

    List<MistakeVo> listBySidCid(int studentId, int courseId);

    MistakeDetailVO getDetail(int mistakeId);

    List<MistakeVo> listBySid(int studentId);
}
