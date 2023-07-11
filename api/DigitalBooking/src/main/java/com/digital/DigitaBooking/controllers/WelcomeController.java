package com.digital.DigitaBooking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WelcomeController {

    @Operation(security = {@SecurityRequirement(name = "bearer-key")},
            summary = "Welcome normal user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    @GetMapping(path = "/welcome/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String user(Authentication authentication) {
        return welcomeLoggedUser(authentication);
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")},
            summary = "Welcome admin user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    @GetMapping(path = "/welcome/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(Authentication authentication) {

        return welcomeLoggedUser(authentication);
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")},
            summary = "Welcome any logged user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    @GetMapping(path = "/welcome/anyone")
    public String any(Authentication authentication) {

        return welcomeLoggedUser(authentication);
    }

    private static String welcomeLoggedUser(Authentication authentication) {
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "Welcome " + userDetails.getUsername();
    }
}
