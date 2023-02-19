package com.yea.authenticator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.yea.authenticator.cache.JedisConnection;
import com.yea.authenticator.config.dto.TwilioConfigProviderDto;
import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;
import com.yea.authenticator.dto.OtpDeliverStatus;
import com.yea.authenticator.util.Utils;

@Service
public class TwilioOtpService implements IOtpSenderStrategy{

	@Autowired
	private TwilioConfigProviderDto twilioConfig;
	
	@Autowired
	private JedisConnection jedis;
	
	private static Logger logger = LogManager.getLogger(TwilioOtpService.class);
	
	@Override
	public String getKey() {
		
		return "SMS";
	}
	
	@Override
	public OtpAuthenticatorResponse sendOtp(OtpAuthenticatorRequest request) {
		
		OtpAuthenticatorResponse otpAuthenticatorResponse;
		
		if (request.getMsisdn() == null || request.getMsisdn().isEmpty()) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "msisdn can not be null" );
		}
		
		try {
			String otp = Utils.generateOtp();
			String otpMessage = "Your otp code is:" + otp;
			
			Message message = Message.creator(
					new PhoneNumber(request.getMsisdn()),
					new PhoneNumber(twilioConfig.getTrialNumber()),
					otpMessage).create();
			
			// added cache for validate
			pushJedis(request.getUserName(), otp, request.getOtpExpirePeriod());
			
			otpAuthenticatorResponse = new OtpAuthenticatorResponse(OtpDeliverStatus.DELIVERED, otpMessage, otp);
			
		} catch (Exception e) {
			otpAuthenticatorResponse = new OtpAuthenticatorResponse(OtpDeliverStatus.FAILED, "Failed while sending sms to:" + request.getMsisdn());
			logger.error(e.getMessage());
			
		}
		
		return otpAuthenticatorResponse;

	}
	
	private void pushJedis(String key, String value, int otpExpirePeriod) {
		String[][] pushValue = new String[1][2];
		pushValue[0][0] = key;
		pushValue[0][1] = value;
		jedis.pushJedis(key, pushValue);
		jedis.setExpire(key, otpExpirePeriod);
		logger.info("Added REDIS - key:" + key + ", value:" + value);
	}

}