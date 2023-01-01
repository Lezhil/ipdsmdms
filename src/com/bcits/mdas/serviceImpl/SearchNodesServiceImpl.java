package com.bcits.mdas.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.SearchNodes;
import com.bcits.mdas.service.SearchNodesService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class SearchNodesServiceImpl extends GenericServiceImpl<SearchNodes> implements SearchNodesService{
	@Override
	public long highcount() {
		BigDecimal hn;
		int i=0;
		
		try
		{
		 hn =(BigDecimal) postgresMdas.createNativeQuery("select max(seq_id) from meter_data.search_nodes where to_char(time_stamp,'yyyy-MM-dd')=to_char(CURRENT_TIMESTAMP,'yyyy-MM-dd')")
				.getSingleResult();
		 i=hn.intValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return i;
		
	}

	@Override
	public void updateLocationsService() {

		 String s="select 'update meter_data.master_main set longitude='''||longitude||''', latitude='''||latitude||''' where mtrno='''||meter_serial_number||''';' from (\n" +
				 "select np.node_id,np.meter_serial_number,sn.longitude,sn.latitude from meter_data.name_plate np left join meter_data.search_nodes sn on sn.node_id=np.node_id  where np.node_id is not null \n" +
				 "and to_char(sn.time_stamp,'yyyy-MM-dd')=to_char(CURRENT_TIMESTAMP,'yyyy-MM-dd')\n" +
				 ") a";
		List<Object[]> l=  postgresMdas.createNativeQuery(s).getResultList();
	  for (Object objects : l) {
		  
		int i=postgresMdas.createNativeQuery(objects.toString()).executeUpdate();
	}
	 
	  
		
	}
	@Override
	public List<SearchNodes> dcuList(){
		return postgresMdas.createNamedQuery("SearchNodes.dculist").getResultList();
		
	}
}
