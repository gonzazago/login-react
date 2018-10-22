package com.react.loginreact.controller;

import com.react.loginreact.exception.AppException;
import com.react.loginreact.model.Role;
import com.react.loginreact.model.User;
import com.react.loginreact.model.enums.RoleName;
import com.react.loginreact.payload.ApiResponse;
import com.react.loginreact.payload.JwtAuthenticationResponse;
import com.react.loginreact.payload.LoginRequest;
import com.react.loginreact.payload.SignUpRequest;
import com.react.loginreact.repository.RoleRepository;
import com.react.loginreact.repository.UserRepository;
import com.react.loginreact.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JWTProvider jwtProvider;


    @PostMapping("/signin")
    public ResponseEntity<?>authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserNameOrMail(),loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return ResponseEntity.ok( new JwtAuthenticationResponse(jwt));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if(userRepository.existsByUserName(signUpRequest.getUserName())){
            return new ResponseEntity<>(new ApiResponse(false,"El nombre de usuario ingresado ya existe"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByMail(signUpRequest.getMail())){
            return new ResponseEntity<>(new ApiResponse(false,"El mail ingresado ya existe"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(),signUpRequest.getUserName(),
                signUpRequest.getMail(),signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRole(Collections.singleton(userRole));

        User result =  userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserName()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true,"Usuario Registrado exitosamente"));
    }


}
