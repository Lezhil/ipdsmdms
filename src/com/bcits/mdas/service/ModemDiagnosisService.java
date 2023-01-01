package com.bcits.mdas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.ModemDiagnosisEntity;
import com.bcits.service.GenericService;

public interface ModemDiagnosisService extends GenericService<ModemDiagnosisEntity> {

	List<ModemDiagnosisEntity> getModemDiagnosisStats(String zone, String circle, String division,String subdiv, ModelMap model, HttpServletRequest request);

	List<ModemDiagnosisEntity> getModemDiagnosisCat(String zone, String circle, String division, String subdiv, String category, ModelMap model,HttpServletRequest request);
	//List<ModemDiagnosisEntity> getModemDiagnosisCat(String category, ModelMap model,HttpServletRequest request);

	List<?> getmodemcount(String zone, String circle, String division,String subdiv,ModelMap model);

	List<?> getValDiagStat(String zone, String circle, String division,String subdiv, String category, ModelMap model);

}
