package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.models.dtos.AuthorizationResponse;
import com.digital.DigitaBooking.models.dtos.UserLogin;
import com.digital.DigitaBooking.models.dtos.UserSignUp;

public interface IAuthenticationService {

    AuthorizationResponse signUp(UserSignUp userSignUp);

    AuthorizationResponse logIn(UserLogin userLogin);

}
