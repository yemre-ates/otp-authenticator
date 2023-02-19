package com.yea.authenticator.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.yea.authenticator.config.dto.EmailConfigProviderDto;

@Configuration
public class MailConfig {
	
    @Autowired
    private EmailConfigProviderDto mailConfigProvider;

    @Bean
    public JavaMailSender mailSender()
    {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailConfigProvider.getHost());
        javaMailSender.setPort(mailConfigProvider.getPort());

        javaMailSender.setUsername(mailConfigProvider.getUsername());
        javaMailSender.setPassword(mailConfigProvider.getPassword());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", mailConfigProvider.getDebug().toString());

        return javaMailSender;
    }

}
