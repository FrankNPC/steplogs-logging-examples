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
		return UUID.randomUUID().toString();
	}
	
}
