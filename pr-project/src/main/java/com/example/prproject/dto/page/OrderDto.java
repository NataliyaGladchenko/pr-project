package com.example.prproject.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDto {
    @Schema(example = "id")
    private String sortBy = "id";

    @Schema(example = "asc")
    private String sortOrder = "asc";
}
