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
//		io.steplogs.logger.boostrap.LoggingInitiation.premain(null);// ensure the first to run if don't use javaagent; no need if it's in spring
		new SpringApplicationBuilder(SteplogsIntegrationExampleBootApplication.class).run(args);//, args);
	}
}
