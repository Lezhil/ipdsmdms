package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.mdas.service.ModemMasterServiceMDAS;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemMasterServiceImplMDAS extends GenericServiceImpl<ModemMasterEntity> implements ModemMasterServiceMDAS 
{

	@SuppressWarnings("unchecked")
	@Override
	public List<ModemMasterEntity> findAll() {
		List<ModemMasterEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("ModemMasterEntity.findAll").getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ModemMasterEntity getEntityByImei(String imei) {
		try{
			return postgresMdas.createNamedQuery("ModemMasterEntity.getEntityByImei", ModemMasterEntity.class).setParameter("modem_imei", imei).getSingleResult();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> findNotInstalledIMEI() {
		String sql="SELECT modem_imei FROM meter_data.modem_master WHERE modem_imei NOT in( SELECT modem_sl_no from meter_data.master_main) ";
		try{
			return postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object  getCountForMappedMeters() {
		String query = "SELECT count(*) as totalMappedMeters\n" +
	              "FROM meter_data.initial_meter_info  WHERE sync_status=1 and data_type='MasterInfo' ";
		try {
			return  postgresMdas.createNativeQuery(query).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
