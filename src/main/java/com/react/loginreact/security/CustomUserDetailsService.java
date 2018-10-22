package com.react.loginreact.security;

import com.react.loginreact.model.User;
import com.react.loginreact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userNameOrMail) throws UsernameNotFoundException {

        User user = userRepository.findByMailOrUserName(userNameOrMail,userNameOrMail)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario indicado no existe o es incorrecto"));
        return UserPrincipal.create(user);
    }

    /**
     * Metodo utilizado por JWTAuthentication Filter*/

    @Transactional
    public UserDetails loadUserbyId(Long id) throws UsernameNotFoundException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("EL usuario con el id Indicado no existe o es incorrecto"));

        return UserPrincipal.create(user);
    }
}
