package me.remil.notes.service.user;

import me.remil.notes.dao.UserRepository;
import me.remil.notes.dto.receive.UserDto;
import me.remil.notes.entity.Users;
import me.remil.notes.exception.IdAlreadyExistsException;
import me.remil.notes.exception.MissingItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void createUser(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().trim().length() < 5) {
            throw new MissingItemException("Null/Invalid username received");
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().length() != 60) {
            throw new MissingItemException("Null/Invalid password");
        }

        if (userDto.getSecretKey() == null || userDto.getSecretKey().trim().length() != 192) {
            throw new MissingItemException("Null/Invalid secret key");
        }

        if (userDto.getCreationTimestamp() == null) {
            throw new MissingItemException("Null/Invalid timestamp");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IdAlreadyExistsException("Malformed request. Username exists");
        }

        Users user = new Users(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getSecretKey(),
                userDto.getCreationTimestamp());

        userRepository.save(user);
    }
}
