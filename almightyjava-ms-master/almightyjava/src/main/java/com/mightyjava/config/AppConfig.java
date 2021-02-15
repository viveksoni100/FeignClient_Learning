package com.mightyjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mightyjava.captcha.CaptchaDetailsSource;
import com.mightyjava.captcha.CaptchaGenerator;
import com.mightyjava.listener.CounterApplicationListener;

@Configuration
public class AppConfig {
	@Bean
	public CaptchaGenerator getCaptchaGenerator() {
		return new CaptchaGenerator();
	}

	@Bean
	public CounterApplicationListener getCounterApplicationListener() {
		return new CounterApplicationListener();
	}

	@Bean
	public CaptchaDetailsSource getCaptchaDetailsSource() {
		return new CaptchaDetailsSource();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
