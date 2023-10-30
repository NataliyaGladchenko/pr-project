package com.example.prproject.controllers;

import com.example.prproject.dto.PageDto;
import com.example.prproject.dto.SimpleUserDto;
import com.example.prproject.dto.UserDto;
import com.example.prproject.dto.filter.DeleteFilter;
import com.example.prproject.dto.filter.TextSearchFilter;
import com.example.prproject.dto.page.PageRequestDto;
import com.example.prproject.services.SimpleUserService;
import com.example.prproject.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.prproject.services.entity.BaseService.DEFAULT_SORT;
import static com.example.prproject.services.entity.BaseService.getPageRequest;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller")
public class UserController extends BaseController {

    private UserService userService;

    private SimpleUserService simpleUserService;

    @GetMapping(value = USERS_URL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(method = "Returns all users")
    public PageDto<SimpleUserDto> getAllUsers(@Valid @ModelAttribute TextSearchFilter filter) {
        Pageable pageable = getPageRequest(filter, DEFAULT_SORT);
        return simpleUserService.getAllUsers(filter, pageable);
    }

    @GetMapping(value = USERS_URL + USER_ID_URL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(method = "Returns user by its ID")
    public UserDto getUser(@PathVariable Integer userId) {
        return userService.getDto(userId);
    }

    @PostMapping(USERS_URL)
    @Operation(method = "Updates user")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
       return userService.update(userDto);
    }

    @DeleteMapping(value = USERS_URL + USER_ID_URL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(method = "Deletes user by its ID")
    public void deleteUser(@PathVariable Integer userId) {
        userService.delete(userId);
    }

    @GetMapping(USERS_URL + ADMIN_URL)
    @PreAuthorize("hasRole('USER')")
    @Operation(method = "Returns admin users")
    public PageDto<SimpleUserDto> getAdminUsersDetails(@ModelAttribute PageRequestDto pageRequestDto) {
        return simpleUserService.getAllAdmins(getPageRequest(pageRequestDto));
    }

    @PutMapping(USERS_URL + REGISTER_URL)
    @Operation(method = "Create user")
    public UserDto createUser(@Valid @RequestBody UserDto userDto){
        return userService.create(userDto);
    }

    @DeleteMapping(USERS_URL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(method = "Deletes multiple users")
    public void deleteUsers(@RequestBody DeleteFilter deleteFilter) {
        userService.deleteUsers(deleteFilter.getIds());
    }

}
