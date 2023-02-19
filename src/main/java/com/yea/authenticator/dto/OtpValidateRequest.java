package com.yea.authenticator.dto;

import lombok.Data;

@Data
public class OtpValidateRequest {

	private String userName;
	private String otp;
}
