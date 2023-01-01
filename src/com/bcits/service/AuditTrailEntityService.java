package com.bcits.service;


import java.util.List;

import com.bcits.entity.AuditTrailEntity;

public interface AuditTrailEntityService extends GenericService<AuditTrailEntity> {

	List<?> getAuditDetails(String mtrNum, String fromDate, String toDate);
	List<?> getDataAquisition(String mtrNo,String fromDate,String toDate );
	List<?> getMtrData(String mtrNum,String date);
    List<?> getValidationTransactions(String mtrNum,String fromDate,String toDate);
    List<?> getSingleValidationData(String MtrNo,String date);
	List<?> getEstimationTransactions(String mtrNum, String fromDate,
			String toDate, String locationType);
	List<?> getSingleEstimationData(String mtrNo, String date);
	
}
