package com.example.prproject.services;

import com.example.prproject.converters.BaseConverter;
import com.example.prproject.converters.LightUserConverter;
import com.example.prproject.dao.BaseRepo;
import com.example.prproject.dao.UserRepo;
import com.example.prproject.domain.User;
import com.example.prproject.dto.PageDto;
import com.example.prproject.dto.SimpleUserDto;
import com.example.prproject.dto.filter.TextSearchFilter;
import com.example.prproject.enums.UserRole;
import com.example.prproject.services.entity.BaseService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleUserService extends BaseService<User, SimpleUserDto> {

    public static final Sort.Order DEFAULT_SORT = Sort.Order.asc("lastName");

    private UserRepo userRepo;

    private LightUserConverter lightUserConverter;

    @Override
    protected BaseRepo<User> getRepo() {
        return userRepo;
    }

    @Override
    protected BaseConverter<User, SimpleUserDto> getConverter() {
        return lightUserConverter;
    }

    public PageDto<SimpleUserDto> getAllUsers(TextSearchFilter filter, Pageable pageable) {
        if (!StringUtils.isBlank(filter.getSearchQuery())) {
            return getConverter().convert(userRepo.getAllByRoleAndTextFilter(UserRole.USER, filter.getSearchQuery().toLowerCase(), pageable));
        }
        return getConverter().convert(userRepo.getAllByRoleIs(UserRole.USER, pageable));
    }

    public PageDto<SimpleUserDto> getAllAdmins(Pageable pageable) {
        return getConverter().convert(userRepo.getAllByRoleIs(UserRole.ADMIN, pageable));
    }
}
