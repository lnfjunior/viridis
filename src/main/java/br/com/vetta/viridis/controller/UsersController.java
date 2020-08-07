package br.com.vetta.viridis.controller;

import br.com.vetta.viridis.dto.CredentialsDTO;
import br.com.vetta.viridis.dto.TokenDTO;
import br.com.vetta.viridis.exception.InvalidPasswordException;
import br.com.vetta.viridis.model.Users;
import br.com.vetta.viridis.security.jwt.JwtService;
import br.com.vetta.viridis.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "${front.end.cors}")
@RequiredArgsConstructor
public class UsersController {

    private final UsersServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users save(@RequestBody @Valid Users users){
        String encryptedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encryptedPassword);
        return userService.save(users);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials){
        try{
            Users users = Users.builder()
                    .login(credentials.getLogin())
                    .password(credentials.getPassword()).build();
            UserDetails userAuthenticated = userService.authenticate(users);
            String token = jwtService.generateToken(users);
            return new TokenDTO(users.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
