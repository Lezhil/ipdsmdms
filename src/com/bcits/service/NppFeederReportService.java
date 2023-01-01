package com.bcits.service;

import com.bcits.entity.NppFeederReport;
import com.bcits.entity.NppFeederReportIntermediate;

public interface NppFeederReportService extends GenericService<NppFeederReportIntermediate> {
	public String insertNppFeederData(String data,String service);
}
