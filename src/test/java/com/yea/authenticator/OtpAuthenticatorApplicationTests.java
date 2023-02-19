package com.yea.authenticator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.yea.authenticator.dto.OtpAuthenticatorRequest;
import com.yea.authenticator.dto.OtpAuthenticatorResponse;
import com.yea.authenticator.dto.OtpAuthenticatorType;
import com.yea.authenticator.dto.OtpDeliverStatus;
import com.yea.authenticator.dto.OtpValidateRequest;

@SpringBootTest(classes = OtpAuthenticatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class OtpAuthenticatorApplicationTests {

	private OtpAuthenticatorRequest request;
	private ResponseEntity<OtpAuthenticatorResponse> response;
	private final String username = "yea" ;
	private final String password = "yea123";

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getUrl() {
		return "http://localhost:".concat(String.valueOf(port));
	}

	@Test
	public void testSendOtpViaSms() {

		request = new OtpAuthenticatorRequest();
		request.setMsisdn("+905316100100");
		request.setOtpExpirePeriod(60);
		request.setUserName("monarocco");
		request.setAuthenticatorType(OtpAuthenticatorType.SMS);

		response = restTemplate.withBasicAuth(username, password).postForEntity(getUrl().concat("/sendOtp"), request,
				OtpAuthenticatorResponse.class);

		assertNotNull(response);
		assertThat(response.getStatusCodeValue() == HttpStatus.SC_OK);
		assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON_UTF8));

	}

	@Test
	public void testSendOtpViaEmail() {

		request = new OtpAuthenticatorRequest();
		request.setEmailAdress("yemre.ates@hotmail.com");
		request.setOtpExpirePeriod(60);
		request.setUserName("monarocco");
		request.setAuthenticatorType(OtpAuthenticatorType.EMAIL);

		response = restTemplate.withBasicAuth(username, password).postForEntity(getUrl().concat("/sendOtp"), request,
				OtpAuthenticatorResponse.class);

		assertNotNull(response);
		assertThat(response.getStatusCodeValue() == HttpStatus.SC_OK);
		assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON_UTF8));

	}
	
	@Test
	public void testDeliveredSmsOtp() {

		request = new OtpAuthenticatorRequest();
		request.setMsisdn("+905316100100");
		request.setOtpExpirePeriod(60);
		request.setUserName("monarocco");
		request.setAuthenticatorType(OtpAuthenticatorType.SMS);

		response = restTemplate.withBasicAuth(username, password).postForEntity(getUrl().concat("/sendOtp"), request,
				OtpAuthenticatorResponse.class);

		assertNotNull(response);
		assertThat(response.getStatusCodeValue() == HttpStatus.SC_OK);
		assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody().getDeliverStatus().equals(OtpDeliverStatus.DELIVERED));

	}
	
	@Test
	public void testDeliveredEmailOtp() {

		request = new OtpAuthenticatorRequest();
		request.setMsisdn("+905316100100");
		request.setOtpExpirePeriod(60);
		request.setUserName("monarocco");
		request.setAuthenticatorType(OtpAuthenticatorType.SMS);

		response = restTemplate.withBasicAuth(username, password).postForEntity(getUrl().concat("/sendOtp"), request,
				OtpAuthenticatorResponse.class);

		assertNotNull(response);
		assertThat(response.getStatusCodeValue() == HttpStatus.SC_OK);
		assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody().getDeliverStatus().equals(OtpDeliverStatus.DELIVERED));

	}

	@Test
	public void testValidateOtp() {

		OtpValidateRequest request = new OtpValidateRequest();
		request.setOtp("666111");
		request.setUserName("monarocco");
		ResponseEntity<Boolean> response = restTemplate.withBasicAuth(username, password)
				.postForEntity(getUrl().concat("/validateOtp"), request, Boolean.class);

		assertNotNull(response);
		assertThat(response.getStatusCodeValue() == HttpStatus.SC_OK);
		assertThat(response.getHeaders().getContentType()
				.equals(org.springframework.http.MediaType.APPLICATION_JSON_UTF8));

	}

}
