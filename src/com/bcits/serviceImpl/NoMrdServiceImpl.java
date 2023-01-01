package com.bcits.serviceImpl;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.entity.NOMRDEntity;
import com.bcits.service.MeterMasterService;
import com.bcits.service.NoMrdService;
import com.bcits.utility.Resultupdated;

@Repository
public class NoMrdServiceImpl extends GenericServiceImpl<NOMRDEntity> implements NoMrdService  {
	
	
	
	@Autowired
	private MeterMasterService metermasterervice;
	

	@Override
	public ArrayList<Resultupdated> insertNoMRD(
			HttpServletRequest request, JSONArray array) {
		
		
		
		
		
		Resultupdated res = null;
		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();
		
		
		

		
		
		NOMRDEntity noMRDEntity=null;
		
		try{

			
			
			for (int i = 0; i < array.length(); i++)
			{
				try{

					res = new Resultupdated();
					
					
					
					
					
					
					noMRDEntity = new NOMRDEntity();
					
					
					JSONObject json =array.getJSONObject(i);
					//consumerOutputLiveMIPEntity.setId(0);

					noMRDEntity.setRdngmonth(Integer.parseInt(json.getString("RDNGMONTH")));
					
					
					
					noMRDEntity.setId(Integer.parseInt(json.getString("ID")));
					noMRDEntity.setTadesc(json.getString("TADESC"));
					noMRDEntity.setMeterno(json.getString("METRNO"));
					noMRDEntity.setMrname(json.getString("MRNAME"));
					noMRDEntity.setName(json.getString("NAME"));
					noMRDEntity.setReadingremark(json.getString("READINGREMARK"));
					noMRDEntity.setMrino(json.getString("MRINO"));
					
					 noMRDEntity.setReadingdate(json.getString("READINGDATE"));
					
					//noMRDEntity.setReadingdate(new Date()); // need to change
					
					noMRDEntity.setRemark(json.getString("REAMRK"));
					noMRDEntity.setAccno(json.getString("ACCNO"));
					noMRDEntity.setExtra3(json.getString("SERVERTOMOBILEDATE"));
					
					noMRDEntity.setExtra4(json.getString("ADDRESS"));
					noMRDEntity.setExtra1(json.getString("NEWREMARK"));
					noMRDEntity.setReason(json.getString("REASON"));
					noMRDEntity.setExtra2(json.getString("TIMESTAMP"));
					noMRDEntity.setVersion(json.getString("SDKVERSION"));
					
					noMRDEntity.setExtra5(json.getString("MNP"));
					noMRDEntity.setSbmtoserverdate(new Date());
					
					
					
					
					
					save(noMRDEntity);
					
					
					
					
					if(noMRDEntity.getExtra1().trim().equals("CMRI not possible")){
						
						// need to update flag to metermaster 
						
						int result = metermasterervice.UpdateNoMRDflag(noMRDEntity.getAccno(), noMRDEntity.getRdngmonth(), noMRDEntity.getMrname());
						
						
						
						
					}
					
					
					
					
					
					
					
					
					
					res.status="UPDATED";
					res.connectionNo = noMRDEntity.getAccno();
					
					
					
					

				}
				catch(Exception e){
					
					e.printStackTrace();
					
					res.status="NOTUPDATED";
					res.connectionNo = noMRDEntity.getAccno();
					
					

				}
				
				noMRDEntity=null;
				
				list.add(res);
				
				
				
			}
			
			
		
	}
		
		catch(Exception e){
			
			
		}
	
		
	
		
		
		// TODO Auto-generated method stub
		return list;
	}
	
	
}
