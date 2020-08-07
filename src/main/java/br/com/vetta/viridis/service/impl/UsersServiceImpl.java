package br.com.vetta.viridis.service.impl;

import br.com.vetta.viridis.exception.InvalidPasswordException;
import br.com.vetta.viridis.model.Users;
import br.com.vetta.viridis.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsersRepository repository;

    @Transactional
    public Users save(Users users){
        return repository.save(users);
    }

    public UserDetails authenticate(Users users){
        UserDetails userDetails = loadUserByUsername(users.getLogin());
        boolean passwordsMatches = encoder.matches( users.getPassword(), userDetails.getPassword() );

        if(passwordsMatches){
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database."));

        String[] roles = users.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(users.getLogin())
                .password(users.getPassword())
                .roles(roles)
                .build();
    }
}
