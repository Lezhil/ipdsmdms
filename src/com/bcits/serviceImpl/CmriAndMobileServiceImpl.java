package com.bcits.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.CDFData;
import com.bcits.entity.D2Data;
import com.bcits.entity.D3Data;
import com.bcits.entity.D4Data;
import com.bcits.entity.D5Data;
import com.bcits.service.CmriAndMobileService;

@Repository
public class CmriAndMobileServiceImpl extends GenericServiceImpl<CDFData> implements CmriAndMobileService{

	
	@Override
	public BigDecimal findCDF_IDMobile(String meterNo, int billmonth) {
		System.out.println("inside  findCDF_IDMobile");
		String qry="SELECT CDF_ID FROM BSMARTMDM_TEST.CDF_DATA WHERE meterno='"+meterNo+"' AND BILLMONTH='"+billmonth+"'";
		System.out.println("qry-->"+qry);
		BigDecimal cdfId=null;
		try {
			cdfId=     (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			System.out.println( "list size--"+cdfId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cdfId;
		
	}
	
	@Override
	public BigDecimal findCDF_IDCmri(String meterNo, int billmonth) {
		String qry="SELECT CDF_ID FROM BSMARTMDM.CDF_DATA WHERE meterno='"+meterNo+"' AND BILLMONTH='"+billmonth+"'";
		System.out.println("qry-->"+qry);
		BigDecimal cdfId=null;
		try {
			cdfId=   (BigDecimal) postgresMdas.createNativeQuery(qry).getSingleResult();
			System.out.println( "list size--"+cdfId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cdfId;
		
	}

	//D1 mobile data
	@SuppressWarnings("unused")
	@Override
	public List<?> findD1_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri) {
		String qryMOBILE="";
		String qryCMRI="";
		List<D2Data> d1_Cmridata=null;
		List<D2Data> d1_Mobiledata=null;
		ArrayList<Map<String, Object>> D1result1=null;
		try{
		if(cdf_idMobile!=null)
		{
			System.out.println("inside D1 query mobile");
			qryMOBILE="SELECT METER_CLASS,METER_TYPE,MANUFACTURER_CODE,MANUFACTURER_NAME,SUBSTR(METERDATE, 0, 10) FROM BSMARTMDM_TEST.D1_DATA WHERE CDF_ID='"+cdf_idMobile+"'";
			System.out.println("qryMOBILE d1-->"+qryMOBILE);
			d1_Mobiledata=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
			
		}
		
		if(cdf_idCmri!=null)
		{
			qryCMRI="SELECT METER_CLASS,METER_TYPE,MANUFACTURER_CODE,MANUFACTURER_NAME,SUBSTR(METERDATE, 0, 10) FROM BSMARTMDM.D1_DATA WHERE CDF_ID='"+cdf_idCmri+"' ";
			System.out.println("qry cmri-->"+qryCMRI);
			d1_Cmridata=postgresMdas.createNativeQuery(qryCMRI).getResultList();
		}
		
		System.out.println("D1_dataMobile-size()--"+d1_Mobiledata.size());
		System.out.println("D1_dataCmri-size()--"+d1_Cmridata);
		 D1result1 =new ArrayList<>();
		if(d1_Cmridata!=null && d1_Mobiledata!=null)
		{
		 if(d1_Cmridata.size()>0 && d1_Mobiledata.size()>0 )
		 {
			 System.out.println("both  have D1 data");
			Iterator<?> iterator1 = d1_Cmridata.iterator();
			for (Iterator<?> iterator = d1_Mobiledata.iterator(); iterator.hasNext();) 
			{
				System.out.println("inside for loop");
				Map<String,Object> map=new HashedMap();
				Object[] obj = (Object[]) iterator.next();
				Object[] obj2 = (Object[]) iterator1.next();
				
				map.put("METER_CLASS_MOBILE", obj[0]);
				map.put("METER_CLASS_CMRI", obj2[0]);
				map.put("METER_TYPE_MOBILE", obj[1]);
				map.put("METER_TYPE_CMRI", obj2[1]);
				map.put("MANUFACTURER_CODE_MOBILE", obj[2]);
				map.put("MANUFACTURER_CODE_CMRI", obj2[2]);
				map.put("MANUFACTURER_NAME_MOBILE", obj[3]);
				map.put("MANUFACTURER_NAME_CMRI", obj2[3]);
				map.put("METERDATE_MOBILE", obj[4]);
				map.put("METERDATE_CMRI", obj2[4]);
				
				D1result1.add(map);
			}
		 }
		}
		 if(d1_Cmridata!=null)
		 {
		 if(d1_Cmridata.size()>0 && (d1_Mobiledata==null  ))
		 {
			 System.out.println("only cmri d1 data");
				/*Iterator<?> iterator1 = d1_Cmridata.iterator();*/
				for (Iterator<?> iterator = d1_Cmridata.iterator(); iterator.hasNext();) 
				{
					
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					/*Object[] obj2 = (Object[]) iterator1.next();*/
					
					map.put("METER_CLASS_MOBILE", "");
					map.put("METER_CLASS_CMRI", obj2[0]);
					map.put("METER_TYPE_MOBILE", "");
					map.put("METER_TYPE_CMRI", obj2[1]);
					map.put("MANUFACTURER_CODE_MOBILE", "");
					map.put("MANUFACTURER_CODE_CMRI", obj2[2]);
					map.put("MANUFACTURER_NAME_MOBILE", "");
					map.put("MANUFACTURER_NAME_CMRI", obj2[3]);
					map.put("METERDATE_MOBILE", "");
					map.put("METERDATE_CMRI", obj2[4]);
					
					D1result1.add(map);
				}
		 }
		 }
		 else if(d1_Mobiledata.size()>0 && (d1_Cmridata==null) )
		 {
			 System.out.println("only mobile d1 data");
				/*Iterator<?> iterator1 = d1_Cmridata.iterator();*/
				for (Iterator<?> iterator = d1_Mobiledata.iterator(); iterator.hasNext();) 
				{
					System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj = (Object[]) iterator.next();
					/*Object[] obj2 = (Object[]) iterator1.next();*/
					
					map.put("METER_CLASS_MOBILE", obj[0]);
					map.put("METER_CLASS_CMRI", "");
					map.put("METER_TYPE_MOBILE", obj[1]);
					map.put("METER_TYPE_CMRI", "");
					map.put("MANUFACTURER_CODE_MOBILE", obj[2]);
					map.put("MANUFACTURER_CODE_CMRI", "");
					map.put("MANUFACTURER_NAME_MOBILE", obj[3]);
					map.put("MANUFACTURER_NAME_CMRI", "");
					map.put("METERDATE_MOBILE", obj[4]);
					map.put("METERDATE_CMRI", "");
					
					D1result1.add(map);
				}
		 }
		 
		 
		 
		 }catch (Exception e) {
				e.printStackTrace();
			}
		return D1result1;
	}

	//D2data	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findD2_DataMobileCmri(BigDecimal cdf_idMobile,
			BigDecimal cdf_idCmri) {
		String qryMOBILE="";
		String qryCMRI="";
		List<D2Data> d2_Cmridata=null;
		List<D2Data> d2_Mobiledata=null;
		ArrayList<Map<String, Object>> D2result1=null;
		try{
		if(cdf_idMobile!=null)
		{
			qryMOBILE="SELECT R_PHASE_VAL,Y_PHASE_VAL,B_PHASE_VAL,R_PHASE_LINE_VAL ,Y_PHASE_LINE_VAL,B_PHASE_LINE_VAL,R_PHASE_ACTIVE_VAL,Y_PHASE_ACTIVE_VAL,B_PHASE_ACTIVE_VAL,R_PHASE_PF_VAL,\n"+
				"Y_PHASE_PF_VAL,B_PHASE_PF_VAL,AVG_PF_VAL,ACTIVE_POWER_VAL,PHASE_SEQUENCE,R_PHASE_CURRENT_ANGLE,Y_PHASE_CURRENT_ANGLE,B_PHASE_CURRENT_ANGLE,D2_KWH FROM BSMARTMDM_TEST.D2_DATA WHERE CDF_ID='"+cdf_idMobile+"'";
			System.out.println("qryMOBILE-->"+qryMOBILE);
			d2_Mobiledata=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
			
		}
		
		if(cdf_idCmri!=null)
		{
			qryCMRI="SELECT R_PHASE_VAL,Y_PHASE_VAL,B_PHASE_VAL,R_PHASE_LINE_VAL ,Y_PHASE_LINE_VAL,B_PHASE_LINE_VAL,R_PHASE_ACTIVE_VAL,Y_PHASE_ACTIVE_VAL,B_PHASE_ACTIVE_VAL,R_PHASE_PF_VAL,\n" +
				"Y_PHASE_PF_VAL,B_PHASE_PF_VAL,AVG_PF_VAL,ACTIVE_POWER_VAL,PHASE_SEQUENCE,R_PHASE_CURRENT_ANGLE,Y_PHASE_CURRENT_ANGLE,B_PHASE_CURRENT_ANGLE,D2_KWH FROM BSMARTMDM.D2_DATA WHERE CDF_ID='"+cdf_idCmri+"'";
			System.out.println("qry cmri-->"+qryCMRI);
			d2_Cmridata=postgresMdas.createNativeQuery(qryCMRI).getResultList();
		}
		
		
			System.out.println("D2_data Mobile-size()--"+d2_Mobiledata);
			System.out.println("D2_data Cmri-size()--"+d2_Cmridata);
			 D2result1 =new ArrayList<>();
			 
			 if(d2_Cmridata!=null && d2_Mobiledata!=null)
			 {
			 if(d2_Cmridata.size()>0 && d2_Mobiledata.size()>0 )
			 {
				 System.out.println("both have d2 data");
				Iterator<?> iterator1 = d2_Cmridata.iterator();
				for (Iterator<?> iterator = d2_Mobiledata.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					Object[] obj = (Object[]) iterator1.next();
					
					map.put("R_PHASE_VAL_CMRI", obj[0]);
					map.put("R_PHASE_VAL_MOBILE", obj2[0]);
					map.put("Y_PHASE_VAL_CMRI", obj[1]);
					map.put("Y_PHASE_VAL_MOBILE", obj2[1]);
					map.put("B_PHASE_VAL_CMRI", obj[2]);
					map.put("B_PHASE_VAL_MOBILE", obj2[2]);
					map.put("R_PHASE_LINE_VAL_CMRI", obj[3]);
					map.put("R_PHASE_LINE_VAL_MOBILE", obj2[3]);
					map.put("Y_PHASE_LINE_VAL_CMRI", obj[4]);
					map.put("Y_PHASE_LINE_VAL_MOBILE", obj2[4]);
					map.put("B_PHASE_LINE_VAL_CMRI", obj[5]);
					map.put("B_PHASE_LINE_VAL_MOBILE", obj2[5]);
					map.put("R_PHASE_ACTIVE_VAL_CMRI", obj[6]);
					map.put("R_PHASE_ACTIVE_VAL_MOBILE", obj2[6]);
					map.put("Y_PHASE_ACTIVE_VAL_CMRI", obj[7]);
					map.put("Y_PHASE_ACTIVE_VAL_MOBILE", obj2[7]);
					map.put("B_PHASE_ACTIVE_VAL_CMRI", obj[8]);
					map.put("B_PHASE_ACTIVE_VAL_MOBILE", obj2[8]);
					map.put("R_PHASE_PF_VAL_CMRI", obj[9]);
					map.put("R_PHASE_PF_VAL_MOBILE", obj2[9]);
					map.put("Y_PHASE_PF_VAL_CMRI", obj[10]);
					map.put("Y_PHASE_PF_VAL_MOBILE", obj2[10]);
					map.put("B_PHASE_PF_VAL_CMRI", obj[11]);
					map.put("B_PHASE_PF_VAL_MOBILE", obj2[11]);
					map.put("AVG_PF_VAL_CMRI", obj[12]);
					map.put("AVG_PF_VAL_MOBILE", obj2[12]);
					map.put("ACTIVE_POWER_VAL_CMRI", obj[13]);
					map.put("ACTIVE_POWER_VAL_MOBILE", obj2[13]);
					map.put("PHASE_SEQUENCE_CMRI", obj[14]);
					map.put("PHASE_SEQUENCE_MOBILE", obj2[14]);
					map.put("R_PHASE_CURRENT_ANGLE_CMRI", obj[15]);
					map.put("R_PHASE_CURRENT_ANGLE_MOBILE", obj2[15]);
					
					map.put("Y_PHASE_CURRENT_ANGLE_CMRI", obj[16]);
					map.put("Y_PHASE_CURRENT_ANGLE_MOBILE", obj2[16]);
					map.put("B_PHASE_CURRENT_ANGLE_CMRI", obj[17]);
					map.put("B_PHASE_CURRENT_ANGLE_MOBILE", obj2[17]);
					map.put("D2_KWH_CMRI", obj[18]);
					map.put("D2_KWH_MOBILE", obj2[18]);
					D2result1.add(map);
				}
			 }
			 }

			 if(d2_Cmridata!=null)
			 {
			  if(d2_Cmridata.size()>0 && (d2_Mobiledata==null))
			 {
				 System.out.println("only cmri d2 data");
					/*Iterator<?> iterator1 = d3_Cmridata.iterator();*/
					for (Iterator<?> iterator = d2_Cmridata.iterator(); iterator.hasNext();) 
					{
						
						Map<String,Object> map=new HashedMap();
						Object[] obj = (Object[]) iterator.next();
						/*Object[] obj2 = (Object[]) iterator1.next();*/
						
						map.put("R_PHASE_VAL_CMRI", obj[0]);
						map.put("R_PHASE_VAL_MOBILE", "");
						map.put("Y_PHASE_VAL_CMRI", obj[1]);
						map.put("Y_PHASE_VAL_MOBILE","");
						map.put("B_PHASE_VAL_CMRI", obj[2]);
						map.put("B_PHASE_VAL_MOBILE", "");
						map.put("R_PHASE_LINE_VAL_CMRI", obj[3]);
						map.put("R_PHASE_LINE_VAL_MOBILE", "");
						map.put("Y_PHASE_LINE_VAL_CMRI", obj[4]);
						map.put("Y_PHASE_LINE_VAL_MOBILE","");
						map.put("B_PHASE_LINE_VAL_CMRI", obj[5]);
						map.put("B_PHASE_LINE_VAL_MOBILE", "");
						map.put("R_PHASE_ACTIVE_VAL_CMRI", obj[6]);
						map.put("R_PHASE_ACTIVE_VAL_MOBILE", "");
						map.put("Y_PHASE_ACTIVE_VAL_CMRI", obj[7]);
						map.put("Y_PHASE_ACTIVE_VAL_MOBILE", "");
						map.put("B_PHASE_ACTIVE_VAL_CMRI", obj[8]);
						map.put("B_PHASE_ACTIVE_VAL_MOBILE", "");
						map.put("R_PHASE_PF_VAL_CMRI", obj[9]);
						map.put("R_PHASE_PF_VAL_MOBILE", "");
						map.put("Y_PHASE_PF_VAL_CMRI", obj[10]);
						map.put("Y_PHASE_PF_VAL_MOBILE", "");
						map.put("B_PHASE_PF_VAL_CMRI", obj[11]);
						map.put("B_PHASE_PF_VAL_MOBILE", "");
						map.put("AVG_PF_VAL_CMRI", obj[12]);
						map.put("AVG_PF_VAL_MOBILE","");
						map.put("ACTIVE_POWER_VAL_CMRI", obj[13]);
						map.put("ACTIVE_POWER_VAL_MOBILE", "");
						map.put("PHASE_SEQUENCE_CMRI", obj[14]);
						map.put("PHASE_SEQUENCE_MOBILE", "");
						map.put("R_PHASE_CURRENT_ANGLE_CMRI", obj[15]);
						map.put("R_PHASE_CURRENT_ANGLE_MOBILE","");
						
						map.put("Y_PHASE_CURRENT_ANGLE_CMRI", obj[16]);
						map.put("Y_PHASE_CURRENT_ANGLE_MOBILE", "");
						map.put("B_PHASE_CURRENT_ANGLE_CMRI", obj[17]);
						map.put("B_PHASE_CURRENT_ANGLE_MOBILE", "");
						map.put("D2_KWH_CMRI", obj[18]);
						map.put("D2_KWH_MOBILE", "");
						
						D2result1.add(map);
					}
			 }
			 }
			 else if(d2_Mobiledata.size()>0 && (d2_Cmridata==null ))
			 {
				 System.out.println("onlly mobile d2 data");
					/*Iterator<?> iterator1 = d3_Cmridata.iterator();*/
					for (Iterator<?> iterator = d2_Mobiledata.iterator(); iterator.hasNext();) 
					{
						
						Map<String,Object> map=new HashedMap();
						Object[] obj2 = (Object[]) iterator.next();
						/*Object[] obj2 = (Object[]) iterator1.next();*/
						System.out.println("obj2[3]-->"+obj2[3]);

						map.put("R_PHASE_VAL_CMRI", "");
						map.put("R_PHASE_VAL_MOBILE", obj2[0]);
						map.put("Y_PHASE_VAL_CMRI", "");
						map.put("Y_PHASE_VAL_MOBILE", obj2[1]);
						map.put("B_PHASE_VAL_CMRI", "");
						map.put("B_PHASE_VAL_MOBILE", obj2[2]);
						map.put("R_PHASE_LINE_VAL_CMRI", "");
						map.put("R_PHASE_LINE_VAL_MOBILE", obj2[3]);
						map.put("Y_PHASE_LINE_VAL_CMRI", "");
						map.put("Y_PHASE_LINE_VAL_MOBILE", obj2[4]);
						map.put("B_PHASE_LINE_VAL_CMRI", "");
						map.put("B_PHASE_LINE_VAL_MOBILE", obj2[5]);
						map.put("R_PHASE_ACTIVE_VAL_CMRI", "");
						map.put("R_PHASE_ACTIVE_VAL_MOBILE", obj2[6]);
						map.put("Y_PHASE_ACTIVE_VAL_CMRI", "");
						map.put("Y_PHASE_ACTIVE_VAL_MOBILE", obj2[7]);
						map.put("B_PHASE_ACTIVE_VAL_CMRI", "");
						map.put("B_PHASE_ACTIVE_VAL_MOBILE", obj2[8]);
						map.put("R_PHASE_PF_VAL_CMRI", "");
						map.put("R_PHASE_PF_VAL_MOBILE", obj2[9]);
						map.put("Y_PHASE_PF_VAL_CMRI", "");
						map.put("Y_PHASE_PF_VAL_MOBILE", obj2[10]);
						map.put("B_PHASE_PF_VAL_CMRI", "");
						map.put("B_PHASE_PF_VAL_MOBILE", obj2[11]);
						map.put("AVG_PF_VAL_CMRI", "");
						map.put("AVG_PF_VAL_MOBILE", obj2[12]);
						map.put("ACTIVE_POWER_VAL_CMRI", "");
						map.put("ACTIVE_POWER_VAL_MOBILE", obj2[13]);
						map.put("PHASE_SEQUENCE_CMRI", "");
						map.put("PHASE_SEQUENCE_MOBILE", obj2[14]);
						map.put("R_PHASE_CURRENT_ANGLE_CMRI", "");
						map.put("R_PHASE_CURRENT_ANGLE_MOBILE", obj2[15]);
						
						map.put("Y_PHASE_CURRENT_ANGLE_CMRI", "");
						map.put("Y_PHASE_CURRENT_ANGLE_MOBILE", obj2[16]);
						map.put("B_PHASE_CURRENT_ANGLE_CMRI", "");
						map.put("B_PHASE_CURRENT_ANGLE_MOBILE", obj2[17]);
						map.put("D2_KWH_CMRI", "");
						map.put("D2_KWH_MOBILE", obj2[18]);
						D2result1.add(map);
					}
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D2result1;
		
	}

	//D3 CMRI&MOBILE data
		@Override
		public List<?> findD3_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri ) {
			
			String qryMOBILE="";
			String qryCMRI="";
			List<D3Data> d3_Cmridata=null;
			List<D3Data> d3_Mobiledata=null;
			ArrayList<Map<String, Object>> D3result1=null;
			try{
			if(cdf_idMobile!=null)
			{
				qryMOBILE="SELECT D3_01_ENERGY,D3_02_ENERGY,D3_03_ENERGY FROM BSMARTMDM_TEST.D3_DATA WHERE CDF_ID='"+cdf_idMobile+"'";
				System.out.println("qry d3 MOBILE-->"+qryMOBILE);
				d3_Mobiledata=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
				
			}
			
			if(cdf_idCmri!=null)
			{
				qryCMRI="SELECT D3_01_ENERGY,D3_02_ENERGY,D3_03_ENERGY FROM BSMARTMDM.D3_DATA WHERE CDF_ID='"+cdf_idCmri+"'";
				System.out.println("qry d3 cmri-->"+qryCMRI);
				d3_Cmridata=postgresMdas.createNativeQuery(qryCMRI).getResultList();
			}
			
			
				System.out.println("D3_dataMobile-size()--"+d3_Mobiledata.size());
				//System.out.println("D3_dataCmri-size()--"+D3_dataCmri.size());
				 D3result1 =new ArrayList<>();
				if(d3_Cmridata!=null && d3_Mobiledata!=null)
				{
				 if(d3_Cmridata.size()>0 && d3_Mobiledata.size()>0 )
				 {
					 System.out.println("both have d3 data");
					Iterator<?> iterator1 = d3_Cmridata.iterator();
					for (Iterator<?> iterator = d3_Mobiledata.iterator(); iterator.hasNext();) 
					{
						
						Map<String,Object> map=new HashedMap();
						Object[] obj = (Object[]) iterator.next();
						Object[] obj2 = (Object[]) iterator1.next();
						
						map.put("D3_01_ENERGY_MOBILE", obj[0]);
						map.put("D3_01_ENERGY_CMRI", obj2[0]);
						map.put("D3_02_ENERGY_MOBILE", obj[1]);
						map.put("D3_02_ENERGY_CMRI", obj2[1]);
						map.put("D3_03_ENERGY_MOBILE", obj[2]);
						map.put("D3_03_ENERGY_CMRI", obj2[2]);
						
						D3result1.add(map);
					}
				 }
				}
				if(d3_Cmridata!=null){
				  if(d3_Cmridata.size()>0 && (d3_Mobiledata==null ))
				 {
					 System.out.println("onlly cmri d3 data");
						/*Iterator<?> iterator1 = d3_Cmridata.iterator();*/
						for (Iterator<?> iterator = d3_Cmridata.iterator(); iterator.hasNext();) 
						{
							System.out.println("inside for loop");
							Map<String,Object> map=new HashedMap();
							Object[] obj = (Object[]) iterator.next();
							/*Object[] obj2 = (Object[]) iterator1.next();*/
							
							map.put("D3_01_ENERGY_MOBILE","" );
							map.put("D3_01_ENERGY_CMRI", obj[0]);
							map.put("D3_02_ENERGY_MOBILE", "");
							map.put("D3_02_ENERGY_CMRI", obj[1]);
							map.put("D3_03_ENERGY_MOBILE", "");
							map.put("D3_03_ENERGY_CMRI", obj[2]);
							
							D3result1.add(map);
						}
				 }
				}
				 else if(d3_Mobiledata.size()>0 && (d3_Cmridata==null ))
				 {
					 System.out.println("only mobile d3 data");
						/*Iterator<?> iterator1 = d3_Cmridata.iterator();*/
						for (Iterator<?> iterator = d3_Mobiledata.iterator(); iterator.hasNext();) 
						{
							
							Map<String,Object> map=new HashedMap();
							Object[] obj = (Object[]) iterator.next();
							/*Object[] obj2 = (Object[]) iterator1.next();*/
							
							map.put("D3_01_ENERGY_MOBILE",obj[0]);
							map.put("D3_01_ENERGY_CMRI", "");
							map.put("D3_02_ENERGY_MOBILE",obj[1]);
							map.put("D3_02_ENERGY_CMRI", "");
							map.put("D3_03_ENERGY_MOBILE",obj[2]);
							map.put("D3_03_ENERGY_CMRI", "");
							
							D3result1.add(map);
						}
				 }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return D3result1;
		}

		//D4 data
		@SuppressWarnings({ "unused", "unused", "unchecked" })
		@Override
		public List<?> findD4_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri,ModelMap model)
				 
		{
			 ArrayList<Map<String, Object>> D4result1=null;
		
			 D4result1 =new ArrayList<>();
			
				 
			model.put("cdf_idMobile", cdf_idMobile);
			model.put("cdf_idCmri", cdf_idCmri);
			
			List<D4Data> d4_Cmridata=null;
			List<D4Data> d4_Mobiledata=null;
			List<D4Data> d4_MobileAndCmri=null;
			
			
			if(cdf_idMobile!=null && cdf_idCmri!=null)
			{
			String qry="select (case WHEN A.profiledate is not null then A.profiledate else B.profiledate end)as profiledate ,nvl(A.records1,'0')Mobile,nvl(B.records2,'0')CMRI from \n" +
					"(SELECT to_char(DAY_PROFILE_DATE,'dd-MM-yyyy') as profiledate,COUNT(*)as records1  FROM BSMARTMDM_TEST.D4_DATA  where CDF_ID='"+cdf_idMobile+"'  GROUP BY DAY_PROFILE_DATE\n" +
					"ORDER BY to_date(to_char(DAY_PROFILE_DATE,'dd-MM-yyyy'),'dd-MM-yyyy'))A\n" +
					"INNER JOIN\n" +
					"(SELECT to_char(DAY_PROFILE_DATE,'dd-MM-yyyy') as profiledate,COUNT(*)as records2  FROM  BSMARTMDM.D4_DATA  where CDF_ID='"+cdf_idCmri+"'  GROUP BY DAY_PROFILE_DATE\n" +
					"ORDER BY to_date(to_char(DAY_PROFILE_DATE,'dd-MM-yyyy'),'dd-MM-yyyy'))B\n" +
					"ON A.profiledate = B.profiledate\n" +
					"ORDER BY to_date(profiledate,'dd-MM-yyyy')";
			
			 System.out.println(" d4 qry--"+qry);
			 
			 d4_MobileAndCmri=postgresMdas.createNativeQuery(qry).getResultList();
			 
			
			}
			
			else if(cdf_idMobile!=null && cdf_idCmri==null)
			{
				System.out.println(" Mobile data is there --- CMRI id is not there....");
				String qry = "SELECT to_char(DAY_PROFILE_DATE,'dd-MM-yyyy') as Profile_Date FROM  BSMARTMDM_TEST.D4_DATA  where CDF_ID='"+cdf_idMobile+"'  GROUP BY DAY_PROFILE_DATE\n" +
				"ORDER BY to_date(to_char(DAY_PROFILE_DATE,'dd-MM-yyyy'),'dd-MM-yyyy')";
					
			    System.out.println(" d4 when only Mobile Data is there qry--"+qry);
			 
			     //postgresMdas.createNativeQuery(qry).getResultList();
			    d4_Mobiledata=postgresMdas.createNativeQuery(qry).getResultList();
			}
			
			else if(cdf_idCmri!=null && cdf_idMobile==null )
			{
				String qry = "SELECT to_char(DAY_PROFILE_DATE,'dd-MM-yyyy') as Profile_Date FROM  BSMARTMDM.D4_DATA  where CDF_ID='"+cdf_idCmri+"'  GROUP BY DAY_PROFILE_DATE\n" +
				"ORDER BY to_date(to_char(DAY_PROFILE_DATE,'dd-MM-yyyy'),'dd-MM-yyyy')";
					
			    System.out.println("D4 when only CMRI Data is there qry--"+qry);
			 
			     //postgresMdas.createNativeQuery(qry).getResultList();
			    d4_Cmridata=postgresMdas.createNativeQuery(qry).getResultList();
			}
		
			
			
			 if(d4_MobileAndCmri!=null)
			 {
				 System.out.println("both have d4 data");
				//Iterator<?> iterator1 = D4_dataMobileAndCMRI.iterator();
				for (Iterator<?> iterator = d4_MobileAndCmri.iterator(); iterator.hasNext();) 
				{
					
					Map<String,Object> map=new HashedMap();
					Object[] obj = (Object[]) iterator.next();
					
					map.put("Date_MOBILE", obj[0]);
				
					
					D4result1.add(map);
				}
			 }
			 
			 
			 else if(d4_Mobiledata!=null )
			 {
				 System.out.println("Only have Mobile D4 data......");
				 
			
				for (Iterator<?> iterator = d4_Mobiledata.iterator(); iterator.hasNext();) 
				{
					
					Map<String,Object> map=new HashedMap();
					Object obj =  (Object) iterator.next();
					
					map.put("Date_MOBILE",obj);
					
					D4result1.add(map);
				}
			 }
			 
			 
			 else if(d4_Cmridata!=null )
			 {
				 System.out.println("both have d4 data");
				//Iterator<?> iterator1 = D4_dataMobileAndCMRI.iterator();
				for (Iterator<?> iterator = d4_Cmridata.iterator(); iterator.hasNext();) 
				{
					
					Map<String,Object> map=new HashedMap();
					Object obj = (Object) iterator.next();
					
					map.put("Date_MOBILE", obj);
					
					D4result1.add(map);
				}
			 }
			 
			 return D4result1;
		}
		
		
 //D4 data on dayprofile date
		
		@SuppressWarnings("unused")
		@Override
		public List<?> findD4_DataOnDayProfileDate(String cdf_idMobile,
				String cdf_idCmri, String DayProfileDate) {
			
			System.out.println("inside findD4_DataOnDayProfileDate");
			System.out.println("cmriCdfId-->"+cdf_idCmri);
			System.out.println("mobileCdfId-->"+cdf_idMobile);
			System.out.println("dayProfileDate-->"+DayProfileDate);
			
			String qryMOBILE="";
			String qryCMRI="";
			List<D4Data> d4_CmriDayProfile=null;
			List<D4Data> d4_MobileDayProfile=null;
			ArrayList<Map<String, Object>> D4DayProfileresult1=null;
			try{
			if(cdf_idMobile!=null )
			{
				
					if(!(cdf_idMobile.isEmpty()))
					{
						qryMOBILE="select MIN_KVA,MAX_KVA,SUM_KWH,SUM_PF,SUM_KVA,INTERVAL_PERIOD,KWH_FLAG,PF_05,PF_05_07,PF_07_09,PF_09,PF_NOLOAD,PF_BLACKOUT,IP_GS_20,IP_GS_20_40,IP_GS_40_60,IP_GS_60,IP_OUT_GS_20,IP_OUT_GS_20_40,IP_OUT_GS_40_60,IP_OUT_GS_60 from BSMARTMDM_TEST.D4_DATA where TO_CHAR(DAY_PROFILE_DATE,'DD-MM-YYYY')='"+DayProfileDate+"' AND CDF_ID='"+cdf_idMobile+"' ";
						System.out.println("qryMOBILEDayProfile-->"+qryMOBILE);
						d4_MobileDayProfile=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
						System.out.println("d4_Mobiledata day-->"+d4_MobileDayProfile);
					}
				
			}
			
			if(cdf_idCmri!=null)
			{
				if(!(cdf_idCmri.isEmpty()))
				{
					qryCMRI="select MIN_KVA,MAX_KVA,SUM_KWH,SUM_PF,SUM_KVA,INTERVAL_PERIOD,KWH_FLAG,PF_05,PF_05_07,PF_07_09,PF_09,PF_NOLOAD,PF_BLACKOUT,IP_GS_20,IP_GS_20_40,IP_GS_40_60,IP_GS_60,IP_OUT_GS_20,IP_OUT_GS_20_40,IP_OUT_GS_40_60,IP_OUT_GS_60 from BSMARTMDM.D4_DATA where TO_CHAR(DAY_PROFILE_DATE,'DD-MM-YYYY')='"+DayProfileDate+"' AND CDF_ID='"+cdf_idCmri+"' ";
					System.out.println("qry cmriDayProfile-->"+qryCMRI);
					d4_CmriDayProfile=postgresMdas.createNativeQuery(qryCMRI).getResultList();
					System.out.println("d4_Cmridata day-->"+d4_CmriDayProfile);
				}
				
			}
			
			D4DayProfileresult1 =new ArrayList<>();
			if(d4_CmriDayProfile!=null && d4_MobileDayProfile!=null)
			{
			 if(d4_CmriDayProfile.size()>0 && d4_MobileDayProfile.size()>0 )
			 {
				 System.out.println(" both have d4  day profile  data");
				Iterator<?> iterator1 = d4_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d4_MobileDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					Object[] obj = (Object[]) iterator1.next();
					
					map.put("MIN_KVA_CMRI", obj[0]);
					map.put("MIN_KVA_MOBILE", obj2[0]);
					map.put("MAX_KVA_CMRI", obj[1]);
					map.put("MAX_KVA_MOBILE", obj2[1]);
					map.put("SUM_KWH_CMRI", obj[2]);
					map.put("SUM_KWH_MOBILE", obj2[2]);
					map.put("SUM_PF_CMRI", obj[3]);
					map.put("SUM_PF_MOBILE", obj2[3]);
					map.put("SUM_KVA_CMRI", obj[4]);
					map.put("SUM_KVA_MOBILE", obj2[4]);
					map.put("INTERVAL_PERIOD_CMRI", obj[5]);
					map.put("INTERVAL_PERIOD_MOBILE", obj2[5]);
					map.put("KWH_FLAG_CMRI", obj[6]);
					map.put("KWH_FLAG_MOBILE", obj2[6]);
					map.put("PF_05_CMRI", obj[7]);
					map.put("PF_05_MOBILE", obj2[7]);
					map.put("PF_05_07_CMRI", obj[8]);
					map.put("PF_05_07_MOBILE", obj2[8]);
					map.put("PF_07_09_CMRI", obj[9]);
					map.put("PF_07_09_MOBILE", obj2[9]);
					map.put("PF_09_CMRI", obj[10]);
					map.put("PF_09_MOBILE", obj2[10]);
					map.put("PF_NOLOAD_CMRI", obj[11]);
					map.put("PF_NOLOAD_MOBILE", obj2[11]);
					map.put("PF_BLACKOUT_CMRI", obj[12]);
					map.put("PF_BLACKOUT_MOBILE", obj2[12]);
					map.put("IP_GS_20_CMRI", obj[13]);
					map.put("IP_GS_20_MOBILE", obj2[13]);
					map.put("IP_GS_20_40_CMRI", obj[14]);
					map.put("IP_GS_20_40_MOBILE", obj2[14]);
					map.put("IP_GS_40_60_CMRI", obj[15]);
					map.put("IP_GS_40_60_MOBILE", obj2[15]);
					
					map.put("IP_GS_60_CMRI", obj[16]);
					map.put("IP_GS_60_MOBILE", obj2[16]);
					map.put("IP_OUT_GS_20_CMRI", obj[17]);
					map.put("IP_OUT_GS_20_MOBILE", obj2[17]);
					map.put("IP_OUT_GS_20_40_CMRI", obj[18]);
					map.put("IP_OUT_GS_20_40_MOBILE", obj2[18]);
					
					map.put("IP_OUT_GS_40_60_CMRI", obj[19]);
					map.put("IP_OUT_GS_40_60_MOBILE", obj2[19]);
					map.put("IP_OUT_GS_60_CMRI", obj[20]);
					map.put("IP_OUT_GS_60_MOBILE", obj2[20]);
					D4DayProfileresult1.add(map);
				}
			 }
			}
		
			
			if(d4_MobileDayProfile.size()>0 && (d4_CmriDayProfile==null))
			 {
				 System.out.println("inside have d4 mobile day profile  data");
				//Iterator<?> iterator1 = d4_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d4_MobileDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					//Object[] obj = (Object[]) iterator1.next();
					
					/*System.out.println("SUM_KWH_MOBILE--"+obj2[2]);
					System.out.println("SUM_PF_MOBILE--"+obj2[3]);
					System.out.println("MIN_KVA_MOBILE--"+obj2[0]);*/
					
					
					map.put("MIN_KVA_CMRI", "");
					map.put("MIN_KVA_MOBILE", obj2[0]);
					map.put("MAX_KVA_CMRI", "");
					map.put("MAX_KVA_MOBILE", obj2[1]);
					map.put("SUM_KWH_CMRI","");
					map.put("SUM_KWH_MOBILE", obj2[2]);
					map.put("SUM_PF_CMRI", "");
					map.put("SUM_PF_MOBILE", obj2[3]);
					map.put("SUM_KVA_CMRI", "");
					map.put("SUM_KVA_MOBILE", obj2[4]);
					map.put("INTERVAL_PERIOD_CMRI", "");
					map.put("INTERVAL_PERIOD_MOBILE", obj2[5]);
					map.put("KWH_FLAG_CMRI", "");
					map.put("KWH_FLAG_MOBILE", obj2[6]);
					map.put("PF_05_CMRI", "");
					map.put("PF_05_MOBILE", obj2[7]);
					map.put("PF_05_07_CMRI", "");
					map.put("PF_05_07_MOBILE", obj2[8]);
					map.put("PF_07_09_CMRI", "");
					map.put("PF_07_09_MOBILE", obj2[9]);
					map.put("PF_09_CMRI", "");
					map.put("PF_09_MOBILE", obj2[10]);
					map.put("PF_NOLOAD_CMRI", "");
					map.put("PF_NOLOAD_MOBILE", obj2[11]);
					map.put("PF_BLACKOUT_CMRI", "");
					map.put("PF_BLACKOUT_MOBILE", obj2[12]);
					map.put("IP_GS_20_CMRI", "");
					map.put("IP_GS_20_MOBILE", obj2[13]);
					map.put("IP_GS_20_40_CMRI", "");
					map.put("IP_GS_20_40_MOBILE", obj2[14]);
					map.put("IP_GS_40_60_CMRI", "");
					map.put("IP_GS_40_60_MOBILE", obj2[15]);
					
					map.put("IP_GS_60_CMRI", "");
					map.put("IP_GS_60_MOBILE", obj2[16]);
					map.put("IP_OUT_GS_20_CMRI", "");
					map.put("IP_OUT_GS_20_MOBILE", obj2[17]);
					map.put("IP_OUT_GS_20_40_CMRI", "");
					map.put("IP_OUT_GS_20_40_MOBILE", obj2[18]);
					
					map.put("IP_OUT_GS_40_60_CMRI", "");
					map.put("IP_OUT_GS_40_60_MOBILE", obj2[19]);
					map.put("IP_OUT_GS_60_CMRI", "");
					map.put("IP_OUT_GS_60_MOBILE", obj2[20]);
					D4DayProfileresult1.add(map);
				}
			 }
			 
			 if(d4_CmriDayProfile!=null)
			 {
			 if(d4_CmriDayProfile.size()>0 && (d4_MobileDayProfile==null  ))
			 {
				 System.out.println("both have d4 cmri day profile data");
				//Iterator<?> iterator1 = d4_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d4_CmriDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					//Object[] obj2 = (Object[]) iterator.next();
					Object[] obj = (Object[]) iterator.next();
					
					map.put("MIN_KVA_CMRI", obj[0]);
					map.put("MIN_KVA_MOBILE", "");
					map.put("MAX_KVA_CMRI", obj[1]);
					map.put("MAX_KVA_MOBILE", "");
					map.put("SUM_KWH_CMRI", obj[2]);
					map.put("SUM_KWH_MOBILE", "");
					map.put("SUM_PF_CMRI", obj[3]);
					map.put("SUM_PF_MOBILE", "");
					map.put("SUM_KVA_CMRI", obj[4]);
					map.put("SUM_KVA_MOBILE", "");
					map.put("INTERVAL_PERIOD_CMRI", obj[5]);
					map.put("INTERVAL_PERIOD_MOBILE", "");
					map.put("KWH_FLAG_CMRI", obj[6]);
					map.put("KWH_FLAG_MOBILE", "");
					map.put("PF_05_CMRI", obj[7]);
					map.put("PF_05_MOBILE", "");
					map.put("PF_05_07_CMRI", obj[8]);
					map.put("PF_05_07_MOBILE", "");
					map.put("PF_07_09_CMRI", obj[9]);
					map.put("PF_07_09_MOBILE", "");
					map.put("PF_09_CMRI", obj[10]);
					map.put("PF_09_MOBILE", "");
					map.put("PF_NOLOAD_CMRI", obj[11]);
					map.put("PF_NOLOAD_MOBILE", "");
					map.put("PF_BLACKOUT_CMRI", obj[12]);
					map.put("PF_BLACKOUT_MOBILE", "");
					map.put("IP_GS_20_CMRI", obj[13]);
					map.put("IP_GS_20_MOBILE", "");
					map.put("IP_GS_20_40_CMRI", obj[14]);
					map.put("IP_GS_20_40_MOBILE", "");
					map.put("IP_GS_40_60_CMRI", obj[15]);
					map.put("IP_GS_40_60_MOBILE", "");
					
					map.put("IP_GS_60_CMRI", obj[16]);
					map.put("IP_GS_60_MOBILE", "");
					map.put("IP_OUT_GS_20_CMRI", obj[17]);
					map.put("IP_OUT_GS_20_MOBILE", "");
					map.put("IP_OUT_GS_20_40_CMRI", obj[18]);
					map.put("IP_OUT_GS_20_40_MOBILE", "");
					
					map.put("IP_OUT_GS_40_60_CMRI", obj[19]);
					map.put("IP_OUT_GS_40_60_MOBILE", "");
					map.put("IP_OUT_GS_60_CMRI", obj[20]);
					map.put("IP_OUT_GS_60_MOBILE", "");
					D4DayProfileresult1.add(map);
				}
			 	}
			 }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return D4DayProfileresult1;
			}

		//D4-loaddata
		@Override
		public List<?> findD4_Load_DataMobileCmri(BigDecimal cdf_idMobile,
				BigDecimal cdf_idCmri, ModelMap model) {
			List D4_LoadData=null;
			try {
			String qry="SELECT DISTINCT(TO_CHAR(A.DAY_PROFILE_DATE,'dd-MM-yyyy')) FROM\n" +
					"((SELECT DAY_PROFILE_DATE,IP_INTERVAL FROM BSMARTMDM_TEST.D4_LOAD_DATA A WHERE A.CDF_ID='"+cdf_idMobile+"' \n" +
					"GROUP BY DAY_PROFILE_DATE,IP_INTERVAL\n" +
					"ORDER BY DAY_PROFILE_DATE,IP_INTERVAL)A\n" +
					"INNER JOIN \n" +
					"(SELECT DAY_PROFILE_DATE,IP_INTERVAL FROM BSMARTMDM.D4_LOAD_DATA A WHERE A.CDF_ID='"+cdf_idCmri+"' \n" +
					"GROUP BY DAY_PROFILE_DATE,IP_INTERVAL\n" +
					"ORDER BY DAY_PROFILE_DATE,IP_INTERVAL)B \n" +
					"ON A.DAY_PROFILE_DATE=B.DAY_PROFILE_DATE AND A.IP_INTERVAL=B.IP_INTERVAL ) ORDER BY TO_CHAR(A.DAY_PROFILE_DATE,'dd-MM-yyyy')";

			System.out.println("d4load data qry-->"+qry);
			
			D4_LoadData=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return D4_LoadData;
		}
		
		//d4loaddata get Intervals

		@Override
		public List<?> D4_Load_DatagetIntervals(String cdf_idMobile,String cdf_idCmri,String DayProfileDate) {
			
			
/*
			System.out.println("inside getD4IPIntervalByDayProfile impl");
			System.out.println("cmriCdfId-->"+cdf_idCmri);
			System.out.println("mobileCdfId-->"+cdf_idMobile);
			System.out.println("dayProfileDate-->"+DayProfileDate);*/
			
			
			List<D4Data> d4_Intervals=null;
			
			ArrayList<Map<String, Object>> D4LoadDayProfileresult1=null;
			try{
			if(cdf_idMobile!=null && cdf_idCmri!=null)
			{
				
					if(!(cdf_idMobile.isEmpty()) && !(cdf_idCmri.isEmpty()))
					{
					String	qry="SELECT A.IP_INTERVAL FROM\n" +
							"((SELECT DAY_PROFILE_DATE,IP_INTERVAL FROM BSMARTMDM_TEST.D4_LOAD_DATA A WHERE A.CDF_ID='"+cdf_idMobile+"' AND TO_CHAR(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+DayProfileDate+"'\n" +
							"GROUP BY DAY_PROFILE_DATE,IP_INTERVAL\n" +
							"ORDER BY DAY_PROFILE_DATE,IP_INTERVAL)A\n" +
							"INNER JOIN \n" +
							"(SELECT DAY_PROFILE_DATE,IP_INTERVAL FROM BSMARTMDM.D4_LOAD_DATA A WHERE A.CDF_ID='"+cdf_idCmri+"' AND TO_CHAR(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+DayProfileDate+"'\n" +
							"GROUP BY DAY_PROFILE_DATE,IP_INTERVAL\n" +
							"ORDER BY DAY_PROFILE_DATE,IP_INTERVAL)B \n" +
							"ON A.DAY_PROFILE_DATE=B.DAY_PROFILE_DATE AND A.IP_INTERVAL=B.IP_INTERVAL ) ORDER BY IP_INTERVAL";
						
						d4_Intervals=postgresMdas.createNativeQuery(qry).getResultList();
						
					}
					
				
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return d4_Intervals;
		}

		@SuppressWarnings("unused")
		@Override
		public List<?> D4_Load_DataALL(String cdf_idMobile, String cdf_idCmri,
				String DayProfileDate, String interval) {
			System.out.println("inside D4_Load_DataALL");
			//System.out.println("cmriCdfId-->"+cdf_idCmri);
			//System.out.println("mobileCdfId-->"+cdf_idMobile);
			//System.out.println("dayProfileDate-->"+DayProfileDate);
			//System.out.println("interval-->"+interval);
			
			String qryMOBILE="";
			String qryCMRI="";
			List<CDFData> d4_LoadCmri=null;
			List<CDFData> d4_LoadMobile=null;
			ArrayList<Map<String, Object>> D4Loadresult1=null;
			try{
			if(cdf_idMobile!=null )
			{
					if(!(cdf_idMobile.isEmpty()))
					{
						qryMOBILE="SELECT KVAVALUE,KWHVALUE,PFVALUE FROM BSMARTMDM_TEST.D4_LOAD_DATA WHERE CDF_ID='"+cdf_idMobile+"' AND TO_CHAR(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+DayProfileDate+"' AND IP_INTERVAL='"+interval+"'";
						System.out.println("qryMOBILE LOAAADayProfile-->"+qryMOBILE);
						d4_LoadMobile=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
						System.out.println("d4_Mobiledata day-->"+d4_LoadMobile);
					}
			}
			
			if(cdf_idCmri!=null)
			{
				if(!(cdf_idCmri.isEmpty()))
				{
					qryCMRI="SELECT KVAVALUE,KWHVALUE,PFVALUE FROM BSMARTMDM.D4_LOAD_DATA WHERE CDF_ID='"+cdf_idCmri+"' AND TO_CHAR(DAY_PROFILE_DATE,'dd-MM-yyyy')='"+DayProfileDate+"' AND IP_INTERVAL='"+interval+"'";
					System.out.println("qry cmriDayLOAAAProfile-->"+qryCMRI);
					d4_LoadCmri=postgresMdas.createNativeQuery(qryCMRI).getResultList();
					System.out.println("d4_Cmridata day-->"+d4_LoadCmri);
				}
				
		}
			
			
			 D4Loadresult1 =new ArrayList<>();
				
			 if(d4_LoadCmri!=null && d4_LoadMobile!=null)
				{
				 if(d4_LoadCmri.size()>0 && d4_LoadMobile.size()>0 )
				 {
						 System.out.println("both have data");
						Iterator<?> iterator1 = d4_LoadCmri.iterator();
						for (Iterator<?> iterator = d4_LoadMobile.iterator(); iterator.hasNext();) 
						{
							System.out.println("inside for both data d4load loop");
							Map<String,Object> map=new HashedMap();
							Object[] obj = (Object[]) iterator.next();
							Object[] obj2 = (Object[]) iterator1.next();
							
							map.put("KVAVALUE_MOBILE", obj[0]);
							map.put("KVAVALUE_CMRI", obj2[0]);
							map.put("KWHVALUE_MOBILE", obj[1]);
							map.put("KWHVALUE_CMRI", obj2[1]);
							map.put("PFVALUE_MOBILE", obj[2]);
							map.put("PFVALUE_CMRI", obj2[2]);
							
							D4Loadresult1.add(map);
						}
					 }
					}
					
			 if(d4_LoadCmri!=null)
			 {
			 if(d4_LoadCmri.size()>0 && (d4_LoadMobile==null  ))
			 {
							 System.out.println("only cmri d4 load data");
								
								for (Iterator<?> iterator = d4_LoadCmri.iterator(); iterator.hasNext();) 
								{
									System.out.println("inside for loop");
									Map<String,Object> map=new HashedMap();
									Object[] obj = (Object[]) iterator.next();
									map.put("KVAVALUE_MOBILE", "");
									map.put("KVAVALUE_CMRI", obj[0]);
									map.put("KWHVALUE_MOBILE", "");
									map.put("KWHVALUE_CMRI", obj[1]);
									map.put("PFVALUE_MOBILE", "");
									map.put("PFVALUE_CMRI", obj[2]);
									
									D4Loadresult1.add(map);
								}	
							}
						 }
					
			  if(d4_LoadMobile.size()>0 && d4_LoadCmri==null)
			 {
				 System.out.println("only mobile d4 load data");
					/*Iterator<?> iterator1 = d3_Cmridata.iterator();*/
					for (Iterator<?> iterator = d4_LoadMobile.iterator(); iterator.hasNext();) 
					{
						System.out.println("inside for loop");
						Map<String,Object> map=new HashedMap();
						Object[] obj = (Object[]) iterator.next();
						/*Object[] obj2 = (Object[]) iterator1.next();*/
						
							System.out.println(obj[2]);
							
						map.put("KVAVALUE_MOBILE", obj[0]);
						map.put("KVAVALUE_CMRI", "");
						map.put("KWHVALUE_MOBILE",obj[1]);
						map.put("KWHVALUE_CMRI", "");
						map.put("PFVALUE_MOBILE", obj[2]);
						map.put("PFVALUE_CMRI", "");
						
						D4Loadresult1.add(map);
					}
			 }
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			return D4Loadresult1;
		}
		
		@SuppressWarnings({ "unchecked", "null" })
		@Override
		public List<?> findD5_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri,ModelMap model)
				 
		{ 
			model.put("cdf_idMobile", cdf_idMobile);
			model.put("cdf_idCmri", cdf_idCmri);
			
			List<?> d5ResultDataMobile=null;
			List<?> d5ResultDataCmri=null;
			List<?> d5ResultDataMobileCmri=null;
		
		   
		   ArrayList<Map<String, Object>> d5Result=null;
			
		   d5Result =new ArrayList<>();
			
			if(cdf_idMobile!=null && cdf_idCmri!=null)
			{
			String qry="select  (case WHEN to_char(A.EVENT_TIME,'YYYY-MM-DD hh:mi:ss') is not null then to_char(A.EVENT_TIME,'YYYY-MM-DD hh:mi:ss') else to_char(B.EVENT_TIME1,'YYYY-MM-DD hh:mi:ss') end)as EVENT_TIME from \n" +
					"(SELECT EVENT_TIME FROM BSMARTMDM_TEST.D5_DATA  where CDF_ID='"+cdf_idMobile+"' and EVENT_TIME is NOT NULL GROUP BY EVENT_TIME\n" +
					"ORDER BY EVENT_TIME)A\n" +
					"inner JOIN\n" +
					"(SELECT (EVENT_TIME) as EVENT_TIME1  FROM  BSMARTMDM.D5_DATA  where CDF_ID='"+cdf_idCmri+"' and EVENT_TIME is NOT NULL GROUP BY EVENT_TIME\n" +
					"ORDER BY EVENT_TIME)B\n" +
					"ON to_char(A.EVENT_TIME,'DD-MM-YYYY hh:mi:ss') =to_char( B.EVENT_TIME1,'DD-MM-YYYY hh:mi:ss')\n" +
					"ORDER BY EVENT_TIME";
			System.out.println("d5 qry------ "+qry);
			
			d5ResultDataMobileCmri= postgresMdas.createNativeQuery(qry).getResultList();
			}
			
			else if(cdf_idMobile!=null && cdf_idCmri==null){
				String qry="SELECT to_char(EVENT_TIME,'YYYY-MM-DD hh:mi:ss') AS event_time  FROM  BSMARTMDM_TEST.D5_DATA  where CDF_ID='"+cdf_idMobile+"' AND EVENT_TIME IS NOT NULL  GROUP BY EVENT_TIME ORDER BY EVENT_TIME";
				d5ResultDataMobile=postgresMdas.createNativeQuery(qry).getResultList();
				System.out.println("D5 only Mobile qry------ "+qry);
			}
			
			else if(cdf_idCmri!=null && cdf_idMobile==null ){
				String qry="SELECT to_char(EVENT_TIME,'YYYY-MM-DD hh:mi:ss') AS event_time  FROM  BSMARTMDM.D5_DATA  where CDF_ID='"+cdf_idCmri+"' AND EVENT_TIME IS NOT NULL  GROUP BY EVENT_TIME ORDER BY EVENT_TIME";
				d5ResultDataCmri=postgresMdas.createNativeQuery(qry).getResultList();
				System.out.println("D5 only CMRI qry------ "+qry);
			}
			
			if(d5ResultDataMobileCmri!=null)
			{
				for(Iterator<?> iterator=d5ResultDataMobileCmri.iterator();iterator.hasNext();)
				{
					Map<String,Object> map=new HashedMap();
					Object obj=(Object) iterator.next();
					map.put("Event_Date", obj);
					//System.out.println("Both Event Date---------------"+obj);
					d5Result.add(map);
				}
				
			}
			else if(d5ResultDataMobile!=null)
			{
				for(Iterator<?> iterator=d5ResultDataMobile.iterator();iterator.hasNext();)
				{
					Map<String,Object> map=new HashedMap();
					Object obj=(Object) iterator.next();
					map.put("Event_Date", obj);
					//System.out.println("Mobile Event Date---------------"+obj);
					d5Result.add(map);
				}
				
			}
			else if(d5ResultDataCmri!=null)
			{
				for(Iterator<?> iterator=d5ResultDataCmri.iterator();iterator.hasNext();)
				{
					Map<String,Object> map=new HashedMap();
					Object obj=(Object) iterator.next();
					map.put("Event_Date", obj);
					//System.out.println("CMRI Event Date---------------"+obj);
					d5Result.add(map);
				}
				
			}
			return d5Result;
		}
		
		
		@SuppressWarnings("unused")
		@Override
		public List<?> findD5_DataOnDayProfileDate(String cdf_idMobile,String cdf_idCmri, String DayProfileDate) {
			
			System.out.println("inside findD4_DataOnDayProfileDate");
			System.out.println("cmriCdfId-->"+cdf_idCmri);
			System.out.println("mobileCdfId-->"+cdf_idMobile);
			System.out.println("dayProfileDate-->"+DayProfileDate);
			
			String qryMOBILE="";
			String qryCMRI="";
			List<D5Data> d5_CmriDayProfile=null;
			List<D5Data> d5_MobileDayProfile=null;
			ArrayList<Map<String, Object>> D4DayProfileresult1=null;
			try{
			if(cdf_idMobile!=null )
			{
				
					if(!(cdf_idMobile.isEmpty()))
					{
						qryMOBILE="select EVENT_CODE_OLD,EVENT_STATUS,EVENT_CODE,R_PHASE_VAL,Y_PHASE_VAL,B_PHASE_VAL,R_PHASE_LINE_VAL,Y_PHASE_LINE_VAL,B_PHASE_LINE_VAL,R_PHASE_ACTIVE_VAL,Y_PHASE_ACTIVE_VAL,B_PHASE_ACTIVE_VAL,R_PHASE_PF_VAL,Y_PHASE_PF_VAL,"+
								"B_PHASE_PF_VAL,AVG_PF_VAL,ACTIVE_POWER_VAL,PHASE_SEQUENCE,D5_KWH from BSMARTMDM_TEST.D5_DATA where TO_CHAR(EVENT_TIME,'yyyy-MM-DD hh:mi:ss')='"+DayProfileDate+"' AND CDF_ID='"+cdf_idMobile+"' ";
						System.out.println("qryMOBILEDayProfile-->"+qryMOBILE);
						d5_MobileDayProfile=postgresMdas.createNativeQuery(qryMOBILE).getResultList();
						System.out.println("=====>> D5_MobileDayProfile day-->"+d5_MobileDayProfile);
					}
				
			}
			
			if(cdf_idCmri!=null)
			{
				if(!(cdf_idCmri.isEmpty()))
				{
					qryCMRI="select  EVENT_CODE_OLD,EVENT_STATUS,EVENT_CODE,R_PHASE_VAL,Y_PHASE_VAL,B_PHASE_VAL,R_PHASE_LINE_VAL,Y_PHASE_LINE_VAL,B_PHASE_LINE_VAL,R_PHASE_ACTIVE_VAL,Y_PHASE_ACTIVE_VAL,B_PHASE_ACTIVE_VAL,R_PHASE_PF_VAL,Y_PHASE_PF_VAL,"+
								"B_PHASE_PF_VAL,AVG_PF_VAL,ACTIVE_POWER_VAL,PHASE_SEQUENCE,D5_KWH from BSMARTMDM.D5_DATA where TO_CHAR(EVENT_TIME,'yyyy-MM-DD hh:mi:ss')='"+DayProfileDate+"' AND CDF_ID='"+cdf_idCmri+"' ";
					System.out.println("qry cmriDayProfile-->"+qryCMRI);
					d5_CmriDayProfile=postgresMdas.createNativeQuery(qryCMRI).getResultList();
					System.out.println("d5_CmriDayProfile day-->"+d5_CmriDayProfile);
				}
				
			}
			
			D4DayProfileresult1 =new ArrayList<>();
			if(d5_CmriDayProfile!=null && d5_MobileDayProfile!=null)
			{
			 if(d5_CmriDayProfile.size()>0 && d5_MobileDayProfile.size()>0 )
			 {
				 System.out.println("inside both have d5  day profile  data");
				Iterator<?> iterator1 = d5_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d5_MobileDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					Object[] obj = (Object[]) iterator1.next();
					
					 
					map.put("EVENT_CODE_OLD1", obj[0]);
					map.put("EVENT_CODE_OLD", obj2[0]);
					
					map.put("EVENT_STATUS1", obj[1]);
					map.put("EVENT_STATUS", obj2[1]);
					
					map.put("EVENT_CODE1", obj[2]);
					map.put("EVENT_CODE", obj2[2]);
					
					map.put("R_PHASE_VAL1", obj[3]);
					map.put("R_PHASE_VAL", obj2[3]);
					
					map.put("Y_PHASE_VAL1", obj[4]);
					map.put("Y_PHASE_VAL", obj2[4]);
					
					map.put("B_PHASE_VAL1", obj[5]);
					map.put("B_PHASE_VAL", obj2[5]);
					
					map.put("R_PHASE_LINE_VAL1", obj[6]);
					map.put("R_PHASE_LINE_VAL", obj2[6]);
					
					map.put("Y_PHASE_LINE_VAL1", obj[7]);
					map.put("Y_PHASE_LINE_VAL", obj2[7]);
					
					map.put("B_PHASE_LINE_VAL1", obj[8]);
					map.put("B_PHASE_LINE_VAL", obj2[8]);
					
					map.put("R_PHASE_ACTIVE_VAL1", obj[9]);
					map.put("R_PHASE_ACTIVE_VAL", obj2[9]);
					
					map.put("Y_PHASE_ACTIVE_VAL1", obj[10]);
					map.put("Y_PHASE_ACTIVE_VAL", obj2[10]);
					
					map.put("B_PHASE_ACTIVE_VAL1", obj[11]);
					map.put("B_PHASE_ACTIVE_VAL", obj2[11]);
					
					map.put("R_PHASE_PF_VAL1", obj[12]);
					map.put("R_PHASE_PF_VAL", obj2[12]);
					
					map.put("Y_PHASE_PF_VAL1", obj[13]);
					map.put("Y_PHASE_PF_VAL", obj2[13]);
					
					map.put("B_PHASE_PF_VAL1", obj[14]);
					map.put("B_PHASE_PF_VAL", obj2[14]);
					
					map.put("AVG_PF_VAL1", obj[15]);
					map.put("AVG_PF_VAL", obj2[15]);
					
					map.put("ACTIVE_POWER_VAL1", obj[16]);
					map.put("ACTIVE_POWER_VAL", obj2[16]);
					
					map.put("PHASE_SEQUENCE1", obj[17]);
					map.put("PHASE_SEQUENCE", obj2[17]);
					
					map.put("D5_KWH1", obj[18]);
					map.put("D5_KWH", obj2[18]);
					D4DayProfileresult1.add(map);
				}
			 }
			 }
			 if(d5_MobileDayProfile.size()>0 && (d5_CmriDayProfile==null) )
			 {
				 System.out.println("inside  d5 mobile day profile  data");
				//Iterator<?> iterator1 = d4_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d5_MobileDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					Object[] obj2 = (Object[]) iterator.next();
					//Object[] obj = (Object[]) iterator1.next();
					
					map.put("EVENT_CODE_OLD1", " ");
					map.put("EVENT_CODE_OLD", obj2[0]);
					
					map.put("EVENT_STATUS1", " ");
					map.put("EVENT_STATUS", obj2[1]);
					
					map.put("EVENT_CODE1", " ");
					map.put("EVENT_CODE", obj2[2]);
					
					map.put("R_PHASE_VAL1", " ");
					map.put("R_PHASE_VAL", obj2[3]);
					
					map.put("Y_PHASE_VAL1", " ");
					map.put("Y_PHASE_VAL", obj2[4]);
					
					map.put("B_PHASE_VAL1", " ");
					map.put("B_PHASE_VAL", obj2[5]);
					
					map.put("R_PHASE_LINE_VAL1", " ");
					map.put("R_PHASE_LINE_VAL", obj2[6]);
					
					map.put("Y_PHASE_LINE_VAL1", " ");
					map.put("Y_PHASE_LINE_VAL", obj2[7]);
					
					map.put("B_PHASE_LINE_VAL1", " ");
					map.put("B_PHASE_LINE_VAL", obj2[8]);
					
					map.put("R_PHASE_ACTIVE_VAL1", " ");
					map.put("R_PHASE_ACTIVE_VAL", obj2[9]);
					
					map.put("Y_PHASE_ACTIVE_VAL1", " ");
					map.put("Y_PHASE_ACTIVE_VAL", obj2[10]);
					
					map.put("B_PHASE_ACTIVE_VAL1", " ");
					map.put("B_PHASE_ACTIVE_VAL", obj2[11]);
					
					map.put("R_PHASE_PF_VAL1", " ");
					map.put("R_PHASE_PF_VAL", obj2[12]);
					
					map.put("Y_PHASE_PF_VAL1", " ");
					map.put("Y_PHASE_PF_VAL", obj2[13]);
					
					map.put("B_PHASE_PF_VAL1", " ");
					map.put("B_PHASE_PF_VAL", obj2[14]);
					
					map.put("AVG_PF_VAL1", " ");
					map.put("AVG_PF_VAL", obj2[15]);
					
					map.put("ACTIVE_POWER_VAL1", " ");
					map.put("ACTIVE_POWER_VAL", obj2[16]);
					
					map.put("PHASE_SEQUENCE1", " ");
					map.put("PHASE_SEQUENCE", obj2[17]);
					
					map.put("D5_KWH1", " ");
					map.put("D5_KWH", obj2[18]);
					D4DayProfileresult1.add(map);
				}
			 }
			 if(d5_CmriDayProfile!=null)
			 {
			 if(d5_CmriDayProfile.size()>0 && (d5_MobileDayProfile==null  ))
			 {
				 System.out.println(" d5 cmri day profile data");
				//Iterator<?> iterator1 = d4_CmriDayProfile.iterator();
				for (Iterator<?> iterator = d5_CmriDayProfile.iterator(); iterator.hasNext();) 
				{
					//System.out.println("inside for loop");
					Map<String,Object> map=new HashedMap();
					//Object[] obj2 = (Object[]) iterator.next();
					Object[] obj = (Object[]) iterator.next();
					
					map.put("EVENT_CODE_OLD1", obj[0]);
					map.put("EVENT_CODE_OLD", " ");
					
					map.put("EVENT_STATUS1", obj[1]);
					map.put("EVENT_STATUS", " ");
					
					map.put("EVENT_CODE1", obj[2]);
					map.put("EVENT_CODE", " ");
					
					map.put("R_PHASE_VAL1", obj[3]);
					map.put("R_PHASE_VAL", " ");
					
					map.put("Y_PHASE_VAL1", obj[4]);
					map.put("Y_PHASE_VAL", " ");
					
					map.put("B_PHASE_VAL1", obj[5]);
					map.put("B_PHASE_VAL", " ");
					
					map.put("R_PHASE_LINE_VAL1", obj[6]);
					map.put("R_PHASE_LINE_VAL", " ");
					
					map.put("Y_PHASE_LINE_VAL1", obj[7]);
					map.put("Y_PHASE_LINE_VAL", " ");
					
					map.put("B_PHASE_LINE_VAL1", obj[8]);
					map.put("B_PHASE_LINE_VAL", " ");
					
					map.put("R_PHASE_ACTIVE_VAL1", obj[9]);
					map.put("R_PHASE_ACTIVE_VAL", " ");
					
					map.put("Y_PHASE_ACTIVE_VAL1", obj[10]);
					map.put("Y_PHASE_ACTIVE_VAL", " ");
					
					map.put("B_PHASE_ACTIVE_VAL1", obj[11]);
					map.put("B_PHASE_ACTIVE_VAL", " ");
					
					map.put("R_PHASE_PF_VAL1", obj[12]);
					map.put("R_PHASE_PF_VAL", " ");
					
					map.put("Y_PHASE_PF_VAL1", obj[13]);
					map.put("Y_PHASE_PF_VAL", " ");
					
					map.put("B_PHASE_PF_VAL1", obj[14]);
					map.put("B_PHASE_PF_VAL", " ");
					
					map.put("AVG_PF_VAL1", obj[15]);
					map.put("AVG_PF_VAL", " ");
					
					map.put("ACTIVE_POWER_VAL1", obj[16]);
					map.put("ACTIVE_POWER_VAL", " ");
					
					map.put("PHASE_SEQUENCE1", obj[17]);
					map.put("PHASE_SEQUENCE", " ");
					
					map.put("D5_KWH1", obj[18]);
					map.put("D5_KWH", " ");
					D4DayProfileresult1.add(map);
				}
			 }
			 }
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			return D4DayProfileresult1;
			}

		
		//* Added by Vijayalaxmi
		@Override
		public List<?> getIpIntervalsD4() 
		{
			List<?> queryList=null;
			ArrayList<Map<String, Object>> d4LoadMapList=null;
			d4LoadMapList=new ArrayList<>();
			
			String qry="SELECT DISTINCT(IP_INTERVAL) FROM BSMARTMDM_TEST.D4_LOAD_DATA WHERE IP_INTERVAL<49 ORDER BY IP_INTERVAL";
			queryList=postgresMdas.createNativeQuery(qry).getResultList();
			
			if(queryList!=null)
			{
				for(Iterator<?> iterator=queryList.iterator();iterator.hasNext();)
				{
					Map<String,Object> map=new HashedMap();
				    Object obj = (Object) iterator.next();
				    map.put("IP_Interval", obj);
				   // System.out.println("IP Intervals in D4Load View--------"+obj);
				    d4LoadMapList.add(map);
				}
			}
			return d4LoadMapList;
		}
}

		

	
	

	


