package com.bcits.mdas.serviceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.mdas.entity.FeederMRMasterEntity;
import com.bcits.mdas.service.FeederMRMasterService;
import com.bcits.mdas.service.SiteLocationService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class FeederMRMasterServiceImpl extends GenericServiceImpl<FeederMRMasterEntity>	implements	FeederMRMasterService {
	
	@Autowired
	private SiteLocationService siteLocationService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMRMasterEntity> findAllMRMasters() {
		BCITSLogger.logger.info("fetching is started");
		return postgresMdas.createNamedQuery("FeederMRMasterEntity.findAllMRMasters").getResultList();

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public FeederMRMasterEntity findMRMaster(String mrCode) 
	{
		try {
			return (FeederMRMasterEntity) postgresMdas.createNamedQuery("FeederMRMasterEntity.findMRMaster").setParameter("mrCode", mrCode).getSingleResult();					
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return null;

	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public byte[] getPhoto(ModelMap model,HttpServletRequest request,HttpServletResponse response,String mrCode) throws IOException 
	{		
		 response.setContentType("image/jpeg/png");
		/* MRMasterComPK pKey=new MRMasterComPK();
		 pKey.setMrCode(mrCode);
		 pKey.setSdoCode(sdoCode);
		 FeederMRMasterEntity data=find(pKey); */
		 
		 FeederMRMasterEntity data=find(mrCode);
		 if(data.getImage()!=null)
		 {
			 byte photo[] = data.getImage();    	
	    	 OutputStream ot = null;
	    	 ot = response.getOutputStream();
	         ot.write(photo);
	         ot.close();    	
	         Base64 b=new Base64();
	         b.encodeBase64(photo);
			 return b.encodeBase64(photo);
		 }
    	 else {
			return null;
		}
			    	
    }
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateMRMaster(FeederMRMasterEntity mrMasterEntity) {
		
		/*MRMasterComPK pKey=new MRMasterComPK();
		pKey.setMrCode(mrMasterEntity.getMrCode());
		pKey.setSdoCode(mrMasterEntity.getSdoCode());
		FeederMRMasterEntity oldData= find(pKey);*/
		FeederMRMasterEntity oldData= find(mrMasterEntity.getMrCode());
		mrMasterEntity.setAllocationFlag(oldData.getAllocationFlag());
		if(mrMasterEntity.getImage()==null)
		{
			mrMasterEntity.setImage(oldData.getImage());
		}
		 update(mrMasterEntity);
		 return 0;
		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getMatchedMRCodes() {

		return postgresMdas.createNamedQuery("FeederMRMasterEntity.findMatchedMRCodes").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteMRMaster(String mrCode) {

		return postgresMdas.createNamedQuery("FeederMRMasterEntity.removeMRMaster").setParameter("mrCode", mrCode).executeUpdate();

	}
	
	public String validateForMrMaster(String mrCode,String password) {
		
		String status = "0";

		List<FeederMRMasterEntity> list =  postgresMdas.createNamedQuery("FeederMRMasterEntity.validateForMrMaster")
				.setParameter("mrCode", mrCode)
				.setParameter("password", password).getResultList();
				if(list.size() > 0)
				{
					status = "1";
				}
				return status;

	}
	
	
public List<Map<String, Object>> getMobileNumberFromMrMaster(String mrCode,String password) {
		
		Map<String, Object> map = null;
		List<Map<String, Object>> categoriesList = new ArrayList<>();

		List<Object[]> list =  postgresMdas.createNamedQuery("FeederMRMasterEntity.getMobileNumFromMrMaster")
				.setParameter("mrCode", mrCode)
				.setParameter("password", password).getResultList();
				if(list.size() > 0)
				{
					 map = new HashMap<String, Object>();
					 map.put("mobileNo", list.get(0));
					 categoriesList.add(map);
					
					
				}
				return categoriesList;

	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<FeederMRMasterEntity> findMobileUser(String userName,String password) 
	{
		return postgresMdas.createNamedQuery("FeederMRMasterEntity.findMobileUser").setParameter("USERNAME", userName).setParameter("PASSWORD", password).getResultList();
	}

	@Override
	public List<String> getMatchedMRCodesForAllocation() {

		return postgresMdas.createNamedQuery("FeederMRMasterEntity.getMatchedMRCodesForAllocation").getResultList();
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateMRMasterData(FeederMRMasterEntity mrMasters,HttpServletRequest request,Date current_date,ModelMap model,String groups,String operation)
	{
		
		// for group
		try {
			
		
		
		if (operation.equals("add") && find(mrMasters.getMrCode())!=null)
		{
			model.put("results", "Entered Device operator code already exist");
		}
		else {
		// end group
		
			mrMasters.setUsername((String) request.getSession().getAttribute("username"));
			mrMasters.setTimestamp(new Timestamp(current_date.getTime()));

			//Added by manjunath
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("image1");
			 try {
				 if(multipartFile.getSize()>0)
				 {
					 byte[] image = IOUtils.toByteArray(multipartFile.getInputStream());				
						mrMasters.setImage(image);	
				}			
			} 
			 catch (IOException e)
			{				
				e.printStackTrace();
			}
			//end by manjunath
			 
			//continue for group
			 
			 //List<MrGroup> grpList=new ArrayList<MrGroup>();		
			 //String[] str=groups.split(",");	
			  
			 
				 //delete if there are records related to mrcode and sdocode
				  /*int res= postgresMdas.createNamedQuery("MrGroup.DeleteData").setParameter("mrcode", mrMasters.getMrCode()).executeUpdate();
				    
					 int id=0;
				 	 try
					 {
						 id=(int)postgresMdas.createNamedQuery("MrGroup.MaxId").getSingleResult();
					 } 
					 catch (Exception e) 
					 {
						e.printStackTrace();
					 }
				 
				 for (int i = 0; i < str.length; i++)
				 {
					 MrGroup mrGrp= new MrGroup();			
				     mrGrp.setId(id+(i+1));	
					 mrGrp.setMrcode(mrMasters.getMrCode());			
					 mrGrp.setGrpName(str[i]);			
					 grpList.add(mrGrp);
				}
				 
				 mrMasters.setMrgroups(grpList);*/
			 
			 //end group
			
			 
			if (operation.equals("add")) 
			{
				//to set defalut Image
				/*if(multipartFile.getSize()==0)
				{
				byte[] bFile = 	userService.defaultImage(request);
				mrMasters.setImage(bFile);			
				}*/
				//End default image
				mrMasters.setPassword("1234");//hardcode PWD
				save(mrMasters);
				model.put("results", "Device Operaor Added Successfully");
			} 
			if(operation.equals("update"))
			{
				updateMRMaster(mrMasters);			
				model.put("results", "Device Operaor Updated Successfully");
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//model.put("activeModules",	moduleMasterService.findAllActivatedModuleMasters());
		model.put("mrmList", findAllMRMasters());
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		model.put("mrMasters", new FeederMRMasterEntity());
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<FeederMRMasterEntity> getNotAllocatedOperators()
	{
			return postgresMdas.createNamedQuery("FeederMRMasterEntity.GetNotAllocatedOperators").getResultList();
		
	}
}
