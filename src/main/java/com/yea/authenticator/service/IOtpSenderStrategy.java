package com.yea.authenticator.service;

import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;

public interface IOtpSenderStrategy {
	
	public OtpAuthenticatorResponse sendOtp(OtpAuthenticatorRequest request);
	public String getKey();
}
