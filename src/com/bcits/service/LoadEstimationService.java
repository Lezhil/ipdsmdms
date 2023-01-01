package com.bcits.service;

import java.util.List;

import com.bcits.entity.LoadSurveyEstimated;

public interface LoadEstimationService extends GenericService<LoadSurveyEstimated> {

	List<LoadSurveyEstimated> getDataForEstimationCheck(String type);

}
