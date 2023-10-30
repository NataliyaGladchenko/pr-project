package com.example.prproject.dao;

import com.example.prproject.domain.User;
import com.example.prproject.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends BaseRepo<User> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User getUserByEmail(String email);

    Page<User> getAllByRoleIs(UserRole role, Pageable pageable);

    @Query(value = "select u from User u where u.role = :role and (lower(u.firstName) like %:pattern% " +
            "or lower(u.lastName) like %:pattern%)")
    Page<User> getAllByRoleAndTextFilter(UserRole role, String pattern, Pageable pageable);
}
