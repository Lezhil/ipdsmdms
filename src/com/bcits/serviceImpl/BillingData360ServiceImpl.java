package com.bcits.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.service.BillingData360Service;

@Service
public class BillingData360ServiceImpl extends GenericServiceImpl<MeterMaster> implements BillingData360Service{

	@Override
	public List<MeterMaster> getBillingData360(String billMonth,
			String meterno, HttpServletRequest request, ModelMap model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*@Override
	public List<MeterMaster> getBillingData360(String billMonth,String meterno,HttpServletRequest request,ModelMap model)
	{
		List<MeterMaster> list= entityManager.createNamedQuery("MeterMaster.FindAllData1").setParameter("metrno", meterno).setParameter("rdngmonth", Integer.parseInt(billMonth)).getResultList();
		
		model.put("billingData", list);	
		model.put("portletTitle", "Billing Details");
		model.put("meterNo", meterno);
		model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}
		String query="SELECT BB.*,BB.CURRDNGKWH-BB.PRE_MONTH_KWH AS CONSUMPTION FROM"+" "
+"("+" "
+"SELECT AA.*,(SELECT CURRDNGKWH FROM METERMASTER WHERE RDNGMONTH=TO_CHAR(ADD_MONTHS(TO_DATE(AA.RDNGMONTH,'YYYYMM'),-1),'YYYYMM') AND ACCNO=AA.ACCNO)"+" "
+"AS PRE_MONTH_KWH FROM"+" "
+"("+" "
+"SELECT MM.RDNGMONTH,MA.CIRCLE,MA.DIVISION,MA.SDONAME, MM.ACCNO,MM.METRNO,MM.MF,MA.CONTRACTDEMAND, COALESCE(MM.XCURRDNGKWH,0),COALESCE(MM.XCURRRDNGKVAH,0),COALESCE(MM.XCURRDNGKVA,0),MM.CURRDNGKWH"+" "
+"FROM BSMARTMDM.MASTER MA,BSMARTMDM.METERMASTER MM WHERE MA.ACCNO=MM.ACCNO AND MM.METRNO='"+meterno+"' AND MM.RDNGMONTH BETWEEN "+" "
+"TO_CHAR(ADD_MONTHS(TO_DATE('"+billMonth+"','YYYYMM'),-5),'YYYYMM') AND '"+billMonth+"' ORDER BY RDNGMONTH DESC"+" "
+")AA"+" "
+")BB";
		String[] consuptionArr=null;
	    String[] monthArr=null;
		MDMLogger.logger.info("==============="+query);
		List<Object[]> data=entityManager.createNativeQuery(query).getResultList();
		if(data.size()>0)
		{
			System.out.println("data->"+data.size());
			System.err.println("data.get(0).length-->"+data.get(0).length);
			model.put("dataArrLength", data.get(0).length);
			model.put("billedDataList", data);
			model.put("viewCategory", "BilledData");
			consuptionArr=new String[data.size()-1];
		    monthArr=new String[data.size()-1];
		    for (int i = 0; i < data.size(); i++)
			{
				Object[] value=data.get(i);
				System.out.println("value--length->"+value.length);
				for (int j = 0; j < value.length; j++)
				{
					if(j==13)
					{
						if(i<data.size()-1)
						{
								consuptionArr[i]=value[j]+"";
						}
					}
					if(j==0)
					{
						if(i<data.size()-1)
						{
							try {
								String value1=value[j]+"";
	                			String value11=new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(value1));
								monthArr[i]=value11;
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		    request.setAttribute("monthArr", monthArr);
			request.setAttribute("consuptionArr", consuptionArr);
		}
		
	    consuptionArr=new String[data.size()-1];
	    monthArr=new String[data.size()-1];
		
		
		return list;
	}
*/
}
