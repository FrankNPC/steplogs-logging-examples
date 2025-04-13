package io.steplogs.example.web.config;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import io.steplogs.example.web.service.SMTPEmailService;
import io.steplogs.logger.Logger;
import io.steplogs.logger.PreDefinition;
import io.steplogs.logger.provider.LoggerProvider;
import io.steplogs.logger.spring.LoggerAutoConfiguration;
import io.steplogs.logger.spring.LoggingHttpHeaderResponseAdvice;
import io.steplogs.logger.spring.LoggingWebMvcConfigurer;
import io.steplogs.spring.rmi.http.prodiver.ServiceProviderConfiguration;
import io.steplogs.logger.spring.LoggerReaderConfiguration;
import jakarta.annotation.Resource;

@Configuration
@EnableScheduling
@Import(value= {
	ServiceProviderConfiguration.class, // Turn on service provider

	LoggingHttpHeaderResponseAdvice.class, // so you can see the trace id in the http response of the web browser.
	LoggerReaderConfiguration.class, // to read the configs from application.xml
	LoggerAutoConfiguration.class // to load the logging related beans
})
public class ExampleWebServerConfiguration {

	@Resource
	@Lazy
	LoggerProvider steplogsLoggerProvider;

	@Resource
	SMTPEmailService smtpEmailService;

	@Resource
	Logger logger;
	
//	@Scheduled(fixedDelay = 15 * 1000) 
	public void sendEmails() {
		for(int i=0; i<10; i++) {
			smtpEmailService.sendVerificationUrl("somebodyemailnotknowhoisabcdxxx@whoismail.unknown", "http://localhost/verifyyouremail");
			// for the long recursive jobs, reset info;
			logger.reset();	
		}
		logger.reset();
	}

	// If to log headers and http payload, override the default LoggingWebMvcConfigurer
	@Bean
	LoggingWebMvcConfigurer getLoggingWebMvcConfigurer() {
		return new LoggingWebMvcConfigurer(List.of(PreDefinition.HTTP_HEADER_STEP_LOG_ID), "/api", "/api/**");
	}

}
