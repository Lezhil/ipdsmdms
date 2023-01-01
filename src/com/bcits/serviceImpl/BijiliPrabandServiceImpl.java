package com.bcits.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bcits.entity.MeterMaster;
import com.bcits.service.BijiliPrabandService;

@Service
public class BijiliPrabandServiceImpl extends GenericServiceImpl<MeterMaster> implements BijiliPrabandService{

	@Override
	public JSONArray getBillDataByMonthly(HttpServletRequest req) {

		System.out.println("insde monthly bill queryy");
		JSONObject obj=new JSONObject();
		JSONArray jary=new JSONArray();
		try 
		{
			String rdngmonth=req.getParameter("billmonth");
		int	rdngmonth1=Integer.parseInt(rdngmonth);
			//int	rdngmonth1=201811;
			List<MeterMaster> list=null;
			try {
	           list= postgresMdas.createNamedQuery("MeterMaster.getAllBillingData").setParameter("rdgMonth",rdngmonth1).getResultList();
		
			} catch (Exception e) {
				e.printStackTrace();
			}
		System.err.println(list.size());
		if (list.size() > 0) 
		{
			for(int i=0;i<list.size();i++)
			{
				obj=new JSONObject();
				obj.put("kno", list.get(i).getMaster().getKno());
				obj.put("meterno", list.get(i).getMetrno());
				obj.put("billmonth", list.get(i).getRdngmonth());
				obj.put("kwh", (list.get(i).getXcurrdngkwh()==null?"0":list.get(i).getXcurrdngkwh()));
				obj.put("kva", (list.get(i).getXcurrdngkva()==null?"0":list.get(i).getXcurrdngkva()));
				obj.put("kvah", (list.get(i).getXcurrrdngkvah()==null?"0":list.get(i).getXcurrrdngkvah()));
				
				jary.put(obj);
			
			}
			
		}
		else
		{
			obj.put("noData", "noData");
		}
		
		} catch (Exception e) 
		{
			e.printStackTrace();
				return jary;
		}
		return jary;
	}

	
	
}
