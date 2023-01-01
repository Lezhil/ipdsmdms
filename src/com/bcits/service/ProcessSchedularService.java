package com.bcits.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcits.entity.ProcessSchedular;

@Service
public interface ProcessSchedularService extends GenericService<ProcessSchedular> {

	List<?> getValidationData();

	List<?> getEstimationData();

	List<?> getSavedDataList();

	List<?> getEditedDataList(String id);

	ProcessSchedular getEntityById(int parseInt);

	List getDataList(String id);


}
