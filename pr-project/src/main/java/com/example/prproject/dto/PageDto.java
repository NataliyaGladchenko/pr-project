package com.example.prproject.dto;

import com.example.prproject.dto.page.PageRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Accessors(chain = true)
public class PageDto<Dto> extends PageRequestDto {

    @Min(0)
    @Schema(example = "100")
    private long totalElements;

    @Min(0)
    @Schema(example = "2")
    private int totalPages;

    private Collection<Dto> content = Collections.emptyList();
}
