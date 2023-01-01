package com.bcits.service;

import java.util.List;

import com.bcits.entity.Location;
import com.bcits.mdas.entity.AmiLocation;

public interface HierarchyService extends GenericService<AmiLocation> 
{
	//FOR GETTING THE HIERARCHY DETAILS
	
			List<AmiLocation> getALLLocationData();
			
			List<AmiLocation> getdistinctZones();
			
			List<AmiLocation> getAllExistingCircles(String zone);
			
			List<AmiLocation> getAlldivisionsUnderCircle(String zone,String circle);
			
			List<AmiLocation> getAllsubDivisionsUnderCircle(String zone,String circle,String division);
			
			List<AmiLocation> getTownsUnderSubdivisions(String zone, String circle, String division, String subdiv);
			
			List<AmiLocation> getSectionUnderTown(String zone,String circle,String division,String subdiv, String town);
			//checking zone
			public int checkingZone(String zone);

			int checkingCircle(String zoneVal, String circle);
			
			//for getting the circle id
			int getCircleUpdatingId(String zone,String circle);

			int checkingDivision(String zoneVal, String circle, String division);

			int getdivisionUpdatingId(String zoneVal, String circle,
					String division);

			int checkingSubDivision(String zoneVal, String circle,
					String division, String subdiv);

			int getSubdivisionUpdatingId(String zoneVal, String circle,
					String division, String subdiv);
			
		    int getTownUpdatingId(String zoneVal, String circle,String division, String subdiv,String town);
		    
		    int getSectionUpdatingId(String zoneVal, String circle,String division, String subdiv,String town,String section);

			

			int createZoneId();

			Object findZoneDetailsByZone(String zoneVal);

			Object findCircleDetailsByZone(String zoneVal, String circle);

			int createDivId(String circleCode);

			Object findDivDetailsByZone(String zoneVal, String circle, String division);

			int createSubDivId(String divCode);
		
			Object findSubDivDetailsByZone(String zoneVal, String circle, String division, String subdivision);

			int checkingTown(String zoneVal, String circle, String division, String subdiv, String town);
			
			Object findTownDetailsByZone(String zoneVal, String circle, String division, String subdivision,String town);
			
			int checkingSection(String zoneVal, String circle, String division, String subdiv, String town,String section);
			
}
