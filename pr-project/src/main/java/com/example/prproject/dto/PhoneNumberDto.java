package com.example.prproject.dto;

import com.example.prproject.dto.base.AuditDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PhoneNumberDto extends AuditDto {

    private String phoneNumber;
}
