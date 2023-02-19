FROM openjdk:8
ENV TZ="Europe/Istanbul"
	
EXPOSE 8080
COPY src/main/resources /resources
ARG JAR_FILE=target/otp-authenticator-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} otp-authenticator.jar

ENTRYPOINT ["java","-jar","-Dlog4j.configurationFile=/resources/log4j2.xml","-Dspring.config.location=file:///resources/application.properties","-Xms1g","-Xmx1g","/otp-authenticator.jar"]