package com.yea.authenticator.config.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "jedis")
@Data
public class JedisConfigProviderDto {
	
	private String url;
	private Integer port;
	private String pass;

}
