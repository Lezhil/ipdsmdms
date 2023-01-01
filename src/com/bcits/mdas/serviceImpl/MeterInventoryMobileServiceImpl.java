package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.MeterInventoryMobileEntity;
import com.bcits.mdas.entity.SurveyOutputMobileEntity;
import com.bcits.mdas.service.MeterInventoryMobileService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class MeterInventoryMobileServiceImpl extends
		GenericServiceImpl<MeterInventoryMobileEntity>
		implements MeterInventoryMobileService {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MeterInventoryMobileEntity> findAll(String sitecode , String mrcode) {
		return postgresMdas.createNamedQuery(
				"MeterInventoryMobileEntity.findAll",
				MeterInventoryMobileEntity.class)
				.setParameter("sitecode", sitecode)
				.setParameter("mrflag", mrcode)
				.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getConsumersForMeterChange(String sitecode , String mrcode, String dtcCode) {
		/*return postgresMdas.createNativeQuery("select * from meter_data.survey_master where sdocode = :sitecode and accno is not null and accno not in ("
				+ "select accno from meter_data.survey_output where sdocode = :sitecode )")
				.setParameter("sitecode", sitecode)
//				.setParameter("mrflag", mrcode)
				.getResultList();*/
		return postgresMdas.createNativeQuery("select * from spdcl.meter_change_view where sdocode = :sitecode and dtccode = :dtcCode ")
				.setParameter("sitecode", sitecode)
				.setParameter("dtcCode", dtcCode)
				.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getConsumerDetailsMeterChangedAlready(String sitecode , String mrcode) {
		/*return entityManager.createNativeQuery("select * from meter_data.survey_master where sdocode = :sitecode and accno is not null and accno not in ("
				+ "select accno from meter_data.survey_output where sdocode = :sitecode )")
				.setParameter("sitecode", sitecode)
//				.setParameter("mrflag", mrcode)
				.getResultList();*/
		return postgresMdas.createNativeQuery("SELECT * FROM meter_data.survey_output where sdocode = :sitecode  and to_date(survey_timings,'YYYY-MM-DD')<'2019-06-04' and status = '0' ")
				.setParameter("sitecode", sitecode)
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getDistinctDTCForMeterChange(String sitecode , String mrcode) {
		/*return postgresMdas.createNativeQuery("select * from meter_data.survey_master where sdocode = :sitecode and accno is not null and accno not in ("
				+ "select accno from meter_data.survey_output where sdocode = :sitecode )")
				.setParameter("sitecode", sitecode)
//				.setParameter("mrflag", mrcode)
				.getResultList();*/
		return postgresMdas.createNativeQuery("select distinct dtccode from spdcl.meter_change_view where sdocode = :sitecode   ")
				.setParameter("sitecode", sitecode)
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean isConsumerExist(String accno,String newmeterno, String sdocode) {
	
		boolean result = false ;
		List<?> dataList=null;
		try
		{
			dataList= postgresMdas.createNativeQuery("SELECT * FROM meter_data.survey_output "
					+ "WHERE accno='"+accno+"' AND sdocode='"+sdocode+"' and newmeterno = '"+newmeterno+"' ").getResultList();
			BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList.toString() + " dataList.isEmpty():"+dataList.isEmpty());
			if(dataList.isEmpty()){
				result = true ;
			}else{
				result = false ;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result  ;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean updateMeterInstalled(String meterNo, String sdocode) {
	
		boolean result = false ;
		int dataList=0;
		try
		{
			dataList= postgresMdas.createNativeQuery("update meter_data.meter_inventory set meter_status = 'INSTALLED' where sitecode = '"+sdocode+"' and meterno = '"+meterNo+"' ").executeUpdate();
			BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList + " dataList.isEmpty():"+dataList);
			if(dataList>0){
				result = true ;
			}else{
				result = false ;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result  ;
	}

	@Override
	public boolean isertToSurveyOutput(SurveyOutputMobileEntity downloadEntity) {
		boolean result = false ;

		String rawquerryy = "insert into meter_data.survey_output (sdocode,kno,accno,consumername,address,mobileno,meterno,"
				+ "connectiontype,poleno,dtcno,latitude,longitude,premise,sticker_no,land_mark,meter_image,premise_image,"
				+ "mrcode,appversion,imei,survey_timings,observation,old_metermake,old_mf,old_ctrn,old_ctrd,currdngkwh,"
				+ "finalreading,newmeterno,newmetermake,newmetertype,newmf,newctratio,newinitialreading,newmeterimage,"
				+ "mcrreportimage,newmeterkvah,oldmeterkvah,newmeterkva,oldmeterkva,oldmeterno_correction,tenderno) values"
				+ "('"
				+ downloadEntity.getSdocode()+"','"	
				+ downloadEntity.getKno()+"','"	
				+ downloadEntity.getAccno()+"','"	
				+ downloadEntity.getConsumername()+"','"	
				+ downloadEntity.getAddress()+"','"	
				+ downloadEntity.getMobileno()+"','"	
				+ downloadEntity.getMeterno()+"','"	
				+ downloadEntity.getConnectiontype()+"','"	
				+ downloadEntity.getPoleno()+"','"	
				+ downloadEntity.getDtcno()+"','"	
				+ downloadEntity.getLatitude()+"','"	
				+ downloadEntity.getLongitude()+"','"	
				+ downloadEntity.getPremise()+"','"	
				+ downloadEntity.getSticker_no()+"','"	
				+ downloadEntity.getLand_mark()+"',:oldimage,:premiseimage,'"	
				+ downloadEntity.getMrcode()+"','"	
				+ downloadEntity.getAppversion()+"','"	
				+ downloadEntity.getImei()+"','"	
				+ downloadEntity.getSurvey_timings()+"','"	
				+ downloadEntity.getObservation()+"','"	
				+ downloadEntity.getOld_metermake()+"','"	
				+ downloadEntity.getOld_mf()+"','"	
				+ downloadEntity.getOld_ctrn()+"','"	
				+ downloadEntity.getOld_ctrd()+"','"	
				+ downloadEntity.getCurrdngkwh()+"','"	
				+ downloadEntity.getFinalreading()+"','"	
				+ downloadEntity.getNewmeterno()+"','"	
				+ downloadEntity.getNewmetermake()+"','"	
				+ downloadEntity.getNewmetertype()+"','"	
				+ downloadEntity.getNewmf()+"','"	
				+ downloadEntity.getNewctratio()+"','"	
				+ downloadEntity.getNewinitialreading()+"',:newimage,:mcreportimage,'"	
				+ downloadEntity.getNewmeterkvah()+"','"	
				+ downloadEntity.getOldmeterkvah()+"','"	
				+ downloadEntity.getNewmeterkva()+"','"	
				+ downloadEntity.getOldmeterkva()+"','"		
				+ downloadEntity.getOldmeterno_correction()+"','"		
				+ downloadEntity.getTenderno()+"');";			
				
		int res = 0; 
		res = postgresMdas.createNativeQuery(rawquerryy)
				.setParameter("oldimage", downloadEntity.getMeter_image())
				.setParameter("premiseimage", downloadEntity.getPremise_image())
				.setParameter("newimage", downloadEntity.getNewmeterimage())
				.setParameter("mcreportimage", downloadEntity.getMcrreportimage())
				.executeUpdate();

		if(res>0){
			result = true;
		}
		return result  ;
	}
	
}
