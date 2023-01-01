package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.SearchNodes;
import com.bcits.service.GenericService;

public interface SearchNodesService extends GenericService<SearchNodes> {

	long highcount();
	void updateLocationsService();
	List<SearchNodes> dcuList();

}
