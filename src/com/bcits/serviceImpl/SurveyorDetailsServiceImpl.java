package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.SurveyorDetailsEntity;
import com.bcits.service.SurveyorDetailsService;
@Repository
public class SurveyorDetailsServiceImpl extends GenericServiceImpl<SurveyorDetailsEntity> implements SurveyorDetailsService{
 @Override
	public List<SurveyorDetailsEntity> surveyorList(){
	 return postgresMdas.createNamedQuery("SurveyorDetailsEntity.surveyorData").getResultList();
  }

@Override
public List<SurveyorDetailsEntity> activeSurveyorList() {
	  return postgresMdas.createNamedQuery("SurveyorDetailsEntity.activeSurveyorList").getResultList();
}

@Override
public String sequenceSurveyorID() {
	String sql="select lv || ( case when LENGTH(ID)=1 then '000' || ID when LENGTH(ID)=2 then '00' || ID when LENGTH(ID)=3 then '0' || ID when LENGTH(ID)=4 then ID END ) As lv_id FROM (select substr(lv_id, 0, 3) as lv, cast (cast(substr (lv_id, 3, LENGTH(lv_id))  As numeric ) + 1 as text ) as ID FROM ( SELECT COALESCE (MAX(suid), 'SN0000') AS lv_id from meter_data.surveyordetails )a )b";
	String seqId=(String) postgresMdas.createNativeQuery(sql).getSingleResult();
	return seqId;
	
}

@Override
public List<SurveyorDetailsEntity> surveyorDetails(String id) {
	return postgresMdas.createNamedQuery("SurveyorDetailsEntity.surveyorDetails").setParameter("id", Long.parseLong(id)).getResultList();
}
	
}
