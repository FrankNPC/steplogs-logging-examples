package io.steplogs.example.web.service.rpc;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.steplogs.example.web.model.User;
import io.steplogs.logger.annotation.Logging;
import io.steplogs.spring.rmi.http.prodiver.Provider;

@Provider // RPC service
@Service
@Logging
public class UserServiceImpl { 
	// In general it should implement UserService, but there will be duplicated beans.
	// So use the same name UserService to mock the service provider to match the service subscriber
	
	public User getById(Long userId){
		if (userId==null) {
			return null;
		}
		User user = new User();
		user.setId(System.currentTimeMillis());
		user.setBirthday(new Date());
		user.setDriverLisenceId("a number that you can't know");
		user.setName("randome guy probably");
		return user;
	}

	public User getBySessionId(String sessionId){
		if (sessionId==null) {
			return null;
		}
		User user = new User();
		user.setId(System.currentTimeMillis());
		user.setBirthday(new Date());
		user.setDriverLisenceId("a number that you can't know");
		user.setName("randome guy probably");
		return user;
	}
	
}
