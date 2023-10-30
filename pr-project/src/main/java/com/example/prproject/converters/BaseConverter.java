package com.example.prproject.converters;

import com.example.prproject.domain.base.BaseEntity;
import com.example.prproject.dto.base.BaseDto;

public abstract class BaseConverter<Entity extends BaseEntity, Dto extends BaseDto> extends AbstractConverter<Entity, Dto> {

    public static Integer getId(BaseEntity entity) {
        return entity == null ? null : entity.getId();
    }

    public static BaseEntity setId(Integer id, BaseEntity entity) {
        return entity == null || id == null ? null : entity.setId(id);
    }
}
