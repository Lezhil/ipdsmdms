package com.bcits.mdas.serviceImpl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.mdas.entity.SiteLocationEntity;
import com.bcits.mdas.service.SiteLocationService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class SiteLocationServiceImpl extends GenericServiceImpl<SiteLocationEntity> implements SiteLocationService {


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SiteLocationEntity> getAllSiteLocations(ModelMap model, HttpServletRequest request) {
		
		return postgresMdas.createNamedQuery("SiteLocationEntity.findAll").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object> getAllSiteCodes() {
		
		return postgresMdas.createNamedQuery("SiteLocationEntity.findAllSDOCodes").getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getAllSdoCodes()
	{
		return postgresMdas.createNamedQuery("SiteLocationEntity.findSections").getResultList();
	}
	
	/*code by manjunath*/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getSectionCodes(String type)
	{
	    int val=0;
		if(type.equals("circle"))
		{
			val=2;
		}
		else if(type.equals("division"))
		{
			val=3;
		}
		else if(type.equals("subdivision"))
		{
			val=4;
		}
		else
		{
			val=5;
		}
		String sql="select DISTINCT :type,substr( cast(sitecode as VARCHAR) ,0,:val) from vcloudengine.location ORDER BY substr( cast(sitecode as VARCHAR) ,0,:val)";
		Query query=postgresMdas.createNativeQuery(sql).setParameter("type", type).setParameter("val", val);
	    return query.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getOnlySectionCodes()
	{
		return postgresMdas.createNamedQuery("SiteLocationEntity.getSectionCodes").getResultList();
	}
	
	@Override
	public void getTreeSiteCodes(String code,HttpServletResponse response) {
		try
		{	
			int val=0;
			String type="";
			String siteCode="";
			if(code.equalsIgnoreCase("cescIp"))
			{
				siteCode="%";
				type="company";
				val=2;
			}
			else if(code.length()==1 && code.equals("0"))//for very first time get all circle data
			{
				siteCode="%";
				type="circle";
				val=2;
			}
			else if(code.length()==1 && !code.equals("0"))//when click on circle
			{
				siteCode=code+"_%";
				type="division";
				val=3;
			}
			else if(code.length()==2)
			{
				siteCode=code+"_%";
				type="subdivision";
				val=4;
			}
			else
			{
				siteCode=code+"_%";
				type="section";
				val=5;
			}
			String sql="select DISTINCT :type,substr( cast(sitecode as VARCHAR) ,0,:val) from vcloudengine.location WHERE cast(sitecode as VARCHAR) LIKE :siteCode ORDER BY substr( cast(sitecode as VARCHAR) ,0,:val)";
			Query query=postgresMdas.createNativeQuery(sql).setParameter("siteCode", siteCode).setParameter("type", type).setParameter("val", val);
		    List list=query.getResultList();
		
			if(list.size()>0)
			{
			   List<JSONObject> jsonList=new ArrayList<JSONObject>();
			    for (Iterator iterator = list.iterator(); iterator.hasNext();)
			    {
					Object[] data =  (Object[]) iterator.next();					
					JSONObject obj=new JSONObject();					
				      try {
				    	
				    	  String s=(String)data[0];
				    	  if(s.equals("CESCOM"))
				    	  {
				    		  
				    		  obj.put("name", "CESC_IP");				         
						    	 obj.put("aliasName","CESC_IP");
						    	 if(code.length()==3)//for section display only item not folder
						    	 {
						    		 obj.put("name","<a href=# style='text-decoration: none !important;' onclick='getRelatedSiteCodeData("+data[1]+");'>"+data[0]+"</a>"); 
						    		 obj.put("type", "item");
						    	 }
						    	 else
						    	 {
						    		 obj.put("type", "folder");
						    	 }				    	
								 JSONObject childobj=new JSONObject();
								 childobj.put("id", 0);
								 childobj.put("head","Cesc");
								 obj.put("additionalParameters",childobj);
								 jsonList.add(obj);
								 break;
				    		  
				    	  }
				    	  else
				    	  {
				         obj.put("name", data[0]);				         
				    	 obj.put("aliasName",data[0]);
				    	 System.out.println("---------Section------------"+data[0]);
				    	 if(code.length()==3)//for section display only item not folder
				    	 {
				    		 obj.put("name","<a href=# style='text-decoration: none !important;' onclick='getRelatedSiteCodeData("+data[1]+");'>"+data[0]+"</a>"); 
				    		 obj.put("type", "item");
				    	 }
				    	 else
				    	 {
				    		 obj.put("type", "folder");
				    	 }				    	
						 JSONObject childobj=new JSONObject();
						 childobj.put("id", data[1]);
						 childobj.put("head",data[0]);
						 obj.put("additionalParameters",childobj);
						 jsonList.add(obj);
				    	  }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			    PrintWriter p=response.getWriter();				
				p.write(jsonList+"");
			     
		}
		else {
			 PrintWriter p=response.getWriter();				
				p.write(null+"");
		}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void getTreeSiteCodesForAsset(String code,HttpServletResponse response) {
		try
		{	
			int val=0;
			String type="";
			String siteCode="";
			if(code.equalsIgnoreCase("CESC"))
			{
				siteCode="%";
				type="company";
				val=2;
			}
			else if(code.length()==1 && code.equals("0"))//for very first time get all circle data
			{
				siteCode="%";
				type="circle";
				val=2;
			}
			else if(code.length()==1 && !code.equals("0"))//when click on circle
			{
				siteCode=code+"_%";
				type="division";
				val=3;
			}
			else if(code.length()==2)
			{
				siteCode=code+"_%";
				type="subdivision";
				val=4;
			}
			else
			{
				siteCode=code+"_%";
				type="section";
				val=5;
			}
			/*String sql="select DISTINCT "+type+",substr( cast(sitecode as VARCHAR) ,0,"+val+") from vcloudengine.location WHERE cast(sitecode as VARCHAR) LIKE :siteCode ORDER BY substr( cast(sitecode as VARCHAR) ,0,"+val+") ;";*/
			String sql="select DISTINCT :type,substr( cast(newsitecode as VARCHAR) ,0,:val) from vcloudengine.location WHERE cast(newsitecode as VARCHAR) LIKE :siteCode AND cast(newsitecode as VARCHAR) NOT LIKE '9%' ORDER BY substr( cast(newsitecode as VARCHAR) ,0,:val)";
			Query query=postgresMdas.createNativeQuery(sql).setParameter("siteCode", siteCode).setParameter("type", type).setParameter("val", val);
		    List list=query.getResultList();
		
			if(list.size()>0)
			{
			   List<JSONObject> jsonList=new ArrayList<JSONObject>();
			    for (Iterator iterator = list.iterator(); iterator.hasNext();)
			    {
					Object[] data =  (Object[]) iterator.next();					
					JSONObject obj=new JSONObject();					
				      try {
				    	
				    	  String s=(String)data[0];
				    	  if(s.equals("CESCOM"))
				    	  {
				    		  
				    		  obj.put("name", "CESC");				         
						    	 obj.put("aliasName","CESC");
						    	 if(code.length()==3)//for section display only item not folder
						    	 {
						    		 obj.put("name","<a href=# style='text-decoration: none !important;' onclick='getRelatedSiteCodeDataForAsset("+data[1]+");'>"+data[0]+"</a>"); 
						    		 obj.put("type", "item");
						    	 }
						    	 else
						    	 {
						    		 obj.put("type", "folder");
						    	 }				    	
								 JSONObject childobj=new JSONObject();
								 childobj.put("id", 0);
								 childobj.put("head","Cesc");
								 obj.put("additionalParameters",childobj);
								 jsonList.add(obj);
								 break;
				    		  
				    	  }
				    	  else
				    	  {
				         obj.put("name", data[0]);				         
				    	 obj.put("aliasName",data[0]);
				    	 System.out.println("---------Section------------"+data[0]);
				    	 if(code.length()==3)//for section display only item not folder
				    	 {
				    		 obj.put("name","<a href=# style='text-decoration: none !important;' onclick='getRelatedSiteCodeData("+data[1]+");'>"+data[0]+"</a>"); 
				    		 obj.put("type", "item");
				    	 }
				    	 else
				    	 {
				    		 obj.put("type", "folder");
				    	 }				    	
						 JSONObject childobj=new JSONObject();
						 childobj.put("id", data[1]);
						 childobj.put("head",data[0]);
						 obj.put("additionalParameters",childobj);
						 jsonList.add(obj);
				    	  }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			    PrintWriter p=response.getWriter();				
				p.write(jsonList+"");
			     
		}
		else {
			 PrintWriter p=response.getWriter();				
				p.write(null+"");
		}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getDbuser(String sdocode)
	{
		String dbUser="";
		try
		{
			dbUser=(String) postgresMdas.createNamedQuery("SiteLocationEntity.findDbUser").setParameter("siteCode", Integer.parseInt(sdocode)).getSingleResult();
		} 
		catch (Exception e) 
		{
			
			return "noVal";
		}
		return dbUser;
		
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SiteLocationEntity> findBySdocode(String sdocode)
	{
		List<SiteLocationEntity> list=null;
		try
		{
			list=postgresMdas.createNamedQuery("SiteLocationEntity.findBySdocode").setParameter("siteCode", Integer.parseInt(sdocode)).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> findDistinctDivision(ModelMap model, HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="SELECT division,substr(cast(sitecode as varchar), 0,3)  FROM vcloudengine.location GROUP BY division,substr(cast(sitecode as varchar), 0,3)";
		try
		{
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> findDistinctSubDivision(String divSdo, ModelMap model,
			HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="SELECT subdivision,substr(cast(sitecode as varchar), 0,4)  FROM vcloudengine.location WHERE substr(cast(sitecode as varchar), 0,3) like :divSdo"+" "
 +"GROUP BY subdivision,substr(cast(sitecode as varchar), 0,4)";
		try
		{
			list=postgresMdas.createNativeQuery(sql).setParameter("divSdo", divSdo).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> findByLocType(String locType, ModelMap model,HttpServletRequest request)
	{
		List<Object[]> list=null;
		String sql="";
		try
		{
			//cast(sitecode as varchar)not IN ('2000','1000')
			if(locType.equalsIgnoreCase("circle"))
			{
				sql="SELECT circle,substr(cast(sitecode as varchar), 0,2) FROM vcloudengine.location   GROUP BY circle,substr(cast(sitecode as varchar), 0,2) ORDER BY substr(cast(sitecode as varchar), 0,2)";
			}
			if(locType.equalsIgnoreCase("division"))
			{
				sql="SELECT division,substr(cast(sitecode as varchar), 0,3) FROM vcloudengine.location  GROUP BY division,substr(cast(sitecode as varchar), 0,3) ORDER BY substr(cast(sitecode as varchar), 0,3);";
			}
			if(locType.equalsIgnoreCase("subdivision"))
			{
				sql="SELECT subdivision,substr(cast(sitecode as varchar), 0,4) FROM vcloudengine.location   GROUP BY subdivision,substr(cast(sitecode as varchar), 0,4) ORDER BY substr(cast(sitecode as varchar), 0,4);";
			}
			if(locType.equalsIgnoreCase("section"))
			{
				sql="SELECT section,sitecode||'',dbuser FROM vcloudengine.location  ORDER BY sitecode;";
			}
			
			list=postgresMdas.createNativeQuery(sql).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SiteLocationEntity> getLocationByMail(String dbUser)
	{
		List<SiteLocationEntity> list=null;
		BCITSLogger.logger.info("===============>dbUser"+dbUser);
		String sql="Select d From SiteLocationEntity d Where d.dbUser like :dbUser";
		try
		{
			list=postgresMdas.createQuery(sql).setParameter("dbUser", dbUser).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
}
