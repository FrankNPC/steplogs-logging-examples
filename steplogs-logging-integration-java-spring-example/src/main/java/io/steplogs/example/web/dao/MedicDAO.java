package io.steplogs.example.web.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import io.steplogs.example.web.model.MedicCondition;
import io.steplogs.example.web.model.Page;
import io.steplogs.logger.annotation.Logging;

@Repository
@Logging
public class MedicDAO {
	//We could use mybatis interface, then it requires APO or proxy on interfaces to get logged.
	
	public List<MedicCondition> query(long userId, Page page){
		MedicCondition medic1 = new MedicCondition();
		medic1.setDeceases("idk, maybe conding fever");
		medic1.setId(System.currentTimeMillis());
		medic1.setTimeCreated(System.currentTimeMillis());
		medic1.setUserId(123L);
		MedicCondition medic2 = new MedicCondition();
		medic2.setDeceases("idk, maybe conding fever");
		medic2.setId(System.currentTimeMillis());
		medic2.setTimeCreated(System.currentTimeMillis());
		medic2.setUserId(456L);
		MedicCondition medic3 = new MedicCondition();
		medic3.setDeceases("idk, maybe conding fever");
		medic3.setId(System.currentTimeMillis());
		medic3.setTimeCreated(System.currentTimeMillis());
		medic3.setUserId(789L);
		return Arrays.asList(medic1, medic2, medic3);
	}
	
	public MedicCondition save(MedicCondition condition){
		MedicCondition medic1 = new MedicCondition();
		medic1.setDeceases(condition.getDeceases());
		medic1.setId(condition.getId());
		medic1.setTimeCreated(condition.getTimeCreated());
		medic1.setUserId(condition.getUserId());
		return medic1;
	}
}
