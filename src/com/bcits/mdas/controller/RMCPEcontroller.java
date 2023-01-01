package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.controller.FirmWareUpgrade;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.MeterGroupMeters;
import com.bcits.mdas.entity.MeterGroups;
import com.bcits.mdas.entity.MeterJobManagement;
import com.bcits.mdas.entity.MeterJobQueries;
import com.bcits.mdas.entity.MeterJobStatus;
import com.bcits.mdas.entity.MeterJobTypes;
import com.bcits.mdas.entity.MeterJobs;
import com.bcits.mdas.jsontoobject.QueryMeterJobJson;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterGroupMetersService;
import com.bcits.mdas.service.MeterGroupsService;
import com.bcits.mdas.service.MeterJobMgmtService;
import com.bcits.mdas.service.MeterJobQueriesService;
import com.bcits.mdas.service.MeterJobStatusService;
import com.bcits.mdas.service.MeterJobTypeService;
import com.bcits.mdas.service.MeterJobsService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class RMCPEcontroller {

	/*
	 * public static final String HESController.hesurl =
	 * HESController.HESController.hesurl;
	 * "https://ugvcl-omni-demo.se.cyco.io/sma/ws"; //public static final String
	 * HESController.hesurl = "https://jvvnl-71mdms.dev.cyanconnode.com/sma/ws";
	 * public static final String hesuser = "sysadmin"; public static final
	 * String hespass = "sysadmin"; public static final String authString =
	 * hesuser + ":" + hespass; static Base64 b=new Base64(); public static
	 * final String HESController.authStringenc =
	 * b.encodeToString(authString.getBytes());
	 */

	@Autowired
	private MasterMainService mastermainservice;
	@Autowired
	private MeterGroupsService mgs;
	@Autowired
	private MeterGroupMetersService mgms;
	@Autowired
	MeterJobsService mjs;
	@Autowired
	MeterJobTypeService mjts;
	@Autowired
	MeterJobMgmtService mjms;
	@Autowired
	MeterJobQueriesService mjqs;
	@Autowired
	MeterJobStatusService mjss;

	public String rmcpe(Model model) {
		return "rmcpe";
	}

	@RequestMapping(value = "/metergroup")
	public String metergroup(HttpServletRequest request, ModelMap model) {
		List<MasterMainEntity> meters = mastermainservice.getmeters();
		System.out.println("metres--->" + meters.size());
		List<String> groupList = new ArrayList<>();
		String res = FirmWareUpgrade.callAPI(1000, 0, "STATIC");
		if (!res.equalsIgnoreCase("NA")) {
			try {
				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for (int i = 0; i < jsarr.length(); i++) {
					jsobj = new JSONObject(jsarr.get(i).toString());
					// System.err.println(i+" "+jsobj.getString("name") + " " +
					// jsobj.getString("type"));
					groupList.add(jsobj.getString("name"));
				}
			} catch (JSONException e) {
				groupList.add("NA");
				e.printStackTrace();
			}
		} else {
			groupList.add("NA");
		}
		model.addAttribute("groupList", groupList);

		model.put("meters", meters);
		return "metergroup";
	}
	@RequestMapping(value = "/metergroupl")
	public String metergroupl(HttpServletRequest request, ModelMap model) {
		List<MasterMainEntity> meters = mastermainservice.getmeters();
		System.out.println("metres--->" + meters.size());
		List<String> groupList = new ArrayList<>();
		String res = FirmWareUpgrade.callAPI(1000, 0, "STATIC");
		if (!res.equalsIgnoreCase("NA")) {
			try {
				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for (int i = 0; i < jsarr.length(); i++) {
					jsobj = new JSONObject(jsarr.get(i).toString());
					// System.err.println(i+" "+jsobj.getString("name") + " " +
					// jsobj.getString("type"));
					groupList.add(jsobj.getString("name"));
				}
			} catch (JSONException e) {
				groupList.add("NA");
				e.printStackTrace();
			}
		} else {
			groupList.add("NA");
		}
		model.addAttribute("groupList", groupList);

		model.put("meters", meters);
		return "metergroup26112018";
	}
	@RequestMapping(value = "/metergrouplistHES", method = { RequestMethod.GET })
	public @ResponseBody
	Object metergrouplistHES() {

		List<String> groupList = new ArrayList<>();
		String res = FirmWareUpgrade.callAPI(1000, 0, "STATIC");
		if (!res.equalsIgnoreCase("NA")) {
			try {
				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for (int i = 0; i < jsarr.length(); i++) {
					jsobj = new JSONObject(jsarr.get(i).toString());
					// System.err.println(i+" "+jsobj.getString("name") + " " +
					// jsobj.getString("type"));
					groupList.add(jsobj.getString("name"));
				}
			} catch (JSONException e) {
				groupList.add("NA");
				e.printStackTrace();
			}
		} else {
			groupList.add("NA");
		}
		
		return groupList;

	}
	@RequestMapping(value = "/metergrouplist", method = { RequestMethod.GET })
	public @ResponseBody
	Object metergrouplist() {

		List<Object[]> l = mgs.getCustomEntityManager("postgresMdas")
				.createNativeQuery("select * from meter_data.meter_groups where status='A' ORDER BY timestamp desc")
				.getResultList();
		return l;

	}

	@RequestMapping(value = "/meterjoblist", method = { RequestMethod.GET })
	public @ResponseBody
	Object meterjoblist() {

		List<Object[]> l = mgs.getCustomEntityManager("postgresMdas")
				.createNativeQuery("select * from meter_data.meter_jobs ORDER BY time_stamp desc")
				.getResultList();
		return l;

	}

	@RequestMapping(value = "/meterjoblist/{job_type}", method = { RequestMethod.GET })
	public @ResponseBody
	Object meterjoblistByType(@PathVariable String job_type) {
		List<?> l = mgs
				.getCustomEntityManager("postgresMdas")
				.createNativeQuery(
						"SELECT j.* FROM meter_data.meter_jobs j, meter_data.meter_job_types t WHERE j.job_name=t.job_name AND t.type='"
								+ job_type + "' ORDER by j.id DESC;")
				.getResultList();
		/*
		 * Map<String, List<?>> map=new HashMap<>(); map.put("data", l);
		 */
		return l;

	}

	@RequestMapping(value = "/createmetergroup", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	String createmetergroup(@RequestParam("mtrnos") String mtrnos) {
		String url = HESController.hesurl + "/meterGroup";
		// int i =mtrnos.length;

		try {

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			/* JSONObject obj = new JSONObject(); */
			// obj.put("meterGroupName", grpname);
			// obj.put("meterGroupType", grptype);
			/*
			 * String[] s=new String[1]; s[0]=mtrnos.replace("\"", "");
			 * obj.put("meters", s);
			 */
			StringEntity body = new StringEntity(mtrnos);

			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			// System.out.println("code: " + code);
			String res = new BasicResponseHandler().handleResponse(response);
			if (code == 201) {
				JsonParser parser = new JsonParser();
				JsonElement jsonTree = parser.parse(mtrnos);
				JsonObject jsonObject = jsonTree.getAsJsonObject();

				String mgn = jsonObject.get("meterGroupName").getAsString();

				String mgt = jsonObject.get("meterGroupType").getAsString();
				MeterGroups mg = new MeterGroups();
				mg.setMeter_group_name(mgn);
				mg.setMeter_group_type(mgt);
				mg.setResponse_code(String.valueOf(code));
				mg.setTimestamp(new Timestamp(new Date().getTime()));
				mg.setStatus("A");
				mgs.save(mg);
				JsonArray arr = jsonObject.getAsJsonArray("meters");
				for (int i = 0; i < arr.size(); i++) {
					MeterGroupMeters mgm = new MeterGroupMeters();
					mgm.setMeter_group_name(mgn);
					mgm.setMeter_number(arr.get(i).getAsString());
					mgm.setTime_stamp(new Timestamp(new Date().getTime()));
					mgms.save(mgm);
				}
			}
			// System.out.println("res: " + res);
			return String.valueOf(code);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/createmeterjob", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	String createmeterjob(@RequestParam("mtrnos") String mtrnos) {
		String url = HESController.hesurl + "/meterJob";
		// int i =mtrnos.length;

		try {

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			JSONObject obj = new JSONObject(mtrnos);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			// System.out.println(httpRequest);
			// System.out.println();
			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();

			// System.out.println("code: " + code);
			String res = new BasicResponseHandler().handleResponse(response);

			System.out.println("code: " + code);


			if(code==201) {
				//String res = new BasicResponseHandler().handleResponse(response);
				JsonParser jp=new JsonParser();
				JsonElement je=jp.parse(mtrnos);
				JsonObject jo=je.getAsJsonObject();
				String jobtype=jo.get("jobType").getAsString();
				String jobname=jo.get("jobName").getAsString();
				String meterGroup=jo.get("meterGroup").getAsString();
				JsonObject joit=jo.get("jobConfiguration").getAsJsonObject();

				JsonArray jsonarrayt = joit.get("commands").getAsJsonArray();
				MeterJobs mj = new MeterJobs();
				mj.setJobName(jobname);
				mj.setJobType(jobtype);
				mj.setMeterGroup(meterGroup);
				mj.setResponseCode(String.valueOf(code));
				mj.setTimestamp(new Timestamp(new Date().getTime()));
				mjs.save(mj);
				for (JsonElement pa : jsonarrayt) {
					JsonObject jot = pa.getAsJsonObject();
					String type = jot.get("type").getAsString();
					/*String active = jot.get("active").getAsString();*/
					String active = String.valueOf(jot.get("active"));
					MeterJobTypes mjt = new MeterJobTypes();
					mjt.setJobName(jobname);
					mjt.setActive(active);
					mjt.setType(type);
					mjt.setTimestamp(new Timestamp(new Date().getTime()));
					mjts.save(mjt);

				}

			}
			return String.valueOf(code);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/createmeterjobupdate", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String createmeterjobupdate(@RequestParam("mtrnos") String mtrnos) {
		String url = HESController.hesurl + "/meterJob";
		// int i =mtrnos.length;

		try {

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			JSONObject obj = new JSONObject(mtrnos);
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			// System.out.println(httpRequest);
			// System.out.println();
			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			// System.out.println("code: " + code);
			String res = new BasicResponseHandler().handleResponse(response);

			if (code == 201 ) {
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(mtrnos);
				JsonObject jo = je.getAsJsonObject();
				String jobtype = jo.get("jobType").getAsString();
				String jobname = jo.get("jobName").getAsString();
				String meterGroup = jo.get("meterGroup").getAsString();
				JsonObject joit = jo.get("jobConfiguration").getAsJsonObject();
				JsonArray jsonarrayt = joit.get("commands").getAsJsonArray();
				MeterJobs mj = new MeterJobs();
				mj.setJobName(jobname);
				mj.setJobType(jobtype);
				mj.setMeterGroup(meterGroup);
				mj.setResponseCode(String.valueOf(code));
				mj.setTimestamp(new Timestamp(new Date().getTime()));
				mjs.save(mj);
				if(mtrnos.equalsIgnoreCase("METER_COMMAND_SET")){
					for (JsonElement pa : jsonarrayt) {
						JsonObject jot = pa.getAsJsonObject();
						MeterJobTypes mjt = new MeterJobTypes();
						mjt.setJobName(jobname);
						String type = jot.get("type").getAsString();
						mjt.setType(type);
						if (type.equalsIgnoreCase("PROFILE_CAPTURE_PERIOD")) {
							String at = jot.get("activationTime").getAsString();
							String pt = jot.get("profileType").getAsString();
							String cp = jot.get("capturePeriod").getAsString();
							mjt.setActivation_time(at);
							mjt.setProfile_type(pt);
							mjt.setCapturePeriodORDemandperiod(cp);

						} else if (type
								.equalsIgnoreCase("DEMAND_INTEGRATION_PERIOD")) {
							String at = jot.get("activationTime").getAsString();

							String dp = jot.get("demandPeriod").getAsString();
							mjt.setActivation_time(at);

							mjt.setCapturePeriodORDemandperiod(dp);

						} else if (type.equalsIgnoreCase("BILLING_PERIOD")) {
							String at = jot.get("activationTime").getAsString();

							JsonObject ijo = jot.get("date").getAsJsonObject();
							mjt.setActivation_time(at);
							mjt.setDay_of_month(ijo.get("dayOfMonth").getAsInt());

						}

						mjt.setTimestamp(new Timestamp(new Date().getTime()));
						mjts.save(mjt);

					}
	
				}
				
			}
			return String.valueOf(code);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/manageMeterJob/{jobName}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String manageMeterJob(@PathVariable String jobName) {
		String url = HESController.hesurl + "/manageMeterJob/" + jobName;
		int code = 0;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			// System.out.println("URL ----> " +url);
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("instruction", "START");
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);

			HttpResponse response = httpClient.execute(httpRequest);
			code = response.getStatusLine().getStatusCode();
			// System.out.println("Status Line " +response.getStatusLine());
			String res = new BasicResponseHandler().handleResponse(response);
			if (code == 200) {
				MeterJobManagement mjm = new MeterJobManagement();
				mjm.setJob_name(jobName);
				mjm.setInstruction("START");
				mjm.setStatus_code(String.valueOf(code));
				mjm.setTimestamp(new Timestamp(new Date().getTime()));
				mjms.save(mjm);
			}

			// System.out.println("res: " + res);

			return String.valueOf(code);
		} catch (Exception e) {
			e.printStackTrace();
			return String.valueOf(code);
		}

	}

	@RequestMapping(value = "/querymeterjob/{jobname}", method = { RequestMethod.GET })
	public @ResponseBody
	QueryMeterJobJson querymeterjob(@PathVariable String jobname) {
		System.out.println(jobname);
		String url = HESController.hesurl + "/queryMeterJob/" + jobname;
		System.out.println(url);
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				MeterJobQueries mjq = new MeterJobQueries();
				mjq.setJobName(jobname);
				mjq.setStausCode(String.valueOf(code));
				mjq.setTimestamp(new Timestamp(new Date().getTime()));
				mjqs.save(mjq);
			}
			String res = new BasicResponseHandler().handleResponse(response);
			Gson gson = new Gson();
			QueryMeterJobJson qmjj = gson
					.fromJson(res, QueryMeterJobJson.class);
			// System.out.println("res: " + res);

			return qmjj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@RequestMapping(value = "/querymeterjobl/{jobname}", method = { RequestMethod.GET })
	public @ResponseBody
	Object querymeterjobl(@PathVariable String jobname) {
		System.out.println(jobname);
		String url = HESController.hesurl + "/queryMeterJob/" + jobname;
		System.out.println(url);
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				MeterJobQueries mjq = new MeterJobQueries();
				mjq.setJobName(jobname);
				mjq.setStausCode(String.valueOf(code));
				mjq.setTimestamp(new Timestamp(new Date().getTime()));
				mjqs.save(mjq);
			}
			String res = new BasicResponseHandler().handleResponse(response);
			

			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/querymeterjobNew/{jobname}", method = { RequestMethod.GET })
	public @ResponseBody
	Object querymeterjobNew(@PathVariable String jobname) {
		System.out.println(jobname);
		String url = HESController.hesurl + "/queryMeterJob/" + jobname;
		System.out.println(url);
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpRequest = new HttpGet(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();

			String res = new BasicResponseHandler().handleResponse(response);

			JsonParser parser = new JsonParser();
			JsonElement jsonTree = parser.parse(res);
			JsonObject jsonObject = jsonTree.getAsJsonObject();
			System.out.println(jsonObject.toString());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// meterStatusForJob
	@RequestMapping(value = "/meterStatusForJob/{jobName}/{count}/{devicestatus}/{startid}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String meterStatusForJob(@PathVariable String jobName,
			@PathVariable int count, @PathVariable String devicestatus,
			@PathVariable int startid) {
		String url = HESController.hesurl + "/meterStatusForJob/" + jobName;
		// System.out.println(url);
		// System.out.println("count"+count+"devicestatus"+devicestatus+"startid"+startid);
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			// System.out.println("URI: "+httpRequest.getURI());
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic "
					+ HESController.authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count", count);
			obj.put("deviceStatus", devicestatus);
			// obj.put("endTime", "2018-10-20T00:00:00+0530");
			// obj.put("sortDirection", "asc");
			// obj.put("sortOrder", "NAME");
			obj.put("startId", startid);
			// obj.put("startTime", "2018-10-10T00:00:00+0530");
			StringEntity body = new StringEntity(obj.toString());
			// System.out.println("body--"+body);
			// System.out.println("obj--"+obj.toString());
			httpRequest.setEntity(body);

			// System.out.println(httpRequest);

			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				MeterJobStatus mjs = new MeterJobStatus();
				mjs.setCount(String.valueOf(count));
				mjs.setDevice_status(devicestatus);
				mjs.setJob_name(jobName);
				mjs.setStart_id(String.valueOf(startid));
				mjs.setTime_stamp(new Timestamp(new Date().getTime()));
				mjs.setStatus_code(String.valueOf(code));

				mjss.save(mjs);
			}
			// System.out.println(response.getStatusLine());

			String res = new BasicResponseHandler().handleResponse(response);

			// System.out.println("res: "+res);

			return res;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/jobList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Object joblist() {
		List<String> l = mjs
				.getCustomEntityManager("postgresMdas")
				.createNativeQuery(
						"select DISTINCT job_name from meter_data.meter_jobs  ORDER BY job_name")
				.getResultList();

		return l;
	}

	
	@RequestMapping(value="/loadCurtailment")
	public String loadCurtailment(HttpServletRequest request,ModelMap model)
	{
		List<MasterMainEntity> meters=mastermainservice.getmeters();
		System.out.println("metres--->"+meters.size());
		List<String> groupList=new ArrayList<>();
		String res=FirmWareUpgrade.callAPI(2000,0,"STATIC");
		if(!res.equalsIgnoreCase("NA")) {
			try{

				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for (int i = 0; i < jsarr.length(); i++) {
					jsobj = new JSONObject(jsarr.get(i).toString());
					// System.err.println(i+" "+jsobj.getString("name") + " " +
					// jsobj.getString("type"));
					groupList.add(jsobj.getString("name"));
				}
			} catch (JSONException e) {
				groupList.add("NA");
				e.printStackTrace();
			}
		} else {
			groupList.add("NA");
		}
		model.addAttribute("groupList", groupList);

		/*
		 * List<?>
		 * l=mjs.getCustomEntityManager("postgresMdas").createNativeQuery(
		 * "SELECT j.* FROM meter_data.meter_jobs j, meter_data.meter_job_types t WHERE j.job_name=t.job_name AND t.type='LOAD_CURTAILMENT';"
		 * ).getResultList(); model.addAttribute("jobList",l);
		 */
		model.put("meters", meters);
		return "loadCurtailment";
	}
	
	@RequestMapping(value="/mdReset")
	public String mdReset(HttpServletRequest request,ModelMap model)
	{
		List<MasterMainEntity> meters=mastermainservice.getmeters();
		System.out.println("metres--->"+meters.size());
		List<String> groupList=new ArrayList<>();
		String res=FirmWareUpgrade.callAPI(2000,0,"STATIC");
		if(!res.equalsIgnoreCase("NA")) {
			try{

				JSONArray jsarr = new JSONArray(res);
				JSONObject jsobj = null;
				for (int i = 0; i < jsarr.length(); i++) {
					jsobj = new JSONObject(jsarr.get(i).toString());
					// System.err.println(i+" "+jsobj.getString("name") + " " +
					// jsobj.getString("type"));
					groupList.add(jsobj.getString("name"));
				}
			} catch (JSONException e) {
				groupList.add("NA");
				e.printStackTrace();
			}
		} else {
			groupList.add("NA");
		}
		model.addAttribute("groupList", groupList);

		/*
		 * List<?>
		 * l=mjs.getCustomEntityManager("postgresMdas").createNativeQuery(
		 * "SELECT j.* FROM meter_data.meter_jobs j, meter_data.meter_job_types t WHERE j.job_name=t.job_name AND t.type='LOAD_CURTAILMENT';"
		 * ).getResultList(); model.addAttribute("jobList",l);
		 */
		model.put("meters", meters);
		return "mdReset";
	}

	@RequestMapping(value = "/meterStatusForJobNew/{jobName}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String meterStatusForJobNew(@PathVariable String jobName,HttpServletRequest request) {
		String url = HESController.hesurl + "/meterStatusForJob/" + jobName;
		String count=request.getParameter("count");
		String devicestatus=request.getParameter("devicestatus");
		String startid=request.getParameter("startid");
		
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);

			JSONObject obj = new JSONObject();
			obj.put("count",(count==null?100:count) );
			
			if(devicestatus!=null) {
				obj.put("deviceStatus", devicestatus);
			}
			
			obj.put("startId", (startid==null?1:startid));
			
			StringEntity body = new StringEntity(obj.toString());
			httpRequest.setEntity(body);
			HttpResponse response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			String res = new BasicResponseHandler().handleResponse(response);
			
			return res;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value="/deleteMeterGroup/{grpName}", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object deleteMeterGroup(@PathVariable String grpName){
		String url = HESController.hesurl + "/meterGroup/"+grpName;
		int code=0;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			System.out.println("URL ----> " +url);
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);
			HttpResponse response = httpClient.execute(httpRequest);
			System.out.println("Status Line " +response.getStatusLine());
			String res = new BasicResponseHandler().handleResponse(response);
			 code = response.getStatusLine().getStatusCode();
		}
		catch(Exception e){
			
		}
		if(code==200){
			String sql="UPDATE master_main.meter_groups set status='D'";
			int i=mgs.getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			return "S";
		}
		else{
			return "F";
		}
		
		
	}
	@RequestMapping(value="/grpMeterList/{grpName}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object metersList(@PathVariable String grpName){
		
		String sql="select meter_number,time_stamp from meter_data.meter_group_meters where meter_group_name='"+grpName+"'";
		List<Object[]> l=mgs.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return l;
		
	}
	
}
