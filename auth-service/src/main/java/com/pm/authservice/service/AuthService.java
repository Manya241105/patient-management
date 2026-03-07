package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.util.JwUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwUtil jwUtil ;
    public AuthService
            (UserService userService, PasswordEncoder passwordEncoder, JwUtil jwUtil) {
        this.passwordEncoder=passwordEncoder;
        this.userService = userService;
        this.jwUtil = jwUtil;
    }

    // password request -> password -> encoded -> @U(O@(@(D+Sdasjlj
    public Optional <String> authenticate(LoginRequestDTO loginRequestDTO){
        Optional <String> token = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u-> jwUtil.generateToken(u.getEmail(),u.getRole()));
        return token;

    }

    public boolean validateToken(String token){
        try{
            jwUtil.validateToken(token);
            return true;
        }
        catch (JwtException e){
            return false;
        }
    }
}
