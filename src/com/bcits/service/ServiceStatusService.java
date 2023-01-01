package com.bcits.service;

import java.util.List;

import com.bcits.entity.Master;
import com.bcits.entity.ServiceStatusEntity;

public interface ServiceStatusService extends GenericService<ServiceStatusEntity> {

public List<?> getServiceStatus();

public List<?> getEditServiceStatus(int id);

public int getModifyServiceStatus(String id,String servicename,String servicestatus,String servicetype);

}

