package com.yea.authenticator.controller.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityController extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.security.user.name}")
    private String userName;
	
	@Value("${spring.security.user.password}")
    private String password;
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
    	String[] users = userName.split(",");
    	String[] passwords = password.split(",");
    	
    	InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = auth.inMemoryAuthentication();
    	for (int i = 0; i < users.length; i++) {
    		inMemoryAuthentication.withUser(users[i]).password("{noop}" + passwords[i]).roles("USER");
		}
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http.authorizeRequests()
    		.anyRequest().authenticated();
        http.httpBasic();
        http.csrf().disable();
        http.cors();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
