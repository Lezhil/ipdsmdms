package com.bcits.service;

import com.bcits.mdas.entity.MasterMainEntity;

public interface InventoryService extends GenericService<MasterMainEntity> {
	
	Object[] getAllModemDetails();

}
