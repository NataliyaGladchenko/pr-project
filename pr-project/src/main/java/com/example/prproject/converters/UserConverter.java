package com.example.prproject.converters;

import com.example.prproject.domain.PhoneNumber;
import com.example.prproject.domain.User;
import com.example.prproject.dto.PhoneNumberDto;
import com.example.prproject.dto.UserDto;
import com.example.prproject.utils.BCryptUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserConverter extends BaseConverter<User, UserDto> {

    private PhoneConverter phoneConverter;

    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(mapper ->
                        mapper.using(ctx -> phoneConverter.convertEntitySet((Set<PhoneNumber>) ctx.getSource()))
                                .map(User::getPhoneNumbers, UserDto::setPhoneNumbers));

        modelMapper.createTypeMap(UserDto.class, User.class)
                .addMappings(mapper ->
                        mapper.using(ctx -> phoneConverter.convertDtoSet((Set<PhoneNumberDto>) ctx.getSource()))
                                .map(UserDto::getPhoneNumbers, User::setPhoneNumbers))
                .addMappings(mapper ->
                        mapper.using(ctx -> {
                            String newPassword = (String) ctx.getSource();
                            return newPassword == null ? ctx.getDestination() : BCryptUtil.getPasswordHash(newPassword);
                        }).map(UserDto::getPassword, User::setPassword));
    }
}
