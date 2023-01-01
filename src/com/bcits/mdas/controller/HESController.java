package com.bcits.mdas.controller;




import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.utility.FilterUnit;

@Controller
public class HESController {
	
	//public static final String hesurl_jvvnl71 = "https://jvvnl-71mdms.dev.cyanconnode.com/sma/dev";
	//public static final String hesurl = "https://ugvcl-omni-demo.se.cyco.io/sma/ws";
	//public static final String hesurl = "https://jvvnl-71mdms.dev.cyanconnode.com/sma/ws";
	public static final String hesurl = "https://jvvnl-tnd1.dev.cyanconnode.com/sma/ws";
	
	//public static final String hesurl = "https://lntmdmsint.dev.cyanconnode.com/sma/ws";
	
	//public static final String hesurl = "https://100.65.196.7/sma/ws";
	public static final String hesuser = "sysadmin";
	public static final String hespass = "sysadmin";
	public static final String authString = hesuser + ":" + hespass;
	static Base64 b=new Base64();
	public static final String authStringenc = b.encodeToString(authString.getBytes());

	
	
	
	@RequestMapping(value = "/jobs", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String jobs() {
		String url = hesurl + "/jobs";
		try {
			
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", 200);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/*@RequestMapping(value = "/nodeMetersInfo/{nodeId}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String nodeMetersInfo(@PathVariable String nodeId) {
		String url = hesurl + "/nodeMetersInfo";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("nodeId", nodeId);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}*/
	
	
	//ON DEMAND PROFILE
	@RequestMapping(value = "/onDemandProfile/{profileType}/{meterId}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String onDemandProfile(@PathVariable String profileType,@PathVariable String meterId) {
		
		
		String url = hesurl + "/onDemandProfile/"+meterId;
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			//System.out.println("URL ----> " +url);
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);
			
			
			String OBISCode			=	"";
			
			
			 
			
			String sampleStartTime	=	"2018-10-16T10:00:00+0530";
			String sampleStopTime	=	"2018-10-16T12:00:00+0530";
			//System.out.println(profileType);
			if("BILLING".equalsIgnoreCase(profileType)){
				//meter No : UST1000004
				 sampleStartTime =  "2018-10-01T00:00:00+0530";
				  sampleStopTime =  "2018-10-30T00:00:00+0530";
				OBISCode="1.0.98.1.0.255";
			}
			if("BULKLOAD".equalsIgnoreCase(profileType)){
				//meter No : UST1000004
				
				OBISCode="1.0.99.1.0.255";
				
			}
			if("DAILYLOAD".equalsIgnoreCase(profileType)){
				//meter No : UST1000004
				OBISCode="1.0.99.2.0.255";
				 sampleStartTime = "2018-10-15T00:00:00+0530";
				  sampleStopTime = "2018-10-16T00:00:00+0530";
			}
			if("CONTROLEDEVENTLOG".equalsIgnoreCase(profileType)){	
				OBISCode="0.0.99.98.6.255";
			}
			if("CURRENTEVENTLOG".equalsIgnoreCase(profileType)){
				//meter No : UST1000004
				OBISCode="0.0.99.98.1.255";
				 sampleStartTime = "2018-09-08T00:00:00+0530";
				 sampleStopTime = "2018-09-09T00:00:00+0530";
			}
			if("INSTANTANEOUS".equalsIgnoreCase(profileType)){
				//meter No :UST1000004
				 OBISCode="1.0.94.91.0.255";
				 sampleStartTime = "2018-10-15T00:00:00+0530";
				 sampleStopTime = "2018-10-16T00:00:00+0530";
			}
			if("NAMEPLATE".equalsIgnoreCase(profileType)){
				//meter No :UST1000004
				OBISCode="0.0.94.91.10.255";
				sampleStartTime = "2018-10-15T00:00:00+0530";
				 sampleStopTime = "2018-10-16T00:00:00+0530";
			}
			if("VOLTAGEEVENTLOG".equalsIgnoreCase(profileType)){
				//meter No :UST1000004
				OBISCode="0.0.99.98.0.255";
				sampleStartTime = "2018-08-04T00:00:00+0530";
				 sampleStopTime = "2018-08-05T00:00:00+0530";
			}
			
			//Following below are nnot available in Ranga reddy Docs
			if("POWEREVENTLOG".equalsIgnoreCase(profileType)){
				OBISCode="0.0.99.98.2.255";
			}
			if("TRANSACTIONEVENTLOG".equalsIgnoreCase(profileType)){
				OBISCode="0.0.99.98.3.255";
			}
			if("NONROLLOVEREVENTLOG".equalsIgnoreCase(profileType)){
				OBISCode="0.0.99.98.5.255";
			}
			if("OTHEREVENTLOG".equalsIgnoreCase(profileType)){
				OBISCode="0.0.99.98.4.255";
			}

			
			JSONObject obj = new JSONObject();
			obj.put("formattedProfileObisCode", OBISCode);
			obj.put("sampleStartTime", sampleStartTime);
			obj.put("sampleStopTime" , sampleStopTime);
				  
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			//System.out.println("Status Line " +response.getStatusLine());
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	@RequestMapping(value = "/queryMeterGroup/{groupName}", method = {RequestMethod.GET })
	public @ResponseBody String queryMeterGroup(@PathVariable String groupName) {
		//System.out.println(groupName);
		String url = hesurl + "/queryMeterGroup/"+groupName;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			//System.out.println("URL ----> " +url);
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);
			
			JSONObject obj = new JSONObject();
				
			obj.put("count" , "5");
			obj.put("startId" , "10");
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			//System.out.println("Status Line " +response.getStatusLine());
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	@RequestMapping(value = "/queryMeterJob/{jobName}", method = {RequestMethod.GET })
	public @ResponseBody String queryMeterJob(@PathVariable String jobName) {
		//System.out.println(jobName);
		String url = hesurl + "/queryMeterJob/"+jobName;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

		
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	@RequestMapping(value = "/nodeMetersInfo/{nodeId}", method = {RequestMethod.GET })
	public @ResponseBody String nodeMetersInfo(@PathVariable String nodeId) {
		//System.out.println(nodeId);
		String url = hesurl + "/nodeMetersInfo/"+nodeId;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

		
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	@RequestMapping(value = "/searchMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String searchMeters() {
		String url = hesurl + "/searchMeters";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("Accept", "*/*");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@RequestMapping(value = "/searchMetersByNode", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String searchMetersByNode() {
		String url = hesurl + "/searchMeters";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("nodeId", "00-1b-c5-0c-60-00-d2-a7");
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	@RequestMapping(value = "/meterGroups", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String meterGroups() {
		String url = hesurl + "/meterGroups";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
				
			
			obj.put("count", "10");
			obj.put("sortDirection", "asc");
			obj.put("sortOrder", "ID");
			obj.put("startId", "1");
			obj.put("type", "STATIC");
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	/*@RequestMapping(value = "/searchMetersByMeterID", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String searchMetersByMeterID() {
		String url = hesurl + "/searchMeters";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("meterId", "USS%20000161");
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}*/
	
	@RequestMapping(value = "/searchMetersByMeterID/{meter_id}", method = {RequestMethod.POST, RequestMethod.GET  })
	public @ResponseBody String searchMetersByMeterID(@PathVariable String meter_id) {
		meter_id=meter_id.replace( "%20" ," ");
		//System.out.println(meter_id);
		String url = hesurl + "/searchMeters";
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

		
			JSONObject obj = new JSONObject();
			obj.put("meterId", meter_id);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/searchMetersByVendor", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String searchMetersByVendor() {
		String url = hesurl + "/searchMeters";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("vendor", "GENUS POWER INFRASTRUCTURES LTD");
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@RequestMapping(value = "/applicationEvents", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String applicationEvents() {
		String url = hesurl + "/applicationEvents";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", 18);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);
			//System.out.println("res: " + res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/applicationEvents/{meter_id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String applicationEvents(@PathVariable String meter_id) {
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		String url = hesurl + "/applicationEvents/"+meter_id;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", 100);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/countMeters", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String countMeters() {
		String url = hesurl + "/countMeters";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("itemCount", 10000);
			obj.put("itemStart", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/meterInfo/{meter_id}", method = {RequestMethod.GET })
	public @ResponseBody String meterInfo(@PathVariable String meter_id) {
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		String url = hesurl + "/meterInfo/"+meter_id;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			/*JSONObject obj = new JSONObject();
			obj.put("count", 100);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);*/

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/meterPing/{meter_id}", method = {RequestMethod.GET })
	public @ResponseBody String meterPing(@PathVariable String meter_id) {
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		String url = hesurl + "/meterPing/"+meter_id;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			/*JSONObject obj = new JSONObject();
			obj.put("count", 100);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);*/

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/meterProfiles/{meter_id}", method = {RequestMethod.GET })
	public @ResponseBody String meterProfiles(@PathVariable String meter_id) {
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		String url = hesurl + "/meterProfiles/"+meter_id;
		//System.out.println(url);
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			/*JSONObject obj = new JSONObject();
			obj.put("count", 100);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);*/

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/meterSamples", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String meterSamples() {
		String url = hesurl + "/meterSamples";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", 20);
			obj.put("startId", 1);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/meterAlarms ", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	static String meterAlarms (int sid,int count) {
		String url = hesurl + "/meterAlarms";
		try {
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", count);
			obj.put("startId", sid);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	//powerSwitch/{meterId}
	@RequestMapping(value="/powerSwitch/{meter_id}",method={RequestMethod.GET})
	public @ResponseBody String powerSwitch(@PathVariable String meter_id){
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		String url =hesurl +"/powerSwitch/"+meter_id;
		//String url =hesurl +"/powerSwitch/"+meter_id;
		//System.out.println(url);
		try {
			
			HttpClient httpclient=createHttpClient_AcceptsUntrustedCerts();
			HttpGet httpRequest= new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);
			
			HttpResponse response=httpclient.execute(httpRequest);
			String res=new BasicResponseHandler().handleResponse(response);
			
			//System.out.println("res: " + res);
			
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@RequestMapping(value="/powerSwitchAction/{meter_id}/{action}",method={RequestMethod.POST,RequestMethod.PUT})
	public @ResponseBody String powerSwitchAction(@PathVariable String meter_id,@PathVariable String action){
		meter_id=meter_id.replace(" ", "%2");
		//System.out.println(meter_id);
		//String url =hesurl_jvvnl71 +"/powerSwitch/"+meter_id;
		String url =hesurl +"/powerSwitch/"+meter_id;
		//System.out.println(url);
		try {
			/*int timeout = 25;
			RequestConfig requestConfig = RequestConfig.custom()
			  .setConnectTimeout(timeout * 1000)
			  .setConnectionRequestTimeout(timeout * 1000)
			  .setSocketTimeout(timeout * 1000).build();.setDefaultRequestConfig(requestConfig)*/
			//HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
			HttpPut httpRequest = new HttpPut(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + authStringenc);
			
			  
			StringEntity  body = null;
			if (action.equalsIgnoreCase("connect")) {
				 body = new StringEntity("\"ENABLED_POWER_ON\"\n");	
			}else {
				body =  new StringEntity("\"DISABLED_POWER_OFF\"\n");	
			}
			
			httpRequest.setEntity(body);
			
			HttpResponse response = httpClient.execute(httpRequest);
			
			String res = new BasicResponseHandler().handleResponse(response);

			//System.out.println("res: " + res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}
	

	//ON DEMAND PROFILE -Srinubabu
		
		public  String onDemandProfileAMI( String profileType, String meterId,String sstartt,String sstopt) {
			
			
			String url = hesurl + "/onDemandProfile/"+meterId;
			try {
				HttpClient httpClient =createHttpClient_AcceptsUntrustedCerts();
				//HttpClient httpClient = HttpClientBuilder.create().build();
				//System.out.println("URL ----> " +url);
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setHeader("Content-Type", "application/json");
				httpRequest.setHeader("Authorization", "Basic " + authStringenc);
				
				
				String OBISCode			=	"";
				
				
				
				// String[] sst=sstartt.split("-");
				// sstartt=sst[2]+"-"+sst[1]+"-"+sst[0];
				sstartt=sstartt.replace(" ", "");
				// String[] spt=sstopt.split("-");
				// sstopt=spt[2]+"-"+spt[1]+"-"+spt[0];
				sstopt=sstopt.replace(" ", "");
				String sampleStartTime	=	"2018-10-16T10:00:00+0530";
				String sampleStopTime	=	"2018-10-16T12:00:00+0530";
				//System.out.println(profileType);
				if("BILLING".equalsIgnoreCase(profileType)){
					//meter No : UST1000004
					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
					OBISCode="1.0.98.1.0.255";
				}
				if("BULKLOAD".equalsIgnoreCase(profileType)){
					//meter No : UST1000004
					
					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
					OBISCode="1.0.99.1.0.255";
					
				}
				if("DAILYLOAD".equalsIgnoreCase(profileType)){
					//meter No : UST1000004
					OBISCode="1.0.99.2.0.255";
					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("CONTROLEDEVENTLOG".equalsIgnoreCase(profileType)){	
					OBISCode="0.0.99.98.6.255";

					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;

					 sampleStartTime = sstartt;
					 sampleStopTime = sstopt;

					
				}
				if("CURRENTEVENTLOG".equalsIgnoreCase(profileType)){
					//meter No : UST1000004
					OBISCode="0.0.99.98.1.255";
					 sampleStartTime = "2018-09-08T00:00:00+0530";
					 sampleStopTime = "2018-09-09T00:00:00+0530";
				}
				if("INSTANTANEOUS".equalsIgnoreCase(profileType)){
					//meter No :UST1000004
					 OBISCode="1.0.94.91.0.255";
					 sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("NAMEPLATE".equalsIgnoreCase(profileType)){
					//meter No :UST1000004
					OBISCode="0.0.94.91.10.255";
					 sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("VOLTAGEEVENTLOG".equalsIgnoreCase(profileType)){
					//meter No :UST1000004
					OBISCode="0.0.99.98.0.255";
					sampleStartTime = "2018-08-04T00:00:00+0530";
					 sampleStopTime = "2018-08-05T00:00:00+0530";
				}
				
				//Following below are nnot available in Ranga reddy Docs
				if("POWEREVENTLOG".equalsIgnoreCase(profileType)){
					OBISCode="0.0.99.98.2.255";
					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("TRANSACTIONEVENTLOG".equalsIgnoreCase(profileType)){
					OBISCode="0.0.99.98.3.255";
					 sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("NONROLLOVEREVENTLOG".equalsIgnoreCase(profileType)){
					OBISCode="0.0.99.98.5.255";
					 sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}
				if("OTHEREVENTLOG".equalsIgnoreCase(profileType)){
					OBISCode="0.0.99.98.4.255";
					sampleStartTime = sstartt;
					 sampleStopTime = sstopt;
				}

				
				JSONObject obj = new JSONObject();
				obj.put("formattedProfileObisCode", OBISCode);
				obj.put("sampleStartTime", sampleStartTime);
				obj.put("sampleStopTime" , sampleStopTime);
					  
				StringEntity body = new StringEntity(obj.toString());
				httpRequest.setEntity(body);

				HttpResponse response = httpClient.execute(httpRequest);
				//System.out.println("Status Line " +response.getStatusLine());
				String res = new BasicResponseHandler().handleResponse(response);

				////System.out.println("res: " + res);
				
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		
		
		public static HttpClient createHttpClient_AcceptsUntrustedCerts() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
			/*  __________ Reference :  http://literatejava.com/networks/ignore-ssl-certificate-errors-apache-httpclient-4-4/#comment-100861 __________  */	    
				HttpClientBuilder b = HttpClientBuilder.create();
			    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			            return true;
			        }
			    }).build();
			    b.setSslcontext( sslContext);
			    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, (X509HostnameVerifier) hostnameVerifier);
			    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			            .register("http", PlainConnectionSocketFactory.getSocketFactory())
			            .register("https", sslSocketFactory)
			            .build();
			    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
			    b.setConnectionManager( connMgr);
			    HttpClient client = b.build();
			    return client;
			}
		
		
		
		@RequestMapping(value = "/onDemandProfile_TNEB/{profileType}/{hesType}/{meterNo}/{fromdate}/{todate}", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody String onDemandProfile_TNEB(
				@PathVariable String profileType,
				@PathVariable String hesType,
				@PathVariable String meterNo,
				@PathVariable String fromdate,
				@PathVariable String todate
				
				) {
			
			String url="";
			String result="";
			try {
			JSONObject obj = new JSONObject();
			
			obj.put("MeterNumber" , meterNo);	
			if("GENUS".equalsIgnoreCase(hesType)) {
				 url = FilterUnit.genusOnDemandUrl;
				 obj.put("TransId" ,numbGen());
				 obj.put("ClientId", "1");
				 obj.put("ClientSecret", "nPt2vbEOj5C4YbdfywuYC2135bg");
				 obj.put("FromDate" , fromdate);
				 obj.put("ToDate" , todate);
				 if("INSTANTANEOUS".equalsIgnoreCase(profileType)){
						obj.put("OnDemandType" , "instant");
					}
					if("BILLING".equalsIgnoreCase(profileType)){
						obj.put("OnDemandType" , "bill");
					}
					if("BULKLOAD".equalsIgnoreCase(profileType)){
						obj.put("OnDemandType" , "load survey");	
					}
					if("EVENTLOGS".equalsIgnoreCase(profileType)){	
						obj.put("OnDemandType" , "event");
					}
					
					try {
						HttpClient httpClient = HttpClientBuilder.create().build();
						HttpPost httpRequest = new HttpPost(url);
						httpRequest.setHeader("Content-Type", "application/json");
							  
						StringEntity body = new StringEntity(obj.toString());
						httpRequest.setEntity(body);
						HttpResponse response = httpClient.execute(httpRequest);
						result = new BasicResponseHandler().handleResponse(response);

					} catch (Exception e) {
						result=null;
						e.printStackTrace();
					}
					
			}
			
			if("ANALOGICS".equalsIgnoreCase(hesType)) {
				url = FilterUnit.analogicsOnDemandUrl;
				obj.put("TransID" ,String.valueOf(numbGen()));
				obj.put("fromDate" , fromdate);
				obj.put("toDate" , todate);
				if("INSTANTANEOUS".equalsIgnoreCase(profileType)){	
					obj.put("onDemndType" , "Instant");
				}
				if("BILLING".equalsIgnoreCase(profileType)){	
					obj.put("onDemndType" , "Billing");
				}
				if("BULKLOAD".equalsIgnoreCase(profileType)){	
					obj.put("onDemndType" , "Load");	
				}
				if("EVENTLOGS".equalsIgnoreCase(profileType)){	
					obj.put("onDemndType" , "Event");
				}
				
				
				try {
					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpPost httpRequest = new HttpPost(url);
					httpRequest.setHeader("Content-Type", "application/json");
					StringEntity body = new StringEntity(obj.toString());
					System.out.println("reqst= "+obj.toString());
					httpRequest.setEntity(body);
					HttpResponse response = httpClient.execute(httpRequest);
					String res = new BasicResponseHandler().handleResponse(response);
					System.out.println("res= "+res);
					JSONObject anlgObj =  new JSONObject(res);
					String resp = anlgObj.optString("response");
					String errorMessage = anlgObj.optString("errorMessage");
					result = anlgObj.optString("data");
				} catch (Exception e) {
					result=null;
					e.printStackTrace();
				}
								
			}
			return result;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			

	}
		
	public long numbGen() {
		    while (true) {
		        long numb = (long)(Math.random() * 100000000 * 1000000); // had to use this as int's are to small for a 13 digit number.
		        if (String.valueOf(numb).length() == 13)
		            return numb;
		    }
	}
		
}
