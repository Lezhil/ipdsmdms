package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bcits.mdas.entity.VirtualLocation;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.ManageVirtualLocationService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Service
public class ManageVirtualLocationServiceImpl extends GenericServiceImpl<VirtualLocation> implements ManageVirtualLocationService {

	@Autowired
	private FeederMasterService feederMasterService;
	@Override
	public List<VirtualLocation> findall() 
	{
		return postgresMdas.createNamedQuery("VirtualLocation.all").getResultList();
	}

	@Override
	public List<Object[]> consumerlist(String sc,String meterno,String accno) {
		String qry="select a.subdivision,c.accno,c.kno,c.name,c.meterno,COALESCE(c.tadesc,'') as cosmunercategory from meter_data.consumermaster c left join meter_data.amilocation a on cast(c.sdocode as integer)=a.sitecode where c.sdocode= '"+sc+"' and c.meterno like '"+meterno+"' and c.accno like '"+accno+"' and (c.vl_id is null or c.vl_id='')";
//		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public boolean findVirtualLocation(String vl) {
		List<VirtualLocation> vll=postgresMdas.createNamedQuery("VirtualLocation.findvl").setParameter("vl", vl).getResultList();
		if(!vll.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public String sequenceVirtualID() {
		String sql="select lv || ( case when LENGTH(ID)=1 then '000' || ID when LENGTH(ID)=2 then '00' || ID when LENGTH(ID)=3 then '0' || ID when LENGTH(ID)=4 then ID END ) As lv_id FROM (select substr(lv_id, 0, 3) as lv, cast (cast(substr (lv_id, 3, LENGTH(lv_id))  As numeric ) + 1 as text ) as ID FROM ( SELECT COALESCE (MAX(vl_id), 'LV0000') AS lv_id from meter_data.virtual_location )a )b";
		String seqId=(String) postgresMdas.createNativeQuery(sql).getSingleResult();
		return seqId;
		
	}

	@Override
	public List<Object[]> feederlist(String sc,String meterno,String fdcode) {
		String qry="select a.*,s.ss_name from (select a.subdivision,c.feedername,c.tpparentid,c.meterno,c.fdr_id,\r\n" + 
				"c.parentid from meter_data.feederdetails c left join meter_data.amilocation a on c.officeid=a.sitecode where c.officeid= '"+sc+"' and c.meterno like '"+meterno+"' and c.fdr_id like '"+fdcode+"' and (vl_id is null or vl_id=''))a, meter_data.substation_details s where s.ss_id=a.parentid";
		//System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<Object[]> dtlist(String sc,String meterno,String dtcode) {
		String qry="select x.*,f.feedername from (select a.subdivision,c.dtname,c.dttpid,c.parentid,c.meterno from meter_data.dtdetails c left join meter_data.amilocation a on c.officeid=a.sitecode where c.officeid='"+sc+"' and c.meterno like '"+meterno+"' and c.dt_id like '"+dtcode+"' and (vl_id is null or vl_id=''))x,meter_data.feederdetails f where f.fdr_id=x.parentid";
//		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<Object[]> vlConsumData(String ldata) {
		String[] s=ldata.split("@");
		if(s[1].equalsIgnoreCase("consumer"))
		{
		String sql="select cast('"+s[2]+"' as text )as subdiv,cast('"+s[1]+"' as text )as loctype,cast('"+s[3]+"' as text) as loc,accno,kno,name,meterno from meter_data.consumermaster where vl_id='"+s[0]+"'";
				List<Object[]> l=postgresMdas.createNativeQuery(sql).getResultList();
				return l;
		}
		else if(s[1].equalsIgnoreCase("feeder"))
		{
			String sql="select cast('"+s[2]+"' as text )as subdiv,cast('"+s[1]+"' as text )as loctype,cast('"+s[3]+"' as text) as loc,feedername,tpparentid,meterno from meter_data.feederdetails where vl_id='"+s[0]+"'";
			List<Object[]> l=postgresMdas.createNativeQuery(sql).getResultList();
			return l;
		}
		else if (s[1].equalsIgnoreCase("dt"))
		{
			String sql="select cast('"+s[2]+"' as text )as subdiv,cast('"+s[1]+"' as text )as loctype,cast('"+s[3]+"' as text) as loc,dtname,tpparentid,parentid,meterno from meter_data.dtdetails where vl_id='"+s[0]+"'";
			List<Object[]> l=postgresMdas.createNativeQuery(sql).getResultList();
			return l;
		}
		else
			return null;
	}
	
	@Override
	public void removeVL(String ldata) {
		String[] s=ldata.split("@");
		if(s[1].equalsIgnoreCase("consumer"))
		{
		String sql="update meter_data.consumermaster set vl_id=null where vl_id='"+s[0]+"'";
				postgresMdas.createNativeQuery(sql).executeUpdate();
		}
		else if(s[1].equalsIgnoreCase("feeder"))
		{
			String sql="update meter_data.feederdetails set vl_id=null where vl_id='"+s[0]+"'";
			postgresMdas.createNativeQuery(sql).executeUpdate();
		}
		else if (s[1].equalsIgnoreCase("dt"))
		{
			String sql="update meter_data.dtdetails set vl_id=null where vl_id='"+s[0]+"'";
			postgresMdas.createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public Object getVirtualLocation(String vlid) {
		Object vll=postgresMdas.createNamedQuery("VirtualLocation.findvldetails").setParameter("vl", vlid).getSingleResult();
		return vll;
	}

	@Override
	public List<Object[]> consumerlistupdate(String sc, String vlid) {
		String qry="select a.subdivision,c.accno,c.kno,c.name,c.meterno, c.vl_id from meter_data.consumermaster c left join meter_data.amilocation a on cast(c.sdocode as integer)=a.sitecode where c.sdocode= '"+sc+"' and (c.vl_id is null or c.vl_id='' or vl_id='"+vlid+"')";
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<Object[]> feederlistupdate(String sc, String vlid) {
		String qry="select a.subdivision,c.feedername,c.tpparentid,c.meterno, c.vl_id from meter_data.feederdetails c left join meter_data.amilocation a on c.officeid=a.sitecode where c.officeid= '"+sc+"' and (vl_id is null or vl_id='' or vl_id='"+vlid+"')";
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<Object[]> dtlistupdate(String sc, String vlid) {
		String qry="select a.subdivision,c.dtname,c.dttpid,c.parentid,c.meterno,c.vl_id from meter_data.dtdetails c left join meter_data.amilocation a on c.officeid=a.sitecode where c.officeid='"+sc+"' and (vl_id is null or vl_id='' or vl_id='"+vlid+"')";
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	
}
