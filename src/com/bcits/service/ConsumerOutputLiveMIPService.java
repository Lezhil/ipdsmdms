package com.bcits.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;

import com.bcits.entity.ConsumerOutputLiveMIPEntity;

public interface ConsumerOutputLiveMIPService extends GenericService<ConsumerOutputLiveMIPEntity>  {
public List<ConsumerOutputLiveMIPEntity> findAll();
public void updateMobileDataToConsumerOutPutLive(HttpServletRequest request, JSONArray array);


}