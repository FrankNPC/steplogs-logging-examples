package io.steplogs.example.web.service.rpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import io.steplogs.example.web.model.User;
import io.steplogs.logger.Logger;
import io.steplogs.logger.annotation.Logging;
import io.steplogs.spring.rmi.http.prodiver.Provider;
import jakarta.annotation.Resource;

@Provider // RPC service
@Service
@Logging
public class UserServiceImpl { // impelements UserService {
	// In general it should implement UserService, but there will be duplicated beans in the same service.
	// So use the same name UserService to mock the service provider to match the service subscriber
	// it provide a service endpoint at http://localhost/api/user/get_by_id.
	@Resource
	Logger logger;
	
	public User getById(Long userId){
		if (userId==null) {
			return null;
		}

		logger.log("This should be remote service, will be with trace", userId);
		User user = new User();
		user.setId(123L);
		try {
			SimpleDateFormat simpleDateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			user.setBirthday(simpleDateFormater.parse("2025-08-10T15:42:17.123+02"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setDriverLisenceId("a number that you can't know");
		user.setName("randome guy probably");
		return user;
	}

	public User getBySessionId(String sessionId){
		if (sessionId==null) {
			return null;
		}
		User user = new User();
		user.setId(456L);
		try {
			SimpleDateFormat simpleDateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			user.setBirthday(simpleDateFormater.parse("2025-08-10T15:42:17.123+02"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setDriverLisenceId("a number that you can't know");
		user.setName("randome guy probably");
		return user;
	}

	public User save(User user){
		if (user==null) {
			return null;
		}

		user.setId(789L);
		try {
			SimpleDateFormat simpleDateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			user.setBirthday(simpleDateFormater.parse("2025-08-10T15:42:17.123+02"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		user.setDriverLisenceId("new DL Id");
//		user.setName("john");
		return user;
	}
}
