package com.yea.authenticator.dto;



import lombok.Data;

@Data
public class OtpAuthenticatorRequest {
	
	private String userName;
	private String msisdn;
	private String emailAdress;
	private Integer otpExpirePeriod;
	private OtpAuthenticatorType authenticatorType;

}
