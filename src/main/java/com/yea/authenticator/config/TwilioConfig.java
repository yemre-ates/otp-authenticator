package com.yea.authenticator.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;
import com.yea.authenticator.config.dto.TwilioConfigProviderDto;

@Configuration
public class TwilioConfig {
	
	@Autowired
	private TwilioConfigProviderDto twilioConfigProvider;
	
	@PostConstruct
	private void initTwilio() {
		Twilio.init(twilioConfigProvider.getAccountSid(),twilioConfigProvider.getAuthToken());
	}

}
