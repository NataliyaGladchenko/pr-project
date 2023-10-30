package com.example.prproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto extends SimpleUserDto {

    private Set<PhoneNumberDto> phoneNumbers;
}
