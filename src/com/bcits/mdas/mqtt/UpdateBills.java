package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.FilterConfig;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.service.MeterMasterService;
import com.ibm.icu.text.SimpleDateFormat;

class UpdateBills extends MasterThread {
	
	@Autowired
	private AmrBillsService amrBillsService;
	
	@Autowired
	private MeterMasterService meterMasterService;

	UpdateBills(FilterConfig filterConfig, String topic, MqttMessage data) {
		super(filterConfig, topic, data);
	}

	@Override
	public void run() {
		super.run();

		//[{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"08/Sep/2017 12:37:27 PM","sys_PF_Billing":1.0,"kWh":0.10000000149011612,"kWh_TZ1":0.0,"kWh_TZ2":0.10000000149011612,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.10000000149011612,"kVAh_TZ1":0.0,"kVAh_TZ2":0.10000000149011612,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.01899999938905239,"OccDate_kW":"08/Sep/2017 12:00:00 PM","kW_TZ1":0.0020000000949949026,"Date_kW_TZ1":"01/Sep/2017 08:00:00 AM","kW_TZ2":0.01899999938905239,"Date_kW_TZ2":"08/Sep/2017 12:00:00 PM","kW_TZ3":0.0,"Date_kW_TZ3":"05/Sep/2017 19:00:00 PM","kW_TZ4":0.004999999888241291,"Date_kW_TZ4":"01/Sep/2017 05:30:00 AM","kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.01899999938905239,"Date_kVA":"08/Sep/2017 12:00:00 PM","kVA_TZ1":0.0020000000949949026,"Date_kVA_TZ1":"01/Sep/2017 08:00:00 AM","kVA_TZ2":0.01899999938905239,"Date_kVA_TZ2":"08/Sep/2017 12:00:00 PM","kVA_TZ3":0.0,"Date_kVA_TZ3":"05/Sep/2017 19:00:00 PM","kVA_TZ4":0.004999999888241291,"Date_kVA_TZ4":"01/Sep/2017 05:30:00 AM","kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Sep/2017 00:00:00 AM","sys_PF_Billing":1.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.01899999938905239,"OccDate_kW":"31/Aug/2017 17:30:00 PM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.01899999938905239,"Date_kW_TZ2":"31/Aug/2017 17:30:00 PM","kW_TZ3":0.004000000189989805,"Date_kW_TZ3":"31/Aug/2017 21:00:00 PM","kW_TZ4":0.003000000026077032,"Date_kW_TZ4":"01/Sep/2017 00:00:00 AM","kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.01899999938905239,"Date_kVA":"31/Aug/2017 17:30:00 PM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.01899999938905239,"Date_kVA_TZ2":"31/Aug/2017 17:30:00 PM","kVA_TZ3":0.004000000189989805,"Date_kVA_TZ3":"31/Aug/2017 21:00:00 PM","kVA_TZ4":0.003000000026077032,"Date_kVA_TZ4":"01/Sep/2017 00:00:00 AM","kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Aug/2017 00:00:00 AM","sys_PF_Billing":0.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0,"OccDate_kW":"04/Jul/2017 15:30:00 PM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.0,"Date_kW_TZ2":"04/Jul/2017 15:30:00 PM","kW_TZ3":0.0,"Date_kW_TZ3":"03/Jul/2017 19:00:00 PM","kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.0,"Date_kVA":"04/Jul/2017 15:30:00 PM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.0,"Date_kVA_TZ2":"04/Jul/2017 15:30:00 PM","kVA_TZ3":0.0,"Date_kVA_TZ3":"03/Jul/2017 19:00:00 PM","kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Jul/2017 00:00:00 AM","sys_PF_Billing":1.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.010999999940395355,"OccDate_kW":"15/Jun/2017 18:00:00 PM","kW_TZ1":0.0010000000474974513,"Date_kW_TZ1":"15/Jun/2017 06:00:00 AM","kW_TZ2":0.010999999940395355,"Date_kW_TZ2":"15/Jun/2017 18:00:00 PM","kW_TZ3":0.0020000000949949026,"Date_kW_TZ3":"14/Jun/2017 19:00:00 PM","kW_TZ4":0.0010000000474974513,"Date_kW_TZ4":"16/Jun/2017 02:30:00 AM","kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.010999999940395355,"Date_kVA":"15/Jun/2017 18:00:00 PM","kVA_TZ1":0.0010000000474974513,"Date_kVA_TZ1":"15/Jun/2017 06:00:00 AM","kVA_TZ2":0.010999999940395355,"Date_kVA_TZ2":"15/Jun/2017 18:00:00 PM","kVA_TZ3":0.0020000000949949026,"Date_kVA_TZ3":"14/Jun/2017 19:00:00 PM","kVA_TZ4":0.0010000000474974513,"Date_kVA_TZ4":"16/Jun/2017 02:30:00 AM","kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Mar/2017 00:00:00 AM","sys_PF_Billing":1.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0,"OccDate_kW":"21/Feb/2017 11:30:00 AM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.0,"Date_kW_TZ2":"21/Feb/2017 11:30:00 AM","kW_TZ3":0.0,"Date_kW_TZ3":"21/Feb/2017 20:30:00 PM","kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.0,"Date_kVA":"21/Feb/2017 11:30:00 AM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.0,"Date_kVA_TZ2":"21/Feb/2017 11:30:00 AM","kVA_TZ3":0.0,"Date_kVA_TZ3":"21/Feb/2017 20:30:00 PM","kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Feb/2017 00:00:00 AM","sys_PF_Billing":1.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0,"OccDate_kW":"03/Jan/2017 18:30:00 PM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.0,"Date_kW_TZ2":"03/Jan/2017 17:30:00 PM","kW_TZ3":0.0,"Date_kW_TZ3":"03/Jan/2017 18:30:00 PM","kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.0,"Date_kVA":"03/Jan/2017 18:30:00 PM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.0,"Date_kVA_TZ2":"03/Jan/2017 17:30:00 PM","kVA_TZ3":0.0,"Date_kVA_TZ3":"03/Jan/2017 18:30:00 PM","kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Jan/2017 00:00:00 AM","sys_PF_Billing":0.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0,"OccDate_kW":"09/Dec/2016 12:00:00 PM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.0,"Date_kW_TZ2":"09/Dec/2016 12:00:00 PM","kW_TZ3":0.0,"Date_kW_TZ3":null,"kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.0,"Date_kVA":"09/Dec/2016 12:00:00 PM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.0,"Date_kVA_TZ2":"09/Dec/2016 12:00:00 PM","kVA_TZ3":0.0,"Date_kVA_TZ3":null,"kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Dec/2016 00:00:00 AM","sys_PF_Billing":0.5,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0020000000949949026,"OccDate_kW":"28/Nov/2016 15:30:00 PM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.0020000000949949026,"Date_kW_TZ2":"28/Nov/2016 15:30:00 PM","kW_TZ3":0.0,"Date_kW_TZ3":"29/Nov/2016 18:30:00 PM","kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.004000000189989805,"Date_kVA":"28/Nov/2016 15:30:00 PM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.004000000189989805,"Date_kVA_TZ2":"28/Nov/2016 15:30:00 PM","kVA_TZ3":0.0,"Date_kVA_TZ3":"29/Nov/2016 18:30:00 PM","kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Jan/2016 00:00:00 AM","sys_PF_Billing":0.5600000023841858,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.007000000216066837,"OccDate_kW":"25/Dec/2015 11:00:00 AM","kW_TZ1":0.0,"Date_kW_TZ1":null,"kW_TZ2":0.007000000216066837,"Date_kW_TZ2":"25/Dec/2015 11:00:00 AM","kW_TZ3":0.0,"Date_kW_TZ3":null,"kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.013000000268220901,"Date_kVA":"25/Dec/2015 11:00:00 AM","kVA_TZ1":0.0,"Date_kVA_TZ1":null,"kVA_TZ2":0.013000000268220901,"Date_kVA_TZ2":"25/Dec/2015 11:00:00 AM","kVA_TZ3":0.0,"Date_kVA_TZ3":null,"kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null},{"transID":null,"METER_ID":0,"meterNumber":"D0261146","IMEI":"868657029196084","serverTime":"08/Sep/2017 12:25:00 PM","billingDate":"01/Sep/2015 00:00:00 AM","sys_PF_Billing":0.0,"kWh":0.0,"kWh_TZ1":0.0,"kWh_TZ2":0.0,"kWh_TZ3":0.0,"kWh_TZ4":0.0,"kWh_TZ5":0.0,"kWh_TZ6":0.0,"kWh_TZ7":0.0,"kWh_TZ8":0.0,"kvarhLag":0.0,"kvarhLead":0.0,"kVAh":0.0,"kVAh_TZ1":0.0,"kVAh_TZ2":0.0,"kVAh_TZ3":0.0,"kVAh_TZ4":0.0,"kVAh_TZ5":0.0,"kVAh_TZ6":0.0,"kVAh_TZ7":0.0,"kVAh_TZ8":0.0,"Demand_kW":0.0,"OccDate_kW":"05/Aug/2015 06:00:00 AM","kW_TZ1":0.0,"Date_kW_TZ1":"05/Aug/2015 06:00:00 AM","kW_TZ2":0.0,"Date_kW_TZ2":null,"kW_TZ3":0.0,"Date_kW_TZ3":null,"kW_TZ4":0.0,"Date_kW_TZ4":null,"kW_TZ5":0.0,"Date_kW_TZ5":null,"kW_TZ6":0.0,"Date_kW_TZ6":null,"kW_TZ7":0.0,"Date_kW_TZ7":null,"kW_TZ8":0.0,"Date_kW_TZ8":null,"kVA":0.0,"Date_kVA":"05/Aug/2015 06:00:00 AM","kVA_TZ1":0.0,"Date_kVA_TZ1":"05/Aug/2015 06:00:00 AM","kVA_TZ2":0.0,"Date_kVA_TZ2":null,"kVA_TZ3":0.0,"Date_kVA_TZ3":null,"kVA_TZ4":0.0,"Date_kVA_TZ4":null,"kVA_TZ5":0.0,"Date_kVA_TZ5":null,"kVA_TZ6":0.0,"Date_kVA_TZ6":null,"kVA_TZ7":0.0,"Date_kVA_TZ7":null,"kVA_TZ8":0.0,"Date_kVA_TZ8":null}]
			
				try {
					JSONArray recs =  new JSONArray(data.toString());
					String imei = null, meterNumber= null;
					
					for (int i = 0; i < recs.length(); i++) {
						JSONObject obj = recs.getJSONObject(i);
						//System.out.println("==================================== PARSE BILLS"+i); 
						String transID=obj.optString("transID");
						String METER_ID=obj.optString("METER_ID");
						meterNumber=obj.optString("meterNumber");
						imei=obj.optString("IMEI");
						String serverTime=obj.optString("serverTime");
						String billingDate=obj.optString("billingDate");
						String sys_PF_Billing=obj.optString("sys_PF_Billing");
						String kWh=obj.optString("kWh");
						String kWh_TZ1=obj.optString("kWh_TZ1");
						String kWh_TZ2=obj.optString("kWh_TZ2");
						String kWh_TZ3=obj.optString("kWh_TZ3");
						String kWh_TZ4=obj.optString("kWh_TZ4");
						String kWh_TZ5=obj.optString("kWh_TZ5");
						String kWh_TZ6=obj.optString("kWh_TZ6");
						String kWh_TZ7=obj.optString("kWh_TZ7");
						String kWh_TZ8=obj.optString("kWh_TZ8");
						String kvarhLag=obj.optString("kvarhLag");
						String kvarhLead=obj.optString("kvarhLead");
						String kVAh=obj.optString("kVAh");
						String kVAh_TZ1=obj.optString("kVAh_TZ1");
						String kVAh_TZ2=obj.optString("kVAh_TZ2");
						String kVAh_TZ3=obj.optString("kVAh_TZ3");
						String kVAh_TZ4=obj.optString("kVAh_TZ4");
						String kVAh_TZ5=obj.optString("kVAh_TZ5");
						String kVAh_TZ6=obj.optString("kVAh_TZ6");
						String kVAh_TZ7=obj.optString("kVAh_TZ7");
						String kVAh_TZ8=obj.optString("kVAh_TZ8");
						String Demand_kW=obj.optString("Demand_kW");
						String OccDate_kW=obj.optString("OccDate_kW");
						String kW_TZ1=obj.optString("kW_TZ1");
						String Date_kW_TZ1=obj.optString("Date_kW_TZ1");
						String kW_TZ2=obj.optString("kW_TZ2");
						String Date_kW_TZ2=obj.optString("Date_kW_TZ2");
						String kW_TZ3=obj.optString("kW_TZ3");
						String Date_kW_TZ3=obj.optString("Date_kW_TZ3");
						String kW_TZ4=obj.optString("kW_TZ4");
						String Date_kW_TZ4=obj.optString("Date_kW_TZ4");
						String kW_TZ5=obj.optString("kW_TZ5");
						String Date_kW_TZ5=obj.optString("Date_kW_TZ5");
						String kW_TZ6=obj.optString("kW_TZ6");
						String Date_kW_TZ6=obj.optString("Date_kW_TZ6");
						String kW_TZ7=obj.optString("kW_TZ7");
						String Date_kW_TZ7=obj.optString("Date_kW_TZ7");
						String kW_TZ8=obj.optString("kW_TZ8");
						String Date_kW_TZ8=obj.optString("Date_kW_TZ8");
						String kVA=obj.optString("kVA");
						String Date_kVA=obj.optString("Date_kVA");
						String kVA_TZ1=obj.optString("kVA_TZ1");
						String Date_kVA_TZ1=obj.optString("Date_kVA_TZ1");
						String kVA_TZ2=obj.optString("kVA_TZ2");
						String Date_kVA_TZ2=obj.optString("Date_kVA_TZ2");
						String kVA_TZ3=obj.optString("kVA_TZ3");
						String Date_kVA_TZ3=obj.optString("Date_kVA_TZ3");
						String kVA_TZ4=obj.optString("kVA_TZ4");
						String Date_kVA_TZ4=obj.optString("Date_kVA_TZ4");
						String kVA_TZ5=obj.optString("kVA_TZ5");
						String Date_kVA_TZ5=obj.optString("Date_kVA_TZ5");
						String kVA_TZ6=obj.optString("kVA_TZ6");
						String Date_kVA_TZ6=obj.optString("Date_kVA_TZ6");
						String kVA_TZ7=obj.optString("kVA_TZ7");
						String Date_kVA_TZ7=obj.optString("Date_kVA_TZ7");
						String kVA_TZ8=obj.optString("kVA_TZ8");
						String Date_kVA_TZ8=obj.optString("Date_kVA_TZ8");
						
						AmrBillsEntity entity = new AmrBillsEntity();
						entity.setMyKey(new KeyBills(getString(meterNumber), getTimeStamp(billingDate)));
						entity.setTimeStamp(new Timestamp(new Date().getTime()));
						entity.setDateKva(getString(Date_kVA));
						entity.setDateKvaTz1(getString(Date_kVA_TZ1));
						entity.setDateKvaTz2(getString(Date_kVA_TZ2));
						entity.setDateKvaTz3(getString(Date_kVA_TZ3));
						entity.setDateKvaTz4(getString(Date_kVA_TZ4));
						entity.setDateKvaTz5(getString(Date_kVA_TZ5));
						entity.setDateKvaTz6(getString(Date_kVA_TZ6));
						entity.setDateKvaTz7(getString(Date_kVA_TZ7));
						entity.setDateKvaTz8(getString(Date_kVA_TZ8));
						entity.setDateKwTz1(getString(Date_kW_TZ1));
						entity.setDateKwTz2(getString(Date_kW_TZ2));
						entity.setDateKwTz3(getString(Date_kW_TZ3));
						entity.setDateKwTz4(getString(Date_kW_TZ4));
						entity.setDateKwTz5(getString(Date_kW_TZ5));
						entity.setDateKwTz6(getString(Date_kW_TZ6));
						entity.setDateKwTz7(getString(Date_kW_TZ7));
						entity.setDateKwTz8(getString(Date_kW_TZ8));
						entity.setDemandKw(getDouble(Demand_kW));
						entity.setImei(getString(imei));
						entity.setKva(getDouble(kVA));
						entity.setKvah(getDouble(kVAh));
						entity.setKvahTz1(getDouble(kVAh_TZ1));
						entity.setKvahTz2(getDouble(kVAh_TZ2));
						entity.setKvahTz3(getDouble(kVAh_TZ3));
						entity.setKvahTz4(getDouble(kVAh_TZ4));
						entity.setKvahTz5(getDouble(kVAh_TZ5));
						entity.setKvahTz6(getDouble(kVAh_TZ6));
						entity.setKvahTz7(getDouble(kVAh_TZ7));
						entity.setKvahTz8(getDouble(kVAh_TZ8));
						entity.setKvarhLag(getDouble(kvarhLag));
						entity.setKvarhLead(getDouble(kvarhLead));
						entity.setKvaTz1(getDouble(kVA_TZ1));
						entity.setKvaTz2(getDouble(kVA_TZ2));
						entity.setKvaTz3(getDouble(kVA_TZ3));
						entity.setKvaTz4(getDouble(kVA_TZ4));
						entity.setKvaTz5(getDouble(kVA_TZ5));
						entity.setKvaTz6(getDouble(kVA_TZ6));
						entity.setKvaTz7(getDouble(kVA_TZ7));
						entity.setKvaTz8(getDouble(kVA_TZ8));
						entity.setKwh(getDouble(kWh));
						entity.setKwhTz1(getDouble(kWh_TZ1));
						entity.setKwhTz2(getDouble(kWh_TZ2));
						entity.setKwhTz3(getDouble(kWh_TZ3));
						entity.setKwhTz4(getDouble(kWh_TZ4));
						entity.setKwhTz5(getDouble(kWh_TZ5));
						entity.setKwhTz6(getDouble(kWh_TZ6));
						entity.setKwhTz7(getDouble(kWh_TZ7));
						entity.setKwhTz8(getDouble(kWh_TZ8));
						entity.setMeterId(getString(METER_ID));
						entity.setOccDateKw(getString(OccDate_kW));	
						entity.setServerTime(getTimeStamp(serverTime));
						entity.setSysPfBilling(getDouble(sys_PF_Billing));
						entity.setTransId(getString(transID));
						entity.setKwTz1(getDouble(kW_TZ1));
						entity.setKwTz2(getDouble(kW_TZ2));
						entity.setKwTz3(getDouble(kW_TZ3));
						entity.setKwTz4(getDouble(kW_TZ4));
						entity.setKwTz5(getDouble(kW_TZ5));
						entity.setKwTz6(getDouble(kW_TZ6));
						entity.setKwTz7(getDouble(kW_TZ7));
						entity.setKwTz8(getDouble(kW_TZ8));
						
						if (amrBillsService.customupdateBySchema(entity,"postgresMdas") instanceof AmrBillsEntity) {
							//System.out.println("========================INSERTED BILL");
						}
						/*System.out.println("transID = "+transID);
						System.out.println("METER_ID = "+METER_ID);
						System.out.println("meterNumber = "+meterNumber);
						System.out.println("IMEI = "+imei);
						System.out.println("serverTime = "+serverTime);
						System.out.println("billingDate = "+billingDate);
						System.out.println("sys_PF_Billing = "+sys_PF_Billing);
						System.out.println("kWh = "+kWh);
						System.out.println("kWh_TZ1 = "+kWh_TZ1);
						System.out.println("kWh_TZ2 = "+kWh_TZ2);
						System.out.println("kWh_TZ3 = "+kWh_TZ3);
						System.out.println("kWh_TZ4 = "+kWh_TZ4);
						System.out.println("kWh_TZ5 = "+kWh_TZ5);
						System.out.println("kWh_TZ6 = "+kWh_TZ6);
						System.out.println("kWh_TZ7 = "+kWh_TZ7);
						System.out.println("kWh_TZ8 = "+kWh_TZ8);
						System.out.println("kvarhLag = "+kvarhLag);
						System.out.println("kvarhLead = "+kvarhLead);
						System.out.println("kVAh = "+kVAh);
						System.out.println("kVAh_TZ1 = "+kVAh_TZ1);
						System.out.println("kVAh_TZ2 = "+kVAh_TZ2);
						System.out.println("kVAh_TZ3 = "+kVAh_TZ3);
						System.out.println("kVAh_TZ4 = "+kVAh_TZ4);
						System.out.println("kVAh_TZ5 = "+kVAh_TZ5);
						System.out.println("kVAh_TZ6 = "+kVAh_TZ6);
						System.out.println("kVAh_TZ7 = "+kVAh_TZ7);
						System.out.println("kVAh_TZ8 = "+kVAh_TZ8);
						System.out.println("Demand_kW = "+Demand_kW);
						System.out.println("OccDate_kW = "+OccDate_kW);
						System.out.println("kW_TZ1 = "+kW_TZ1);
						System.out.println("Date_kW_TZ1 = "+Date_kW_TZ1);
						System.out.println("kW_TZ2 = "+kW_TZ2);
						System.out.println("Date_kW_TZ2 = "+Date_kW_TZ2);
						System.out.println("kW_TZ3 = "+kW_TZ3);
						System.out.println("Date_kW_TZ3 = "+Date_kW_TZ3);
						System.out.println("kW_TZ4 = "+kW_TZ4);
						System.out.println("Date_kW_TZ4 = "+Date_kW_TZ4);
						System.out.println("kW_TZ5 = "+kW_TZ5);
						System.out.println("Date_kW_TZ5 = "+Date_kW_TZ5);
						System.out.println("kW_TZ6 = "+kW_TZ6);
						System.out.println("Date_kW_TZ6 = "+Date_kW_TZ6);
						System.out.println("kW_TZ7 = "+kW_TZ7);
						System.out.println("Date_kW_TZ7 = "+Date_kW_TZ7);
						System.out.println("kW_TZ8 = "+kW_TZ8);
						System.out.println("Date_kW_TZ8 = "+Date_kW_TZ8);
						System.out.println("kVA = "+kVA);
						System.out.println("Date_kVA = "+Date_kVA);
						System.out.println("kVA_TZ1 = "+kVA_TZ1);
						System.out.println("Date_kVA_TZ1 = "+Date_kVA_TZ1);
						System.out.println("kVA_TZ2 = "+kVA_TZ2);
						System.out.println("Date_kVA_TZ2 = "+Date_kVA_TZ2);
						System.out.println("kVA_TZ3 = "+kVA_TZ3);
						System.out.println("Date_kVA_TZ3 = "+Date_kVA_TZ3);
						System.out.println("kVA_TZ4 = "+kVA_TZ4);
						System.out.println("Date_kVA_TZ4 = "+Date_kVA_TZ4);
						System.out.println("kVA_TZ5 = "+kVA_TZ5);
						System.out.println("Date_kVA_TZ5 = "+Date_kVA_TZ5);
						System.out.println("kVA_TZ6 = "+kVA_TZ6);
						System.out.println("Date_kVA_TZ6 = "+Date_kVA_TZ6);
						System.out.println("kVA_TZ7 = "+kVA_TZ7);
						System.out.println("Date_kVA_TZ7 = "+Date_kVA_TZ7);
						System.out.println("kVA_TZ8 = "+kVA_TZ8);
						System.out.println("Date_kVA_TZ8 = "+Date_kVA_TZ8);*/
						
						try{
							SimpleDateFormat sdfToday=new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sdfMonth=new SimpleDateFormat("yyyyMM");
							SimpleDateFormat sdfBillDate = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");//billingDate":"08/Sep/2017 12:37:27 PM
							Date billdate=sdfBillDate.parse(billingDate);
							
							String xmlDate=sdfToday.format(new Date());
							String month=sdfMonth.format(new Date());
							String billDatechk=sdfToday.format(billdate);
							String flag="3";
							
							if(billDatechk.equalsIgnoreCase(xmlDate)){
								
								meterMasterService.updateBillDataTOMM(month, meterNumber, kWh, kVAh, kVA, sys_PF_Billing,
										 xmlDate, kWh_TZ1, kWh_TZ2, kWh_TZ3, kWh_TZ4, kWh_TZ5, kWh_TZ6, kWh_TZ7, kWh_TZ8, kVAh_TZ1, kVAh_TZ2, kVAh_TZ3, kVAh_TZ4, kVAh_TZ5, 
										kVAh_TZ6, kVAh_TZ7, kVAh_TZ8, kVA_TZ1, kVA_TZ1, kVA_TZ2, kVA_TZ3, kVA_TZ4, kVA_TZ5, kVA_TZ6, kVA_TZ7, Demand_kW, flag,billDatechk);
							}
						} catch(Exception e){
							
						}
						
					}
					
					if(imei!=null){
						new UpdateCommunication(filterConfig,imei, meterNumber, topic, null).start();//TODO
					}
					
					if(imei!=null){
						new UpdateMasterMain(filterConfig, imei, meterNumber).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
