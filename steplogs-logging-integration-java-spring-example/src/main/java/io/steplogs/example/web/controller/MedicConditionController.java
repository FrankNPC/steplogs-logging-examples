package io.steplogs.example.web.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.steplogs.example.web.model.MedicCondition;
import io.steplogs.example.web.model.MedicConditionDO;
import io.steplogs.example.web.model.Page;
import io.steplogs.example.web.model.User;
import io.steplogs.example.web.service.MedicService;
import io.steplogs.example.web.service.SMTPEmailScheduler;
import io.steplogs.example.web.service.rpc.UserService;
import io.steplogs.example.web.test.ForStaticMethod;
import io.steplogs.logger.Logger;
import io.steplogs.logger.provider.LoggerFactory;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class MedicConditionController {

	private static final ObjectMapper objectMapper = new ObjectMapper()
			.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
	
	private static final Logger logger = LoggerFactory.getAsyncLogger();
	
	@Resource
	MedicService medicService;

	@Resource
	UserService userService;
	
	@Resource
	SMTPEmailScheduler emailScheduler;
	
	// Hit the api with URL: http://localhost/api/medic/query?session_id=xxx&offset=0&size=123
	@RequestMapping(value = "/medic/query", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> query(@RequestParam(value = "callback", defaultValue="") String callback,
			@RequestParam(value = "session_id", defaultValue="") String session_id,
			@RequestParam(value = "offset", defaultValue="") Integer offset,
			@RequestParam(value = "size", defaultValue="") Integer size
			) throws JsonProcessingException {
		Page page = new Page();
		page.setOffset(offset);
		page.setSize(size);
		ForStaticMethod.runStatic(System.currentTimeMillis());
		List<MedicConditionDO> queryRet = medicService.query(session_id, page);
		return JsonToString(queryRet, callback);
	}

	// Hit the api with URL: http://localhost/api/medic/save?id=123&deceases=unkown
	@RequestMapping(value = "/medic/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestParam(value = "callback", defaultValue="") String callback,
			MedicCondition medicCondition) throws JsonProcessingException {
		logger.log("save request paylod, should be with trace", medicCondition);
		new ForStaticMethod().query(System.currentTimeMillis(), null);//should not print log
		MedicConditionDO medicRet = medicService.save(medicCondition);
		return JsonToString(medicRet, callback);
	}

	// Hit the api with URL: http://localhost/api/user/save?name=broking&driverLisenceId=123456&age=1001
	@RequestMapping(value = "/user/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestParam(value = "callback", defaultValue="") String callback,
			User user) throws JsonProcessingException {
		ForStaticMethod.runStatic(user.getId());
		User savedUser = userService.save(user);
		ForStaticMethod.runStatic(savedUser.getId());
		return JsonToString(savedUser, callback);
	}
	
	// Hit the api with URL: http://localhost/api/medic/test_async_email
	@RequestMapping(value = "/medic/test_async_email", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testAsyncEmail() throws JsonProcessingException{
//		emailScheduler.childThreadDelivery("test@email.email", "http://localhost/childThreadDelivery/"+System.currentTimeMillis());
		emailScheduler.unHookedThreadDelivery("test@email.email", "http://localhost/unHookedThreadDelivery/"+System.currentTimeMillis());
		return JsonToString(new MedicConditionDO(), "");
	}
	
	protected <T> ResponseEntity<String> JsonToString(T result, String callback) throws JsonProcessingException {
		if (callback==null || callback.isEmpty()) {
			return ResponseEntity.ok(objectMapper.writeValueAsString(result));
		} else {
			return ResponseEntity.ok(callback + "(" + objectMapper.writeValueAsString(result) + ")");
		}
	}
}
