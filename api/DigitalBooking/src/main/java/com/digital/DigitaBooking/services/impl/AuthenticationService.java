package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.jwt.JwtService;
import com.digital.DigitaBooking.models.dtos.AuthorizationResponse;
import com.digital.DigitaBooking.models.dtos.UserLogin;
import com.digital.DigitaBooking.models.dtos.UserSignUp;
import com.digital.DigitaBooking.services.IAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthorizationResponse signUp(UserSignUp userSignUp) {
        UserDetails userDetails = userService.registerUser(userSignUp);
        return AuthorizationResponse.builder()
                .jwt(jwtService.generateToken(userDetails))
                .build();
    }

    @Override
    public AuthorizationResponse logIn(UserLogin userLogin) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUserName(),
                        userLogin.getPassword()));

        String jwt = jwtService
                .generateToken((UserDetails) authentication.getPrincipal());

        return AuthorizationResponse.builder()
                .jwt(jwt)
                .build();
    }
}
