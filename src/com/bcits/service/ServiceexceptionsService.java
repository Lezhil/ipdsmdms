package com.bcits.service;

import java.util.List;
import com.bcits.entity.ServiceExceptionEntity;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


public interface ServiceexceptionsService extends GenericService<ServiceExceptionEntity>
{																				
    List<?>getExceptionreports();
	List<?>getExceptionreport(String fdate,String tdate,String service);
	List<?> getmobileno();
}
