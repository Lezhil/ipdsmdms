/**
 * 
 */
package com.bcits.mdas.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.FeederMRMasterEntity;
import com.bcits.service.GenericService;

/**
 * @author user11
 *
 */
public interface FeederMRMasterService extends GenericService<FeederMRMasterEntity> {

	/**
	 * Fetching all MRMasters details in the form of List.
	 * 
	 * @return List object which contains all MRMasterEntity Objects.
	 */
	public List<FeederMRMasterEntity> findAllMRMasters();

	public FeederMRMasterEntity findMRMaster(String mrCode);

	public Integer updateMRMaster(FeederMRMasterEntity mrMasterEntity);

	public List<String> getMatchedMRCodes();

	public Integer deleteMRMaster(String mrCode);
	
	public String validateForMrMaster(String mrCode,String password);

	public List<FeederMRMasterEntity> findMobileUser(String userName,String password);

	public List<String> getMatchedMRCodesForAllocation();
	
	byte[] getPhoto(ModelMap model,HttpServletRequest request,HttpServletResponse response,String mrCode) throws IOException;

	void updateMRMasterData(FeederMRMasterEntity mrMasters,HttpServletRequest request,Date current_date,ModelMap model,String groups,String operation);

	List<FeederMRMasterEntity> getNotAllocatedOperators();

	public List<Map<String, Object>> getMobileNumberFromMrMaster(String mrcode, String password);
}
