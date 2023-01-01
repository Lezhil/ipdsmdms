/**
 * 
 */
package com.bcits.serviceImpl;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.TownEntity;
import com.bcits.service.TownMasterService;

/**
 * @author User
 *
 */

@Repository
public class TownMasterServiceImpl extends GenericServiceImpl<TownEntity> implements TownMasterService {


	@Override
	public TownEntity getTownEntity(String towncode) {
		try {
			 return getCustomEntityManager("postgresMdas").createNamedQuery("TownEntity.getTownEntity",TownEntity.class)
					 .setParameter("towncode", towncode)
					 .getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
