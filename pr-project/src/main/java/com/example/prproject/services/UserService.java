package com.example.prproject.services;

import com.example.prproject.converters.BaseConverter;
import com.example.prproject.converters.UserConverter;
import com.example.prproject.dao.BaseRepo;
import com.example.prproject.dao.UserRepo;
import com.example.prproject.domain.User;
import com.example.prproject.dto.UserDto;
import com.example.prproject.services.entity.BaseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService extends BaseService<User, UserDto> {

    public final static String ROLE_PREFIX = "ROLE_";

    private UserRepo userRepo;
    private UserConverter userConverter;

    private MailService mailService;

    @Override
    protected BaseRepo<User> getRepo() {
        return userRepo;
    }

    @Override
    protected BaseConverter<User, UserDto> getConverter() {
        return userConverter;
    }

    public User getUser(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by email " + email));
    }

    public boolean isExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public void deleteUserByEmail(String email) {
        Integer userId = userRepo.getUserByEmail(email).getId();
        userRepo.deleteById(userId);
    }

    @Override
    public UserDto create(UserDto dto) {
     //   mailService.sendRegisterMail(dto.getEmail());
        return super.create(dto);
    }

    @Override
    public void delete(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with provided id not found"));
      //  mailService.sendDeleteMessage(user.getEmail());
        super.delete(id);
    }

    public void deleteUsers(List<Integer> ids) {
        ids.forEach(this::delete);
    }
}
