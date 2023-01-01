package com.bcits.mdas.serviceImpl;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.entity.NewConsumersEntity;
import com.bcits.mdas.service.NewConsumerService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class NewConsumerServiceImpl extends GenericServiceImpl<NewConsumersEntity> implements NewConsumerService 
{}
