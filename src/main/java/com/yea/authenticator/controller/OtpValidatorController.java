package com.yea.authenticator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yea.authenticator.dto.OtpValidateRequest;
import com.yea.authenticator.service.OtpValidatorService;

@RestController
public class OtpValidatorController {
	
	@Autowired
	private OtpValidatorService otpValidatorService;
	
	@PostMapping(path = "/validateOtp")
	private ResponseEntity<Boolean> validateOtp(@RequestBody OtpValidateRequest otpValidateEntity) {
		
		return ResponseEntity.ok(otpValidatorService.validateOtp(otpValidateEntity));
		
	}

}
