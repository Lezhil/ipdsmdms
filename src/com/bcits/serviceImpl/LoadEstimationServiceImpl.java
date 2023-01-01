package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.LoadSurveyEstimated;
import com.bcits.service.LoadEstimationService;
@Repository
public class LoadEstimationServiceImpl extends GenericServiceImpl<LoadSurveyEstimated> implements LoadEstimationService {

	@Override
	public List<LoadSurveyEstimated> getDataForEstimationCheck(String type) {
		
		if("Current".equals(type)) {
			return getCustomEntityManager("postgresMdas").createNamedQuery("LoadSurveyEstimated.getdataForCheckCurrent", LoadSurveyEstimated.class).getResultList();
		} else if("Voltage".equals(type)) {
			return getCustomEntityManager("postgresMdas").createNamedQuery("LoadSurveyEstimated.getdataForCheckVoltage", LoadSurveyEstimated.class).getResultList();
		} else {
			return null;
		}
		
	}

}
