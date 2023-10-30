package com.example.prproject.dto.filter;

import com.example.prproject.dto.page.PageRequestDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TextSearchFilter extends PageRequestDto {

    private String searchQuery;
}

