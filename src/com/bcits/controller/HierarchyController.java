package com.bcits.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.TownEntity;
import com.bcits.mdas.entity.AmiLocation;
import com.bcits.mdas.service.BusinessRoleService;
import com.bcits.service.HierarchyService;
import com.bcits.service.TownMasterService;

@Controller
public class HierarchyController 
{
	@Autowired
	private HierarchyService hierarchyService;
	
	@Autowired
	private TownMasterService townMasterService;
	
	@Autowired
	private BusinessRoleService businessRoleService;
	
	
	//private static String  defaultZone=null;
	  @RequestMapping(value="/hierarchyLevels",method={RequestMethod.GET,RequestMethod.POST})
		public String  hierarchyLevelsPage(HttpServletResponse response,HttpServletRequest request,ModelMap model)
		{
		  
		  //hierarchyService.getALLLocationData();
		  //model.addAttribute("existingCircles",hierarchyService.getAllExistingCircles());
		  model.addAttribute("existingZones",hierarchyService.getdistinctZones());
		  model.addAttribute("latestHierarchyData",hierarchyService.getALLLocationData());
		  return "HierarchyLevels";
		}
	  @RequestMapping(value="/getCirclesUnderZones",method={RequestMethod.GET,RequestMethod.POST})
	  public @ResponseBody List<AmiLocation>  getCirclesbyZones(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String zone)
	   {
		        //model.addAttribute("existingCircles",hierarchyService.getAllExistingCircles());
		        return hierarchyService.getAllExistingCircles(zone);
			 
	   }
	  @RequestMapping(value="/getDivisionsUnderCircles",method={RequestMethod.GET,RequestMethod.POST})
	  public @ResponseBody List<AmiLocation>  getDivisionssbyCircle(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String zone,@RequestParam String circle)
	   {
		        //model.addAttribute("existingCircles",hierarchyService.getAllExistingCircles());
		        return hierarchyService.getAlldivisionsUnderCircle(zone, circle);
			 
	   }
	  @RequestMapping(value="/getSubdivisionsUnderDivision",method={RequestMethod.GET,RequestMethod.POST})
	  public @ResponseBody List<AmiLocation>  getSubdivisionsUnderDivision(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String zone,@RequestParam String circle,@RequestParam String division)
	   {
		        //model.addAttribute("existingCircles",hierarchyService.getAllExistingCircles());
		        return hierarchyService.getAllsubDivisionsUnderCircle(zone, circle, division);
			 
	   }
	  @RequestMapping(value="/getTownsUnderSubdivisions",method={RequestMethod.GET,RequestMethod.POST})
	  public @ResponseBody List<AmiLocation>  getTownsUnderSubdivisions(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String zone,@RequestParam String circle,@RequestParam String division,@RequestParam String subdiv)
	   {
		        //model.addAttribute("existingCircles",hierarchyService.getAllExistingCircles());
		        return hierarchyService.getTownsUnderSubdivisions(zone, circle, division,subdiv);
			 
	   }
	  
	  @RequestMapping(value="/getSectionUnderTown",method= {RequestMethod.GET,RequestMethod.POST})
	  public @ResponseBody List<AmiLocation> getSectionUnderTown(HttpServletResponse response,HttpServletRequest request,ModelMap model,@RequestParam String zone,@RequestParam String circle, @RequestParam String division,@RequestParam String subdiv,@RequestParam String town){
		  
		  
		  			return hierarchyService.getSectionUnderTown(zone,circle,division,subdiv,town);
	  }
	  
	  
	  @RequestMapping(value="/addingZone",method={RequestMethod.GET,RequestMethod.POST})
	  public String  addingZone(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	   {
		        //hierarchyService.getAllsubDivisionsUnderCircle(zone, circle, division);
		        
		  String zoneVal=request.getParameter("newZone");
		  String zoneCode=request.getParameter("newZoneCode");
		        AmiLocation loc=new AmiLocation();
		       
		       String saveStatus="";
		       int status=hierarchyService.checkingZone(zoneCode);
		        if(status==0)
		        {
		        	loc.setZoneCode(hierarchyService.createZoneId());
		        	loc.setZone(zoneVal.toUpperCase());
		        	loc.setTp_zonecode(zoneCode.trim());
		        	//loc.se
		        	loc.setComapny(businessRoleService.getCompanyName());
		        	loc.setDiscom("TNEB");
		        	loc.setDiscom_code(7000000);
		        	

		        	try {
		        		hierarchyService.save(loc);	
					} catch (Exception e) {
						e.printStackTrace();
					}
		        	 
		        	 saveStatus="NEW REGION ADDED SUCCESSFULLY.";
		        	 model.put("results",saveStatus );
		        }else
		        {
		        	saveStatus="REGION ALREADY EXISTS!";
		        	model.put("results",saveStatus );
		        }
		        model.addAttribute("latestHierarchyData",hierarchyService.getALLLocationData());
		        model.addAttribute("existingZones",hierarchyService.getdistinctZones());
		        return "HierarchyLevels";
		        /*return "redirect:/hierarchyLevels";*/
			 
	   }
	  
	  
	  @RequestMapping(value="/newcircleunderzone",method={RequestMethod.GET,RequestMethod.POST})
	  public String  newcircleUnderzone(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	   {
		        //hierarchyService.getAllsubDivisionsUnderCircle(zone, circle, division);
		        
		  String zoneVal=request.getParameter("zoneFor");
		  String circle=request.getParameter("newCircle");
		  String circleCode=request.getParameter("newCircleCode");
		  System.err.println("the updating zoneVal ===>"+zoneVal);
		  System.err.println("the updating circle ===>"+circle);
		  System.err.println("the updating circleCode ===>"+circleCode);
		  String saveStatus="";
		  Object[] zoneData = (Object[]) hierarchyService.findZoneDetailsByZone(zoneVal);
		  if(zoneData.length>0) {
			  int status=hierarchyService.checkingCircle(zoneData[0].toString(),circleCode);
		        if(status==0)
		        {
		        	//NEED TO FIND THE EXISTING OBJECT ID AND NEED TO UPDATE
		        	int existingId=hierarchyService.getCircleUpdatingId(zoneVal, circle);
		        	if(existingId!=0)
		        	{
		        		// System.err.println("the updating location id===>"+existingId);
			        	 AmiLocation locData = hierarchyService.find(existingId);
			        	 locData.setCircle(circle.toUpperCase());
			        	 locData.setCircleCode(Integer.parseInt(zoneData[2].toString().substring(0, 1)+""+circleCode.substring(1, 4)+"000"));
			        	 locData.setTp_circlecode(circleCode.trim());
							try
							{
								hierarchyService.update(locData);
								saveStatus="NEW CIRCLE UPDATED SUCCESSFULLY UNDER REGION: "+zoneVal;
							}catch(Exception e)
							{
								 saveStatus="PROBLEM WHILE UPDATING THE CIRCLE FOR REGION: "+zoneVal;
							}
							model.put("results",saveStatus );
		        	}else
		        	{
		        		try
		        		{
		        			AmiLocation loc=new AmiLocation();
			        		loc.setZone(zoneVal.toUpperCase());
			        		loc.setZoneCode(Integer.parseInt(zoneData[2].toString().trim()));
			        		loc.setTp_zonecode(zoneData[0].toString().trim());
			        		loc.setCircle(circle.toUpperCase());
			        		loc.setCircleCode(Integer.parseInt(zoneData[2].toString().substring(0, 1)+""+circleCode.substring(1, 4)+"000"));
				        	loc.setTp_circlecode(circleCode.trim());
			        		loc.setComapny(businessRoleService.getCompanyName());
			        		loc.setDiscom("TNEB");
				        	loc.setDiscom_code(7000000);
			        		hierarchyService.save(loc);
			        		saveStatus="NEW CIRCLE ADDED SUCCESSFULLY UNDER REGION: "+zoneVal;
		        		}catch(Exception e)
		        		{
		        			saveStatus="PROBLEM WHILE ADDING THE CIRCLE FOR REGION: "+zoneVal;
		        		}
		        		model.put("results",saveStatus );
		        	}
		        	
		        	 
		        }else
		        {
		        	saveStatus="CIRCLE ALREADY EXISTS UNDER REGION "+zoneVal;
		        	model.put("results",saveStatus );
		        }
			  
		  }
		  	       
        model.addAttribute("latestHierarchyData",hierarchyService.getALLLocationData());
        model.addAttribute("existingZones",hierarchyService.getdistinctZones());
        return "HierarchyLevels";
		       /* return "redirect:/hierarchyLevels";*/	 
	   }
	  
	@RequestMapping(value = "/newsubdivisionunderzone", method = { RequestMethod.GET, RequestMethod.POST })
	public String newsubdivisionunderzone(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		String zoneVal = request.getParameter("newZoneFor3");
		String circle = request.getParameter("selCircle2");
		String division = request.getParameter("div1");
		String subdiv = request.getParameter("newSubdivision");
		String subdivCode = request.getParameter("newSubdivisionCode");

		String saveStatus = "";
		Object[] divData = (Object[]) hierarchyService.findDivDetailsByZone(zoneVal, circle, division);
		if (divData.length > 0) {
			
			int status = hierarchyService.checkingSubDivision(divData[0].toString(), divData[3].toString(), divData[6].toString(), subdivCode);
			

			if (status == 0) {
				int existid = hierarchyService.getSubdivisionUpdatingId(zoneVal, circle, division, subdiv);
				int subdivid =hierarchyService.createSubDivId(divData[8].toString());
				System.out.println("existid--->" + existid);
				if (existid != 0) {
					AmiLocation locData = hierarchyService.find(existid);
					locData.setSubDivision(subdiv.toUpperCase());
					locData.setTp_subdivcode(subdivCode.trim());					
		        	locData.setSitecode(Integer.parseInt(divData[8].toString().substring(0, 5)+""+subdivid));
					
					try {
						hierarchyService.update(locData);
						saveStatus = "NEW SUBDIVISION UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division;

					} catch (Exception e) {
						saveStatus = "PROBLEM WHILE UPDATING THE SUBDIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division;
					}
					model.put("results", saveStatus);
				} else {
					try {
						AmiLocation loc = new AmiLocation();
						
						loc.setZone(zoneVal.toUpperCase());
		        		loc.setZoneCode(Integer.parseInt(divData[2].toString().trim()));
		        		loc.setTp_zonecode(divData[0].toString().trim());
		        		
		        		loc.setCircle(circle.toUpperCase());
		        		loc.setCircleCode(Integer.parseInt(divData[5].toString()));
			        	loc.setTp_circlecode(divData[3].toString().trim());
			        	
		        		loc.setComapny(businessRoleService.getCompanyName());
					
						loc.setDivision(division.toUpperCase());
						loc.setTp_divcode(divData[6].toString().trim());					
						loc.setDivisionCode(Integer.parseInt(divData[8].toString().trim()));
						
						
						loc.setSubDivision(subdiv.toUpperCase());
						loc.setTp_subdivcode(subdivCode.trim());					
			        	loc.setSitecode(Integer.parseInt(divData[8].toString().substring(0, 5)+""+subdivid));
			        	loc.setDiscom("TNEB");
			        	loc.setDiscom_code(7000000);
						
						hierarchyService.save(loc);
						saveStatus = "NEW SUBDIVISION ADDED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division;
					} catch (Exception e) {
						saveStatus = "PROBLEM WHILE ADDING THE SUBDIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division;
					}
					model.put("results", saveStatus);
				}
			} else {
				saveStatus = "SUBDIVISION ALREADY EXISTS UNDER REGION " + zoneVal + " ,CIRCLE " + circle+ ",DIVISION: " + division;
				model.put("results", saveStatus);
			}

		}else {
			saveStatus = "PROBLEM WHILE ADDING THE SUBDIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division;
		}		
		model.addAttribute("latestHierarchyData", hierarchyService.getALLLocationData());
		model.addAttribute("existingZones", hierarchyService.getdistinctZones());
		return "HierarchyLevels";
	}
	  
	
	
	
	  
	  //code to add new section
	  
	  @RequestMapping(value = "/newSectionunderzone", method = { RequestMethod.GET, RequestMethod.POST })
	  public String newSectionunderzone (HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		  
		  HttpSession session = request.getSession(false);
		    if (session == null) {
				return "redirect:./?sessionVal=expired";
			}
		    
		    
		    String zoneVal = request.getParameter("newZoneFor5");
			String circle = request.getParameter("selCircle4");
			String division = request.getParameter("div3");
			String subdiv = request.getParameter("subdiv");
			String town = request.getParameter("town");
			String section=request.getParameter("newSection");
			String sectionCode = request.getParameter("newSectionCode");
			
			String saveStatus = "";
			Object[] sectionData = (Object[]) hierarchyService.findTownDetailsByZone(zoneVal, circle, division, subdiv,town);
			if (sectionData.length > 0) {
				
				
				int status = hierarchyService.checkingSection(sectionData[0].toString(), sectionData[3].toString(), sectionData[6].toString(), sectionData[9].toString(),sectionData[12].toString(),sectionCode);
				if (status == 0) {
					
					int existid = hierarchyService.getSectionUpdatingId(zoneVal, circle, division, subdiv,town,section);
					
					System.out.println("existid--->" + existid);
					
					if (existid != 0) {
						AmiLocation locData = hierarchyService.find(existid);
						locData.setSection(section.toUpperCase());
						locData.setTp_sectioncode(sectionCode.trim());
						locData.setSitecode(Integer.parseInt(sectionData[8].toString().substring(0, 5)+""+section));
						
						
						
						try {
							hierarchyService.update(locData);
							saveStatus = "NEW SECTION UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv +",TOWN_IPDS: "+town;

						} catch (Exception e) {
							saveStatus = "PROBLEM WHILE UPDATING THE SECTION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv+",TOWN_IPDS: "+town;
						}
						model.put("results", saveStatus);
					}else {
						
						try {
							AmiLocation loc = new AmiLocation();
							
							loc.setZone(zoneVal.toUpperCase());
			        		loc.setZoneCode(Integer.parseInt(sectionData[2].toString().trim()));
			        		loc.setTp_zonecode(sectionData[0].toString().trim());
			        		
			        		loc.setCircle(circle.toUpperCase());
			        		loc.setCircleCode(Integer.parseInt(sectionData[5].toString()));
				        	loc.setTp_circlecode(sectionData[3].toString().trim());
				        	
			        		loc.setComapny(businessRoleService.getCompanyName());
						
							loc.setDivision(division.toUpperCase());
							loc.setTp_divcode(sectionData[6].toString().trim());					
							loc.setDivisionCode(Integer.parseInt(sectionData[8].toString().trim()));
							
							
							loc.setSubDivision(subdiv.toUpperCase());
							loc.setTp_subdivcode(sectionData[9].toString().trim());					
				        	loc.setSitecode(Integer.parseInt(sectionData[11].toString().trim()));
				        	
				        	
				    		
							loc.setTown_ipds(town.toUpperCase());
							loc.setTown_ipds((sectionData[12].toString().trim()));
									
							
//				        	loc.setSection(Integer.parseInt(sectionData[13].toString().trim()));
				        	
				        	
				        	loc.setDiscom("TNEB");
				        	loc.setDiscom_code(7000000);
				       
				        	loc.setSection(section.toUpperCase());
							loc.setTp_sectioncode(sectionCode.trim());	
							
							System.out.println("lOCATION DATA====="+ loc);
							
							hierarchyService.save(loc);
							
							saveStatus = "NEW SECTION UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv+",TOWN_IPDS:"+town;

						}catch (Exception e) {
							saveStatus = "PROBLEM WHILE UPDATING THE SECTION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv+",TOWN_IPDS:"+town;
						}
						
						model.put("results", saveStatus);
					}
					
				} else {
					saveStatus = "SECTION ALREADY EXISTS UNDER REGION " + zoneVal + " ,CIRCLE " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv+",TOWN_IPDS:"+town;
					model.put("results", saveStatus);
				}
				
			}else {
				saveStatus = "PROBLEM WHILE ADDING THE SUBDIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv+",TOWN_IPDS:"+town;
			}	
			model.addAttribute("latestHierarchyData", hierarchyService.getALLLocationData());
			model.addAttribute("existingZones", hierarchyService.getdistinctZones());
			return "HierarchyLevels";
		  
	  }
	  
	   
	  @RequestMapping(value="/newdivisionunderzone",method={RequestMethod.GET,RequestMethod.POST})
	  public String newdivisionunderzone(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	  {
		String zoneVal = request.getParameter("newZoneFor2");
		String circle = request.getParameter("selCircle1");
		String division = request.getParameter("newDivision");
		String divisionCode = request.getParameter("newDivisionCode");
//		System.out.println("zone--->" + zoneVal);
//		System.out.println("circle--->" + circle);
//		System.out.println("division--->" + division);

		String saveStatus = "";
		Object[] circleData = (Object[]) hierarchyService.findCircleDetailsByZone(zoneVal, circle);
		if (circleData.length > 0) {
			int status = hierarchyService.checkingDivision(circleData[0].toString(), circleData[3].toString(), divisionCode);

			if (status == 0) {

				int existid = hierarchyService.getdivisionUpdatingId(zoneVal, circle, division);
				int divid =hierarchyService.createDivId(circleData[5].toString());
//				System.out.println("existid--->" + existid);
				if (existid != 0) {
					AmiLocation locData = hierarchyService.find(existid);
					locData.setDivision(division.toUpperCase());
					locData.setTp_divcode(divisionCode.trim());					
		        	locData.setDivisionCode(Integer.parseInt(circleData[5].toString().substring(0, 4)+""+divid));
		        	
					try {
						hierarchyService.update(locData);
						saveStatus = "NEW DIVISION UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle;

					} catch (Exception e) {
						saveStatus = "PROBLEM WHILE UPDATING THE DIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle;
					}
					model.put("results", saveStatus);
				} else {
					try {
						AmiLocation loc = new AmiLocation();
						loc.setZone(zoneVal.toUpperCase());
		        		loc.setZoneCode(Integer.parseInt(circleData[2].toString().trim()));
		        		loc.setTp_zonecode(circleData[0].toString().trim());
		        		loc.setCircle(circle.toUpperCase());
		        		loc.setCircleCode(Integer.parseInt(circleData[5].toString()));
			        	loc.setTp_circlecode(circleData[3].toString().trim());
		        		loc.setComapny(businessRoleService.getCompanyName());
		        		
		        		loc.setDiscom("TNEB");
			        	loc.setDiscom_code(7000000);
			        	
						loc.setDivision(division.toUpperCase());
						loc.setTp_divcode(divisionCode.trim());					
//						loc.setDivisionCode(Integer.parseInt(circleData[5].toString().substring(0, 4)+""+divisionCode.substring(3, 4)+"00"));
						loc.setDivisionCode(Integer.parseInt(circleData[5].toString().substring(0, 4)+""+divid));
						
						
						hierarchyService.save(loc);
						saveStatus = "NEW DIVISION ADDED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle;
					} catch (Exception e) {
						saveStatus = "PROBLEM WHILE ADDING THE DIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle;
					}
					model.put("results", saveStatus);
				}
			} else {
				saveStatus = "CIRCLE ALREADY EXISTS UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle;
				model.put("results", saveStatus);
			}
		}else {
			saveStatus = "PROBLEM WHILE ADDING THE DIVISION FOR REGION: " + zoneVal + " ,CIRCLE: " + circle;
		}

		model.addAttribute("latestHierarchyData", hierarchyService.getALLLocationData());
		model.addAttribute("existingZones", hierarchyService.getdistinctZones());
		return "HierarchyLevels";
	  }

	  
	  //Code to Add New Town
	  @RequestMapping(value = "/newTownunderzone", method = { RequestMethod.GET, RequestMethod.POST })
		public String newTownunderzone(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		  
		    HttpSession session = request.getSession(false);
		    if (session == null) {
				return "redirect:./?sessionVal=expired";
			}

			String zoneVal = request.getParameter("newZoneFor4");
			String circle = request.getParameter("selCircle3");
			String division = request.getParameter("div2");
			String subdiv = request.getParameter("subdiv");
			String town = request.getParameter("newTown");
			String townCode = request.getParameter("newTownCode");
			
//			System.out.println("zone--->" + zoneVal);
//			System.out.println("circle--->" + circle);
//		    System.out.println("division--->" + division);
//		    System.out.println("Subdivision--->" + subdiv);
//		    System.out.println("town--->" + town);
//		    System.out.println("townCode--->" + townCode);
		    

			String saveStatus = "";
			Object[] townData = (Object[]) hierarchyService.findSubDivDetailsByZone(zoneVal, circle, division, subdiv);
			if (townData.length > 0) {
				
				int status = hierarchyService.checkingTown(townData[0].toString(), townData[3].toString(), townData[6].toString(), townData[9].toString() ,townCode);
				

				if (status == 0) {
					int existid = hierarchyService.getTownUpdatingId(zoneVal, circle, division, subdiv,town);
//					int subdivid =hierarchyService.createSubDivId(subDivData[8].toString());
					System.out.println("existid--->" + existid);
					if (existid != 0) {
						AmiLocation locData = hierarchyService.find(existid);
						locData.setTown_ipds(town.toUpperCase());
						locData.setTp_towncode(townCode.trim());				
			        	//locData.setSitecode(Integer.parseInt(subDivData[8].toString().substring(0, 5)+""+subdivid));
						
						TownEntity townEntity = new TownEntity();
						townEntity.setTown_name(town.toUpperCase());
						townEntity.setTowncode(townCode.trim());
						townEntity.setCreateddate(new Timestamp(new Date().getTime()));
						townEntity.setCreatedby(session.getAttribute("username").toString());

						if (townMasterService.update(townEntity) instanceof TownEntity) {
//							 System.out.println("UPDATED TownEntity=========== ");
						}
						
						try {
							hierarchyService.update(locData);
							saveStatus = "NEW TOWN UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;

						} catch (Exception e) {
							saveStatus = "PROBLEM WHILE UPDATING THE TOWN FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;
						}
						model.put("results", saveStatus);
					} else {
						try {
							AmiLocation loc = new AmiLocation();
							
							loc.setZone(zoneVal.toUpperCase());
			        		loc.setZoneCode(Integer.parseInt(townData[2].toString().trim()));
			        		loc.setTp_zonecode(townData[0].toString().trim());
			        		
			        		loc.setCircle(circle.toUpperCase());
			        		loc.setCircleCode(Integer.parseInt(townData[5].toString()));
				        	loc.setTp_circlecode(townData[3].toString().trim());
				        	
			        		loc.setComapny(businessRoleService.getCompanyName());
						
							loc.setDivision(division.toUpperCase());
							loc.setTp_divcode(townData[6].toString().trim());					
							loc.setDivisionCode(Integer.parseInt(townData[8].toString().trim()));
							
							
							loc.setSubDivision(subdiv.toUpperCase());
							loc.setTp_subdivcode(townData[9].toString().trim());					
				        	loc.setSitecode(Integer.parseInt(townData[11].toString().trim()));
				        	
				        	
				        	loc.setDiscom("TNEB");
				        	loc.setDiscom_code(7000000);
				       
				        	loc.setTown_ipds(town.toUpperCase());
							loc.setTp_towncode(townCode.trim());	
							
							System.out.println("lOCATION DATA====="+ loc);
//				        	loc.setSitecode(Integer.parseInt(subDivData[8].toString().substring(0, 5)+""+subdivid));
							
										
							hierarchyService.save(loc);
							
							TownEntity townEntity = new TownEntity();
							townEntity.setTown_name(town.toUpperCase());
							townEntity.setTowncode(townCode.trim());
							townEntity.setCreateddate(new Timestamp(new Date().getTime()));
							townEntity.setCreatedby(session.getAttribute("username").toString());
							
							
//							townMasterService.save(townEntity);
//							townMasterService.customsaveBySchema(townEntity,"postgresMdas");
							if (townMasterService.update(townEntity) instanceof TownEntity) {
//								 System.out.println("UPDATED TownEntity=========== ");
							}
							
							
							saveStatus = "NEW TOWN UPDATED SUCCESSFULLY UNDER REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;

						} catch (Exception e) {
							e.printStackTrace();
							saveStatus = "PROBLEM WHILE UPDATING THE TOWN FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;
						}
						model.put("results", saveStatus);
					}
				} else {
					saveStatus = "TOWN ALREADY EXISTS UNDER REGION " + zoneVal + " ,CIRCLE " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;
					model.put("results", saveStatus);
				}

			}else {
				saveStatus = "PROBLEM WHILE ADDING THE TOWN FOR REGION: " + zoneVal + " ,CIRCLE: " + circle+ ",DIVISION: " + division+",SUB-DIVISION: " + subdiv;
			}		
			model.addAttribute("latestHierarchyData", hierarchyService.getALLLocationData());

			model.addAttribute("existingZones", hierarchyService.getdistinctZones());
			return "HierarchyLevels";
		
	  }  
	  

	  
}
