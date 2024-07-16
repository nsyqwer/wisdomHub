package com.nsy.mapper.mapstruct;

import com.nsy.model.dto.AddChooserDto;
import com.nsy.model.dto.AddSiginDto;
import com.nsy.model.pojo.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityDtoMapstruct {
    Activity siginDtoToActivity(AddSiginDto Sigin);
    Activity addChooserToActivity(AddChooserDto chooser);
}
