/**
 * 
 */
package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.AmiLocation;
import com.bcits.mdas.entity.InitialMeterInfo;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.NamePlate;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.BoundaryDetailsService;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.InitialMeterInfoService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.mdas.service.NamePlateService;
import com.bcits.service.DtDetailsService;

/**
 * @author Tarik
 *
 */

@Controller
public class InitialMeterDataSyncController {

	@Autowired
	private InitialMeterInfoService initialMeterInfoService;

	@Autowired
	private MasterMainService mainService;

	@Autowired
	private NamePlateService namePlateService;

	@Autowired
	private MeterInventoryService meterInventoryService;

	@Autowired
	private DtDetailsService dtDetailsService;

	@Autowired
	private FeederDetailsService feederDetailsService;

	@Autowired
	private BoundaryDetailsService boundaryDetailsService;

	@Autowired
	private AmiLocationService amiLocationService;

	//@Scheduled(cron = "0 0/3 * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/initialMeterNamePlateDataSync", method = { RequestMethod.GET })
	public @ResponseBody void initialMeterNamePlateDataSync() {
		try {
			System.out.println("Started NamePlate Sync"+new Date());
			List<InitialMeterInfo> info = initialMeterInfoService.getInitialMeterInfo();

			if (!info.isEmpty()) {
				new SyncNameplateThread(info).start();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Scheduled(cron = "0 0/2 * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/initialMeterFeederDataSync", method = { RequestMethod.GET })
	public @ResponseBody void initialMeterFeederDataSync() {
		try {
//			System.out.println("Started InitialMeterFeederDataSync method " + new Date());
			List<InitialMeterInfo> info = initialMeterInfoService.initialMeterFeederDataSync();

			if (!info.isEmpty()) {

				for (InitialMeterInfo item : info) {

					try {
						syncInitialFeederMeter(item);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Scheduled(cron = "0 0/2 * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/initialMeterBoundaryDataSync", method = { RequestMethod.GET })
	public @ResponseBody void initialMeterBoundaryDataSync() {
		try {
			System.out.println("Started InitialMeterBoundaryDataSync method " + new Date());
			List<InitialMeterInfo> info = initialMeterInfoService.initialMeterBoundaryDataSync();

			if (!info.isEmpty()) {
				
				for (InitialMeterInfo item : info) {

					try {
						syncInitialBoundaryMeter(item);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class SyncNameplateThread extends Thread{
		List<InitialMeterInfo> data;
		public SyncNameplateThread(List<InitialMeterInfo> data) {		
			this.data=data;		
		}
		 	
	@Override
	public void run() {
			try {
				System.out.println("Started NamePlate Thread:- "+new Date());
				for (InitialMeterInfo item : data) {

					try {
						syncInitialSingle(item);
					} catch (Exception e) {
						e.printStackTrace();

					}

				}
				System.out.println("Stoped NamePlate Thread:- "+new Date());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/rejectDTMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String rejectDTMeters(HttpServletRequest request, HttpSession session) {

		String msg = null;
		try {
			int v = initialMeterInfoService.rejectDTMeters(request.getParameter("meterNo").trim(),
					session.getAttribute("username").toString());
//			System.out.println("output=="+v);
			if (v != 0) {
				msg = "sucess";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/checkDTMeterExistOrNot", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String checkDTMeterExistOrNot(HttpServletRequest request, HttpSession session) {

		String msg = null;
		try {
			msg = initialMeterInfoService.checkDTMeterExistOrNot(request.getParameter("meterNo").trim());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/approveDTMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String approveDTMeters(HttpServletRequest request, HttpSession session) {

		String msg = null;
		String meterNo = request.getParameter("meterNo").trim();
		int id = Integer.parseInt(request.getParameter("id").trim());

		InitialMeterInfo dtEntity = initialMeterInfoService.getDTNotAppDetailByMeter(id, meterNo);

		try {
			int i = syncInitialDTMeter(dtEntity, session.getAttribute("username").toString());
			initialMeterInfoService.duplicateDTMeters(meterNo, session.getAttribute("username").toString());
			if (i == 1) {
				msg = "sucess";
			}
			if (i == 2) {
				msg = "duplicate";
			}
			if (i == 3) {
				msg = "failure";
			}

		} catch (Exception e) {
			msg = "failure";
			e.printStackTrace();
		}
		return msg;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/replaceAppDTMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String replaceAppDTMeters(HttpServletRequest request, HttpSession session) {

		String msg = null;
		String meterNo = request.getParameter("meterNo").trim();
		int id = Integer.parseInt(request.getParameter("id").trim());

		InitialMeterInfo dtEntity = initialMeterInfoService.getDTNotAppDetailByMeter(id, meterNo);

		try {
			syncReplaceAppInitialDTMeter(dtEntity, session.getAttribute("username").toString());
			initialMeterInfoService.duplicateDTMeters(meterNo, session.getAttribute("username").toString());
			msg = "sucess";
		} catch (Exception e) {
			msg = "failure";
			e.printStackTrace();
		}
		return msg;
	}

	private void syncReplaceAppInitialDTMeter(InitialMeterInfo item, String username) {
		if ("MasterInfo".equalsIgnoreCase(item.getData_type())) {

			AmiLocation amiloc = amiLocationService.getAmiLocationDetails(item.getCirclecode(), item.getDivision_code(),
					item.getSubdivisioncode(), item.getTowncode(), item.getSectioncode());
			try {

				NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());

				if (nP == null) {
					try {
						NamePlate nPlate = new NamePlate();

						nPlate.setMeter_serial_number(
								item.getMeterid().isEmpty() || item.getMeterid() == null ? "" : item.getMeterid());
						nPlate.setMeter_type(item.getMetertype().isEmpty() || item.getMetertype() == null ? ""
								: item.getMetertype());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nPlate.setFirmware_version(
								item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? ""
										: item.getFirmwareversion());
						nPlate.setCurrent_rating(
								item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? ""
										: item.getCurrentrating());
						nPlate.setManufacturer_name(
								item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? ""
										: item.getManufacturername().trim());
						nPlate.setYear_of_manufacture(item.getManufactureyear());
						nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
						nPlate.setFlag(username);

						namePlateService.update(nPlate);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					try {
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nP.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nP.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nP.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nP.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nP.setFirmware_version(
								item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? ""
										: item.getFirmwareversion());
						nP.setCurrent_rating(item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? ""
								: item.getCurrentrating());
						nP.setManufacturer_name(
								item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? ""
										: item.getManufacturername().trim());
						nP.setYear_of_manufacture(item.getManufactureyear());
						nP.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
						nP.setFlag(username);

						namePlateService.update(nP);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
				// checkMeterNoExist
				if (mtrInv == null) {
					try {
						MeterInventoryEntity inventory = new MeterInventoryEntity();

						inventory.setMeterno(item.getMeterid());
						if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 1);
							inventory.setConnection_type("12");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("33");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("34");
						}

						inventory.setMeter_make(item.getManufacturername());
						inventory.setMeter_accuracy_class(item.getMeterclass());
						inventory.setMeter_commisioning(item.getMetercommissioning());
						if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
							inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
						}
						inventory.setMeter_current_rating(item.getCurrentrating());
						inventory.setMeter_ip_period(item.getIpperiod());
						inventory.setMeter_model(item.getMetermodel());
						inventory.setMeter_status("INSTOCK");
						inventory.setMeter_voltage_rating(item.getVoltagerating());
						inventory.setMonth(item.getManufactureyear());
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}
						inventory.setEntryby(username);
						inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
						inventory.setMeterdigit(item.getMeterdigit());

						meterInventoryService.update(inventory);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					try {
						if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())
									&& "2".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 1);
								mtrInv.setConnection_type("12");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "3".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("33");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "4".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("34");
							}

							mtrInv.setMeter_make(item.getManufacturername());
							mtrInv.setMeter_accuracy_class(item.getMeterclass());
							mtrInv.setMeter_commisioning(item.getMetercommissioning());
							if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
								mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
							}
							mtrInv.setMeter_current_rating(item.getCurrentrating());
							mtrInv.setMeter_ip_period(item.getIpperiod());
							mtrInv.setMeter_model(item.getMetermodel());

							mtrInv.setMeter_status("INSTOCK");

							mtrInv.setMeter_voltage_rating(item.getVoltagerating());
							mtrInv.setMonth(item.getManufactureyear());
							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
								} else {
									mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
								} else {
									mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
								}

							}
							mtrInv.setUpdateby(username);
							mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							mtrInv.setMeterdigit(item.getMeterdigit());

							meterInventoryService.update(mtrInv);
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}

				// SYNC Process For DTType Meter

				if ("DT".equalsIgnoreCase(item.getType())) {
					try {

						// Removing Attached meter from current DT & Deleting same information from
						// Master Main
						replaceAppDtMeterSync(item, amiloc, username);
						// Adding same meter with another DT & Master Main
						dtMeterSync(item, amiloc, username);

					} catch (Exception e) {
						item.setSync_status(3);
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				item.setSync_status(3);
				e.printStackTrace();
			}
			initialMeterInfoService.update(item);
		}

	}
	
	private int syncInitialDTMeter(InitialMeterInfo item, String username) {

		if ("MasterInfo".equalsIgnoreCase(item.getData_type())) {

			AmiLocation amiloc = amiLocationService.getAmiLocationDetails(item.getCirclecode(), item.getDivision_code(),
					item.getSubdivisioncode(), item.getTowncode(), item.getSectioncode());
			try {

				NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());

				if (nP == null) {
					try {
						NamePlate nPlate = new NamePlate();

						nPlate.setMeter_serial_number(
								item.getMeterid().isEmpty() || item.getMeterid() == null ? "" : item.getMeterid());
						nPlate.setMeter_type(item.getMetertype().isEmpty() || item.getMetertype() == null ? ""
								: item.getMetertype());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nPlate.setFirmware_version(
								item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? ""
										: item.getFirmwareversion());
						nPlate.setCurrent_rating(
								item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? ""
										: item.getCurrentrating());
						nPlate.setManufacturer_name(
								item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? ""
										: item.getManufacturername().trim());
						nPlate.setYear_of_manufacture(item.getManufactureyear());
						nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
						nPlate.setFlag(username);

						namePlateService.update(nPlate);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					try {
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nP.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nP.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nP.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nP.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nP.setFirmware_version(
								item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? ""
										: item.getFirmwareversion());
						nP.setCurrent_rating(item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? ""
								: item.getCurrentrating());
						nP.setManufacturer_name(
								item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? ""
										: item.getManufacturername().trim());
						nP.setYear_of_manufacture(item.getManufactureyear());
						nP.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
						nP.setFlag(username);

						namePlateService.update(nP);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
				// checkMeterNoExist
				if (mtrInv == null) {
					try {
						MeterInventoryEntity inventory = new MeterInventoryEntity();

						inventory.setMeterno(item.getMeterid());
						if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 1);
							inventory.setConnection_type("12");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("33");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("34");
						}

						inventory.setMeter_make(item.getManufacturername());
						inventory.setMeter_accuracy_class(item.getMeterclass());
						inventory.setMeter_commisioning(item.getMetercommissioning());
						if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
							inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
						}
						inventory.setMeter_current_rating(item.getCurrentrating());
						inventory.setMeter_ip_period(item.getIpperiod());
						inventory.setMeter_model(item.getMetermodel());
						inventory.setMeter_status("INSTOCK");
						inventory.setMeter_voltage_rating(item.getVoltagerating());
						inventory.setMonth(item.getManufactureyear());
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}
						inventory.setEntryby(username);
						inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
						inventory.setMeterdigit(item.getMeterdigit());

						meterInventoryService.update(inventory);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					try {
						if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())
									&& "2".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 1);
								mtrInv.setConnection_type("12");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "3".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("33");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "4".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("34");
							}

							mtrInv.setMeter_make(item.getManufacturername());
							mtrInv.setMeter_accuracy_class(item.getMeterclass());
							mtrInv.setMeter_commisioning(item.getMetercommissioning());
							if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
								mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
							}
							mtrInv.setMeter_current_rating(item.getCurrentrating());
							mtrInv.setMeter_ip_period(item.getIpperiod());
							mtrInv.setMeter_model(item.getMetermodel());

							mtrInv.setMeter_status("INSTOCK");

							mtrInv.setMeter_voltage_rating(item.getVoltagerating());
							mtrInv.setMonth(item.getManufactureyear());
							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
								} else {
									mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
								} else {
									mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
								}

							}
							mtrInv.setUpdateby(username);
							mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							mtrInv.setMeterdigit(item.getMeterdigit());

							meterInventoryService.update(mtrInv);
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}

				// SYNC Process For DTType Meter

				if ("DT".equalsIgnoreCase(item.getType())) {
					try {
						dtMeterSync(item, amiloc, username);
					} catch (Exception e) {
						item.setSync_status(3);
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				item.setSync_status(3);
				e.printStackTrace();
			}
			initialMeterInfoService.update(item);

		}
		return item.getSync_status();

	}

	private void syncInitialSingle(InitialMeterInfo item) {
		if ("NamePlate".equalsIgnoreCase(item.getData_type())) {
			try {
				nameplateInventorySync(item);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

//		if ("MasterInfo".equalsIgnoreCase(item.getData_type())) {
//
//			
//			
//			System.out.println("Inside MasterInfo");
//			try {
//				AmiLocation amiloc = amiLocationService.getAmiLocationDetails(item.getCirclecode(), item.getDivision_code(),
//						item.getSubdivisioncode(), item.getTowncode());
//
//				NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());
//				System.out.println("Np=="+nP.toString());
//				System.out.println("MeterNo=="+item.getMeterid());
//
//				if (nP == null) {
//					System.out.println("Inside MasterInfo>NamePlate=Null");
//					try {
//						NamePlate nPlate = new NamePlate();
//
//						nPlate.setMeter_serial_number(
//								item.getMeterid().isEmpty() || item.getMeterid() == null ? "" : item.getMeterid());
//						nPlate.setMeter_type(item.getMetertype().isEmpty() || item.getMetertype() == null ? ""
//								: item.getMetertype());
//
//						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
//							if (item.getCt_ratio().contains("/")) {
//								nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
//							} else {
//								nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
//							}
//						}
//
//						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {
//
//							if (item.getPtratio().contains("/")) {
//								nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
//							} else {
//								nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
//							}
//
//						}
//
//						nPlate.setFirmware_version(
//								item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? ""
//										: item.getFirmwareversion());
//						nPlate.setCurrent_rating(
//								item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? ""
//										: item.getCurrentrating());
//						nPlate.setManufacturer_name(
//								item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? ""
//										: item.getManufacturername().trim());
//						nPlate.setYear_of_manufacture(item.getManufactureyear());
//						nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//						nPlate.setFlag("SYNC");
//
//						namePlateService.update(nPlate);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//				}
//
//				MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
//				//checkMeterNoExist
//				if (mtrInv.equals(null) || mtrInv.equals("") ) {
//					try {
//						System.out.println("Inside Inventory is null");
//						MeterInventoryEntity inventory = new MeterInventoryEntity();
//
//						inventory.setMeterno(item.getMeterid());
//						if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
//							inventory.setMeter_connection_type((short) 1);
//							inventory.setConnection_type("12");
//						}
//						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
//							inventory.setMeter_connection_type((short) 3);
//							inventory.setConnection_type("33");
//						}
//						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
//							inventory.setMeter_connection_type((short) 3);
//							inventory.setConnection_type("34");
//						}
//
//						inventory.setMeter_make(item.getManufacturername());
//						inventory.setMeter_accuracy_class(item.getMeterclass());
//						inventory.setMeter_commisioning(item.getMetercommissioning());
//						if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
//							inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
//						}
//						inventory.setMeter_current_rating(item.getCurrentrating());
//						inventory.setMeter_ip_period(item.getIpperiod());
//						inventory.setMeter_model(item.getMetermodel());
//						inventory.setMeter_status("INSTOCK");
//						inventory.setMeter_voltage_rating(item.getVoltagerating());
//						inventory.setMonth(item.getManufactureyear());
//						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
//							if (item.getCt_ratio().contains("/")) {
//								inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
//							} else {
//								inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
//							}
//						}
//						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {
//
//							if (item.getPtratio().contains("/")) {
//								inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
//							} else {
//								inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
//							}
//
//						}
//						inventory.setEntryby("SYNC");
//						inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
//						inventory.setMeterdigit(item.getMeterdigit());
//
//						meterInventoryService.update(inventory);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//				} else {
//					System.out.println("Inside Inventory not null");
//					try {
//						if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {
//							System.out.println("Inside Inventory alredt installed");
//							if ("1 Phase".equalsIgnoreCase(item.getMetertype())
//									&& "2".equalsIgnoreCase(item.getWire())) {
//								mtrInv.setMeter_connection_type((short) 1);
//								mtrInv.setConnection_type("12");
//							}
//							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
//									&& "3".equalsIgnoreCase(item.getWire())) {
//								mtrInv.setMeter_connection_type((short) 3);
//								mtrInv.setConnection_type("33");
//							}
//							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
//									&& "4".equalsIgnoreCase(item.getWire())) {
//								mtrInv.setMeter_connection_type((short) 3);
//								mtrInv.setConnection_type("34");
//							}
//
//							mtrInv.setMeter_make(item.getManufacturername());
//							mtrInv.setMeter_accuracy_class(item.getMeterclass());
//							mtrInv.setMeter_commisioning(item.getMetercommissioning());
//							if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
//								mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
//							}
//							mtrInv.setMeter_current_rating(item.getCurrentrating());
//							mtrInv.setMeter_ip_period(item.getIpperiod());
//							mtrInv.setMeter_model(item.getMetermodel());
//
//							mtrInv.setMeter_status("INSTOCK");
//
//							mtrInv.setMeter_voltage_rating(item.getVoltagerating());
//							mtrInv.setMonth(item.getManufactureyear());
//							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
//								if (item.getCt_ratio().contains("/")) {
//									mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
//								} else {
//									mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
//								}
//							}
//							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {
//
//								if (item.getPtratio().contains("/")) {
//									mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
//								} else {
//									mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
//								}
//
//							}
//							mtrInv.setUpdateby("SYNC");
//							mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
//							mtrInv.setMeterdigit(item.getMeterdigit());
//
//							meterInventoryService.update(mtrInv);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//
//					}
//				}
//				
//				// SYNC Process For DTType Meter
//
////				if ("DT".equalsIgnoreCase(item.getType())) {
////					try {
////						dtMeterSync(item, amiloc);
////					} catch (Exception e) {
////						item.setSync_status(3);
////						e.printStackTrace();
////					}
////				}
//				
//				// SYNC Process For FeederType Meter
//				
////				if ("FEEDER".equalsIgnoreCase(item.getType())) {
////					try {
////						feederMeterSync(item, amiloc);
////					} catch (Exception e) {
////						item.setSync_status(3);
////						e.printStackTrace();
////					}
////				}
//
//				// SYNC Process For BoundaryType Meter
//
////				if ("BOUNDARY".equalsIgnoreCase(item.getType())) {
////					try {
////						System.out.println("Inside BOUNDARY method");
////						boundaryMeterSync(item);
////					} catch (Exception e) {
////						item.setSync_status(3);
////						e.printStackTrace();
////					}
////				}
//
//			} catch (Exception e) {
//				item.setSync_status(3);
//				e.printStackTrace();
//			}
//			initialMeterInfoService.update(item);
//
//		}
	}

	private void syncInitialFeederMeter(InitialMeterInfo item) {

		if ("MasterInfo".equalsIgnoreCase(item.getData_type())) {
//			System.out.println("Inside MasterInfo");
			try {
				NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());
//				System.out.println("Np=="+nP.toString());
//				System.out.println("MeterNo=="+item.getMeterid());

				if (nP == null) {
//					System.out.println("Inside MasterInfo>NamePlate=Null");
					try {
						NamePlate nPlate = new NamePlate();

						nPlate.setMeter_serial_number(item.getMeterid().isEmpty() || item.getMeterid() == null ? "" : item.getMeterid());
						nPlate.setMeter_type(item.getMetertype().isEmpty() || item.getMetertype() == null ? "": item.getMetertype());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nPlate.setFirmware_version(item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? "": item.getFirmwareversion());
						nPlate.setCurrent_rating(item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? "": item.getCurrentrating());
						nPlate.setManufacturer_name(item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? "": item.getManufacturername().trim());
						nPlate.setYear_of_manufacture(item.getManufactureyear());
						nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
						nPlate.setFlag("SYNC");

						namePlateService.update(nPlate);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
				// checkMeterNoExist
				if (mtrInv == null) {
					try {
//						System.out.println("Inside Inventory is null");
						MeterInventoryEntity inventory = new MeterInventoryEntity();

						inventory.setMeterno(item.getMeterid());
						if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 1);
							inventory.setConnection_type("12");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("33");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("34");
						}

						inventory.setMeter_make(item.getManufacturername());
						inventory.setMeter_accuracy_class(item.getMeterclass());
						inventory.setMeter_commisioning(item.getMetercommissioning());
						if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
							inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
						}
						inventory.setMeter_current_rating(item.getCurrentrating());
						inventory.setMeter_ip_period(item.getIpperiod());
						inventory.setMeter_model(item.getMetermodel());
						inventory.setMeter_status("INSTOCK");
						inventory.setMeter_voltage_rating(item.getVoltagerating());
						inventory.setMonth(item.getManufactureyear());
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}
						inventory.setEntryby("SYNC");
						inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
						inventory.setMeterdigit(item.getMeterdigit());

						meterInventoryService.update(inventory);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
//					System.out.println("Inside Inventory not null");
					try {
						if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {
//							System.out.println("Inside Inventory alredt installed");
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())
									&& "2".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 1);
								mtrInv.setConnection_type("12");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "3".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("33");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())
									&& "4".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("34");
							}

							mtrInv.setMeter_make(item.getManufacturername());
							mtrInv.setMeter_accuracy_class(item.getMeterclass());
							mtrInv.setMeter_commisioning(item.getMetercommissioning());
							if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
								mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
							}
							mtrInv.setMeter_current_rating(item.getCurrentrating());
							mtrInv.setMeter_ip_period(item.getIpperiod());
							mtrInv.setMeter_model(item.getMetermodel());

							mtrInv.setMeter_status("INSTOCK");

							mtrInv.setMeter_voltage_rating(item.getVoltagerating());
							mtrInv.setMonth(item.getManufactureyear());
							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
								} else {
									mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
								} else {
									mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
								}

							}
							mtrInv.setUpdateby("SYNC");
							mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							mtrInv.setMeterdigit(item.getMeterdigit());

							meterInventoryService.update(mtrInv);
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}

				// SYNC Process For FeederType Meter

				if ("FEEDER".equalsIgnoreCase(item.getType())) {
					try {
						AmiLocation amiloc = amiLocationService.getAmiLocationDetails(item.getCirclecode(),item.getDivision_code(), item.getSubdivisioncode(), item.getTowncode(), item.getSectioncode());
						if (amiloc != null) {
							feederMeterSync(item, amiloc);
						} else {
							item.setSync_status(3);
							item.setUpdatedby("SYNC");
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						}

					} catch (Exception e) {
						item.setSync_status(3);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}
			initialMeterInfoService.update(item);

		}
	}

	private void syncInitialBoundaryMeter(InitialMeterInfo item) {

		if ("MasterInfo".equalsIgnoreCase(item.getData_type())) {
//			System.out.println("Inside MasterInfo");
			try {
				NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());
//				System.out.println("Np=="+nP.toString());
//				System.out.println("MeterNo=="+item.getMeterid());

				if (nP == null) {
					System.out.println("Inside MasterInfo>NamePlate=Null");
					try {
						NamePlate nPlate = new NamePlate();

						nPlate.setMeter_serial_number(item.getMeterid().isEmpty() || item.getMeterid() == null ? "" : item.getMeterid());
						nPlate.setMeter_type(item.getMetertype().isEmpty() || item.getMetertype() == null ? "": item.getMetertype());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}

						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}

						nPlate.setFirmware_version(item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? "": item.getFirmwareversion());
						nPlate.setCurrent_rating(item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? "": item.getCurrentrating());
						nPlate.setManufacturer_name(item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? "": item.getManufacturername().trim());
						nPlate.setYear_of_manufacture(item.getManufactureyear());
						nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
						nPlate.setFlag("SYNC");

						namePlateService.update(nPlate);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
				// checkMeterNoExist
				if (mtrInv == null) {
					try {
//						System.out.println("Inside Inventory is null");
						MeterInventoryEntity inventory = new MeterInventoryEntity();

						inventory.setMeterno(item.getMeterid());
						if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 1);
							inventory.setConnection_type("12");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("33");
						}
						if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
							inventory.setMeter_connection_type((short) 3);
							inventory.setConnection_type("34");
						}

						inventory.setMeter_make(item.getManufacturername());
						inventory.setMeter_accuracy_class(item.getMeterclass());
						inventory.setMeter_commisioning(item.getMetercommissioning());
						if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
							inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
						}
						inventory.setMeter_current_rating(item.getCurrentrating());
						inventory.setMeter_ip_period(item.getIpperiod());
						inventory.setMeter_model(item.getMetermodel());
						inventory.setMeter_status("INSTOCK");
						inventory.setMeter_voltage_rating(item.getVoltagerating());
						inventory.setMonth(item.getManufactureyear());
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
							} else {
								inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
							} else {
								inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
							}

						}
						inventory.setEntryby("SYNC");
						inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
						inventory.setMeterdigit(item.getMeterdigit());

						meterInventoryService.update(inventory);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
//					System.out.println("Inside Inventory not null");
					try {
						if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {
//							System.out.println("Inside Inventory alredt installed");
							if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 1);
								mtrInv.setConnection_type("12");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("33");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
								mtrInv.setMeter_connection_type((short) 3);
								mtrInv.setConnection_type("34");
							}

							mtrInv.setMeter_make(item.getManufacturername());
							mtrInv.setMeter_accuracy_class(item.getMeterclass());
							mtrInv.setMeter_commisioning(item.getMetercommissioning());
							if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
								mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
							}
							mtrInv.setMeter_current_rating(item.getCurrentrating());
							mtrInv.setMeter_ip_period(item.getIpperiod());
							mtrInv.setMeter_model(item.getMetermodel());

							mtrInv.setMeter_status("INSTOCK");

							mtrInv.setMeter_voltage_rating(item.getVoltagerating());
							mtrInv.setMonth(item.getManufactureyear());
							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
								} else {
									mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
								} else {
									mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
								}

							}
							mtrInv.setUpdateby("SYNC");
							mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							mtrInv.setMeterdigit(item.getMeterdigit());

							meterInventoryService.update(mtrInv);
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
				// SYNC Process For BoundaryType Meter

				if ("BOUNDARY".equalsIgnoreCase(item.getType())) {
					try {
						
						AmiLocation amiloc = amiLocationService.getAmiLocationDetails(item.getCirclecode(),item.getDivision_code(), item.getSubdivisioncode(), item.getTowncode(), item.getSectioncode());
						if (amiloc != null) {
							boundaryMeterSync(item);
						} else {
							item.setSync_status(3);
							item.setUpdatedby("SYNC");
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						}	
					} catch (Exception e) {
						item.setSync_status(3);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						e.printStackTrace();
					}

				}

			} catch (Exception e) {
				item.setSync_status(3);
				e.printStackTrace();
			}
			initialMeterInfoService.update(item);

		}
	}
//dt meter data sync
	private void dtMeterSync(InitialMeterInfo item, AmiLocation amiloc, String username) {

		List<DtDetailsEntity> dtEntity = dtDetailsService.getDtDetailsByDttpid(item.getDtcode(), item.getTowncode());
		// If multiDt or singleDt is present in this particular town then below steps
		if (!dtEntity.isEmpty()) {
			// If single DT is present then below operation
			if (dtEntity.size() == 1) {
				for (int i = 0; i < dtEntity.size(); i++) {
					DtDetailsEntity dtMaster = (DtDetailsEntity) dtEntity.get(i);
					// For this single DT,we are checking that meter is present or not ,If it is not
					// attached then below operation
					if (dtMaster.getMeterno() == null || dtMaster.getMeterno().isEmpty()) {

						dtMaster.setMeterno(item.getMeterid());
						dtMaster.setDttype("LT");
						dtMaster.setUpdate(username);
						dtMaster.setUpdatedate(new Timestamp(System.currentTimeMillis()));

						MasterMainEntity master = new MasterMainEntity();
						// Need to add master data also here when we are going to add dt meter
						try {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
								master.setPhase("1");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
								master.setPhase("3");
							}
							if (item.getSubdivisioncode() != null && !"".equalsIgnoreCase(item.getSubdivisioncode())) {
								master.setSdocode(String.valueOf(amiloc.getSitecode()));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

//						Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(item.getMeterid());
						master.setMtrno(item.getMeterid());
						master.setMtrmake(item.getManufacturername());
						master.setFdrcategory("DT");
						master.setCustomer_name(item.getDtcode() + "-" + item.getDtname());
						master.setAccno(dtMaster.getDt_id());
						master.setZone(amiloc.getZone().toUpperCase());
						master.setCircle(item.getCircle().toUpperCase());

						master.setDivision(item.getDivision().toUpperCase());
						master.setSubdivision(item.getSubdivision().toUpperCase());
						master.setTown_code(item.getTowncode());
						master.setSubstation(item.getSubstation().toUpperCase());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
							} else {
								master.setCt_ratio(item.getCt_ratio());
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
							} else {
								master.setPt_ratio(item.getPtratio());
							}

						}

						master.setFdrname(item.getTowncode() + "-" + item.getFeedername());
						master.setFdrcode(dtMaster.getParentid());
						master.setDiscom("TNEB");
						master.setFdrtype("IPDS");
						master.setDlms("DLMS");
						master.setInstallation_date(item.getInstallationdate());
						master.setHes_type("GENUS");
//						master.setVoltage_kv(item.getVoltagerating());
						master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
						master.setFeeder_type("Regular Feeder");
						master.setFeeder_status("Feeder Live");
						master.setLocation_id(item.getDtcode());
						master.setCreate_time(new Timestamp(System.currentTimeMillis()));

						try {
							dtDetailsService.update(dtMaster);
							mainService.update(master);
							// meterInventoryService.updateMeterNoInstalled(item.getMeterid(), "SYNC");

							MeterInventoryEntity mtrInvboundary = meterInventoryService
									.getMeterInventoryEntity(item.getMeterid());
							if (mtrInvboundary != null) {
								mtrInvboundary.setMeter_status("INSTALLED");
								mtrInvboundary.setUpdateby(username);
								mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
								meterInventoryService.update(mtrInvboundary);
							}

							item.setSync_status(1);
							item.setUpdatedby(username);
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

						} catch (Exception e) {
							item.setSync_status(3);
							item.setUpdatedby(username);
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
							e.printStackTrace();
						}

					} else {
						// Previously This Dt was Single DT,&currently another meter is also attached
						// with this DT,So we are making it MultiDT

						try {

							DtDetailsEntity d = new DtDetailsEntity();
							MasterMainEntity master = new MasterMainEntity();

							d.setDtname(item.getDtname());
							d.setDttype("LT");
							d.setDttpid(item.getDtcode());
							d.setParent_substation(item.getSubstationcode());

							try {
								if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
									d.setPhase(1);
									master.setPhase("1");
								}
								if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
									d.setPhase(3);
									master.setPhase("3");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// d.setParent_feeder(item.getFeedername());
							d.setTpparentid(item.getFeedercode());
							try {
								Object[] fdr = (Object[]) feederDetailsService
										.getfdrIdByTpfeederId(item.getFeedercode(), item.getTowncode());
								d.setParentid(fdr[1].toString());// Need another service to set OurFeeder Id
								d.setParent_feeder(fdr[0].toString());
								master.setFdrcode(fdr[1].toString());
							} catch (Exception e) {
								e.printStackTrace();

							}
							d.setMeterno(item.getMeterid());
							d.setMetermanufacture(item.getManufacturername());
							d.setCrossdt(0);
							d.setVolt_mf(1.0);
							d.setCurrent_mf(1.0);
							try {
								d.setSubdivision(amiloc.getSubDivision());
							} catch (Exception e) {
								item.setSync_status(3);
								item.setUpdatedby(username);
								item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
								e.printStackTrace();
							}
							d.setTp_town_code(item.getTowncode());
							d.setDeleted(0);

							String dtid = dtDetailsService.getDtid();
							try {
								if (dtid != null && dtid != "") {
									d.setDt_id(dtid);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (item.getCapacity() != null && !"".equalsIgnoreCase(item.getCapacity())) {
									d.setDtcapacity(Double.parseDouble(item.getCapacity()));
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (item.getSubdivisioncode() != null
										&& !"".equalsIgnoreCase(item.getSubdivisioncode())) {
									d.setOfficeid((long) amiloc.getSitecode());
									master.setSdocode(String.valueOf(amiloc.getSitecode()));

								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (item.getMf() != null && !"".equalsIgnoreCase(item.getMf())) {
									d.setMf(Double.parseDouble(item.getMf()));
								} else {
									d.setMf(Double.parseDouble("1.0"));
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							d.setEntryby(username);
							d.setEntrydate(new Timestamp(System.currentTimeMillis()));
							try {
								dtDetailsService.update(d);
//								Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(item.getMeterid());
								master.setMtrno(item.getMeterid());
								master.setMtrmake(item.getManufacturername());
								master.setFdrcategory("DT");
								master.setCustomer_name(item.getDtname());
								master.setAccno(dtid);
								master.setZone(amiloc.getZone().toUpperCase());
								master.setCircle(item.getCircle().toUpperCase());

								master.setDivision(item.getDivision().toUpperCase());
								master.setSubdivision(item.getSubdivision().toUpperCase());
								master.setTown_code(item.getTowncode());
								master.setSubstation(item.getSubstation().toUpperCase());
								if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
									if (item.getCt_ratio().contains("/")) {
										master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
									} else {
										master.setCt_ratio(item.getCt_ratio());
									}
								}
								if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

									if (item.getPtratio().contains("/")) {
										master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
									} else {
										master.setPt_ratio(item.getPtratio());
									}

								}
								master.setSimno(item.getSimid());
								master.setFdrname(item.getTowncode() + "-" + item.getFeedername());
//								master.setFdrcode(item.getFeedercode());
								master.setFdrtype("IPDS");
								master.setDlms("DLMS");
								master.setInstallation_date(item.getInstallationdate());
								master.setHes_type("GENUS");
								master.setDiscom("TNEB");
								master.setFeeder_type("Regular Feeder");
								master.setFeeder_status("Feeder Live");
//								master.setVoltage_kv(item.getVoltagerating());
								master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
								master.setCreate_time(new Timestamp(System.currentTimeMillis()));
								master.setLocation_id(item.getDtcode());
								mainService.update(master);
								MeterInventoryEntity mtrInvboundary = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
								if (mtrInvboundary != null) {
									mtrInvboundary.setMeter_status("INSTALLED");
									mtrInvboundary.setUpdateby(username);
									mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
									meterInventoryService.update(mtrInvboundary);
								}
								item.setSync_status(1);
								item.setUpdatedby(username);
								item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

							} catch (Exception e) {
								item.setSync_status(3);
								item.setUpdatedby(username);
								item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
								e.printStackTrace();
							}
						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			} else {
				// If Multiple Dt present Then check where meterNo is null
				int dtFlag = 0;
				for (int i = 0; i < dtEntity.size(); i++) {

					DtDetailsEntity dtMaster = (DtDetailsEntity) dtEntity.get(i);

					if (dtMaster.getMeterno() == null || dtMaster.getMeterno().isEmpty()) {
						dtFlag = 1;
						dtMaster.setMeterno(item.getMeterid());
						dtMaster.setDttype("LT");
						dtMaster.setUpdate(username);
						dtMaster.setUpdatedate(new Timestamp(System.currentTimeMillis()));

						MasterMainEntity master = new MasterMainEntity();
						///// need to add master data also here when we are going to add dt meter
						try {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
								master.setPhase("1");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
								master.setPhase("3");
							}
							if (item.getSubdivisioncode() != null && !"".equalsIgnoreCase(item.getSubdivisioncode())) {
								master.setSdocode(String.valueOf(amiloc.getSitecode()));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

//						Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(item.getMeterid());
						master.setMtrno(item.getMeterid());
						master.setMtrmake(item.getManufacturername());
						master.setFdrcategory("DT");
						master.setCustomer_name(item.getDtname());
						master.setAccno(dtMaster.getDt_id());
						master.setZone(amiloc.getZone().toUpperCase());
						master.setCircle(item.getCircle().toUpperCase());

						master.setDivision(item.getDivision().toUpperCase());
						master.setSubdivision(item.getSubdivision().toUpperCase());
						master.setTown_code(item.getTowncode());
						master.setSubstation(item.getSubstation().toUpperCase());
						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
							} else {
								master.setCt_ratio(item.getCt_ratio());
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
							} else {
								master.setPt_ratio(item.getPtratio());
							}

						}
						master.setSimno(item.getSimid());
						master.setFdrname(item.getTowncode() + "-" + item.getFeedername());
						master.setFdrcode(dtMaster.getParentid());
						master.setDiscom("TNEB");
						master.setFdrtype("IPDS");
						master.setDlms("DLMS");
						master.setInstallation_date(item.getInstallationdate());
						master.setHes_type("GENUS");
//						master.setVoltage_kv(item.getVoltagerating());
						master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
						master.setFeeder_type("Regular Feeder");
						master.setFeeder_status("Feeder Live");
						master.setCreate_time(new Timestamp(System.currentTimeMillis()));
						master.setLocation_id(item.getDtcode());
						dtDetailsService.update(dtMaster);
						mainService.update(master);
						
//						meterInventoryService.updateMeterNoInstalled(item.getMeterid(), "SYNC");

						MeterInventoryEntity mtrInvboundary = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
						if (mtrInvboundary != null) {
							mtrInvboundary.setMeter_status("INSTALLED");
							mtrInvboundary.setUpdateby(username);
							mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							meterInventoryService.update(mtrInvboundary);
						}
						item.setSync_status(1);
						item.setUpdatedby(username);
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

						break;
					}

				}
				if (dtFlag == 0) {

					try {

						DtDetailsEntity d = new DtDetailsEntity();
						MasterMainEntity master = new MasterMainEntity();

						d.setDtname(item.getDtname());
						d.setDttpid(item.getDtcode());
						d.setDttype("LT");
						d.setParent_substation(item.getSubstationcode());

						try {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
								d.setPhase(1);
								master.setPhase("1");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
								d.setPhase(3);
								master.setPhase("3");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						// d.setParent_feeder(item.getFeedername());
						d.setTpparentid(item.getFeedercode());
						try {
							Object[] fdr = (Object[]) feederDetailsService.getfdrIdByTpfeederId(item.getFeedercode(),
									item.getTowncode());
							d.setParentid(fdr[1].toString());// Need another service to set OurFeeder Id
							d.setParent_feeder(fdr[0].toString());
							master.setFdrcode(fdr[1].toString());
						} catch (Exception e) {
							e.printStackTrace();

						}
						d.setMeterno(item.getMeterid());
						d.setMetermanufacture(item.getManufacturername());
						d.setCrossdt(0);
						d.setVolt_mf(1.0);
						d.setCurrent_mf(1.0);
						d.setSubdivision(amiloc.getSubDivision());
						d.setTp_town_code(item.getTowncode());
						d.setDeleted(0);

						String dtid = dtDetailsService.getDtid();
						try {
							if (dtid != null && dtid != "") {
								d.setDt_id(dtid);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if (item.getCapacity() != null && !"".equalsIgnoreCase(item.getCapacity())) {
								d.setDtcapacity(Double.parseDouble(item.getCapacity()));
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if (item.getSubdivisioncode() != null && !"".equalsIgnoreCase(item.getSubdivisioncode())) {
								d.setOfficeid((long) amiloc.getSitecode());
								master.setSdocode(String.valueOf(amiloc.getSitecode()));

							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if (item.getMf() != null && !"".equalsIgnoreCase(item.getMf())) {
								d.setMf(Double.parseDouble(item.getMf()));
							} else {
								d.setMf(Double.parseDouble("1.0"));
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						d.setEntryby(username);
						d.setEntrydate(new Timestamp(System.currentTimeMillis()));
						try {
							dtDetailsService.update(d);
//							Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(item.getMeterid());
							master.setMtrno(item.getMeterid());
							master.setMtrmake(item.getManufacturername());
							master.setFdrcategory("DT");
							master.setCustomer_name(item.getDtname());
							master.setAccno(dtid);
							master.setZone(amiloc.getZone().toUpperCase());
							master.setCircle(item.getCircle().toUpperCase());

							master.setDivision(item.getDivision().toUpperCase());
							master.setSubdivision(item.getSubdivision().toUpperCase());
							master.setTown_code(item.getTowncode());
							master.setSubstation(item.getSubstation().toUpperCase());
							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
								} else {
									master.setCt_ratio(item.getCt_ratio());
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
								} else {
									master.setPt_ratio(item.getPtratio());
								}

							}
							master.setSimno(item.getSimid());
							master.setFdrname(item.getTowncode() + "-" + item.getFeedername());
							// master.setFdrcode(item.getFeedercode());
							master.setFdrtype("IPDS");
							master.setDlms("DLMS");
							master.setInstallation_date(item.getInstallationdate());
							master.setHes_type("GENUS");
							master.setDiscom("TNEB");
							master.setFeeder_type("Regular Feeder");
							master.setFeeder_status("Feeder Live");
//							master.setVoltage_kv(item.getVoltagerating());
							master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
							master.setCreate_time(new Timestamp(System.currentTimeMillis()));
							master.setLocation_id(item.getDtcode());
							mainService.update(master);
							MeterInventoryEntity mtrInvboundary = meterInventoryService
									.getMeterInventoryEntity(item.getMeterid());
							if (mtrInvboundary != null) {
								mtrInvboundary.setMeter_status("INSTALLED");
								mtrInvboundary.setUpdateby(username);
								mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
								meterInventoryService.update(mtrInvboundary);
							}
							item.setSync_status(1);
							item.setUpdatedby(username);
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

						} catch (Exception e) {
							item.setSync_status(3);
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
			}

		} else {
			try {

				DtDetailsEntity d = new DtDetailsEntity();
				MasterMainEntity master = new MasterMainEntity();

				d.setDtname(item.getDtname());
				d.setDttpid(item.getDtcode());
				d.setDttype("LT");
				d.setParent_substation(item.getSubstationcode());

				try {
					if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
						d.setPhase(1);
						master.setPhase("1");
					}
					if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
						d.setPhase(3);
						master.setPhase("3");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// d.setParent_feeder(item.getFeedername());
				d.setTpparentid(item.getFeedercode());
				try {
					Object[] fdr = (Object[]) feederDetailsService.getfdrIdByTpfeederId(item.getFeedercode(),
							item.getTowncode());
					d.setParentid(fdr[1].toString());// Need another service to set OurFeeder Id
					d.setParent_feeder(fdr[0].toString());
					master.setFdrcode(fdr[1].toString());
				} catch (Exception e) {
					e.printStackTrace();

				}
				d.setMeterno(item.getMeterid());
				d.setMetermanufacture(item.getManufacturername());
				d.setCrossdt(0);
				d.setVolt_mf(1.0);
				d.setCurrent_mf(1.0);
				d.setSubdivision(amiloc.getSubDivision());
				d.setTp_town_code(item.getTowncode());
				d.setDeleted(0);

				String dtid = dtDetailsService.getDtid();
				try {
					if (dtid != null && dtid != "") {
						d.setDt_id(dtid);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (item.getCapacity() != null && !"".equalsIgnoreCase(item.getCapacity())) {
						d.setDtcapacity(Double.parseDouble(item.getCapacity()));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (item.getSubdivisioncode() != null && !"".equalsIgnoreCase(item.getSubdivisioncode())) {
						d.setOfficeid((long) amiloc.getSitecode());
						master.setSdocode(String.valueOf(amiloc.getSitecode()));

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (item.getMf() != null && !"".equalsIgnoreCase(item.getMf())) {
						d.setMf(Double.parseDouble(item.getMf()));
					} else {
						d.setMf(Double.parseDouble("1.0"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				d.setEntryby(username);
				d.setEntrydate(new Timestamp(System.currentTimeMillis()));
				try {
					dtDetailsService.update(d);
//					Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(item.getMeterid());
					master.setMtrno(item.getMeterid());
					master.setMtrmake(item.getManufacturername());
					master.setFdrcategory("DT");
					master.setCustomer_name(item.getDtname());
					master.setAccno(dtid);
					master.setZone(amiloc.getZone().toUpperCase());
					master.setCircle(item.getCircle().toUpperCase());

					master.setDivision(item.getDivision().toUpperCase());
					master.setSubdivision(item.getSubdivision().toUpperCase());
					master.setTown_code(item.getTowncode());
					master.setSubstation(item.getSubstation().toUpperCase());
					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							master.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

						if (item.getPtratio().contains("/")) {
							master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							master.setPt_ratio(item.getPtratio());
						}

					}
					master.setSimno(item.getSimid());
					master.setFdrname(item.getTowncode() + "-" + item.getFeedername());

					master.setFdrtype("IPDS");
					master.setDlms("DLMS");
					master.setInstallation_date(item.getInstallationdate());
					master.setHes_type("GENUS");
//					master.setVoltage_kv(item.getVoltagerating());
					master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
					master.setFeeder_type("Regular Feeder");
					master.setFeeder_status("Feeder Live");
					master.setDiscom("TNEB");
					master.setCreate_time(new Timestamp(System.currentTimeMillis()));
					master.setLocation_id(item.getDtcode());
					mainService.update(master);
					MeterInventoryEntity mtrInvboundary = meterInventoryService
							.getMeterInventoryEntity(item.getMeterid());
					if (mtrInvboundary != null) {
						mtrInvboundary.setMeter_status("INSTALLED");
						mtrInvboundary.setUpdateby(username);
						mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
						meterInventoryService.update(mtrInvboundary);
					}
					item.setSync_status(1);
					item.setUpdatedby(username);
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

				} catch (Exception e) {
					item.setSync_status(3);
					item.setUpdatedby(username);
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
					e.printStackTrace();
				}
			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby(username);
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();

			}
		}
	}

	private void replaceAppDtMeterSync(InitialMeterInfo item, AmiLocation amiloc, String username) {

		DtDetailsEntity dtEntity = dtDetailsService.getDtDetailsByMeter(item.getMeterid());
		// If meter is present in this particular DT then below steps
		if (dtEntity != null) {
			// For this single DT meter is present or not checking,If it is attached then
			// below operation
			if (dtEntity.getMeterno() != null || !dtEntity.getMeterno().isEmpty()) {

				try {
					// Removing Meter for particular DT
					dtEntity.setMeterno(null);
					dtEntity.setUpdate(username);
					dtEntity.setUpdatedate(new Timestamp(System.currentTimeMillis()));
					dtDetailsService.update(dtEntity);

					// Deleting Old Master Data for particular meterNo
					MasterMainEntity master = mainService.getEntityByMtrNO(item.getMeterid());
					mainService.delete(master.getId());

					item.setSync_status(1);
					item.setUpdatedby(username);
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				} catch (Exception e) {
					item.setSync_status(3);
					item.setUpdatedby(username);
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
					e.printStackTrace();
				}

			}

		}
	}

	private void feederMeterSync(InitialMeterInfo item, AmiLocation amiloc) {
		List<FeederEntity> fdrEntity = feederDetailsService.checkMeterAttachedonFeeder(item.getFeedercode(),
				item.getMeterid());
		if (fdrEntity == null || fdrEntity.isEmpty()) {

			try {
				List<FeederEntity> feederEntity = feederDetailsService.getFeederDetailsByFeederId(item.getFeedercode(),
						item.getTowncode());
				if (feederEntity.size() == 1) {
					for (int i = 0; i < feederEntity.size(); i++) {
						FeederEntity feederMaster = (FeederEntity) feederEntity.get(i);

						if (feederMaster.getMeterno() == null || feederMaster.getMeterno().isEmpty()) {

							feederMaster.setMeterno(item.getMeterid());
							feederMaster.setUpdateby("SYNC");
							feederMaster.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							feederMaster.setMeter_installed(1);
							feederMaster.setCrossfdr(0);
							feederMaster.setFeeder_type(item.getFeeder_type());
							feederMaster.setDeleted(0);

							MasterMainEntity master = new MasterMainEntity();
							///// need to add master data also here when we are going to add dt meter
							try {
								if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
									master.setPhase("1");
								}
								if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
									master.setPhase("3");
								}
								if (item.getSubdivisioncode() != null
										&& !"".equalsIgnoreCase(item.getSubdivisioncode())) {
									master.setSdocode(String.valueOf(amiloc.getSitecode()));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							master.setMtrno(item.getMeterid());
							master.setMtrmake(item.getManufacturerid());
							master.setFdrcategory("FEEDER METER");
							master.setCustomer_name(item.getTowncode() + "-" + item.getFeedername());
							master.setAccno(feederMaster.getFdrid());

							master.setZone(item.getRegion().toUpperCase());
							master.setCircle(item.getCircle().toUpperCase());
							master.setDivision(item.getDivision().toUpperCase());
							master.setSubdivision(item.getSubdivision().toUpperCase());
							master.setTown_code(item.getTowncode());
							master.setSubstation(item.getSubstation().toUpperCase());

							if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
								if (item.getCt_ratio().contains("/")) {
									master.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
								} else {
									master.setCt_ratio(item.getCt_ratio());
								}
							}
							if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

								if (item.getPtratio().contains("/")) {
									master.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
								} else {
									master.setPt_ratio(item.getPtratio());
								}

							}

							master.setSimno(item.getSimid());
							master.setFdrname(item.getTowncode() + "-" + item.getFeedername());
							master.setFdrcode(feederMaster.getFdrid());
							master.setDiscom("TNEB");
							master.setFdrtype(item.getFeeder_type());
							master.setDlms("DLMS");
							master.setInstallation_date(item.getInstallationdate());
							master.setHes_type("ANALOGICS");
							master.setFeeder_type("Regular Feeder");
							master.setFeeder_status("Feeder Live");
							master.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
							master.setCreate_time(new Timestamp(System.currentTimeMillis()));
							master.setLocation_id(item.getFeedercode());
							item.setSync_status(1);
							item.setUpdatedby("SYNC");
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

							try {
								feederDetailsService.update(feederMaster);
								mainService.update(master);
//								feederDetailsService.setMeterInventoflag(item.getMeterid(), "SYNC");
								MeterInventoryEntity mtrInvFe = meterInventoryService
										.getMeterInventoryEntity(item.getMeterid());
								if (mtrInvFe != null) {
									mtrInvFe.setMeter_status("INSTALLED");
									mtrInvFe.setUpdateby("SYNC");
									mtrInvFe.setUpdatedate(new Timestamp(System.currentTimeMillis()));
									meterInventoryService.update(mtrInvFe);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							item.setSync_status(2);
							item.setUpdatedby("SYNC");
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						}
					}

				} else {
					try {
						String qry = "select sitecode  from meter_data.amilocation where subdivision ='"
								+ item.getSubdivision() + "' ";
						String SubdivId = feederDetailsService.getCustomEntityManager("postgresMdas")
								.createNativeQuery(qry).getSingleResult().toString();

						FeederEntity fe = new FeederEntity();
						MasterMainEntity masterMain = new MasterMainEntity();

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								fe.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
							} else {
								fe.setCt_ratio(item.getCt_ratio());
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								fe.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
							} else {
								fe.setPt_ratio(item.getPtratio());
							}

						}

						fe.setFeedername(item.getTowncode() + "-" + item.getFeedername());
						fe.setManufacturer(item.getManufacturername());
						fe.setTpparentid(item.getSubstationcode());
						fe.setTpfdrid(item.getFeedercode());
						if (!"".equals(item.getCapacity()) && item.getCapacity() != null) {
							fe.setVoltagelevel(Double.parseDouble(CtPtRatioCalculation(item.getCapacity())));
						}
						fe.setMeterno(item.getMeterid());
						fe.setOfficeid(Integer.parseInt(SubdivId));
						fe.setParentid(feederDetailsService.getSSIdByTpsubstationId(item.getSubstationcode(),
								item.getTowncode()));
						if (!"".equals(item.getMf()) && item.getMf() != null) {
							fe.setMf(Double.parseDouble(item.getMf()));
						}
						fe.setCrossfdr(0);
						fe.setDeleted(0);
						fe.setTpTownCode(item.getTowncode());

						fe.setEntryby("SYNC");
						fe.setEntrydate(new Timestamp(System.currentTimeMillis()));
						

						// fe.setVolt_mf(1.0);
						// fe.setCurrent_mf(1.0);
						fe.setMeter_installed(1);

						String fdrid = feederDetailsService.getfdrid();
						try {
							if (fdrid != null && fdrid != "") {
								fe.setFdrid(fdrid);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						// Inserting and adding flag to Master main table.

						HashMap<String, String> hh = feederDetailsService.getlocationHireachy(item.getSubdivision());
						String zone = hh.get("ZONE");
						String circle = hh.get("CIRCLE");
						String division = hh.get("DIVISION");
						String subdivision = hh.get("SUBDIVISION");
						
						masterMain.setId(456789);
						masterMain.setZone(zone.toUpperCase());
						masterMain.setCircle(circle.toUpperCase());
						masterMain.setDivision(division.toUpperCase());
						masterMain.setSubdivision(subdivision.toUpperCase());
						masterMain.setSubstation(item.getSubstation().toUpperCase());
						masterMain.setTown_code(item.getTowncode());

						masterMain.setMtrno(item.getMeterid());
						masterMain.setMtrmake(item.getManufacturerid());
						masterMain.setFdrname(item.getTowncode() + "-" + item.getFeedername());
						
						masterMain.setFdrcode(fdrid);
						masterMain.setLatitude(item.getLattitude());
						masterMain.setLongitude(item.getLongitude());

						if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
							if (item.getCt_ratio().contains("/")) {
								masterMain.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
							} else {
								masterMain.setCt_ratio(item.getCt_ratio());
							}
						}
						if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

							if (item.getPtratio().contains("/")) {
								masterMain.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
							} else {
								masterMain.setPt_ratio(item.getPtratio());
							}

						}

						masterMain.setSimno(item.getSimid());
						masterMain.setDiscom("TNEB");
						masterMain.setHes_type("ANALOGICS");
						masterMain.setFdrcategory("FEEDER METER");
						masterMain.setCustomer_name(item.getTowncode() + "-" + item.getFeedername());
						masterMain.setAccno(fdrid);
						masterMain.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
						// masterMain.setVoltage_kv(voltageKVCalculation(item.getCapacity()));
						fe.setFeeder_type(item.getFeeder_type());
						masterMain.setFdrtype(item.getFeeder_type());
						masterMain.setCreate_time(new Timestamp(System.currentTimeMillis()));
						masterMain.setLocation_id(item.getFeedercode());
						
						
						try {
							if ("1 Phase".equalsIgnoreCase(item.getMetertype())) {
								masterMain.setPhase("1");
							}
							if ("3 Phase".equalsIgnoreCase(item.getMetertype())) {
								masterMain.setPhase("3");
							}
							if (item.getSubdivisioncode() != null
									&& !"".equalsIgnoreCase(item.getSubdivisioncode())) {
								masterMain.setSdocode(SubdivId);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							feederDetailsService.update(fe);
							mainService.update(masterMain);
							// feederDetailsService.setMeterInventoflag(item.getMeterid(), "SYNC");
							MeterInventoryEntity mtrInvFe = meterInventoryService
									.getMeterInventoryEntity(item.getMeterid());
							if (mtrInvFe != null) {
								mtrInvFe.setMeter_status("INSTALLED");
								mtrInvFe.setUpdateby("SYNC");
								mtrInvFe.setUpdatedate(new Timestamp(System.currentTimeMillis()));
								meterInventoryService.update(mtrInvFe);
							}
							item.setSync_status(1);
							item.setUpdatedby("SYNC");
							item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						item.setSync_status(3);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}
		} else {
			item.setSync_status(2);
			item.setUpdatedby("SYNC");
			item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
		}
	}

	private void boundaryMeterSync(InitialMeterInfo item) {
		FeederEntity feederEntity = feederDetailsService.getMtrExistOnFeeder(item.getFeedercode());
		if (feederEntity != null) {
			System.out.println("Inside BOUNDARY feederEntity is not null,meter is attached");

			FeederEntity boundaryEntity = feederDetailsService.getBoundaryDetailsByFeederId(item.getBoundaryid(),item.getFeedercode(), item.getTowncode());
			if (boundaryEntity != null) {

				System.out.println("Inside BOUNDARY boundaryEntity is not null,meter is attached or not");
				System.out.println("Boundary MeterNo=" + boundaryEntity.getMeterno());
				System.out.println("Boundary MeterNo ID=" + boundaryEntity.getId());

				if (boundaryEntity.getMeterno() == null || boundaryEntity.getMeterno().isEmpty()) {
					System.out.println("Inside BOUNDARY boundaryEntity is not null,meter is  not attached");

					// Update Boundary meter details//

					boundaryEntity.setMeterno(item.getMeterid());
					boundaryEntity.setUpdateby("SYNC");
					boundaryEntity.setUpdatedate(new Timestamp(System.currentTimeMillis()));
					
					

					// Add Master Boundary Data//

					MasterMainEntity masterMain = new MasterMainEntity();
					HashMap<String, String> hh = feederDetailsService.getlocationHireachy(item.getSubdivision());
					String zone = hh.get("ZONE");
					String circle = hh.get("CIRCLE");
					String division = hh.get("DIVISION");
					String subdivision = hh.get("SUBDIVISION");

					masterMain.setZone(zone.toUpperCase());
					masterMain.setCircle(circle.toUpperCase());
					masterMain.setDivision(division.toUpperCase());
					masterMain.setSubdivision(subdivision.toUpperCase());
					masterMain.setSubstation(item.getSubstation().toUpperCase());
					masterMain.setTown_code(item.getTowncode());

					masterMain.setFdrname(boundaryEntity.getFeedername());
					masterMain.setFdrcode(boundaryEntity.getFdrid());
					masterMain.setMtrno(item.getMeterid());
					masterMain.setMtrmake(item.getManufacturername());
					masterMain.setLatitude(item.getLattitude());
					masterMain.setLongitude(item.getLongitude());

					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							masterMain.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							masterMain.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {
						if (item.getPtratio().contains("/")) {
							masterMain.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							masterMain.setPt_ratio(item.getPtratio());
						}
					}
					masterMain.setMla(item.getBoundaryid());
					masterMain.setMf(item.getMf());
					masterMain.setDiscom("TNEB");
					masterMain.setHes_type("ANALOGICS");
					masterMain.setSdocode(item.getSubdivisioncode());
					masterMain.setFdrcategory("BOUNDARY METER");
					masterMain.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
//					masterMain.setVoltage_kv(voltageKVCalculation(item.getCapacity()));
					masterMain.setCustomer_name(item.getTowncode() + "-" + item.getFeedername());
					masterMain.setAccno(item.getBoundaryid());
					
					boundaryEntity.setFeeder_type(item.getFeeder_type());
					masterMain.setFdrtype(item.getFeeder_type());
					masterMain.setCreate_time(new Timestamp(System.currentTimeMillis()));
					masterMain.setLocation_id(item.getBoundaryid());
					try {
						feederDetailsService.update(boundaryEntity);
						mainService.update(masterMain);
						// feederDetailsService.setMeterInventoflag(item.getMeterid(), "SYNC");
						MeterInventoryEntity mtrInvboundary = meterInventoryService
								.getMeterInventoryEntity(item.getMeterid());
						if (mtrInvboundary != null) {
							mtrInvboundary.setMeter_status("INSTALLED");
							mtrInvboundary.setUpdateby("SYNC");
							mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							meterInventoryService.update(mtrInvboundary);
						}

						item.setSync_status(1);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

					} catch (Exception e) {
						item.setSync_status(3);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
						e.printStackTrace();
					}
				}
				// If Meter is already attached with this Boundary,So no need to do any
				// operation
				else {
					item.setSync_status(2);
					item.setUpdatedby("SYNC");
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				}

			} else {

				System.out.println("Inside BOUNDARY boundaryEntity is  null,meter is not attached");

				try {
					String qry = "select sitecode  from meter_data.amilocation where lower(subdivision) =lower('"
							+ item.getSubdivision() + "') ";
					String SubdivName = feederDetailsService.getCustomEntityManager("postgresMdas")
							.createNativeQuery(qry).getSingleResult().toString();

					System.out.println("Inside BOUNDARY boundaryEntity is  null SubdivName=" + SubdivName);

					FeederEntity fe = new FeederEntity();
					MasterMainEntity masterMain = new MasterMainEntity();

					// Get FeederId based ontpfeederCode,TownCode
//					 String fdrid =feederDetailsService.getFeederId(item.getFeedercode(),item.getSubstationcode(), item.getTowncode()); 

					// String bdrId = boundaryDetailsService.getlatestBoundaryId("");// Generate New
					// Boundary ID based on feederCode

					// Get FeederId based on tpfeederCode
//					String fdrid =feederDetailsService.getFeederIdbyTpfdrId(item.getFeedercode()); 

					if (!"".equals(item.getCapacity()) && item.getCapacity() != null) {
						fe.setVoltagelevel(Double.parseDouble(CtPtRatioCalculation(item.getCapacity())));
					}
					fe.setFeedername(item.getTowncode() + "-" + item.getFeedername());
					fe.setOfficeid(Integer.parseInt(SubdivName));
					String ssID = feederDetailsService.getSSIdByTpsubstationId(item.getSubstationcode(),
							item.getTowncode());
//					 if (!"".equals(ssID) && ssID!= null) {
//						 fe.setParentid(ssID);
//					 }

					fe.setParentid(feederEntity.getParentid());
					fe.setTpparentid(item.getSubstationcode());
					fe.setManufacturer(item.getManufacturername());
					fe.setMeter_installed(1);
					fe.setMeterno(item.getMeterid());
					fe.setMf(Double.parseDouble(item.getMf()));

					fe.setCrossfdr(1);
					fe.setDeleted(0);
					fe.setTpfdrid(item.getFeedercode());
					fe.setEntryby("SYNC");
					fe.setEntrydate(new Timestamp(System.currentTimeMillis()));
//					fe.setFdrid(feederEntity.getFdrid());// Changed By New ID
					String fDRId=feederDetailsService.getfdrid();
					fe.setFdrid(fDRId);
//					 if (!"".equals(fdrid) && fdrid!= null) {
//					   fe.setFdrid(fdrid);
//					 }else {
//						 fe.setFdrid(fDRId);
//					 }
					fe.setBoundary_id(item.getBoundaryid());
					fe.setBoundary_name(item.getBoundaryname());
					fe.setBoundary_location(item.getBoundaryname());

					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							fe.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							fe.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {
						if (item.getPtratio().contains("/")) {
							fe.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							fe.setPt_ratio(item.getPtratio());
						}

					}
					fe.setBoundry_feeder(true);
					fe.setMeter_ratio(null);
					fe.setTpTownCode(item.getTowncode());

					if ("".equals(item.getMeterid())) {
						fe.setMeterno(null);
						fe.setManufacturer(null);
						fe.setCt_ratio(null);
						fe.setPt_ratio(null);
						fe.setMeter_ratio(null);
						fe.setImp_exp(null);
					}

					// Saving data to Master main

					HashMap<String, String> hh = feederDetailsService.getlocationHireachy(item.getSubdivision());
					String zone = hh.get("ZONE");
					String circle = hh.get("CIRCLE");
					String division = hh.get("DIVISION");
					String subdivision = hh.get("SUBDIVISION");

					masterMain.setZone(zone.toUpperCase());
					masterMain.setCircle(circle.toUpperCase());
					masterMain.setDivision(division.toUpperCase());
					masterMain.setSubdivision(subdivision.toUpperCase());
					masterMain.setSubstation(item.getSubstation().toUpperCase());
					masterMain.setTown_code(item.getTowncode());

					masterMain.setFdrname(item.getTowncode() + "-" + item.getFeedername());

//					 if (!"".equals(fdrid) && fdrid!= null) {
//						   masterMain.setFdrcode(fdrid);
//						   masterMain.setAccno(fdrid);
//						 }else {
//						 masterMain.setFdrcode(fDRId);
//						 masterMain.setAccno(fDRId);
//						}

					masterMain.setFdrcode(fDRId);
					masterMain.setAccno(item.getBoundaryid());
					masterMain.setMtrno(item.getMeterid());
					masterMain.setMtrmake(item.getManufacturername());
					masterMain.setLatitude(item.getLattitude());
					masterMain.setLongitude(item.getLongitude());

					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							masterMain.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							masterMain.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

						if (item.getPtratio().contains("/")) {
							masterMain.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							masterMain.setPt_ratio(item.getPtratio());
						}

					}
					masterMain.setMla(item.getBoundaryid());
					masterMain.setMf(item.getMf());
					masterMain.setDiscom("TNEB");
					masterMain.setHes_type("ANALOGICS");
					masterMain.setSdocode(item.getSubdivisioncode());
					masterMain.setFdrcategory("BOUNDARY METER");
//					masterMain.setVoltage_kv(voltageKVCalculation(item.getCapacity()));
					masterMain.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
					masterMain.setCustomer_name(item.getTowncode() + "-" + item.getFeedername());
					
					fe.setFeeder_type(item.getFeeder_type());
					masterMain.setFdrtype(item.getFeeder_type());
					masterMain.setCreate_time(new Timestamp(System.currentTimeMillis()));
					masterMain.setLocation_id(item.getBoundaryid());
					try {
						feederDetailsService.update(fe);
						mainService.update(masterMain);
						MeterInventoryEntity mtrInvboundary = meterInventoryService
								.getMeterInventoryEntity(item.getMeterid());
						if (mtrInvboundary != null) {
							mtrInvboundary.setMeter_status("INSTALLED");
							mtrInvboundary.setUpdateby("SYNC");
							mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							meterInventoryService.update(mtrInvboundary);
						}

						// feederDetailsService.setMeterInventoflag(item.getMeterid(), "SYNC");

						item.setSync_status(1);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					item.setSync_status(3);
					item.setUpdatedby("SYNC");
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
					e.printStackTrace();
				}

			}

		}

		// If feeder is not present in that given town with meter =

		else {

			try {
				
				System.out.println("Inside Else condition..........................................................................");

				FeederEntity boundaryEntity = feederDetailsService.getBoundaryDetailsByFeederId(item.getBoundaryid(),item.getFeedercode(), item.getTowncode());
				if (boundaryEntity == null) {

					String qry = "select sitecode  from meter_data.amilocation where lower(subdivision) =lower('"+ item.getSubdivision() + "') ";
					String SubdivCode = feederDetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult().toString();
					System.out.println("If feeder is not present SubdivCode=" + SubdivCode);
					FeederEntity fe = new FeederEntity();
					MasterMainEntity masterMain = new MasterMainEntity();

					String fdrid = feederDetailsService.getFeederId(item.getFeedercode(), item.getSubstationcode(),item.getTowncode()); // Get our FeederId
					String fdrId = feederDetailsService.getfdrid();

					if (!"".equals(item.getCapacity()) && item.getCapacity() != null) {
						fe.setVoltagelevel(Double.parseDouble(CtPtRatioCalculation(item.getCapacity())));
					}
					fe.setFeedername(item.getTowncode() + "-" + item.getFeedername());
					fe.setOfficeid(Integer.parseInt(SubdivCode));

					String ssID = feederDetailsService.getSSIdByTpsubstationId(item.getSubstationcode(),item.getTowncode());
					if (!"".equals(ssID) && ssID != null) {
						fe.setParentid(ssID);
					}

					fe.setTpparentid(item.getSubstationcode());
					fe.setManufacturer(item.getManufacturername());
					fe.setMeter_installed(1);
					fe.setMeterno(item.getMeterid());
					fe.setMf(Double.parseDouble(item.getMf()));

					fe.setCrossfdr(1);
					fe.setDeleted(0);
					fe.setTpfdrid(item.getFeedercode());
					fe.setEntryby("SYNC");
					fe.setEntrydate(new Timestamp(System.currentTimeMillis()));

					try {
//					if (fdrId != null && fdrId != "") {
//						fe.setFdrid(fdrId);
//					}			
						masterMain.setSdocode(SubdivCode);
						if (!"".equals(fdrid) && fdrid != null) {
//							masterMain.setFdrcode(fdrid);
//							masterMain.setAccno(fdrid);
//							fe.setFdrid(fdrid);
							masterMain.setFdrcode(fdrId);//Changed By new ID
							masterMain.setAccno(item.getBoundaryid());
							fe.setFdrid(fdrId);
						} else {
							fe.setFdrid(fdrId);
							masterMain.setFdrcode(fdrId);
							masterMain.setAccno(item.getBoundaryid());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					fe.setBoundary_id(item.getBoundaryid());
					fe.setBoundary_name(item.getBoundaryname());
					fe.setBoundary_location(item.getBoundaryname());

					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							fe.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							fe.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

						if (item.getPtratio().contains("/")) {
							fe.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							fe.setPt_ratio(item.getPtratio());
						}

					}
					fe.setBoundry_feeder(true);
					fe.setMeter_ratio(null);
					fe.setTpTownCode(item.getTowncode());

					if ("".equals(item.getMeterid())) {
						fe.setMeterno(null);
						fe.setManufacturer(null);
						fe.setCt_ratio(null);
						fe.setPt_ratio(null);
						fe.setMeter_ratio(null);
						fe.setImp_exp(null);
					}

					// Saving data to master main

					HashMap<String, String> hh = feederDetailsService.getlocationHireachy(item.getSubdivision());
					String zone = hh.get("ZONE");
					String circle = hh.get("CIRCLE");
					String division = hh.get("DIVISION");
					String subdivision = hh.get("SUBDIVISION");

					masterMain.setZone(zone.toUpperCase());
					masterMain.setCircle(circle.toUpperCase());
					masterMain.setDivision(division.toUpperCase());
					masterMain.setSubdivision(subdivision.toUpperCase());
					masterMain.setSubstation(item.getSubstation().toUpperCase());
					masterMain.setTown_code(item.getTowncode());

					masterMain.setFdrname(item.getTowncode() + "-" + item.getFeedername());
//				masterMain.setFdrcode(fdrId);
					masterMain.setMtrno(item.getMeterid());
					masterMain.setMtrmake(item.getManufacturername());
					masterMain.setLatitude(item.getLattitude());
					masterMain.setLongitude(item.getLongitude());

					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							masterMain.setCt_ratio(CtPtRatioCalculation(item.getCt_ratio()));
						} else {
							masterMain.setCt_ratio(item.getCt_ratio());
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

						if (item.getPtratio().contains("/")) {
							masterMain.setPt_ratio(CtPtRatioCalculation(item.getPtratio()));
						} else {
							masterMain.setPt_ratio(item.getPtratio());
						}

					}

					masterMain.setMla(item.getBoundaryid());
					masterMain.setMf(item.getMf());
					masterMain.setDiscom("TNEB");
					masterMain.setHes_type("ANALOGICS");

					masterMain.setFdrcategory("BOUNDARY METER");
//				masterMain.setVoltage_kv(voltageKVCalculation(item.getCapacity()));
					masterMain.setVoltage_kv(voltageKVCalculation(item.getVoltagerating()));
					
					fe.setFeeder_type(item.getFeeder_type());
					masterMain.setFdrtype(item.getFeeder_type());
					masterMain.setCreate_time(new Timestamp(System.currentTimeMillis()));
					masterMain.setLocation_id(item.getBoundaryid());
					try {
						feederDetailsService.update(fe);
						mainService.update(masterMain);
						MeterInventoryEntity mtrInvboundary = meterInventoryService.getMeterInventoryEntity(item.getMeterid());
						if (mtrInvboundary != null) {
							mtrInvboundary.setMeter_status("INSTALLED");
							mtrInvboundary.setUpdateby("SYNC");
							mtrInvboundary.setUpdatedate(new Timestamp(System.currentTimeMillis()));
							meterInventoryService.update(mtrInvboundary);
						}
						// feederDetailsService.setMeterInventoflag(item.getMeterid(), "SYNC");
						item.setSync_status(1);
						item.setUpdatedby("SYNC");
						item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					item.setSync_status(2);
					item.setUpdatedby("SYNC");
					item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				}
			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}

		}

	}

	private void nameplateInventorySync(InitialMeterInfo item) {
		NamePlate nP = namePlateService.getNamePlateDataByMeterNo(item.getMeterid());
		if (nP == null) {
			// System.out.println("Started NamePlate is Null"+new Date());
			try {
				NamePlate nPlate = new NamePlate();
				nPlate.setMeter_serial_number(item.getMeterid().trim());
				nPlate.setMeter_type(item.getMetertype().trim());

				if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
					if (item.getCt_ratio().contains("/")) {
						nPlate.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
					} else {
						nPlate.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
					}
				}

				if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

					if (item.getPtratio().contains("/")) {
						nPlate.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
					} else {
						nPlate.setPt_ratio(Double.parseDouble(item.getPtratio()));
					}

				}

				nPlate.setFirmware_version(item.getFirmwareversion().isEmpty() || item.getFirmwareversion() == null ? "": item.getFirmwareversion());
				nPlate.setCurrent_rating(item.getCurrentrating().isEmpty() || item.getCurrentrating() == null ? "": item.getCurrentrating());
				nPlate.setManufacturer_name(item.getManufacturername().isEmpty() || item.getManufacturername().trim() == null ? "": item.getManufacturername().trim());
				nPlate.setYear_of_manufacture(item.getManufactureyear());
				nPlate.setCreatedTime(new Timestamp(System.currentTimeMillis()));
				nPlate.setFlag("SYNC");
				namePlateService.update(nPlate);				
			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}

		}

		MeterInventoryEntity mtrInv = meterInventoryService.getMeterInventoryEntity(item.getMeterid());

		if (mtrInv == null) {
			try {
				MeterInventoryEntity inventory = new MeterInventoryEntity();
				inventory.setMeterno(item.getMeterid());
				if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
					inventory.setMeter_connection_type((short) 1);
					inventory.setConnection_type("12");
				}
				if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
					inventory.setMeter_connection_type((short) 3);
					inventory.setConnection_type("33");
				}
				if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
					inventory.setMeter_connection_type((short) 3);
					inventory.setConnection_type("34");
				}
				inventory.setMeter_make(item.getManufacturername());
				inventory.setMeter_accuracy_class(item.getMeterclass());
				inventory.setMeter_commisioning(item.getMetercommissioning());
				inventory.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
				inventory.setMeter_current_rating(item.getCurrentrating());
				inventory.setMeter_ip_period(item.getIpperiod());
				inventory.setMeter_model(item.getMetermodel());
				inventory.setMeter_status("INSTOCK");
				inventory.setMeter_voltage_rating(item.getVoltagerating());
				inventory.setMonth(item.getManufactureyear());

				if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
					if (item.getCt_ratio().contains("/")) {
						inventory.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
					} else {
						inventory.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
					}
				}
				if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

					if (item.getPtratio().contains("/")) {
						inventory.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
					} else {
						inventory.setPt_ratio(Double.parseDouble(item.getPtratio()));
					}

				}

				inventory.setEntryby("SYNC");
				inventory.setEntrydate(new Timestamp(System.currentTimeMillis()));
				inventory.setMeterdigit(item.getMeterdigit());
				meterInventoryService.update(inventory);
			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}

		} else {

			try {

				if (!"INSTALLED".equalsIgnoreCase(mtrInv.getMeter_status())) {

					if ("1 Phase".equalsIgnoreCase(item.getMetertype()) && "2".equalsIgnoreCase(item.getWire())) {
						mtrInv.setMeter_connection_type((short) 1);
						mtrInv.setConnection_type("12");
					}
					if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "3".equalsIgnoreCase(item.getWire())) {
						mtrInv.setMeter_connection_type((short) 3);
						mtrInv.setConnection_type("33");
					}
					if ("3 Phase".equalsIgnoreCase(item.getMetertype()) && "4".equalsIgnoreCase(item.getWire())) {
						mtrInv.setMeter_connection_type((short) 3);
						mtrInv.setConnection_type("34");
					}

					mtrInv.setMeter_make(item.getManufacturername());
					mtrInv.setMeter_accuracy_class(item.getMeterclass());
					mtrInv.setMeter_commisioning(item.getMetercommissioning());
					if (!"".equals(item.getMeterconstant()) && item.getMeterconstant() != null) {
						mtrInv.setMeter_constant(Double.parseDouble(item.getMeterconstant()));
					}
					mtrInv.setMeter_current_rating(item.getCurrentrating());
					mtrInv.setMeter_ip_period(item.getIpperiod());
					mtrInv.setMeter_model(item.getMetermodel());

					mtrInv.setMeter_voltage_rating(item.getVoltagerating());
					mtrInv.setMonth(item.getManufactureyear());
					if (!"".equals(item.getCt_ratio()) && item.getCt_ratio() != null) {
						if (item.getCt_ratio().contains("/")) {
							mtrInv.setCt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getCt_ratio())));
						} else {
							mtrInv.setCt_ratio(Double.parseDouble(item.getCt_ratio()));
						}
					}
					if (!"".equals(item.getPtratio()) && item.getPtratio() != null) {

						if (item.getPtratio().contains("/")) {
							mtrInv.setPt_ratio(Double.parseDouble(CtPtRatioCalculation(item.getPtratio())));
						} else {
							mtrInv.setPt_ratio(Double.parseDouble(item.getPtratio()));
						}

					}
					mtrInv.setUpdateby("SYNC");
					mtrInv.setUpdatedate(new Timestamp(System.currentTimeMillis()));
					mtrInv.setMeterdigit(item.getMeterdigit());
					meterInventoryService.update(mtrInv);
				}

			} catch (Exception e) {
				item.setSync_status(3);
				item.setUpdatedby("SYNC");
				item.setUpdateddate(new Timestamp(System.currentTimeMillis()));
				e.printStackTrace();
			}
		}

		if (mtrInv == null && nP == null) {
			item.setSync_status(1);
			item.setUpdatedby("SYNC");
			item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

		}
		if (mtrInv != null && nP == null) {
			item.setSync_status(1);
			item.setUpdatedby("SYNC");
			item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

		}

		if (mtrInv == null && nP != null) {
			item.setSync_status(1);
			item.setUpdatedby("SYNC");
			item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

		}
		if (mtrInv != null && nP != null) {
			item.setSync_status(2);
			item.setUpdatedby("SYNC");
			item.setUpdateddate(new Timestamp(System.currentTimeMillis()));

		}
		initialMeterInfoService.update(item);
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
//			ctratio=String.valueOf(engine.eval(pt));
			ratio = String.valueOf(engine.eval(pt));
//			System.out.println("CT Ratio= "+Long.parseLong(String.valueOf(engine.eval(pt))));
//			System.out.println("PT Ratio= "+Long.parseLong(String.valueOf(engine.eval(pt))));
//			System.out.println("MF= "+Long.parseLong(String.valueOf(engine.eval(pt)))* Long.parseLong(String.valueOf(engine.eval(ct))));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratio;
	}

	public String voltageKVCalculation(String volt) {

		String ratio = "";
		volt = volt.toUpperCase();
		volt = volt.replace("K", "");
		volt = volt.replace("A", "");
		volt = volt.replace("V", "");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			ratio = String.valueOf(engine.eval(volt));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ratio;
	}

	@RequestMapping(value = "/dtMeterApproval", method = { RequestMethod.GET, RequestMethod.POST })
	public String dtMeterApproval(HttpServletRequest request, ModelMap model) {
		model.addAttribute("results", "notDisplay");
		return "dtMeterApproval";
	}

	@RequestMapping(value = "/viewDTAppDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List viewDTAppDetails(HttpServletRequest request, ModelMap model) {
		String reportType = request.getParameter("reportType").trim();
		String region = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String towncode = request.getParameter("town");
//		System.out.println("reportType =: "+reportType);

		List<?> list = null;
		try {
			if ("APPROVED".equalsIgnoreCase(reportType)) {
				list = initialMeterInfoService.getDTAppDetails(region,circle,towncode);

			} else {
				list = initialMeterInfoService.getDTNotAppDetails(region,circle,towncode);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@RequestMapping(value = "/viewDTMeterWiseDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object viewDTMeterWiseDetails(HttpServletRequest request, ModelMap model) {
		int id = Integer.parseInt(request.getParameter("id").trim());
		String meterNo = request.getParameter("meterNo").trim();
		String time = request.getParameter("time").trim();
		String reportType = request.getParameter("reportType").trim();

//		System.out.println("id =: "+id+" meterNo=: "+meterNo+" time=: "+time);
//		System.out.println("reportType =: "+reportType);

		// List<?> list = null;
		InitialMeterInfo info = null;
		try {
			if ("APPROVED".equalsIgnoreCase(reportType)) {
				// list = initialMeterInfoService.getDTAppDetails();

			} else {

				info = initialMeterInfoService.getDTNotAppDetailByMeter(id, meterNo);
				// System.out.println(info.getMeterid());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	@RequestMapping(value = "/appdtmtrlistpdf/{reportType}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void appdtmtrlistpdf(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@PathVariable String reportType) {
		try {
			if ("APPROVED".equalsIgnoreCase(reportType)) {
				initialMeterInfoService.getDtMtrAppList(request, response, reportType);

			} else {
				initialMeterInfoService.getDtMtrNotappList(request, response, reportType);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/periodwisecommunicationpdf/{zn}/{cr}/{tn}/{fromdate}/{todate}/{townnames}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void periodwisecommunicationpdf(HttpServletRequest request, HttpServletResponse response,
			ModelMap model, @PathVariable String zn, @PathVariable String cr, 
			 @PathVariable String tn, @PathVariable String fromdate,
			@PathVariable String todate, @PathVariable String townnames) {
		
		//String townames = request.getParameter("townnames");
		String zne = "", crcl = "", div = "", subdiv = "", town = "";
		if (zn.equalsIgnoreCase("ALL")) {
			zne = "%";
		} else {
			zne = zn;
		}
		if (cr.equalsIgnoreCase("ALL")) {
			crcl = "%";
		} else {
			crcl = cr;
		}
		
		
		 if (tn.equalsIgnoreCase("ALL")) { town = "%"; } else { town = tn; }
		 
System.out.println(town);
		initialMeterInfoService.getPreiodCommSumm(request, response, zne, crcl, town, fromdate, todate,townnames);
	}

}
