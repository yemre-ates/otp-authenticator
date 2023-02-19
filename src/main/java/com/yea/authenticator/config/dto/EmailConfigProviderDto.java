package com.yea.authenticator.config.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "email")
@Data
public class EmailConfigProviderDto {
	
	private String host;
	private Integer port;
	private String username;
	private String password;
	private Boolean debug;

}
