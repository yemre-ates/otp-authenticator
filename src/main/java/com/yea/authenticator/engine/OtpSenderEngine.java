package com.yea.authenticator.engine;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;
import com.yea.authenticator.dto.OtpAuthenticatorType;
import com.yea.authenticator.service.EmailOtpService;
import com.yea.authenticator.service.IOtpSenderStrategy;
import com.yea.authenticator.service.TwilioOtpService;

@Service
public class OtpSenderEngine {
	
	private IOtpSenderStrategy otpSenderService;
	
	private final Map<String, IOtpSenderStrategy> senderMap;
	
	public OtpSenderEngine(@Autowired EmailOtpService emailService, @Autowired TwilioOtpService smsService) {
		
		senderMap = new HashMap<String, IOtpSenderStrategy>();
		senderMap.put(emailService.getKey(),emailService);
		senderMap.put(smsService.getKey(), smsService);
		
	}

	public OtpAuthenticatorResponse sendOtp(OtpAuthenticatorRequest request) {
		
		otpSenderService = getSenderStrategy(request.getAuthenticatorType());
		return otpSenderService.sendOtp(request);
				
	}
	
	private IOtpSenderStrategy getSenderStrategy(OtpAuthenticatorType type) {
		
			return senderMap.get(type.toString());
	}
	
}
