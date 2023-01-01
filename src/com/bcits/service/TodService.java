package com.bcits.service;

import java.util.List;

public interface TodService extends GenericService<Object>
{
   
	List<?>gettodreport(String fdate,String tdate,String mtrno);
} 


