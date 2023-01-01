package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.ReadingRemark;
import com.bcits.service.ReadingRemarkService;
/*Author: Ved Prakash Mishra*/
@Repository
public class ReadingRemarkServiceImpl extends GenericServiceImpl<ReadingRemark> implements ReadingRemarkService {

	@Override
	public List<ReadingRemark> selectReadingRemark() {
	
		return postgresMdas.createNamedQuery("ReadingRemark.SelectReadingRemark").getResultList();
	}

	

}
