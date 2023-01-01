package com.bcits.mdas.service;

import java.util.List;

import com.bcits.entity.CmriUploadStatusEntity;
import com.bcits.service.GenericService;

public interface CmriUploadStatusService extends GenericService<CmriUploadStatusEntity>
{
	public List<?> getcmriUploadData(String meterNumber,String month);
	public List<?> getMeterExistInMDAS(String meterNumber);
	public List<?> getAllUploadStatusData(String month);
	
}
