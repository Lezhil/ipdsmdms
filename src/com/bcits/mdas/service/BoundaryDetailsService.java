/**
 * 
 */
package com.bcits.mdas.service;

import java.util.HashMap;
import java.util.List;

import com.bcits.entity.FeederEntity;
import com.bcits.mdas.entity.BoundaryMetersEntity;
import com.bcits.service.GenericService;

/**
 * @author Tarik
 *
 */
public interface BoundaryDetailsService extends GenericService<BoundaryMetersEntity> {
	
	public String getlatestBoundaryId(String feedercode);

	//public List<FeederEntity> getMeterDetailsByFdrcode(String fdrcode);
	
	//public List<BoundaryMetersEntity> getMeterDetailsByFdrcode(String fdrcode);
	
	public HashMap<String, String> getlocationHireachy(String feedercode, String tp_sdocode);

}
