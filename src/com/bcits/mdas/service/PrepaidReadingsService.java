package com.bcits.mdas.service;

import java.util.List;

import com.bcits.mdas.entity.PrepaidReadings;
import com.bcits.service.GenericService;

public interface PrepaidReadingsService extends GenericService<PrepaidReadings> {

	List<PrepaidReadings> getAllReadingsByMtrnoDate(String mtrNo, String fromDate, String toDate);

	List<PrepaidReadings> getPreviousDaysReading(String mtrno, String date);

}
