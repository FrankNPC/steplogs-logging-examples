package io.steplogs.example.web.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Configuration;

import io.steplogs.example.web.service.SMTPEmailScheduler;
import io.steplogs.example.web.service.SMTPEmailService;
import io.steplogs.logger.Logger;
import jakarta.annotation.Resource;

@Configuration
@EnableScheduling
public class ExampleWebServerScheduler {

	@Resource
	SMTPEmailScheduler emailScheduler;

	@Resource
	SMTPEmailService smtpEmailService;
	
	@Resource
	Logger logger;
	
	@Scheduled(fixedDelay = 15 * 1000) 
	public void sendEmails() {
		for(int i=0; i<10; i++) {
//			smtpEmailService.sendVerificationUrl("somebodyemailnotknowhoisabcdxxx@whoismail.unknown", "http://localhost/verifyyouremail");
//			logger.reset();
		}
		logger.reset();
	}
	
	@Scheduled(initialDelay = 5*1000, fixedDelay = 5*1000) 
	public void runEmailRunnable() {
		emailScheduler.runMailRunnable();
	}

}
