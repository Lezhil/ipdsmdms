package com.bcits.mdas.serviceImpl;


import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.ModemTransactionEntity;
import com.bcits.mdas.service.ModemTransactionService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class ModemTransactionImpl extends GenericServiceImpl<ModemTransactionEntity> implements ModemTransactionService{

	@Override
	public String getPhonenoByImei(String modemID) {
		try {
			return (String) getCustomEntityManager("postgresMdas").createNativeQuery("SELECT phone_no FROM meter_data.modem_master WHERE modem_imei='"+modemID+"'").getSingleResult();
		}catch (Exception e) {
			return "";
		}
	}

	/*@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public int saveCommands(Timestamp time_stamp, int is_single_modem,
			String modem_number, String location_breadcrumbs, String command,
			String purpose, String media, String user_id) {
		
		int count=0;
		try
		{
			count = entityManager.createNamedQuery("ModemTransactionEntity.saveCommands",ModemTransactionEntity.class).setParameter("time_stamp",time_stamp).setParameter("is_single_modem",is_single_modem).
					setParameter("modem_number",modem_number).setParameter("location_breadcrumbs",location_breadcrumbs).setParameter("command",command).
					setParameter("purpose",purpose).setParameter("media",media).setParameter("user_id",user_id).getFirstResult();	
		}
		catch(Exception e)
		{
			System.err.print("error in ModemTransactionImpl");
			
		}
		
		
		return count;
	}
*/
	
}
