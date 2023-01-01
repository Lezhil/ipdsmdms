package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.entity.MRDeviceEntity;
import com.bcits.mdas.service.MRDeviceService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class MRDeviceServiceImpl extends GenericServiceImpl<MRDeviceEntity>	implements
		MRDeviceService {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MRDeviceEntity> findAllMRDevices() {

		return postgresMdas.createNamedQuery("MRDeviceEntity.findAll")
				.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getMatchedDeviceIds(Integer sdoCode) {
		return postgresMdas
				.createNamedQuery("MRDeviceEntity.findMatchedDeviceIds")
				.setParameter("sdoCode", sdoCode).getResultList();

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getMatchedDeviceIdsForAllocation(Integer sdoCode) {

		return postgresMdas
				.createNamedQuery(
						"MRDeviceEntity.getMatchedDeviceIdsForAllocation")
				.setParameter("sdoCode", sdoCode).getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findByDevice(String deviceid ) {
		System.out.println("DEVICE ID :::"+deviceid );
		BCITSLogger.logger.info(" >>>>>>>>findByDevice>>>>>>> ");
		Object object = null ;
try {
	 object = postgresMdas.createNamedQuery("MRDeviceEntity.findByDevice").setParameter("deviceid", deviceid).getSingleResult();
	//BCITSLogger.logger.info(" >>>>>>>>>>>>>>>> "+object.toString());
} catch (Exception e) {
	e.printStackTrace();
	//BCITSLogger.logger.info(" >>>>>>>>>>>>>>>> "+object.toString());
}
		
		return object.toString();
	}

}
