package com.yea.authenticator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yea.authenticator.cache.JedisConnection;
import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;
import com.yea.authenticator.dto.OtpDeliverStatus;
import com.yea.authenticator.util.Utils;

@Service
public class EmailOtpService implements IOtpSenderStrategy{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private JedisConnection jedis;
	
	private static Logger logger = LogManager.getLogger(EmailOtpService.class);
	
	@Override
	public String getKey() {
		
		return "EMAIL";
	}
	
	@Override
	public OtpAuthenticatorResponse sendOtp(OtpAuthenticatorRequest request) {
		
		OtpAuthenticatorResponse otpAuthenticatorResponse;
		
		if (request.getEmailAdress() == null || request.getEmailAdress().isEmpty()) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "emailAdress can not be null" );
		}
		
		try {
			
			String otp = Utils.generateOtp();
			SimpleMailMessage mailMessage = prepareMailMessage(request, otp);
			
			javaMailSender.send(mailMessage);
			
			//added cache for validate..
			pushJedis(request.getUserName(), otp, request.getOtpExpirePeriod());
			
			otpAuthenticatorResponse = new OtpAuthenticatorResponse(OtpDeliverStatus.DELIVERED, mailMessage.getText(), otp);
			
		} catch (Exception e) {
			otpAuthenticatorResponse = new OtpAuthenticatorResponse(OtpDeliverStatus.FAILED, "Failed while sending email to:" + request.getEmailAdress());
			logger.error(e.getMessage());
		}
		return otpAuthenticatorResponse;
		
	}

	private SimpleMailMessage prepareMailMessage(OtpAuthenticatorRequest request, String otp) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(request.getEmailAdress());
		mailMessage.setSubject("Otp");
		mailMessage.setText("Your otp code is:" + otp);
		
		return mailMessage;
		
	}
	
	private void pushJedis(String key, String value, int otpExpirePeriod) {
		String[][] pushValue = new String[1][1];
		pushValue[0][0] = key;
		pushValue[0][1] = value;
		jedis.pushJedis(key, pushValue);
		jedis.setExpire(key, otpExpirePeriod);
		logger.info("Added REDIS - key:" + key + ", value:" + value);
	}


}
