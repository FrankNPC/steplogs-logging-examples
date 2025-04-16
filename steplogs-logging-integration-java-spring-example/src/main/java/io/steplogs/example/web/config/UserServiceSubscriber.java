package io.steplogs.example.web.config;


import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import io.steplogs.example.web.service.rpc.UserService;
import io.steplogs.logger.annotation.LoggingUnit;
import io.steplogs.logger.provider.LoggerProvider;
import io.steplogs.logger.spring.LoggingHeaderClientHttpRequestInterceptor;
import io.steplogs.logger.spring.LoggingMethodPointcut;
import io.steplogs.logger.spring.LoggingTraceMethodInterceptor;
import io.steplogs.spring.rmi.http.subscriber.AbstractServiceSubscriber;
import io.steplogs.spring.rmi.http.subscriber.ServiceClientTemplate;
import jakarta.annotation.Resource;

@Configuration
public class UserServiceSubscriber<T> extends AbstractServiceSubscriber implements ServiceClientTemplate<T> {

	@Value("${example.service.user.host}")
	private String profileHost;

	@Override
	public String getBaseUrl() {
		return profileHost;
	}
	
	@Resource
	@Lazy
	LoggerProvider steplogsLoggerProvider;
	
	private Advisor[] advisors;
	@Override
	public Advisor[] getAdvisors() {
		if (advisors==null) {
			LoggingTraceMethodInterceptor.addClasses(
					LoggingUnit.Builder().catchPackages("io.steplogs.example.web.service").build(), 
					UserService.class);

			// Pointcut to intercept the methods if want to log on interfaces or class without @Logging, before sending out http requests and save the response as bean IO. 
			advisors = new Advisor [] { new LoggingMethodPointcut(steplogsLoggerProvider) };
		}
		return advisors;
	}
	
	private CloseableHttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(1024);
		connectionManager.setDefaultMaxPerRoute(1024);
		connectionManager.setConnectionConfigResolver(route -> {
			return ConnectionConfig.custom()
					.setConnectTimeout(15, TimeUnit.SECONDS)
					.setSocketTimeout(15, TimeUnit.SECONDS)
					.setTimeToLive(2, TimeUnit.MINUTES)
					.setValidateAfterInactivity(30, TimeUnit.SECONDS)
					.build();
		});

		return HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setConnectionManagerShared(true)
				.build();
	}
	
	@Override
	public RestClient getRestClient() {
		return RestClient
			.builder(new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient())))
			.baseUrl(getBaseUrl())
			
			// Pass the log id to the next service to form traces through HTTP request header
			.requestInterceptor(new LoggingHeaderClientHttpRequestInterceptor(steplogsLoggerProvider))
			.build();
	}

	@Bean
	UserService getUserRPCService() {
		return this.getProxyFactoryBean(UserService.class, this);
	}

}
