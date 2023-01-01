package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bcits.entity.AssessmentEntity;

public interface AssessmentsService extends GenericService<AssessmentEntity>
{

	Object getDistinctCircle();

	Object getDistinctBillmonth();

	Object getDistinctCategory();

	Object getDistinctTamperType();

	List<AssessmentEntity> getAssesmentDetails(String billmonth, String circle,String category, String tamperType, String discom);

	List<AssessmentEntity> getAssesmentMoreDetails(String billmonth,String circle, String category, String tamperType, String meterNo);

	List<?> getMeterLifeCycleData(String mtrNo,String flag,HttpSession session,String Fdrcategory);

}
