package com.example.prproject.dto;

import com.example.prproject.annotation.ValidPassword;
import com.example.prproject.dto.base.AuditDto;
import com.example.prproject.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
public class SimpleUserDto extends AuditDto {

    @NotBlank
    @Length(max = 100)
    private String firstName;

    @NotBlank
    @Length(max = 100)
    private String lastName;

    @Email
    @Length(min = 8, max = 100)
    private String email;

    @ValidPassword
    @Length(min = 8, max = 100)
    private String password;

    private UserRole role = UserRole.USER;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
