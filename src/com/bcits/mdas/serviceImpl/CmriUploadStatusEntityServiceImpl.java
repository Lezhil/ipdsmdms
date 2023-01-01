package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.CmriUploadStatusEntity;
import com.bcits.mdas.service.CmriUploadStatusService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class CmriUploadStatusEntityServiceImpl extends GenericServiceImpl<CmriUploadStatusEntity> implements CmriUploadStatusService
{

	@Override
	public List<?> getcmriUploadData(String meterNumber,String month) 
	{	
		List<?> list=null;
			//String sql="SELECT * FROM meter_data.cmri_upload_status WHERE meter_number='"+meterNumber+"' AND month ='"+month+"' AND parsed =1";
			String sql="SELECT * FROM meter_data.xml_upload_summary WHERE meterno='"+meterNumber+"' AND month ='"+month+"' AND status='parsed' ";
			try{
				list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				return list;
			}catch(Exception e)
			{
			 e.printStackTrace(); 
			 return null;
			}
			
	}

	@Override
	public List<?> getAllUploadStatusData(String month) {
		List result=new ArrayList();
		//String qry="SELECT * FROM meter_data.cmri_upload_status WHERE month='"+month+"' ORDER BY time_stamp DESC";
		String qry="SELECT * FROM meter_data.xml_upload_summary WHERE month='"+month+"' ORDER BY uploaddate DESC";
		try {
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}

	@Override
	public List<?> getMeterExistInMDAS(String meterNumber) {
		List result=new ArrayList();
		String qry="SELECT * FROM meter_data.master_main WHERE mtrno='"+meterNumber+"'";
		try {
		result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}
	
}
