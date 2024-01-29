package com.ruchi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SmsSender {
	// Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
	
	@Value("${TWILIO_ACCOUNT_SID}") 
	public String ACCOUNT_SID;
	
	@Value("${TWILIO_AUTH_TOKEN}") 
	public String AUTH_TOKEN;
	
	@Value("${TWILIO_FROM_NUM}") 
    public String fromNum;
    
    public String sendSms (String toNumber, String otp) {
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    	String msg = "This is your otp: "+ otp;
        Message message = Message.creator(
                new PhoneNumber("+15075306371"),
                new PhoneNumber(fromNum),
                msg).create();
        if (message != null) {
        	return "OTP send to cellphone";
        }
        return "OTP couldn't be sent to cellphone";
    }
   
}
