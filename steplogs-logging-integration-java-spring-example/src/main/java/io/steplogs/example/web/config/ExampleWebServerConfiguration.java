package io.steplogs.example.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

import io.steplogs.logger.provider.LoggerProvider;
import io.steplogs.logger.spring.AutoConfigurationSteplogsLogger;
import io.steplogs.logger.spring.LoggingHttpRequestFilter;
import io.steplogs.spring.rmi.http.prodiver.AutoConfigurationServiceProvider;
import jakarta.annotation.Resource;

@Configuration
@Import(value= {
	AutoConfigurationSteplogsLogger.class, // to load the configs from application.xml for logging related beans

	AutoConfigurationServiceProvider.class, // Turn on RPC service provider
//	LoggingHttpHeaderResponseAdvice.class, // so you can see the trace id in the http response of the web browser.
})
public class ExampleWebServerConfiguration {

	@Resource
	@Lazy
	LoggerProvider steplogsLoggerProvider;

	// pick up step log id from last app/service/http request header to form traces, and log http payload for specific paths
	@Bean
	FilterRegistrationBean<LoggingHttpRequestFilter> loggingResetInFilter() {
		FilterRegistrationBean<LoggingHttpRequestFilter> reg = new FilterRegistrationBean<>(
				new LoggingHttpRequestFilter(steplogsLoggerProvider, true));
		reg.setOrder(Ordered.HIGHEST_PRECEDENCE);
		reg.addUrlPatterns("/api", "/api/*", "/remote_api/*");
		return reg;
	}
	
//	@Bean
//	LoggingWebMvcConfigurer getLoggingWebMvcConfigurer() {
//		return new LoggingWebMvcConfigurer(steplogsLoggerProvider, true, List.of(PreDefinition.HTTP_HEADER_STEP_LOG_ID, PreDefinition.HTTP_HEADER_STEP_TRACE_ID, PreDefinition.HTTP_HEADER_STEP_LOG_SKIP), 
//				"/api", "/api/**", "/remote_api/**");
//	}
}
