/**
 * 
 */
package com.bcits.mdas.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bcits.entity.TownEntity;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.BoundaryDetailsService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.service.DtDetailsService;
import com.bcits.service.TownMasterService;



/**
 * @author Tarik
 *
 */
@Controller
public class UploadExeclController {
	
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private MeterInventoryService meterInventoryService;

	@Autowired
	private DtDetailsService dtDetailsService;

	@Autowired
	private FeederDetailsService feederdetailsservice;

	@Autowired
	private BoundaryDetailsService boundaryDetailsService;
	
	@Autowired
	private TownMasterService townMasterService;
	
	@Autowired
	private AmiLocationService amiLocationService;
	
	int uploadFlag = 0;
	int flag = 1;
	int dflag = 1;
	Date goLiveDateNew =null;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/uploadTownFile", method = { RequestMethod.GET, RequestMethod.POST })
	public String uploadTownFile(HttpServletRequest request, HttpServletResponse response,
			Model model,HttpSession session) {
		Date goLiveDateNew =null;
		String extension = null;
		String excel = "xlsx";

		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile myFile = multipartRequest.getFile("excelfileUpload");

		//	File temp_file = new File(myFile.getOriginalFilename());
			String fileName = myFile.getOriginalFilename();

			if (myFile != null && !myFile.isEmpty() && myFile.getOriginalFilename() != " ") {
				extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

				if (!extension.equals(excel)) {
					model.addAttribute("alert_type", "success");
					model.addAttribute("results", "Please choose only .xlsx file to upload.!!!!!");

				} else {

					try {

						InputStream file = myFile.getInputStream();
						// FileInputStream file = new FileInputStream(getTempFile(myFile));
						// Get the workbook instance for XLSX file
						XSSFWorkbook workbook = new XSSFWorkbook(file);
						// Get first sheet from the workbook
						XSSFSheet sheet = workbook.getSheetAt(0);
						// Get iterator to all the rows in current sheet

						List<String> values;
						int noOfSheets = workbook.getNumberOfSheets();
						int lastRows = 0;
						int lastColumn = 0;
						String SheetName = "";

						for (int i = 0; i < noOfSheets; i++) {
							try {

								SheetName = workbook.getSheetName(i);
								lastRows = workbook.getSheetAt(i).getLastRowNum();
								values = new ArrayList<String>();
								System.out.println(lastRows);

								//String[] Circle_Code = new String[lastRows + 1];
								String[] Town_Code = new String[lastRows + 1];
								String[] Town_Name = new String[lastRows + 1];
								String[] TechnicalLoss = new String[lastRows + 1];
								String[] BaseLineLoss = new String[lastRows + 1];
								String[] GoLive_Date = new String[lastRows + 1];
								
						       /* List<String> list = Arrays.asList(TechnicalLoss);
						        List<String> listForBaseLineLoss = Arrays.asList(BaseLineLoss);

								
							if(list.contains("1")  || list.contains("2") || list.contains("3") || list.contains("4") || list.contains("5") || list.contains("6") || list.contains("7") || list.contains("8") || list.contains("9") || list.contains("10") || listForBaseLineLoss.contains("1") || listForBaseLineLoss.contains("2") || listForBaseLineLoss.contains("3") || listForBaseLineLoss.contains("4") || listForBaseLineLoss.contains("5") || listForBaseLineLoss.contains("6") || listForBaseLineLoss.contains("7") || listForBaseLineLoss.contains("8") || listForBaseLineLoss.contains("9") || listForBaseLineLoss.contains("10")){
						*/	
								if (workbook.getSheetAt(i).getRow(0) != null) {

									lastColumn = workbook.getSheetAt(i).getRow(0).getLastCellNum();

									String cellGotValue = "";

									//int Circle_Codecol = 0;
									int Town_Codecol = 0;
									int Town_Namecol = 0;
									int TechnicalLosscol = 0;
									int BaseLineLosscol = 0;
									int GoLive_Datecol = 0;

									if (lastRows == 0) {
										uploadFlag = 1;
										model.addAttribute("alert_type", "success");
										model.addAttribute("results", "Records are not avaliable in excel to upload");
									}

									for (int j = 0; j <= lastRows; j++) {

										//
										if (j == 0)// To get Column Names First row in Excel
										{
											for (int k = j; k < lastColumn; k++) {
												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);
												if (cellNull != null) {
													cellGotValue = cellNull.getStringCellValue();
													cellGotValue = cellGotValue.trim();

												}
//												if (cellGotValue.equalsIgnoreCase("Circle_Code")) {
//													Circle_Codecol = k;
//
//												}
												if (cellGotValue.equalsIgnoreCase("TOWN CODE")) {
													Town_Codecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("TOWN NAME")) {
													Town_Namecol = k;

												}
												if (cellGotValue.equalsIgnoreCase("TECHNICAL LOSS(%)")) {
													TechnicalLosscol = k;

												}
												if (cellGotValue.equalsIgnoreCase("BASELINE LOSS(%)")) {
													BaseLineLosscol = k;

												}
												if (cellGotValue.equalsIgnoreCase("GOLIVE DATE")) {
													GoLive_Datecol = k;

												}
												
											}
										}

										else {
											String nextLine[] = null;

											for (int k = 0; k < lastColumn; k++) {

												XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);

												if (cellNull != null) {

													if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
														if (DateUtil.isCellDateFormatted(cellNull)) {
															Date date = cellNull.getDateCellValue();
															SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
															cellGotValue = sdf.format(date);
															cellGotValue = cellGotValue.trim();
														} else {
															if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
																cellNull.setCellType(Cell.CELL_TYPE_STRING);
															}
															cellGotValue = cellNull.getStringCellValue();

														}
													} 
													
													if (cellNull.getCellType() == Cell.CELL_TYPE_FORMULA) {

														switch (cellNull.getCachedFormulaResultType()) {
														case Cell.CELL_TYPE_NUMERIC:

															break;
														case Cell.CELL_TYPE_STRING:

															break;
														}
													} 
													else {

														DataFormatter formatter = new DataFormatter(); // creating formatter using the default  locale
														Cell cell = cellNull;
														cellGotValue = formatter.formatCellValue(cell); // Returns the formatted value of a cell as a String regardless of the cell type.
														// cellGotValue=String.valueOf(cellNull.getStringCellValue());
														cellGotValue = cellGotValue.trim();

													}

												} else {
													cellGotValue = "";
												}

//												if (k == Circle_Codecol) {
//													Circle_Code[j - 1] = cellGotValue.trim();
//												}
												if (k == Town_Codecol) {
													Town_Code[j - 1] = cellGotValue.trim();
												}
												if (k == Town_Namecol) {
													Town_Name[j - 1] = cellGotValue.trim();
												}
												if (k == TechnicalLosscol) {
													TechnicalLoss[j - 1] = cellGotValue.trim();

												}
												if (k == BaseLineLosscol) {
													BaseLineLoss[j - 1] = cellGotValue.trim();
												}
												if (k == GoLive_Datecol) {
													GoLive_Date[j - 1] = cellGotValue.trim();
												}
									
											}

										}
									}
									
//									for (int n = 0; n < lastRows; n++) {
//										if(GoLive_Date[n].trim() != "") {
//											try {
//												SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//												
//												//String d2=formatter.format(format.parse(GoLive_Date[n].trim()));
//											   
//												if(!isValidDate(GoLive_Date[n].trim())) {
//													dflag = 0;
//													break;
//												}
//												
//											}catch(Exception e){
//												dflag = 0;
//												uploadFlag = 3;
//												model.addAttribute("results", "OOPS! Something went wrong!!");
//												model.addAttribute("alert_type", "error");
//											}
//										}
//										
//									}
									
									
									
									if (dflag == 1) {
										uploadFlag = 0;

										for (int n = 0; n < lastRows; n++) {

											try {
										        String regex = "[+-]?[0-9][0-9]*"; 
										        Pattern p = Pattern.compile(regex);
										        Matcher m = p.matcher(TechnicalLoss[n].trim());
										        Matcher o = p.matcher(BaseLineLoss[n].trim()); 

										        	
										        if((m.find() && m.group().equals(TechnicalLoss[n].trim())) && (o.find() && o.group().equals(BaseLineLoss[n].trim()))) {
										        	
//											System.out.println("Town_Code="+Town_Code[n].trim());
//											System.out.println("Town_Name ="+Town_Name[n].trim());
//											System.out.println("TechnicalLoss= "+TechnicalLoss[n].trim());
//											System.out.println("BaseLineLoss = "+BaseLineLoss[n].trim());
//											System.out.println("GoLive_Date = "+GoLive_Date[n].trim());

												TownEntity town = townMasterService.getTownEntity(Town_Code[n].trim());
												if (town != null) {
													// town.setTown_name(Town_Name[n].trim());
													if (TechnicalLoss[n].trim() != "") {
														town.setTechnical_loss(TechnicalLoss[n].trim());
													}

													if (BaseLineLoss[n].trim() != "") {
														town.setBaseline_loss(BaseLineLoss[n].trim());
													}

													if (GoLive_Date[n].trim() != "") {
														//try {
															SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
															SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
															// String
															//check
															// d1=format.parse(GoLive_Date[n].trim().toString()).toString();
															if(!(GoLive_Date[n].isEmpty())) {
															String d2 = formatter.format(format.parse(GoLive_Date[n].trim()));
															goLiveDateNew = getDate(d2);

															town.setGolivedate(goLiveDateNew);
															}
															//flag = 1;

//														} catch (Exception e) {
//															//flag = 0;
//															uploadFlag = 3;
//															model.addAttribute("results","OOPS! Something went wrong!!");
//															model.addAttribute("alert_type", "error");
//														}

													}

													town.setUpdatedby(session.getAttribute("username").toString());
													town.setUpdateddate(new Timestamp(System.currentTimeMillis()));
													uploadFlag = 1;
													dflag = 1;
													townMasterService.update(town);
													model.addAttribute("results", "Data Uploaded Successfully");
													model.addAttribute("alert_type", "success");
												} else {
													//flag = 0;
													System.out.println("Missing Town Info=" + Town_Code[n].trim());
												}
//												if (flag == 1) {
//													flag = 0;
//													
//												}
											}
											else {
												uploadFlag = 3;
												dflag = 1;
												model.addAttribute("results", "OOPS! Check Technical Loss Or Baseline Loss it should contain only Numbers!!");
												model.addAttribute("alert_type", "error");
											}
												

											} catch (Exception e) {
												flag = 0;
												uploadFlag = 3;
												// result = "OOPS! Something went wrong!!";
												model.addAttribute("results", "OOPS! Something went wrong!!");
												model.addAttribute("alert_type", "error");
												e.printStackTrace();
											}

										}
									}else {
										uploadFlag = 3;
										dflag = 1;
										model.addAttribute("results", "OOPS! Something went wrong!!");
										model.addAttribute("alert_type", "error");
									}	

								}
							
						/*}
							else {
								
								uploadFlag = 3;
								dflag = 1;
								model.addAttribute("results", "OOPS! Check Technical Loss Or Baseline Loss it should contain only Numbers!!");
								model.addAttribute("alert_type", "error");
							}*/

							} catch (Exception e) {
								flag = 0;
								e.printStackTrace();
							}

						}

					} catch (Exception e) {
						flag = 0;
						e.printStackTrace();
					}

				}
					
				
			} 

		} catch (Exception e) {
			flag = 0;
			model.addAttribute("alert_type", "error");
			model.addAttribute("results", "Something went wrong !!!!!");
			e.printStackTrace();
		}
		return "towndetails";

	}

	
	

	// checks if headers from excel matches the headers for template from db based
	// on mandaotry filds.
	private Object[] validateTempalte(List<String> headers) {
		Object[] values = new Object[2];
		Boolean isTempateProper = true;
		List<String> errorMessages = new ArrayList<String>();
		List<?> headerDetails = new ArrayList<String>();
		// headerDetails.add("Feeder_Code");
		ArrayList<String> listOne = new ArrayList<>(Arrays.asList("Feeder_Code", "Boundary_ID", "Boundary_Name",
				"Boundary_Location", "MeterNo", "Meter_Make", "CT", "PT", "MF"));

		Collections.sort(listOne);
		Collections.sort(headers);

		// Compare unequal lists example

		boolean isEqual = listOne.equals(headers); // false
		System.out.println(isEqual);

		for (String country : headers) {
			System.out.println(country);
		}

//      String[] Feeder_Code = new String[lastRows + 1];
//		String[] Boundary_ID = new String[lastRows + 1];
//		String[] Boundary_Name = new String[lastRows + 1];
//		String[] Boundary_Location = new String[lastRows + 1];
//		String[] MeterNo = new String[lastRows + 1];
//		String[] Meter_Make = new String[lastRows + 1];
//		String[] CT = new String[lastRows + 1];
//		String[] PT = new String[lastRows + 1];
//		String[] MF = new String[lastRows + 1];

		return values;
	}

	private Map<String, Integer> assignHeaderSeqNumber(List<String> headers) {
		Map<String, Integer> headerSeqNumber = new HashMap<String, Integer>();

		int i = 0;
		for (String columnName : headers) {
			headerSeqNumber.put(columnName, i);
			i++;
		}

		return headerSeqNumber;

	}

	public File getTempFile(MultipartFile multipartFile) {
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
		FileItem fileItem = commonsMultipartFile.getFileItem();
		DiskFileItem diskFileItem = (DiskFileItem) fileItem;
		String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
		File file = new File(absPath);

		// trick to implicitly save on disk small files (<10240 bytes by default)
		if (!file.exists()) {
			try {
				file.createNewFile();
				multipartFile.transferTo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return file;
	}
	
	public String CtPtRatioCalculation(String pt) {

		String ratio = "";
		pt = pt.toUpperCase();
		pt = pt.replace("K", "000");
		pt = pt.replace("A", "");
		pt = pt.replace("V", "");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			ratio = String.valueOf(engine.eval(pt));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratio;
	}
	
	Date getDate(String value) {
		// SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date(format.parse(value).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	boolean isValidDate(String input) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     try {
	          format.parse(input);
	          return true;
	     }
	     catch(ParseException e){
	          return false;
	     }
	}
	
	

}
