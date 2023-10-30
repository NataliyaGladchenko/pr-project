package com.example.prproject.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteFilter {
    private List<Integer> ids;
}
