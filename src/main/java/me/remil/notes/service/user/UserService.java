package me.remil.notes.service.user;

import me.remil.notes.dto.receive.UserDto;

public interface UserService {
    boolean checkUsernameExists(String username);
    void createUser(UserDto userDto);
}
