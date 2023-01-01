package com.bcits.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;

import com.bcits.entity.NOMRDEntity;
import com.bcits.utility.Resultupdated;

public interface NoMrdService extends GenericService<NOMRDEntity> {
	
	 public ArrayList<Resultupdated> insertNoMRD(HttpServletRequest request, JSONArray array);

}