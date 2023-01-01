package com.bcits.service;

import java.util.List;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.SurveyorDetailsEntity;

public interface SurveyorDetailsService extends GenericService<SurveyorDetailsEntity>{

	List<SurveyorDetailsEntity> surveyorList();

	List<SurveyorDetailsEntity> activeSurveyorList();

	String sequenceSurveyorID();

	List<SurveyorDetailsEntity> surveyorDetails(String id);

	

	

}
