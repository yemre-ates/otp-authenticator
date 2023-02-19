package com.yea.authenticator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;
import com.yea.authenticator.engine.OtpSenderEngine;

@RestController
public class OtpSenderController {

	@Autowired
	private OtpSenderEngine otpSenderEngine;
	
	@PostMapping(path = "/sendOtp")
	public ResponseEntity<OtpAuthenticatorResponse> sendOtp(@RequestBody OtpAuthenticatorRequest request) {
		
		 OtpAuthenticatorResponse response = otpSenderEngine.sendOtp(request);
		 return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
}
