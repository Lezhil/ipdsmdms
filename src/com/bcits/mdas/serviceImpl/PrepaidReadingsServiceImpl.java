package com.bcits.mdas.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.PrepaidReadings;
import com.bcits.mdas.service.PrepaidReadingsService;
import com.bcits.serviceImpl.GenericServiceImpl;
@Repository
public class PrepaidReadingsServiceImpl extends GenericServiceImpl<PrepaidReadings> implements PrepaidReadingsService{

	@Override
	public List<PrepaidReadings> getAllReadingsByMtrnoDate(String mtrNo, String fromDate, String toDate) {
		
		System.out.println(mtrNo+"----"+fromDate+"----"+toDate);
		
		List<PrepaidReadings> list = postgresMdas.createNamedQuery("PrepaidReadings.getAllReadingsByMtrnoDate", PrepaidReadings.class)
								.setParameter("mtrno", mtrNo)
								.setParameter("fromdate", fromDate)
								.setParameter("todate", toDate).getResultList();
			
		System.out.println(list.size());
		return list;
	}

	@Override
	public List<PrepaidReadings> getPreviousDaysReading(String mtrno, String date) {
		List<PrepaidReadings> list = postgresMdas.createNamedQuery("PrepaidReadings.getPreviousDaysReading", PrepaidReadings.class)
				.setParameter("mtrno", mtrno)
				.setParameter("date", date)
				.getResultList();

		System.out.println(list.size());
		return list;
	}

}
