package com.bcits.service;

import java.util.List;

import com.bcits.entity.Assesment;

public interface AssesmentService extends GenericService<Assesment>
{

	public  List<Assesment> findAll();

}