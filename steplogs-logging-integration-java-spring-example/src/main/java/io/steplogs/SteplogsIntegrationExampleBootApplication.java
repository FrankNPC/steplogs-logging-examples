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
		new SpringApplicationBuilder(SteplogsIntegrationExampleBootApplication.class).run(args);
	}
}
