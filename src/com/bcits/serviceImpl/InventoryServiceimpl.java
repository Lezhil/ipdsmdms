package com.bcits.serviceImpl;
import org.springframework.stereotype.Repository;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.service.InventoryService;
@Repository
public class InventoryServiceimpl extends GenericServiceImpl<MasterMainEntity> implements InventoryService {

	@Override
	public Object[] getAllModemDetails() {
	        Object[] modem={"0","0","0"};
	        try {
	            String query="SELECT count(*), "
	                            +" count(CASE WHEN installed='1' THEN 1 END) as installed, "
	                            +"  count(CASE WHEN installed='0' THEN 1 END) as notinstalled, "
	                            +" count(CASE WHEN working_status='0' THEN 1 END) as notworking FROM meter_data.modem_master";
	            
	            return (Object[]) getCustomEntityManager("postgresMdas").createNativeQuery(query).getSingleResult();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return modem;
	        }
	   
	}

}
