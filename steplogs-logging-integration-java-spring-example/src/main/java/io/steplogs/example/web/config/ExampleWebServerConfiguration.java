package io.steplogs.example.web.config;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import io.steplogs.example.web.service.SMTPEmailScheduler;
import io.steplogs.example.web.service.SMTPEmailService;
import io.steplogs.logger.Logger;
import io.steplogs.logger.PreDefinition;
import io.steplogs.logger.provider.LoggerProvider;
import io.steplogs.logger.spring.AutoConfigurationSteplogsLogger;
import io.steplogs.logger.spring.LoggingHttpHeaderHandlerInterceptor;
import io.steplogs.logger.spring.LoggingHttpHeaderResponseAdvice;
import io.steplogs.spring.rmi.http.prodiver.AutoConfigurationServiceProvider;
import jakarta.annotation.Resource;

@Configuration
@EnableScheduling
@Import(value= {
	AutoConfigurationSteplogsLogger.class, // to load the configs from application.xml for logging related beans

	AutoConfigurationServiceProvider.class, // Turn on RPC service provider
	LoggingHttpHeaderResponseAdvice.class, // so you can see the trace id in the http response of the web browser.
})
public class ExampleWebServerConfiguration {

	@Resource
	@Lazy
	LoggerProvider steplogsLoggerProvider;

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
			//logger.reset();
		}
		logger.reset();
	}
	
	@Scheduled(initialDelay = 5*1000, fixedDelay = 5*1000) 
	public void runEmailRunnable() {
		emailScheduler.runMailRunnable();
	}
	
//	LoggingWebMvcConfigurer getLoggingWebMvcConfigurer() {
//		return new LoggingWebMvcConfigurer(List.of(PreDefinition.HTTP_HEADER_STEP_LOG_ID), "/api", "/api/**");
//	}

	// to log headers and http payload
	@Bean
	WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(new LoggingHttpHeaderHandlerInterceptor(steplogsLoggerProvider, true, List.of(PreDefinition.HTTP_HEADER_STEP_LOG_ID, PreDefinition.HTTP_HEADER_STEP_TRACE_ID, PreDefinition.HTTP_HEADER_STEP_LOG_SKIP)))
				.addPathPatterns("/api", "/api/**");
			}
		};
	}
}
