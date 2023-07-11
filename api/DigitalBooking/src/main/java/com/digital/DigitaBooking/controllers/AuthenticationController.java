package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.models.dtos.AuthorizationResponse;
import com.digital.DigitaBooking.models.dtos.UserLogin;
import com.digital.DigitaBooking.models.dtos.UserSignUp;
import com.digital.DigitaBooking.services.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Login process verifies user credentials such as name and password and provides a JWT token.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content)})
    @PostMapping(path = "/login")
    public AuthorizationResponse logIn(@RequestBody @Valid @NonNull UserLogin userLogin) {
        return authenticationService.logIn(userLogin);
    }

    @Operation(summary = "Register an account and receive a JWT token in return.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)})
    @PostMapping(path = "/sign-up")
    public AuthorizationResponse signUp(@RequestBody @Valid @NonNull UserSignUp userSignUp) {
        return authenticationService.signUp(userSignUp);
    }

}
