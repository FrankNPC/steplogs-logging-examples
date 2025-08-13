package io.steplogs.example.web.test;

import java.util.UUID;

import io.steplogs.example.web.model.Page;
import io.steplogs.logger.annotation.Logging;

@Logging
public class ForStaticMethod {
	
	public String query(long userId, Page page){
		return UUID.randomUUID().toString();
	}

	public static String runStatic(long userId){
		return "e1d2fb44-c29c-4925-93cc-99081578eaf8";
	}
	
}
