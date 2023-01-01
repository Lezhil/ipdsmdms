package com.bcits.service;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.CDFData;

public interface CmriAndMobileService extends GenericService<CDFData>{
	
	public BigDecimal findCDF_IDMobile(String meterNo, int billmonth);
	public BigDecimal findCDF_IDCmri(String meterNo, int billmonth);
	
//D1data	
//public List<?> findD1_DataCmri(BigDecimal cdf_id);
//public List<?> findD1_DataMobile(BigDecimal cdf_id);
public List<?> findD1_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri);

//d2data
public List<?> findD2_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri);

//D3data
public List<?> findD3_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri);

//D4data
public List<?> findD4_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri,ModelMap model);

public List<?> findD4_DataOnDayProfileDate(String cdf_idMobile,String cdf_idCmri,String DayProfileDate);


//D4_LOAD_DATA
public List<?> findD4_Load_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri,ModelMap model);

//D4_LOAD_DATA getIntervals

public List<?> D4_Load_DatagetIntervals(String cdf_idMobile,String cdf_idCmri,String DayProfileDate);

//D4_LOAD_DATA getIntervals

public List<?> D4_Load_DataALL(String cdf_idMobile,String cdf_idCmri,String DayProfileDate,String interval);

public List<?> findD5_DataMobileCmri(BigDecimal cdf_idMobile,BigDecimal cdf_idCmri, ModelMap model);

public List findD5_DataOnDayProfileDate(String mobileCdfId, String cmriCdfId,String trim);
public List<?> getIpIntervalsD4();




}
