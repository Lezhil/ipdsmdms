package com.bcits.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.ConsumerOutputLiveMIPEntity;
import com.bcits.service.ConsumerOutputLiveMIPService;


@Repository
public class ConsumerOutputLiveMIPServiceImpl  extends GenericServiceImpl<ConsumerOutputLiveMIPEntity> implements ConsumerOutputLiveMIPService {

	
	
	
	
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ConsumerOutputLiveMIPEntity> findAll()
	{
		return postgresMdas.createNamedQuery("ConsumerOutputLiveMIPEntity.findAll").getResultList();

	}
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public  void updateMobileDataToConsumerOutPutLive(HttpServletRequest request, JSONArray array) {
		
		
		ConsumerOutputLiveMIPEntity consumerOutputLiveMIPEntity=null;
		
		try{

			
			
			for (int i = 0; i < array.length(); i++)
			{
				try{

					
					consumerOutputLiveMIPEntity = new ConsumerOutputLiveMIPEntity();
					
					
					JSONObject json =array.getJSONObject(i);
					//consumerOutputLiveMIPEntity.setId(0);

					consumerOutputLiveMIPEntity.setMeterno(json.getString("METERNO"));
					consumerOutputLiveMIPEntity.setConsumerid(json.getString("CONSUMERID"));
					
					
					consumerOutputLiveMIPEntity.setConsumername(json.getString("CONSUMERNAME"));
					consumerOutputLiveMIPEntity.setAddress(json.getString("ADDRESS"));
					consumerOutputLiveMIPEntity.setCmrid(json.getString("CMRID"));
					consumerOutputLiveMIPEntity.setBillmonth(json.getString("BILLMONTH"));
					consumerOutputLiveMIPEntity.setPkwh(json.getString("PKWH"));
					consumerOutputLiveMIPEntity.setPkvah(json.getString("PKVAH"));
					consumerOutputLiveMIPEntity.setPkva(json.getString("PKVA"));
					consumerOutputLiveMIPEntity.setPkw(json.getString("PKW"));
					consumerOutputLiveMIPEntity.setPpf(json.getString("PPF"));
					consumerOutputLiveMIPEntity.setCkwh(json.getString("CKWH"));
					consumerOutputLiveMIPEntity.setCkvah(json.getString("CKVAH"));
					consumerOutputLiveMIPEntity.setCkva(json.getString("CKVA"));
					consumerOutputLiveMIPEntity.setCkw("");
					consumerOutputLiveMIPEntity.setCpf(json.getString("CPF"));
					consumerOutputLiveMIPEntity.setOldseal(json.getString("OLDSEAL"));
					consumerOutputLiveMIPEntity.setCurrentseal(json.getString("CURRENTSEAL"));
					consumerOutputLiveMIPEntity.setMeterremarks(json.getString("METERREMARKS"));
					consumerOutputLiveMIPEntity.setOtherremarks(json.getString("OTHERREMARKS"));
					consumerOutputLiveMIPEntity.setPhotoid("");
					consumerOutputLiveMIPEntity.setMrname(json.getString("MRNAME"));
					consumerOutputLiveMIPEntity.setIndustryType(json.getString("INDUSTRYTYPE"));
					consumerOutputLiveMIPEntity.setDemandType(json.getString("DEMANDTYPE"));
					consumerOutputLiveMIPEntity.setSyncstatus(json.getString("SYNCSTATUS"));
					consumerOutputLiveMIPEntity.setDevicefirmwareversion(json.getString("DEVICEFIRMWAREVERSION"));
					consumerOutputLiveMIPEntity.setSubmitdatetimestamp(json.getString("SUBMITDATETIMESTAMP"));
					consumerOutputLiveMIPEntity.setServertomobiledate(json.getString("SERVERTOMOBILEDATE"));
					
					consumerOutputLiveMIPEntity.setSubmitstatus(json.getString("SUBMITSTATUS"));
					consumerOutputLiveMIPEntity.setSbmno(json.getString("SBM_NO"));
					consumerOutputLiveMIPEntity.setExtra1(json.getString("PHONE"));
					consumerOutputLiveMIPEntity.setExtra2(json.getString("EXTRA2"));
					consumerOutputLiveMIPEntity.setExtra3(json.getString("EXTRA3"));
					consumerOutputLiveMIPEntity.setExtra4(json.getString("EXTRA4"));
					consumerOutputLiveMIPEntity.setExtra5(json.getString("EXTRA5"));
					
					
					
					
					save(consumerOutputLiveMIPEntity);
					consumerOutputLiveMIPEntity=null;
					

				}
				catch(Exception e){
					
					e.printStackTrace();
					

				}
			}
			
			
		
	}
		
		catch(Exception e){
			
			
		}
	
		
	}
		
	
}
