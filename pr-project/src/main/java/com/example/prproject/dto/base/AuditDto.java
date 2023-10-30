package com.example.prproject.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
@Getter
@Setter
@Accessors(chain = true)
public class AuditDto extends BaseDto {

    @Schema(hidden = true)
    private Date createdDate;

    @Schema(hidden = true)
    private Date updatedDate;

}
