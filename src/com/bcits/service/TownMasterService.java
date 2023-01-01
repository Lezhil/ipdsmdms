/**
 * 
 */
package com.bcits.service;

import org.springframework.web.multipart.MultipartFile;


import com.bcits.entity.TownEntity;

/**
 * @author User
 *
 */
public interface TownMasterService  extends GenericService<TownEntity> {

	TownEntity getTownEntity(String towncode);

}
