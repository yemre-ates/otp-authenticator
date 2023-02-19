package com.yea.authenticator.dto;

import java.util.List;

import lombok.Data;

@Data
public class Email {
	
	   private List<String> recipients;
	   private List<String> ccList;
	   private String subject;
	   private String body;

}
