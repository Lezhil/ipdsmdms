/**
 * 
 */
package com.bcits.mdas.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.FeederEntity;
import com.bcits.mdas.entity.BoundaryMetersEntity;
import com.bcits.mdas.service.BoundaryDetailsService;
import com.bcits.serviceImpl.GenericServiceImpl;

/**
 * @author Tarik
 *
 */
@Repository
public class BoundaryDetailsServiceImpl extends GenericServiceImpl<BoundaryMetersEntity> implements BoundaryDetailsService{

	@Override
	public String getlatestBoundaryId(String feedercode) {
		try{
			String a="SELECT b.part1||'B'||(CASE WHEN length(b.part2)=1 THEN '0'||b.part2 ELSE b.part2 END) as brdrid FROM ( "
					+ "SELECT split_part(a.bid, 'B',1) as part1, CAST(CAST(split_part(a.bid, 'B',2) as INTEGER)+1 as TEXT) as part2 FROM ( "
					+ "SELECT COALESCE(max(boundary_id),'"+feedercode+"B00') as bid FROM meter_data.feederdetails WHERE tp_fdr_id='"+feedercode+"')a )b";
			return String.valueOf(postgresMdas.createNativeQuery(a).getSingleResult());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

//	@Override
//	public List<BoundaryMetersEntity> getMeterDetailsByFdrcode(String fdrcode) {
//		try {
//			return postgresMdas.createNamedQuery("BoundaryMetersEntity.getMeterDetailsByFdrcode",BoundaryMetersEntity.class)
//					.setParameter("tp_feedercode", fdrcode).getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	

	
	public HashMap<String, String> getlocationHireachy(String feedercode,String sdocode){
		HashMap<String, String> h=new HashMap<>();
		String qry = "";
		try {
			
			qry = "SELECT DISTINCT ami.zone,ami.circle,ami.division,ami.subdivision,ami.discom,fdr.officeid,ssd.ss_name from meter_data.amilocation ami,\r\n" + 
					"meter_data.feederdetails fdr, meter_data.substation_details ssd\r\n" + 
					"where  ssd.sstp_id=fdr.tpparentid and ami.tp_towncode=ssd.parent_town and  ami.tp_subdivcode='"+sdocode+"'  and fdr.tp_fdr_id='"+feedercode+"'";
			System.out.println(qry);
			Object[] list = (Object[]) getCustomEntityManager("postgresMdas")
					.createNativeQuery(qry).getSingleResult();
			
				h.put("ZONE", list[0]+"");
				h.put("CIRCLE", list[1]+"");
				h.put("DIVISION", list[2]+"");
				h.put("SUBDIVISION", list[3]+"");
				h.put("DISCOM", list[4]+"");
				h.put("SDOCODE", list[5]+"");
				h.put("SS_NAME", list[6]+"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return h;
	
	}

}
