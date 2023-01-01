package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.MRDeviceAllocationEntity;
import com.bcits.mdas.service.MRDeviceAllocationService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class MRDeviceAllocationServiceImpl extends
GenericServiceImpl<MRDeviceAllocationEntity>
implements MRDeviceAllocationService {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MRDeviceAllocationEntity> findAllMRDeviceAllocations() {

		return postgresMdas.createNamedQuery(
				"MRDeviceAllocationEntity.findAll",
				MRDeviceAllocationEntity.class).getResultList();
	}



	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean[] isUserExist(String sdoCode,String bmdReading, String imeiNumber ) 
	{
		boolean[] result = {false , false};
		List<MRDeviceAllocationEntity> dataList=null ;
		try
		{

			System.out.println("sdoCode"+sdoCode);
			System.out.println("bmdReading"+bmdReading);
			System.out.println("imeiNumber"+imeiNumber);
			dataList = postgresMdas.createNamedQuery("MRDeviceAllocationEntity.isUserExist")
					.setParameter("sdoCode", Integer.parseInt(sdoCode))
					.setParameter("mrCode", bmdReading)
					.setParameter("deviceid", imeiNumber)
					.setMaxResults(1).getResultList();
			BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList.toString() + " dataList.isEmpty():"+dataList.isEmpty());
			if(!dataList.isEmpty())
			{
				result[0] = true ;

				/*Long count = (Long)postgresMdas.createNamedQuery("MRDeviceAllocationEntity.isDateExist")	.setParameter("sdoCode", sdoCode) .setParameter("bmdReading", bmdReading).getSingleResult();

					if (count > 0)
						result[1] = true;
					else
						result[1] = false;*/


				result[1] = true;



			}else
			{
				result[0] = false ;
				result[1] = false;
			}


		}
		catch(Exception e)
		{
			result[0] = false ;
			result[1] = false;
			BCITSLogger.logger.error("isUserExist:",e);
			e.printStackTrace();
		}
		return result  ;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean[] isUserExistOtherApps(String sdoCode,String bmdReading, String imeiNumber ) 
	{
		boolean[] result = {false , false};
		
		return result  ;
	}





	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String[] getLoginCredentials(String imeiNumber ) 
	{
		String[] result = {"" , ""};
		String sdocode = "";
		String mrcode = "";

		List<MRDeviceAllocationEntity> dataList=null ;
		try
		{


			dataList= postgresMdas.createNamedQuery("MRDeviceAllocationEntity.getLoginCredentials").setParameter("deviceid", imeiNumber).getResultList();
			System.out.println("SELECT sdocode,mrcode from vcloudengine.deviceallocation where deviceid = '"+imeiNumber+"' ");
			//				dataList= postgresMdas.createNativeQuery("SELECT sdocode,mrcode from vcloudengine.deviceallocation where deviceid = '"+imeiNumber+"' ").getResultList();
			BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList.get(0).getMrCode() + " dataList.isEmpty():"+dataList.isEmpty());
			if(!dataList.isEmpty())
			{
				sdocode= String.valueOf(dataList.get(0).getSdoCode());
				mrcode =String.valueOf(dataList.get(0).getMrCode());	
				result[0] = sdocode ;
				result[1] = mrcode;



			}else
			{
				result[0] = sdocode ;
				result[1] = mrcode;
			}


		}
		catch(Exception e)
		{
			result[0] = sdocode ;
			result[1] = mrcode;
			e.printStackTrace();
		}
		return result  ;
	}
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String[] getMRDetails(String imeiNumber ) 
	{
		String[] result = {"" , ""};
		String sdocode = "";
		String mrcode = "";

		List<?> dataList=null ;
		try
		{


			//			dataList= postgresMdas.createNamedQuery("MRDeviceAllocationEntity.getLoginCredentials").setParameter("deviceid", imeiNumber).getResultList();
			System.out.println("SELECT mrname,mrmobileno from vcloudengine.mrtable where device = '"+imeiNumber+"' ");
			dataList= postgresMdas.createNativeQuery("SELECT mrname,mrmobileno from vcloudengine.mrtable where device = '"+imeiNumber+"' ").getResultList();
			BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList.get(0) + " dataList.isEmpty():"+dataList.isEmpty());
			if(!dataList.isEmpty())
			{
				Object[] arrayobj = (Object[]) dataList.get(0);
				sdocode= String.valueOf(arrayobj[0]);
				mrcode =String.valueOf(arrayobj[1]);	
				result[0] = sdocode ;
				result[1] = mrcode;



			}else
			{
				result[0] = sdocode ;
				result[1] = mrcode;
			}


		}
		catch(Exception e)
		{
			result[0] = sdocode ;
			result[1] = mrcode;
			e.printStackTrace();
		}
		return result  ;
	}

















}
