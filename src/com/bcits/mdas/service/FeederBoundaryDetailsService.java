/**
 * 
 */
package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.FeederEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface FeederBoundaryDetailsService extends GenericService<FeederEntity> {


	//public List<?> getfeederdetails(String ssid);
	
	public List<?> getTownByCircle(String circle,String zone);

	public List<?> getSubStaTionByTown(String town,String circle,String zone);

	List<?> generateBoundaryReportData();

	List<?> getfeederdetails(String ssid, String tp_towncode);

	//List<FeederEntity> getMeterDetailsByFdrcode(String fdrcode);
	
	//List<FeederEntity> getAllDetailsbyId(String ruleId);
	
	//List<?> getfeederdetails(String ssid,String tp_towncode);
	
	void getBoundaryDetailsPdf(HttpServletRequest request,HttpServletResponse response,String circle,String town,String substation);

	void getBoundaryPdf(HttpServletRequest request,HttpServletResponse response,String zone,String circle,String town,String townnames,String substation,String substationname);

	public void getRFMReportPDF(HttpServletRequest request, HttpServletResponse response);

}
