package io.steplogs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "io.steplogs.example" })
public class SteplogsIntegrationExampleBootApplication {

	public static void main(String[] args) {
		//io.steplogs.logger.boostrap.LoggingInitiation.premain(null); // must load before everything. or add https://github.com/FrankNPC/steplogs-logging-examples/blob/main/steplogs-logging-integration-java-spring-example/src/main/resources/META-INF/spring.factories 
		new SpringApplicationBuilder(SteplogsIntegrationExampleBootApplication.class).run(args);
	}
}
