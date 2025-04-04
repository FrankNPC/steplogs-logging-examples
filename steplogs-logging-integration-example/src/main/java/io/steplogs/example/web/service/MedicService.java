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
import io.steplogs.refino.Return;
import jakarta.annotation.Resource;

@Service
@Logging
public class MedicService {

	@Resource
	UserService userService;

	@Resource
	MedicDAO medicDAO;
	
	public Return<List<MedicConditionDO>> query(String sessionId, Page page){
		Return<User> getUserRet = userService.getBySessionId(sessionId);
		if (!getUserRet.isSuccess()) {
			return Return.Builder.builder().copyInfo(getUserRet).build();
		}
		List<MedicCondition> medicList = medicDAO.query(getUserRet.getEntity().getId(), page);
//		List<MedicCondition> medicList = medicDAO.query(0L, page);
		if (medicList!=null) {
			return Return.OK(medicList.stream().map(medic -> {
				Return<User> userRet = userService.getById(medic.getUserId());
				MedicConditionDO medicConditionDO = new MedicConditionDO();
				medicConditionDO.setMedicConditionDO(medic);
				medicConditionDO.setUser(userRet.getEntity());
				return medicConditionDO;
			}).collect(Collectors.toList()));
		}
		return Return.Error("Nothing");
	}
	
	public Return<MedicConditionDO> save(MedicCondition condition){
		MedicCondition medicCondition = medicDAO.save(condition);
		if (medicCondition == null) {
			return Return.Error("Failed to save");
		}
		
		Return<User> userRet = userService.getById(medicCondition.getUserId());
		MedicConditionDO medicConditionDO = new MedicConditionDO();
		medicConditionDO.setMedicConditionDO(medicCondition);
		medicConditionDO.setUser(userRet.getEntity());
		return Return.OK(medicConditionDO);
	}

}
