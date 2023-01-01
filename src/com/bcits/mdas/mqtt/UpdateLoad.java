package com.bcits.mdas.mqtt;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.FilterConfig;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.AmrLoadEntity.KeyLoad;
import com.bcits.mdas.service.AmrLoadService;

class UpdateLoad extends MasterThread {
	
	@Autowired
	private AmrLoadService amrLoadService;

	UpdateLoad(FilterConfig filterConfig, String topic, MqttMessage data) {
		super(filterConfig, topic, data);
	}

	@Override
	public void run() {
		super.run();

		//[{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 07:45:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.4499969482422,"Vy":3.68999981880188,"Vb":3.6999998092651367,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 08:00:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.489990234375,"Vy":3.669999837875366,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 08:15:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.489990234375,"Vy":3.6399998664855957,"Vb":3.6399998664855957,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 08:30:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.33999633789062,"Vy":3.669999837875366,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 08:45:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.51998901367188,"Vy":3.68999981880188,"Vb":3.6999998092651367,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 09:00:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.72000122070312,"Vy":3.6499998569488525,"Vb":3.6499998569488525,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 09:15:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.489990234375,"Vy":3.669999837875366,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 09:30:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.4499969482422,"Vy":3.679999828338623,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 09:45:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.3199920654297,"Vy":3.669999837875366,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 10:00:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":231.14999389648438,"Vy":3.68999981880188,"Vb":3.6999998092651367,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 10:15:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.88999938964844,"Vy":3.68999981880188,"Vb":3.68999981880188,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 10:30:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.8300018310547,"Vy":3.669999837875366,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 10:45:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.8199920654297,"Vy":3.669999837875366,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 11:00:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.79998779296875,"Vy":3.679999828338623,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 11:15:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.77999877929688,"Vy":3.669999837875366,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 11:30:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.739990234375,"Vy":3.679999828338623,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 11:45:00 AM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.76998901367188,"Vy":3.68999981880188,"Vb":3.68999981880188,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 12:00:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.75,"Vy":3.6599998474121094,"Vb":3.6599998474121094,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 12:15:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.72000122070312,"Vy":3.6599998474121094,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 12:30:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.63999938964844,"Vy":3.6599998474121094,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 12:45:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.72999572753906,"Vy":3.669999837875366,"Vb":3.669999837875366,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 13:00:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.76998901367188,"Vy":3.679999828338623,"Vb":3.68999981880188,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 13:15:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.77999877929688,"Vy":3.679999828338623,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0},{"transID":null,"structureSize":11,"TimeStamp":"08/Sep/2017 13:30:00 PM","KWH_Exp":0.0,"KWH_Imp":0.0,"ModemTime":"08/Sep/2017 13:43:00 PM","MeterNumber":"AP910990","Imei":"865733020001137","Ir":0.0,"Iy":0.0,"Ib":0.0,"Vr":230.6999969482422,"Vy":3.679999828338623,"Vb":3.679999828338623,"KWH":0.0,"KVAH":0.0,"KVARH_Q1":0.0,"KVARH_Q3":0.0,"KVARH_Q4":0.0,"KVARH_Q2":0.0,"KVARH_Lag":0.0,"KVARH_Lead":0.0,"Frequnecy":0.0,"NetKWH":0.0}]
				 try {
						String imei = null, meterNumber= null;
						JSONArray recs =  new JSONArray(data.toString());
						for (int i = 0; i < recs.length(); i++) {
							JSONObject obj = recs.getJSONObject(i);
							//System.out.println("==================================== PARSE LOADSURVEY"+i); 
							String transID=obj.optString("transID");
							String structureSize=obj.optString("structureSize");
							String readTime=obj.optString("TimeStamp");
							String KWH_Exp=obj.optString("KWH_Exp");
							String KWH_Imp=obj.optString("KWH_Imp");
							String ModemTime=obj.optString("ModemTime");
							meterNumber=obj.optString("MeterNumber");
							imei=obj.optString("Imei");
							String Ir=obj.optString("Ir");
							String Iy=obj.optString("Iy");
							String Ib=obj.optString("Ib");
							String Vr=obj.optString("Vr");
							String Vy=obj.optString("Vy");
							String Vb=obj.optString("Vb");
							String KWH=obj.optString("KWH");
							String KVAH=obj.optString("KVAH");
							String KVARH_Q1=obj.optString("KVARH_Q1");
							String KVARH_Q3=obj.optString("KVARH_Q3");
							String KVARH_Q4=obj.optString("KVARH_Q4");
							String KVARH_Q2=obj.optString("KVARH_Q2");
							String KVARH_Lag=obj.optString("KVARH_Lag");
							String KVARH_Lead=obj.optString("KVARH_Lead");
							String Frequnecy=obj.optString("Frequnecy");
							String NetKWH=obj.optString("NetKWH");

							AmrLoadEntity entity = new AmrLoadEntity();
							entity.setTimeStamp(new Timestamp(new Date().getTime()));
							entity.setMyKey(new KeyLoad(getString(meterNumber), getTimeStamp(readTime)));
							entity.setFrequency(getDouble(Frequnecy));
							entity.setiB(getDouble(Ib));
							entity.setImei(getString(imei));
							entity.setiR(getDouble(Ir));
							entity.setiY(getDouble(Iy));
							entity.setKvah(getDouble(KVAH));
							entity.setKvarhLag(getDouble(KVARH_Lag));
							entity.setKvarhLead(getDouble(KVARH_Lead));
							entity.setKvarhQ1(getDouble(KVARH_Q1));
							entity.setKvarhQ2(getDouble(KVARH_Q2));
							entity.setKvarhQ3(getDouble(KVARH_Q3));
							entity.setKvarhQ4(getDouble(KVARH_Q4));
							entity.setKwh(getDouble(KWH));
							entity.setKwhExp(getDouble(KWH_Exp));
							entity.setKwhImp(getDouble(KWH_Imp));
							entity.setModemTime(getTimeStamp(ModemTime));
							entity.setNetKwh(getDouble(NetKWH));
							entity.setStructureSize(getInteger(structureSize));
							entity.setTransId(getString(transID));
							entity.setvB(getDouble(Vb));
							entity.setvR(getDouble(Vr));
							entity.setvY(getDouble(Vy));

						if (amrLoadService.customupdateBySchema(entity,"postgresMdas") instanceof AmrLoadEntity) {
							//System.out.println("========================INSERTED LOAD");
						}
							
							/*System.out.println("transID = "+transID);
							System.out.println("structureSize = "+structureSize);
							System.out.println("TimeStamp = "+readTime);
							System.out.println("KWH_Exp = "+KWH_Exp);
							System.out.println("KWH_Imp = "+KWH_Imp);
							System.out.println("ModemTime"+ModemTime);
							System.out.println("MeterNumber = "+meterNumber);
							System.out.println("Imei = "+imei);
							System.out.println("Ir = "+Ir);
							System.out.println("Iy"+Iy);
							System.out.println("Ib = "+Ib);
							System.out.println("Vr = "+Vr);
							System.out.println("Vy = "+Vy);
							System.out.println("Vb = "+Vb);
							System.out.println("KWH = "+KWH);
							System.out.println("KVAH = "+KVAH);
							System.out.println("KVARH_Q1 = "+KVARH_Q1);
							System.out.println("KVARH_Q3 = "+KVARH_Q3);
							System.out.println("KVARH_Q4 = "+KVARH_Q4);
							System.out.println("KVARH_Q2 = "+KVARH_Q2);
							System.out.println("KVARH_Lag = "+KVARH_Lag);
							System.out.println("KVARH_Lead = "+KVARH_Lead);
							System.out.println("Frequnecy = "+Frequnecy);
							System.out.println("NetKWH = "+NetKWH);*/
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
