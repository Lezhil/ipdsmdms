package com.bcits.mdas.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class AmrBillsServiceImpl extends GenericServiceImpl<AmrBillsEntity>
		implements AmrBillsService {
	
	//tomo have to work 
	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<AmrBillsEntity> getRecords(String meterno, String fileDate) {
		System.out.println("fileDate========>" + fileDate);
		List<AmrBillsEntity> abb=null;
		
		 String get6monthsBillQuery="SELECT * from meter_data.bill_history where meter_number='"+meterno+"' and  to_char(billing_date, 'YYYY-MM-DD HH24:MI:SS') like '%01 00:00:00' order by billing_date desc limit 6";

		 System.out.println("get6monthsBillQuery sql==============="+get6monthsBillQuery);



		 Query query = entityManager.createNativeQuery(get6monthsBillQuery);
		 List<?> lis = query.getResultList();

		 for (Iterator<?> iterator = lis.iterator(); iterator.hasNext();)
		 {
			 AmrBillsEntity values = (AmrBillsEntity) iterator.next();
			 
			 
			 abb.add(values);
		 }

			return abb; 
		
		 
		
	

	}*/

	/*@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<AmrBillsEntity> previous4Month(String meterNumber,
			String fileDate) {
		
			System.out.println("fileDate========>" + fileDate);
			List<AmrBillsEntity> abb=null;
		
		String qu="SELECT a from meter_data.bill_history a where a.meter_number='"+meterNumber+"' and  to_char(a.billing_date, 'YYYY-MM-DD HH24:MI:SS') like '%01 00:00:00' order by a.billing_date DESC limit 6";
		System.out.println("qu======="+qu);
		Query query = entityManager.createNativeQuery(qu);
		List<AmrBillsEntity> lis = query.getResultList();
		abb.addAll(lis);
		return abb;
		
		
		
	}
	*/

	
	
	
	
	
	
	
	
/*	//
	@Override
	public List<AmrBillsEntity> getRecords(String meterno, String fileDate) {
		System.out.println("fileDate========>" + fileDate);
		String d="";
		try {
			d=new SimpleDateFormat("yyyy-MM").format(new SimpleDateFormat("yyyy-MM-dd").parse(fileDate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		fileDate=d;
		System.out.println("file Date After========>" + fileDate);
		List<AmrBillsEntity> abb = null;
		try {
			System.out.println("arm bills entity");
			// abb=
			// entityManager.createNamedQuery("AmrBillsEntity.getBillHistory",AmrBillsEntity.class).setParameter("meterno",meterno).setParameter("fileDate",fileDate).setMaxResults(1).getResultList();
			abb = entityManager
					.createNamedQuery("AmrBillsEntity.getBillHistory",
							AmrBillsEntity.class)
					.setParameter("meterno", meterno)
					.setMaxResults(5)
					.getResultList();
			System.out.println("after bills");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return abb;//
}*/
		
		
		 
	
	 //old one for no b2 tag
	  @Override
	public List<AmrBillsEntity> getRecords(String meterno, String fileDate)  {
		List<AmrBillsEntity> abb = new ArrayList<>();
		List<AmrBillsEntity> abb1= new ArrayList<>();
	 	final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
		DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		
		
		
		 try{
		    	//System.out.println("arm bills entity");
				abb1= getCustomEntityManager("postgresMdas").createNamedQuery("AmrBillsEntity.getBillHistoryFirst",AmrBillsEntity.class).setParameter("meterno",meterno).setParameter("fileDate",fileDate).setMaxResults(1).getResultList();
				 //System.out.println("after bills");
		       }
		       catch (Exception e) 
		       {
			e.printStackTrace();
		         }
		
		
		try {
			  d = formatter.parse(fileDate+" 00:00:00");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
    try{
    	//System.out.println("arm bills entity");
		abb= getCustomEntityManager("postgresMdas").createNamedQuery("AmrBillsEntity.getBillHistory",AmrBillsEntity.class).setParameter("meterno",meterno).setParameter("fileDate",d).setMaxResults(4).getResultList();
		 //System.out.println("after bills");
       }
       catch (Exception e) 
       {
	e.printStackTrace();
         }	
    
    //System.out.println("======abb   "+abb.size());
   // System.out.println("======abb1   "+abb1.size());

    abb.addAll(abb1);
   // System.out.println("total abb========="+abb.size());
        return abb;
	
	}

	@Override
	public List<AmrBillsEntity> getbillHistoryDetails(String mtrNo, String frmDate, String tDate) {
		List<AmrBillsEntity> list=null;
		try 
		{
			list = getCustomEntityManager("postgresMdas").createNamedQuery("AmrBillsEntity.getbillHistoryDetails", AmrBillsEntity.class).setParameter("meterno", mtrNo).setParameter("from", frmDate).setParameter("to", tDate).getResultList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				AmrBillsEntity amrBillsEntity = (AmrBillsEntity) iterator.next();
				ObjectMapper om=new ObjectMapper();
				
				System.out.println(om.writeValueAsString(amrBillsEntity));
				
			}
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AmrBillsEntity> getBillHistory6months(String mtrNo,ArrayList<String> strlist,String radioValue) 
	{
		String s="";
		for(int i=0;i<strlist.size();i++){
			String t=strlist.get(i);
			//s+="'"+t+"'";
			s+=t;
			if(i<(strlist.size()-1)){
				s+=",";
			}
		}
		//System.out.println("-----------S--------- "+s);
		String[] str=s.split(",");
		List<AmrBillsEntity> list=null;
		String sql=null;
		try
		{
			
			if("meterno".equals(radioValue)) {

				
				
					sql="Select * from (select to_char(AA.billing_date,'yyyy-mm-dd HH24:MI:SS')as billing_date,AA.kwh,AA.kvah,AA.kva,AA.date_kva,\n" +
						"AA.kwh-(case when AA.PreviousKwh is null then AA.kwh else AA.PreviousKwh end) as kwh_consumption,\n" +
						"AA.kvah-( case when AA.Previouskvah is null then AA.kvah else AA.Previouskvah end) as kvah_consumption,\n" +
						"AA.kwh_tz1,AA.kwh_tz2,AA.kwh_tz3,AA.kwh_tz4,AA.kwh_tz5,AA.kwh_tz6,AA.kwh_tz7,AA.kwh_tz8,AA.\n" +
						"kvah_tz1,AA.kvah_tz2,AA.kvah_tz3,AA.kvah_tz4,AA.kvah_tz5,AA.kvah_tz6,AA.kvah_tz7,AA.kvah_tz8,AA.\n" +
						"demand_kw,AA.occ_date_kw,AA.kvarh_lag,AA.kvarh_lead,AA.bill_kwh_export,AA.bill_kvah_export,AA.\n" +
						"PreviousKwh,AA.PreviousKvah,AA.PEV_billing_date FROM (\n" +
						"SELECT\n" +
						"billing_date,kwh,kvah,kva,date_kva,\n" +
						"kwh_tz1,kwh_tz2,kwh_tz3,kwh_tz4,kwh_tz5,kwh_tz6,kwh_tz7,kwh_tz8,\n" +
						"kvah_tz1,kvah_tz2,kvah_tz3,kvah_tz4,kvah_tz5,kvah_tz6,kvah_tz7,kvah_tz8,\n" +
						"demand_kw,occ_date_kw,kvarh_lag,kvarh_lead,bill_kwh_export,bill_kvah_export,\n" +
						"LAG(kwh) OVER ( ORDER BY billing_date ) AS PreviousKwh,\n" +
						"LAG(kvah) OVER (ORDER BY billing_date ) AS PreviousKvah,\n" +
						"LAG(billing_date) OVER ( ORDER BY billing_date ) AS PEV_billing_date\n" +
					"FROM meter_data.bill_history WHERE meter_number ='"+mtrNo+"'\n" +
					"AND to_char(billing_date,'yyyy-mm-dd HH24:MI:SS') in ('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"','"+str[5]+"')\n" +
						"order by billing_date) AA ORDER BY AA.billing_date desc)x where billing_date in ('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"')";
					
				System.out.println("sql--->"+sql);
			
				/*
				 * sql="Select * from (select to_char(AA.billing_date,'yyyy-mm-dd HH24:MI:SS')as billing_date,AA.kwh,AA.kvah,AA.kva,AA.date_kva,\r\n"
				 * +
				 * "AA.kwh-(case when AA.PreviousKwh is null then AA.kwh else AA.PreviousKwh end) as kwh_consumption,\r\n"
				 * +
				 * "AA.kvah-( case when AA.Previouskvah is null then AA.kvah else AA.Previouskvah end) as kvah_consumption,\r\n"
				 * +
				 * "AA.kwh_tz1,AA.kwh_tz2,AA.kwh_tz3,AA.kwh_tz4,AA.kwh_tz5,AA.kwh_tz6,AA.kwh_tz7,AA.kwh_tz8,AA.\r\n"
				 * +
				 * "kvah_tz1,AA.kvah_tz2,AA.kvah_tz3,AA.kvah_tz4,AA.kvah_tz5,AA.kvah_tz6,AA.kvah_tz7,AA.kvah_tz8,AA.\r\n"
				 * +
				 * "demand_kw,AA.occ_date_kw,AA.kvarh_lag,AA.kvarh_lead,AA.bill_kwh_export,AA.bill_kvah_export,AA.\r\n"
				 * +
				 * "PreviousKwh,AA.PreviousKvah,AA.PEV_billing_date,AA.dt_capacity,AA.fdrname,AA.meter_number,AA.section FROM (\r\n"
				 * + "SELECT\r\n" + "bh.billing_date,bh.kwh,bh.kvah,bh.kva,bh.date_kva,\r\n" +
				 * "bh.kwh_tz1,bh.kwh_tz2,bh.kwh_tz3,bh.kwh_tz4,bh.kwh_tz5,bh.kwh_tz6,bh.kwh_tz7,bh.kwh_tz8,\r\n"
				 * +
				 * "bh.kvah_tz1,bh.kvah_tz2,bh.kvah_tz3,bh.kvah_tz4,bh.kvah_tz5,bh.kvah_tz6,bh.kvah_tz7,bh.kvah_tz8,\r\n"
				 * +
				 * "bh.demand_kw,bh.occ_date_kw,bh.kvarh_lag,bh.kvarh_lead,bh.bill_kwh_export,bh.bill_kvah_export,\r\n"
				 * + "LAG(bh.kwh) OVER ( ORDER BY bh.billing_date ) AS PreviousKwh,\r\n" +
				 * "LAG(bh.kvah) OVER (ORDER BY bh.billing_date ) AS PreviousKvah,\r\n" +
				 * "LAG(bh.billing_date) OVER ( ORDER BY bh.billing_date ) AS PEV_billing_date,max( \r\n"
				 * + "						                CASE \r\n" +
				 * "						                    WHEN (c.dtcapacity = (100)) THEN 100 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (250)) THEN 250 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (500)) THEN 500 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (25)) THEN 25 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (40)) THEN 40 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (63)) THEN 63 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (315)) THEN 315 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (300)) THEN 300 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (200)) THEN 200 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (175)) THEN 175 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (160)) THEN 160 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (150)) THEN 150 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (75)) THEN 75 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (50)) THEN 50 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity = (16)) THEN 16 \r\n"
				 * +
				 * "						                    WHEN (c.dtcapacity IS NULL) THEN 100 \r\n"
				 * + "						                    ELSE NULL  \r\n" +
				 * "						                END) AS dt_capacity,mm.fdrname,ami.section,bh.meter_number\r\n"
				 * +
				 * "FROM meter_data.bill_history bh LEFT JOIN meter_data.dtdetails c ON(bh.meter_number=c.meterno) LEFT JOIN meter_data.master_main mm ON(bh.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code) WHERE meter_number ='"
				 * +mtrNo+"'\r\n" +
				 * "AND to_char(billing_date,'yyyy-mm-dd HH24:MI:SS') in ('"+str[0]+"','"+str[1]
				 * +"','"+str[2]+"','"+str[3]+"','"+str[4]+"','"+str[5]
				 * +"') GROUP BY bh.billing_date,bh.kwh,bh.kvah,bh.kva,bh.date_kva,\r\n" +
				 * "bh.kwh_tz1,bh.kwh_tz2,bh.kwh_tz3,bh.kwh_tz4,bh.kwh_tz5,bh.kwh_tz6,bh.kwh_tz7,bh.kwh_tz8,\r\n"
				 * +
				 * "bh.kvah_tz1,bh.kvah_tz2,bh.kvah_tz3,bh.kvah_tz4,bh.kvah_tz5,bh.kvah_tz6,bh.kvah_tz7,bh.kvah_tz8,\r\n"
				 * +
				 * "bh.demand_kw,bh.occ_date_kw,bh.kvarh_lag,bh.kvarh_lead,bh.bill_kwh_export,bh.bill_kvah_export,mm.fdrname,ami.section,bh.meter_number\r\n"
				 * + "\r\n" +
				 * "order by billing_date) AA ORDER BY AA.billing_date desc)x where billing_date in ('"
				 * +str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"') \r\n" + " ";
				 */
			
			System.out.println("sql--->"+sql);
			
			}
			else{
				sql="select * ,AA.kwh-AA.PreviousKwh as kwh_consumption,AA.kvah-AA.Previouskvah as kvah_consumption FROM (\n" +
						"SELECT\n" +
						"	*,LAG(kwh) OVER ( ORDER BY billing_date ) AS PreviousKwh,  LAG(kvah) OVER ( ORDER BY billing_date ) AS PreviousKvah,LAG(billing_date) OVER ( ORDER BY billing_date ) AS PEV_billing_date\n" +
						"FROM\n" +
						"	meter_data.bill_history A\n" +
						"WHERE\n" +
						"	A .meter_number =(SELECT distinct metrno from meter_data.metermaster where kno='"+mtrNo+"')\n" +
						"AND A .billing_date in ('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"') order by billing_date\n" +
						") AA  ORDER BY AA.billing_date desc";                                                                     
			}
			
			System.out.println("in billing history-----"+sql);
				
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		
			//System.err.println("===>"+sql);
			
			/*}
			else
			{*/
				/*String sql="SELECT\n" +
						"	*\n" +
						"FROM\n" +
						"	meter_data.bill_history A\n" +
						"WHERE\n" +
						"	A .meter_number =(select mtrno from meter_data.master_main where accno='"+mtrNo+"')\n" +
						"AND A .billing_date in ('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"')";*/
				
			/*	
				String sql="select * ,AA.kwh-AA.PreviousKwh as kwh_consumption,AA.kvah-AA.Previouskvah as kvah_consumption FROM (\n" +
						"SELECT\n" +
						"	*,LAG(kwh) OVER ( ORDER BY billing_date ) AS PreviousKwh,  LAG(kvah) OVER ( ORDER BY billing_date ) AS PreviousKvah,LAG(billing_date) OVER ( ORDER BY billing_date ) AS PEV_billing_date\n" +
						"FROM\n" +
						"	meter_data.bill_history A\n" +
						"WHERE\n" +
						"	A .meter_number =(select mtrno from meter_data.master_main where accno='"+mtrNo+"')\n" +
						"AND A .billing_date in ('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"') order by billing_date\n" +
						") AA where AA.PreviousKwh is not null  ORDER BY AA.billing_date desc;";
				
				list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				System.err.println("===>"+sql);
			}*/
			/*list = getCustomEntityManager("postgresMdas").createNamedQuery("AmrBillsEntity.getbillHistory6months", AmrBillsEntity.class).setParameter("meterno", mtrNo).setParameter("listMonths", s).getResultList();*/
			
			
			//System.err.println("===>"+list.size());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<AmrBillsEntity> getbillHistoryDetailsInfo(String mtrNo) {
		
		List<AmrBillsEntity> list=null;
		String sql=null;
		try
		{
		
		if("meterno"!=null || "meterno"==null ) {
			sql="select DISTINCT ON(bh.meter_number)bh.meter_number,mm.fdrname,c.dtname,ami.section,max( \r\n" + 
					"						   CASE \r\n" + 
					"										WHEN (c.dtcapacity = (100)) THEN 100 \r\n" + 
					"										WHEN (c.dtcapacity = (250)) THEN 250 \r\n" + 
					"										WHEN (c.dtcapacity = (500)) THEN 500 \r\n" + 
					"										WHEN (c.dtcapacity = (25)) THEN 25 \r\n" + 
					"										WHEN (c.dtcapacity = (40)) THEN 40 \r\n" + 
					"										WHEN (c.dtcapacity = (63)) THEN 63 \r\n" + 
					"										WHEN (c.dtcapacity = (315)) THEN 315 \r\n" + 
					"										WHEN (c.dtcapacity = (300)) THEN 300 \r\n" + 
					"										WHEN (c.dtcapacity = (200)) THEN 200 \r\n" + 
					"										WHEN (c.dtcapacity = (175)) THEN 175 \r\n" + 
					"										WHEN (c.dtcapacity = (160)) THEN 160 \r\n" + 
					"										WHEN (c.dtcapacity = (150)) THEN 150 \r\n" + 
					"										WHEN (c.dtcapacity = (75)) THEN 75 \r\n" + 
					"										WHEN (c.dtcapacity = (50)) THEN 50 \r\n" + 
					"										WHEN (c.dtcapacity = (16)) THEN 16 \r\n" + 
					"										WHEN (c.dtcapacity IS NULL) THEN 100 \r\n" + 
					"										ELSE NULL  \r\n" + 
					"								END) AS dt_capacity from meter_data.bill_history bh LEFT JOIN meter_data.dtdetails c ON (bh.meter_number=c.meterno) LEFT JOIN meter_data.master_main mm ON(bh.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(mm.town_code=ami.tp_towncode) where bh.meter_number='"+mtrNo+"' GROUP BY bh.meter_number,mm.fdrname,c.dtname,ami.section";
			
			
			
			}
			

			
			System.out.println(sql);
			
			
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
}
