package com.example.prproject.converters;

import com.example.prproject.domain.User;
import com.example.prproject.dto.SimpleUserDto;
import com.example.prproject.utils.BCryptUtil;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Conditions;
import org.springframework.stereotype.Service;

@Service
public class LightUserConverter extends BaseConverter<User, SimpleUserDto> {

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(SimpleUserDto.class, User.class)
                .addMappings(mapper ->
                        mapper.when(Conditions.isNotNull())
                                .using(ctx -> BCryptUtil.getPasswordHash(((String) ctx.getSource())))
                                .map(SimpleUserDto::getPassword, User::setPassword));
    }
}
