package com.example.prproject.security;

import com.example.prproject.domain.User;
import com.example.prproject.enums.UserRole;
import com.example.prproject.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.emptyList;


@Service
@AllArgsConstructor
public class DefaultUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    private UserService userService;

    public UserDetails updatePassword(UserDetails user, String newPassword) {
        User domainUser = userService.getUser(user.getUsername());
        domainUser.setPassword(newPassword);
        userService.save(domainUser);

        return new PlatformUser(user.getUsername(), newPassword, domainUser.getId(), emptyList());
    }

    public void createUser(UserDetails user) {
        User domainUser = new User(user.getUsername(), user.getPassword());
        userService.save(domainUser);
    }

    public void updateUser(UserDetails user) {
        User domainUser = userService.getUser(user.getUsername());
        domainUser.setPassword(user.getPassword());
        userService.save(domainUser);
    }

    public void deleteUser(String username) {
        userService.deleteUserByEmail(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context for current user.");
        }
        User domainUser = userService.getUser(currentUser.getName());
        domainUser.setPassword(newPassword);
        userService.save(domainUser);
    }

    public boolean userExists(String username) {
        return userService.isExists(username);
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getUser(username);
            return new PlatformUser(username, user.getPassword(), user.getId(), getAuthorities(user.getRole()));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private Set<GrantedAuthority> getAuthorities(UserRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

}
