package com.example.prproject.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PageRequestDto extends OrderDto {

    @Min(1)
    @Schema(example = "1")
    private int pageNumber = 1;

    @Min(1)
    @Max(500)
    @Schema(example = "50")
    private int pageSize = 50;
}
