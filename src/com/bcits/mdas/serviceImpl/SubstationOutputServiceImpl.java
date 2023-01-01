package com.bcits.mdas.serviceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.SubstationOutputEntity;
import com.bcits.mdas.service.SubstationOutputService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class SubstationOutputServiceImpl extends GenericServiceImpl<SubstationOutputEntity>  implements SubstationOutputService
{

	
	@Override
	public List<?> getSubStationBYdistrict(String district) {
		if(district !=null)
		{
			String qry="SELECT SITECODE,id,SUBSTATION_NAME,SUBSTAtION_ADDRESS, SUBSTATION_DISTRICT,ACCURACY from vcloudengine.substation_output where SUBSTATION_DISTRICT=:district";
			System.out.println(qry);
			return postgresMdas.createNativeQuery(qry).setParameter("district",district).getResultList();
	//	return postgresMdas.createNamedQuery("SubstationOutputEntity.getSubstation").setParameter("substation_district",district).getResultList();
		}
		else
		{
			return null;
		}
		}

	@Override
	public byte[] getImage(HttpServletRequest request,HttpServletResponse response, int id, String imagetype) 
	{
		
		try
		{  
			String qry="SELECT * from vcloudengine.substation_output where id=:id";
		System.out.println(qry);	
		SubstationOutputEntity substation=(SubstationOutputEntity)postgresMdas.createNativeQuery(qry,SubstationOutputEntity.class).setParameter("id",id).getSingleResult();
		response.setContentType("image/jpeg/png");
		if(imagetype.equalsIgnoreCase("First"))
		{
		if(substation.getFirst_image()!=null)
		 {
			 byte photo[] =substation.getFirst_image();    	
	    	 OutputStream ot = null;
	    	 try {
				ot = response.getOutputStream();
				ot.write(photo);
				ot.close(); 
			} catch (IOException e) {
				
				e.printStackTrace();
			}   	
	         Base64 b=new Base64();
	         
			 return b.encodeBase64(photo);
        }
		else
		{
			return null;
		}
		}   
		else if(imagetype.equalsIgnoreCase("Second"))
		{
			if(substation.getSecond_image()!=null)
			 {
				 byte photo[] =substation.getSecond_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
			
		}
		else if(imagetype.equalsIgnoreCase("Third"))
		{
			if(substation.getThird_image()!=null)
			 {
				 byte photo[] =substation.getThird_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
		}
		else if(imagetype.equalsIgnoreCase("Fourth"))
		{
			if(substation.getFourth_image()!=null)
			 {
				 byte photo[] =substation.getFourth_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
		}
		else if(imagetype.equalsIgnoreCase("Fifth"))
		{
			if(substation.getFifth_image()!=null)
			 {
				 byte photo[] =substation.getFifth_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
		}
		else if(imagetype.equalsIgnoreCase("Six"))
		{
			if(substation.getSixth_image()!=null)
			 {
				 byte photo[] =substation.getSixth_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
		}
		else if(imagetype.equalsIgnoreCase("Seven"))
		{
			if(substation.getSeventh_image()!=null)
			 {
				 byte photo[] =substation.getSeventh_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
		}
		else if(imagetype.equalsIgnoreCase("eight"))
		{
			if(substation.getEighth_image()!=null)
			 {
				 byte photo[] =substation.getEighth_image();    	
		    	 OutputStream ot = null;
		    	 try {
					ot = response.getOutputStream();
					ot.write(photo);
					ot.close(); 
				} catch (IOException e) {
					
					e.printStackTrace();
				}   	
		         Base64 b=new Base64();
		         
				 return b.encodeBase64(photo);
	        }
			else
			{
				return null;
			}
			
		}
		
		else 
		{
			return null;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public List<Map<String, Object>> getSubstationAllDAta(String fromdate,String district,
			ModelMap model) {
		List<Map<String, Object>> result=new ArrayList<>();
		String qry="SELECT SITECODE, SUBSTATION_DISTRICT,SUBSTATION_NAME,SUBSTAtION_ADDRESS,ACCURACY from vcloudengine.substation_output where  to_date(TIMESTAMP, 'dd/MM/yyyy')<= to_date(:fromdate, 'dd/MM/yyyy')  and state !='' \n" +
				"order by SITECODE,SUBSTATION_DISTRICT";
		System.out.println(">>>>>>>>>>>"+qry);
		
		 List<?> list=postgresMdas.createNativeQuery(qry).setParameter("fromdate", fromdate).getResultList();
		 
		 if(list.size()>0){
		 for (Iterator<?> iterator = list.iterator(); iterator.hasNext();)
		   {
				Object[] obj1 = (Object[]) iterator.next();
				Map<String, Object> data=new HashMap<>();
				data.put("sitecode", obj1[0]);
				data.put("subdistrict", obj1[1]);
				data.put("subname", obj1[2]);
				data.put("subaddress", obj1[3]);
				data.put("accurcy", obj1[4]);
				result.add(data);
			}
		      model.put("msg2", "display");
		   }
		 else
		     {
			   model.put("msg3", "notdisplay");
		     }
		 
		 return result;
	}

}
