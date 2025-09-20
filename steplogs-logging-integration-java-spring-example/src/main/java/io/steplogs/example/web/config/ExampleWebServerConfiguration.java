package io.steplogs.example.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

import io.steplogs.logger.provider.LoggerProvider;
import io.steplogs.logger.spring.AutoConfigurationSteplogsLogger;
import io.steplogs.logger.spring.LoggingHttpRequestWebFilter;
import io.steplogs.spring.rmi.http.prodiver.AutoConfigurationServiceProvider;
import jakarta.annotation.Resource;

@Configuration
@Import(value= {
	AutoConfigurationSteplogsLogger.class, // to load the configs from application.xml for logging related beans

	AutoConfigurationServiceProvider.class, // Turn on RPC service provider
})
public class ExampleWebServerConfiguration {

	@Resource
	@Lazy
	LoggerProvider steplogsLoggerProvider;

	// read step log id from last app/service/http request header to form traces, and log http payload for specific paths
	@Bean
	FilterRegistrationBean<LoggingHttpRequestWebFilter> loggingResetInFilter() {
		FilterRegistrationBean<LoggingHttpRequestWebFilter> reg = new FilterRegistrationBean<>(
				new LoggingHttpRequestWebFilter(steplogsLoggerProvider, true));
		reg.setOrder(Ordered.HIGHEST_PRECEDENCE);
		reg.addUrlPatterns("/api", "/api/*", "/remote_api/*");
		return reg;
	}
}
