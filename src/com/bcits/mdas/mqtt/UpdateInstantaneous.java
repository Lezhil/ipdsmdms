package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.FilterConfig;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity.KeyInstantaneous;
import com.bcits.mdas.service.AmrInstantaneousService;

class UpdateInstantaneous extends MasterThread {
	
	@Autowired
	private AmrInstantaneousService instantaneousService;

	UpdateInstantaneous(FilterConfig filterConfig, String topic, MqttMessage data) {
		super(filterConfig, topic, data);
	}

	@Override
	public void run() {
		super.run();

		//{"iRAngle":0.0,"iYAngle":0.0,"iBAngle":0.0,"KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"07/Sep/2017 18:50:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.019999999552965164,"Iy":0.0,"Ib":0.019999999552965164,"PFr":1.0,"PFy":1.0,"PFb":1.0,"KWH":26.0,"KVAH":26.0,"TransID":null,"KVAH_Imp":0.0,"ReadTime":"07/Sep/2017 18:49:39 PM","Vrn":230.97801208496094,"Vyn":3.6660001277923584,"Vbn":3.7110002040863037,"ThreePhasePF":1.0,"Frequency":50.01000213623047,"KVA":0.004999999888241291,"Power_KW":0.004000000189989805,"KVAR":0.003000000026077032,"PowerOffCount":174,"TamperCount":97,"MDResetCount":69,"ProgrammCount":0,"MDResetDate":"01/Sep/2017 00:00:00 AM","MDKW":0.0,"Date_MDKW":null,"MDKVA":0.0,"Date_MDKVA":null,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"PowerOffDuration":24565,"KVAH_Exp":0.0}
				try {
				//	System.out.println("INSTANTANEOUS=======");
					JSONObject obj = new JSONObject(data.toString());
					String iRAngle=obj.optString("iRAngle");
					String iYAngle=obj.optString("iYAngle");
					String iBAngle=obj.optString("iBAngle");
					String KWH_Exp=obj.optString("KWH_Exp");
					String KWH_Imp=obj.optString("KWH_Imp");
					String ModemTime=obj.optString("ModemTime");
					String MeterNumber=obj.optString("MeterNumber");
					String Imei=obj.optString("Imei");
					String Ir=obj.optString("Ir");
					String Iy=obj.optString("Iy");
					String Ib=obj.optString("Ib");
					String PFr=obj.optString("PFr");
					String PFy=obj.optString("PFy");
					String PFb=obj.optString("PFb");
					String KWH=obj.optString("KWH");
					String KVAH=obj.optString("KVAH");
					String TransID=obj.optString("TransID");
					String KVAH_Imp=obj.optString("KVAH_Imp");
					String ReadTime=obj.optString("ReadTime");
					String Vrn=obj.optString("Vrn");
					String Vyn=obj.optString("Vyn");
					String Vbn=obj.optString("Vbn");
					String ThreePhasePF=obj.optString("ThreePhasePF");
					String Frequency=obj.optString("Frequency");
					String KVA=obj.optString("KVA");
					String Power_KW=obj.optString("Power_KW");
					String KVAR=obj.optString("KVAR");
					String PowerOffCount=obj.optString("PowerOffCount");
					String TamperCount=obj.optString("TamperCount");
					String MDResetCount=obj.optString("MDResetCount");
					String ProgrammCount=obj.optString("ProgrammCount");
					String MDResetDate=obj.optString("MDResetDate");
					String MDKW=obj.optString("MDKW");
					String Date_MDKW=obj.optString("Date_MDKW");
					String MDKVA=obj.optString("MDKVA");
					String Date_MDKVA=obj.optString("Date_MDKVA");
					String KVARH_Lag=obj.optString("KVARH_Lag");
					String KVARH_Lead=obj.optString("KVARH_Lead");
					String PowerOffDuration=obj.optString("PowerOffDuration");
					String KVAH_Exp=obj.optString("KVAH_Exp");
					
					/*System.out.println("iRAngle = "+iRAngle);
					System.out.println("iYAngle = "+iYAngle);
					System.out.println("iBAngle = "+iBAngle);
					System.out.println("KWH_Exp = "+KWH_Exp);
					System.out.println("KWH_Imp = "+KWH_Imp);
					System.out.println("ModemTime = "+ModemTime);
					System.out.println("MeterNumber = "+MeterNumber);
					System.out.println("Imei = "+Imei);
					System.out.println("Iy = "+Iy);
					System.out.println("Ir = "+Ir);
					System.out.println("Ib = "+Ib);
					System.out.println("PFr = "+PFr);
					System.out.println("PFy = "+PFy);
					System.out.println("PFb = "+PFb);
					System.out.println("KWH = "+KWH);
					System.out.println("KVAH = "+KVAH);
					System.out.println("TransID = "+TransID);
					System.out.println("KVAH_Imp = "+KVAH_Imp);
					System.out.println("ReadTime = "+ReadTime);
					System.out.println("Vrn = "+Vrn);
					System.out.println("Vyn = "+Vyn);
					System.out.println("Vbn = "+Vbn);
					System.out.println("ThreePhasePF = "+ThreePhasePF);
					System.out.println("Frequency = "+Frequency);
					System.out.println("KVA = "+KVA);
					System.out.println("Power_KW = "+Power_KW);
					System.out.println("KVAR = "+KVAR);
					System.out.println("PowerOffCount = "+PowerOffCount);
					System.out.println("TamperCount = "+TamperCount);
					System.out.println("MDResetCount = "+MDResetCount);
					System.out.println("ProgrammCount = "+ProgrammCount);
					System.out.println("MDResetDate = "+MDResetDate);
					System.out.println("MDKW = "+MDKW);
					System.out.println("Date_MDKW = "+Date_MDKW);
					System.out.println("MDKVA = "+MDKVA);
					System.out.println("Date_MDKVA = "+Date_MDKVA);
					System.out.println("KVARH_Lag = "+KVARH_Lag);
					System.out.println("KVARH_Lead = "+KVARH_Lead);
					System.out.println("PowerOffDuration = "+PowerOffDuration);
					System.out.println("KVAH_Exp = "+KVAH_Exp);*/
					
					new UpdateCommunication(filterConfig,Imei, MeterNumber, topic, null).start();//TODO
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

					AmrInstantaneousEntity entity = new AmrInstantaneousEntity();
					entity.setMyKey(new KeyInstantaneous(MeterNumber, dateFormat.parse(dateFormat.format(new Date()))));
					entity.setTimeStamp(new Timestamp(new Date().getTime()));
					entity.setDateMdKva(getString(Date_MDKVA));
					entity.setDateMdKw(getString(Date_MDKW));
					entity.setFrequency(getDouble(Frequency));
					entity.setiB(getDouble(Ib));
					entity.setiBAngle(getDouble(iBAngle));
					entity.setImei(getString(Imei));
					entity.setiR(getDouble(Ir));
					entity.setiRAngle(getDouble(iRAngle));
					entity.setiY(getDouble(Iy));
					entity.setiYAngle(getDouble(iYAngle));
					entity.setKva(getDouble(KVA));
					entity.setKvah(getDouble(KVAH));
					entity.setKvahExp(getDouble(KVAH_Exp));
					entity.setKvahImp(getDouble(KVAH_Imp));
					entity.setKvar(getDouble(KVAR));
					entity.setKvarhLag(getDouble(KVARH_Lag));
					entity.setKvarhLead(getDouble(KVARH_Lead));
					entity.setKwh(getDouble(KWH));
					entity.setKwhExp(getDouble(KWH_Exp));
					entity.setKwhImp(getDouble(KWH_Imp));
					entity.setMdKva(getDouble(MDKVA));
					entity.setMdKw(getDouble(MDKW));
					entity.setMdResetCount(getInteger(MDResetCount));
					entity.setMdResetDate(getString(MDResetDate));
					entity.setModemTime(getTimeStamp(ModemTime));
					entity.setPfB(getDouble(PFb));
					entity.setPfR(getDouble(PFr));
					entity.setPfThreephase(getDouble(ThreePhasePF));
					entity.setPfY(getDouble(PFy));
					entity.setPowerKw(getDouble(Power_KW));
					entity.setPowerOffCount(getInteger(PowerOffCount));
					entity.setPowerOffDuration(getInteger(PowerOffDuration));
					entity.setProgrammingCount(getInteger(ProgrammCount));
					entity.setReadTime(getTimeStamp(ReadTime));
					entity.setTamperCount(getInteger(TamperCount));
					entity.setTransId(getString(TransID));
					entity.setvB(getDouble(Vbn));
					entity.setvR(getDouble(Vrn));
					entity.setvY(getDouble(Vyn));

					if(instantaneousService.customupdateBySchema(entity,"postgresMdas") instanceof AmrInstantaneousEntity){
						//System.out.println("UPDATED INSTATNTANEOUS=========== ");
					}
					if(Imei !=null){
						new UpdateMasterMain(filterConfig, Imei, MeterNumber).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
	}

}
