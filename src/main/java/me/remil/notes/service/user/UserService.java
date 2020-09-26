package me.remil.notes.service.user;

import me.remil.notes.dto.receive.UserDto;

import java.util.Date;

public interface UserService {
    boolean checkUsernameExists(String username);
    void createUser(UserDto userDto);
    String fetchSecretKey(String username);
}
