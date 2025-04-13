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
import io.steplogs.example.web.service.MedicService;
import io.steplogs.example.web.service.rpc.UserService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api")
public class MedicConditionController {

	private static final ObjectMapper objectMapper = new ObjectMapper()
			.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
	
	@Resource
	MedicService medicService;

	@Resource
	UserService userService;
	
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
		List<MedicConditionDO> queryRet = medicService.query(session_id, page);
		return JsonToString(queryRet, callback);
	}

	// Hit the api with URL: http://localhost/api/medic/save?id=123&deceases=unkown
	@RequestMapping(value = "/medic/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestParam(value = "callback", defaultValue="") String callback,
			MedicCondition medicCondition) throws JsonProcessingException {
		MedicConditionDO medicRet = medicService.save(medicCondition);
		return JsonToString(medicRet, callback);
	}
	
	protected <T> ResponseEntity<String> JsonToString(T result, String callback) throws JsonProcessingException {
		if (callback==null || callback.isEmpty()) {
			return ResponseEntity.ok(objectMapper.writeValueAsString(result));
		} else {
			return ResponseEntity.ok(callback + "(" + objectMapper.writeValueAsString(result) + ")");
		}
	}
}
