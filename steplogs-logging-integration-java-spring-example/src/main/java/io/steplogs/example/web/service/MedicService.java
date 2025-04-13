package io.steplogs.example.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.steplogs.example.web.dao.MedicDAO;
import io.steplogs.example.web.model.MedicCondition;
import io.steplogs.example.web.model.MedicConditionDO;
import io.steplogs.example.web.model.Page;
import io.steplogs.example.web.model.User;
import io.steplogs.example.web.service.rpc.UserService;
import io.steplogs.logger.annotation.Logging;
import jakarta.annotation.Resource;

@Service
@Logging
public class MedicService {

	@Resource
	UserService userService;

	@Resource
	MedicDAO medicDAO;
	
	public List<MedicConditionDO> query(String sessionId, Page page){
		User getUserRet = userService.getBySessionId(sessionId);
		if (getUserRet == null) {
			return null;
		}
		List<MedicCondition> medicList = medicDAO.query(getUserRet.getId(), page);
//		List<MedicCondition medicList = medicDAO.query(0L, page);
		if (medicList!=null) {
			return medicList.stream().map(medic -> {
				User userRet = userService.getById(medic.getUserId());
				MedicConditionDO medicConditionDO = new MedicConditionDO();
				medicConditionDO.setMedicConditionDO(medic);
				medicConditionDO.setUser(userRet);
				return medicConditionDO;
			}).collect(Collectors.toList());
		}
		return null;
	}
	
	public MedicConditionDO save(MedicCondition condition){
		MedicCondition medicCondition = medicDAO.save(condition);
		if (medicCondition == null) {
			return null;
		}
		
		User userRet = userService.getById(medicCondition.getUserId());
		MedicConditionDO medicConditionDO = new MedicConditionDO();
		medicConditionDO.setMedicConditionDO(medicCondition);
		medicConditionDO.setUser(userRet);
		return medicConditionDO;
	}

}
