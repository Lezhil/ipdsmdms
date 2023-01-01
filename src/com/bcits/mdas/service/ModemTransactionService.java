package com.bcits.mdas.service;

import com.bcits.mdas.entity.ModemTransactionEntity;
import com.bcits.service.GenericService;

public interface ModemTransactionService extends GenericService<ModemTransactionEntity>{

	String getPhonenoByImei(String modemID);

	//int saveCommands( Timestamp time_stamp,int is_single_modem,String modem_number,String location_breadcrumbs,String command,String purpose,String media,String user_id);
	
}
