package com.yea.authenticator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpAuthenticatorResponse {
	
	private OtpDeliverStatus deliverStatus;
	private String message;
	private String otp;
	
	public OtpAuthenticatorResponse(OtpDeliverStatus deliverStatus, String message) {
		this.deliverStatus = deliverStatus;
		this.message = message;
	}

}
