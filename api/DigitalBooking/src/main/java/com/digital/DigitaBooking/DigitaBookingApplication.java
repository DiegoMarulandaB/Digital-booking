package com.digital.DigitaBooking;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Clock;

@SpringBootApplication
public class DigitaBookingApplication {


	public static void main(String[] args) {
		SpringApplication.run(DigitaBookingApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Clock clock(){
		return Clock.systemDefaultZone();
	}

}
