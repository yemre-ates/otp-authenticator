package com.yea.authenticator.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yea.authenticator.cache.JedisConnection;
import com.yea.authenticator.dto.OtpValidateRequest;

@Service
public class OtpValidatorService {

	@Autowired
	private JedisConnection jedis;
	
	public boolean validateOtp(OtpValidateRequest otpValidateEntity) {
		
		Map<String, String> cachedOtpObj = jedis.getFromJedis(otpValidateEntity.getUserName());
		String  cachedOtp = cachedOtpObj.get(otpValidateEntity.getUserName());
		if(cachedOtp != null && cachedOtp.equals(otpValidateEntity.getOtp()))
			return true;
		return false;
		
	}
}
