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

//	@Bean("JobSchedulerPool")
//	ThreadPoolTaskScheduler JobSchedulerPool() {
//		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//		threadPoolTaskScheduler.setPoolSize(2);
//		threadPoolTaskScheduler.setThreadNamePrefix("ExampleWebServerScheduler-");
//		return threadPoolTaskScheduler;
//	}

	@Scheduled(fixedDelay = 15 * 1000) 
	public void sendEmails() {
		for(int i=0; i<10; i++) {
			smtpEmailService.sendVerificationUrl("somebodyemailnotknowhoisabcdxxx@whoismail.unknown", "http://localhost/verifyyouremail");
			logger.reset();
		}
	}
	
	@Scheduled(fixedDelay = 5*1000, initialDelay = 5*1000) 
	public void runEmailRunnable() {
		emailScheduler.runMailRunnable();
	}

}
