package com.yea.authenticator.config.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfigProviderDto {
    private String accountSid;
    private String authToken;
    private String trialNumber;

}