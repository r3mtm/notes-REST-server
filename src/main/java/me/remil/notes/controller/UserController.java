package me.remil.notes.controller;

import me.remil.notes.dto.receive.UserDto;
import me.remil.notes.exception.BadParameterException;
import me.remil.notes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"}, allowCredentials = "true")
@RequestMapping("/api")
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/check-username")
    public boolean doesUserExists(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", null);
        if (username == null) {
            throw new BadParameterException("Invalid request: No username found");
        }
        return userService.checkUsernameExists(username);
    }

    @PostMapping("/create-account")
    public void createAccount(@RequestBody UserDto user) {
        userService.createUser(user);
    }
}
