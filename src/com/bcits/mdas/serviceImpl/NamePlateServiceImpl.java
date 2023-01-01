package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.entity.ValidationProcessReportEntity;
import com.bcits.mdas.service.NamePlateService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class NamePlateServiceImpl extends GenericServiceImpl<NamePlate> implements NamePlateService {
	
	public List<Object[]> nodeIdList(){
		List<Object[]> l=postgresMdas.createNamedQuery("NamePlate.getNodeList").getResultList();
		return l;
		
	}

	@Override
	public NamePlate getNamePlateDataByMeterNo(String meterid) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("NamePlate.getNamePlateDataByMeterNo", NamePlate.class)
					.setParameter("meterid", meterid)
					.getSingleResult();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

}
