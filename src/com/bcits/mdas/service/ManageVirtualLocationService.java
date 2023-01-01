package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.VirtualLocation;
import com.bcits.service.GenericService;

public interface ManageVirtualLocationService  extends GenericService<VirtualLocation> {
       List<VirtualLocation> findall();

	//List<Object[]> consumerlist(String sc);
	List<Object[]> consumerlistupdate(String sc, String vlid);
	boolean findVirtualLocation(String vl);
	       String sequenceVirtualID();

		//List<Object[]> feederlist(String sc);
		List<Object[]> feederlistupdate(String sc, String vlid);
		//List<Object[]> dtlist(String sc);
		List<Object[]> dtlistupdate(String sc, String vlid);
		List<Object[]> vlConsumData(String ldata);

		void removeVL(String vl);

		Object getVirtualLocation(String vlid);

		List<Object[]> consumerlist(String sc, String meterno, String accno);

		List<Object[]> feederlist(String sc, String meterno, String fdcode);

		List<Object[]> dtlist(String sc, String meterno, String dtcode);

	
}
