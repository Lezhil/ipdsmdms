package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bcits.entity.DTHealthEntity;

public interface DTHealthService extends GenericService<DTHealthEntity> {

	void proceessDTHealthData();

	List<?> getDTHealthReport(String zone,String subdiv, String rdngmnth, String circle, String division, String town,String towncode);

	void manualproceessDTHealthData(String month);

	void processDTHealthData(String rdngmonth);
	public int pushLoadSurveyDt();
	public void loadSurveyDtDailyPush();
	public void loadSurveyDtDataPush(String rdngmonth);
	public void loadSurveyDtDataPushDayWise();
	public void dtpowerfailure();
	public void lfpfuf();
	public void unbalance();
	public void fdrlist();
	public void underload_overload();
	public void underload_overload_seventy();
	public void workingstatus();
	public void dailydtcomm();
	public void dailyConsumption_tneb();
	public void dtDashboardReportQueryReferench();
	public void dtDashboardReportQueryReferench1();
	public void dtDashboardReportQueryReferench2();
	void getDThealthreportPDF(HttpServletRequest request, HttpServletResponse response, String zone1, String crcl,
			String twn, String month, String townname1);

}
