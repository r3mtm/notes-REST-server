package me.remil.notes.controller;

import me.remil.notes.dto.send.SessionKeyDto;
import me.remil.notes.exception.BadParameterException;
import me.remil.notes.exception.UnauthorizedRequestException;
import me.remil.notes.jwt.util.JwtTokenUtil;
import me.remil.notes.service.session.SessionKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"}, allowCredentials = "true")
@RequestMapping("/api/session")
public class SessionKeyController {

    private JwtTokenUtil jwtTokenUtil;

    final private SessionKeyService sessionKeyService;

    @Autowired
    public SessionKeyController(SessionKeyService sessionKeyService) {
        this.sessionKeyService = sessionKeyService;
    }

    @PostMapping("/create")
    public void createSession(@RequestHeader("cookie") String token, @RequestBody Map<String, String> body) {
        token = token.substring(6);
        String username = body.getOrDefault("username", null);
        String encryptedKey = body.getOrDefault("sessionSecretKey", null);
        String uuid = body.getOrDefault("keyId", null);
        Date tokenExDate = jwtTokenUtil.getExpirationDateFromToken(token);
        if (username == null || encryptedKey == null || uuid == null) {
            throw new BadParameterException("Invalid request: parameter missing");
        } else if (!jwtTokenUtil.getUsernameFromToken(token).equals(username)) {
            throw new UnauthorizedRequestException("Unauthorized request: Invalid credentials received");
        }
        sessionKeyService.saveSessionKeys(username, encryptedKey, uuid, tokenExDate);
    }

    @PostMapping("/fetch")
    public SessionKeyDto fetchSessionKey(@RequestHeader("cookie") String token, @RequestBody Map<String, String> body) {
        token = token.substring(6);
        String username = body.getOrDefault("username", null);
        String uuid = body.getOrDefault("uuid", null);
        if (username == null || uuid == null) {
            throw new BadParameterException("Invalid request: parameter missing");
        } else if (!jwtTokenUtil.getUsernameFromToken(token).equals(username)) {
            throw new UnauthorizedRequestException("Unauthorized request: Invalid credentials received");
        }
        return sessionKeyService.getSessionKeys(username, uuid);
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
