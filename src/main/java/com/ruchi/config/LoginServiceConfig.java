package com.ruchi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ruchi.utils.ImageNameGenerator;
import com.ruchi.utils.Imagefile;
import com.ruchi.utils.SmsSender;



@Configuration
public class LoginServiceConfig {
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
//	
//	@Bean
//	public RestTemplate restTemplate() {
//		return  new RestTemplate();
//	}
//	
	@Bean
	public ImageNameGenerator imageNameGenerator() {
		return new ImageNameGenerator();
	}
	
	@Bean
	public Imagefile imageFile() {
		return new Imagefile();
	}
	
	@Bean
	public SmsSender smsSender() {
		return new SmsSender();
	}
	
}
