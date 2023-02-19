# otp-authenticator

## Summary
- This is authentication server side app which is sends otp via sms or email. 
- Also otp can be validate with validator service. (Otp stored in the redis cache)
- Developed according to SOLID principles, it can be expanded.(adding new send channel, ussd etc.)
- Spring boot app and redis dockerized.

## APIs
***/sendOtp***: Post request, sends otp via sms or email.

```
{
    "userName":"monarocco",    
    "msisdn":"+9053161*****",    
    "emailAdress":"yemre*****@hotmail.com",    
    "otpExpirePeriod":"60",    
    "authenticatorType":"SMS"
}
```

***/validateOtp***: Post request, otp validate service.

```
{  
    "userName":"monarocco",
    "otp":"903200"
}
```

## Settings in application.properties
- twilio.* = Get account id, auth token and trial number from https://www.twilio.com/try-twilio
- email.* = Email account info
- jedis.* = Redis node connection info
- spring.security.* = spring boot web services basic auth info
  
## Test 
Integration tests are written using JUnit. 
5/5 test passed.

- OtpAuthenticatorApplicationTests.testSendOtpViaSms
- OtpAuthenticatorApplicationTests.testSendOtpViaEmail
- OtpAuthenticatorApplicationTests.testDeliveredSmsOtp  
- OtpAuthenticatorApplicationTests.testDeliveredEmailOtp  
- OtpAuthenticatorApplicationTests.testValidateOtp  

## How to run?
```
git clone https://github.com/yemre-ates/otp-authenticator.git
mvn clean install
docker compose up

```
- make request for http://localhost:9090/sendOtp or http://localhost:9090/validateOtp with above given parameters.
