package com.bcits.mdas.controller;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.bcits.controller.DataExchangeController;
import com.bcits.mdas.service.AndroidService;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.SendEBillRegistrationOTPMail;
import com.bcits.mdas.utility.SendEmail;
import com.bcits.mdas.utility.StaticProperties;

@Controller
public class AndroidConsumerController {
	@Autowired
	AndroidService androidService;
	@Autowired
	DataExchangeController dec;

	@RequestMapping(value = "/register/registerNewMobUser/{UserName}/{ConsumerName}/{Password}/{Emailid}/{MobileNo}/{otp}/{ebillReg}/{que1}/{ans1}/{que2}/{ans2}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody String insert_new(@PathVariable String UserName, @PathVariable String ConsumerName,
			@PathVariable String Password, @PathVariable String Emailid, @PathVariable String MobileNo,
			@PathVariable String otp, @PathVariable String ebillReg, @PathVariable String que1,
			@PathVariable String ans1, @PathVariable String que2, @PathVariable String ans2) {
		return androidService.insert_new(UserName, ConsumerName, Password, Emailid, MobileNo, otp, ebillReg, que1, ans1,
				que2, ans2);
	}

	@RequestMapping(value = "/securityQuestions", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Object securityQuestions(HttpServletResponse response) {
		JSONArray mainArray = new JSONArray();
		try {
			String query = "SELECT id,questions from meter_data.security_questions WHERE question_code='1'";
			JSONArray subarrayArray1 = new JSONArray();

			List<Object[]> array = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query)
					.getResultList();
			// System.out.println("In section
			// getLocationDetails==>"+query+"==>"+array.size());
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", String.valueOf(array.get(i)[0]));
				obj.put("questions", String.valueOf(array.get(i)[1]));
				subarrayArray1.put(obj);
			}
			mainArray.put(subarrayArray1);
			String query2 = "SELECT id,questions from meter_data.security_questions WHERE question_code='2'";
			JSONArray subarrayArray2 = new JSONArray();
			List<Object[]> array1 = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query2)
					.getResultList();
			// System.out.println("In section
			// getLocationDetails==>"+query+"==>"+array.size());
			for (int i = 0; i < array1.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", String.valueOf(array1.get(i)[0]));
				obj.put("questions", String.valueOf(array1.get(i)[1]));
				subarrayArray2.put(obj);
			}
			mainArray.put(subarrayArray2);

		} catch (Exception e) {
			Timestamp lastmodfydate = new Timestamp(System.currentTimeMillis());
			
			dec.Servicelog("Security Questions Service","MOBILE","MDM","test",lastmodfydate);
			e.printStackTrace();
		}
		return mainArray.toString();
	}

	@RequestMapping(value = "/reg/final/mobileValidateAndSendOTP/{UserName}/{ConsumerName}/{Password}/{Emailid}/{MobileNo}/{OTP}", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Object mobileValidateSendOtpAMI(@PathVariable String UserName,
			@PathVariable String ConsumerName, @PathVariable String Password, @PathVariable String Emailid,
			@PathVariable String MobileNo, @PathVariable String OTP) {

		List<?> list = null;

		System.out.println("in mobileValidateAndSendOTP++++++" + UserName);
		String accountHolder = null;
		int result = 0;

		try {
			String sql1 = "select customer_login_name from meter_data.ncpt_customers where upper(customer_login_name)  = upper('"
					+ UserName + "')";
			System.out.println(sql1);
			list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();

			if (list.size() >= 1) {
				System.out.println("username already exists");
				return "user_exists";
				/*
				 * ml.add("user_exists"); return ml;
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "connection error";
		}
		System.out.println("This is my OTP Number : " + OTP + "  Phone  " + MobileNo + " AccountHolder  "
				+ accountHolder + " ====++++++++++++++++++++++++++++++++++++++++++++++++++++==");
		System.out.println(OTP + "  OTP");

		System.out.println("Before Sending OTP..====================");
		result = 1;

		System.out.println("==================Result = " + result);
		System.out.println("Here I will send the OTP to phone.");
		// String appName=ResourceBundle.getBundle("messages").getString("smsEmailMsgHeader");
		try {
			/*String message = ConsumerName + " OTP Verification - Your OTP for Bijli Mitra APP is " + OTP;*/
			//String message = ConsumerName + " OTP Verification - Your OTP for " +appName+ "APP is " + OTP;
			String message="Dear Customer, your OTP for APSPDCL Smart Meter Application is "+OTP+". Use this OTP to register.";
			String targetURL = StaticProperties.domainNameBijliPrabandh + "sendSMSGeneric/" + message + "/" + MobileNo;
			/*
			 * System.err.println("otp sent targetURL s===========>"+targetURL);
			 * RestTemplate template = new RestTemplate(); UriComponentsBuilder builder =
			 * UriComponentsBuilder.fromHttpUrl(targetURL); String serverResponse =
			 * template.getForObject(builder.build().encode().toUri(), String.class);
			 */
//			String serverResponse=sendSmsNew(targetURL);
//			System.out.println("otp sent responce===========>"+serverResponse);
			//old
			//new SendSmsNew(targetURL).start();
			//new
			sendSMSGUPSHUP(message,MobileNo);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (!(Emailid.equals("NA"))) {
				// sendEmail
				System.err.println("Email came");
				try {
					new Thread(new SendEBillRegistrationOTPMail(Emailid, ConsumerName, OTP, "reg")).start();
					return ("OTP Sent Successfully to your registered Email ID.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ("Failed to Send OTP to the Email :" + Emailid + ", try Again later!");
				}
			}
		} catch (Exception e) {
		}

		if (result == 1) {
			return "verify_otp";
		} else {
			return "otp_not_sent";
		}

	}

	/*
	 * @RequestMapping(value =
	 * "/register/final/mobileValidateAndSendOTP/{UserName}/{ConsumerName}/{Password}/{Emailid}/{MobileNo}/{OTP}",
	 * method=RequestMethod.GET,produces = {"application/json; charset=UTF-8"})
	 * public @ResponseBody String mobileValidateAndSendOTP(@PathVariable String
	 * UserName, @PathVariable String ConsumerName, @PathVariable String
	 * Password,@PathVariable String Emailid,@PathVariable String
	 * MobileNo, @PathVariable String OTP) { List <?>list=null; List<String> ml=new
	 * ArrayList();
	 * System.out.println("in mobileValidateAndSendOTP++++++"+UserName); String
	 * accountHolder=null; int result = 0;
	 * 
	 * try { String sql1 =
	 * "select customer_login_name from meter_data.ncpt_customers where upper(customer_login_name)  = upper('"
	 * + UserName + "')"; System.out.println(sql1); list =
	 * androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql1)
	 * .getResultList();
	 * 
	 * if (list.size() >= 1) { System.out.println("username already exists"); return
	 * "user_exists";
	 * 
	 * ml.add("user_exists"); return ml;
	 * 
	 * } } catch (Exception e) { e.printStackTrace(); return "connection error"; }
	 * 
	 * System.out.println("This is my OTP Number : "+ OTP +"  Phone  "+
	 * MobileNo+" AccountHolder  "
	 * +accountHolder+" ====++++++++++++++++++++++++++++++++++++++++++++++++++++=="
	 * ); System.out.println(OTP+"  OTP");
	 * 
	 * System.out.println("Before Sending OTP..===================="); result = 1;
	 * 
	 * System.out.println("==================Result = " + result);
	 * System.out.println("Here I will send the OTP to phone.");
	 * 
	 * 
	 * try { String message =
	 * ConsumerName+" OTP Verification - Your OTP for Bijli Mitra APP is " +OTP;
	 * String targetURL =
	 * StaticProperties.domainNameBijliPrabandh+"sendSMSGeneric/"+message+"/"+
	 * MobileNo; System.err.println("otp sent targetURL s===========>"+targetURL);
	 * RestTemplate template = new RestTemplate(); UriComponentsBuilder builder =
	 * UriComponentsBuilder.fromHttpUrl(targetURL); String serverResponse =
	 * template.getForObject(builder.build().encode().toUri(), String.class); //
	 * String serverResponse=sendSmsNew(targetURL); //
	 * System.out.println("otp sent responce===========>"+serverResponse); new
	 * SendSmsNew(targetURL).start(); } catch (Exception e1) { e1.printStackTrace();
	 * }
	 * 
	 * try{ if(!(Emailid.equals("NA"))){ //sendEmail
	 * System.err.println("Email came"); try { new Thread(new
	 * SendEBillRegistrationOTPMail (Emailid,ConsumerName,OTP,"reg")).start();
	 * return("OTP Sent Successfully to your registered Email ID."); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
	 * return("Failed to Send OTP to the Email :"+Emailid+", try Again later!"); } }
	 * }catch(Exception e){ }
	 * 
	 * if (result == 1) { return "verify_otp"; } else { return "otp_not_sent"; } }
	 */
	public static class SendSmsNew extends Thread {
		String targetURL;

		SendSmsNew(String targetURL) {
			this.targetURL = targetURL;
		}

		@Override
		public void run() {
			super.run();
			System.out.println(sendSmsNew(targetURL));
		}
	}

	public static String sendSmsNew(String targetURL) {
		return sendSmsRest(targetURL);
	}

	private static String sendSmsRest(String targetURL) {
		RestTemplate template = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetURL);
		return template.getForObject(builder.build().encode().toUri(), String.class);
	}

	@RequestMapping(value = "/login/checkNewMitraMob", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getFeederMonitoringDataDayWise(@RequestParam String usernameMob,@RequestParam String passwordMob) throws JSONException {
		String accountDetails = "", sdocode = "", emailid = "", phoneNo = "", fullname = "", ebill = "";

		System.out.println("loginNew");

		/*
		 * JSONObject obj = new JSONObject(loginData); String usernameMob =
		 * obj.getString("usernameMob"); String passwordMob =
		 * obj.getString("passwordMob");
		 */
		
		List<?> list = null, list2 = null;

		try {

			String query = "SELECT customer_name,customer_contact_no,customer_email_id,ebill_registration from meter_data.ncpt_customers WHERE upper(customer_login_name)= upper('"
					+ usernameMob + "') and password='" + passwordMob + "'";
			list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

			System.out.println("query==>>" + query + "==>>" + list.size());
			if (list.size() == 0) {
				return "notfound";
			} else {
				String json = new Gson().toJson(list);
				System.out.println("fullname==>" + json);

				for (int i = 0; i < list.size(); i++) {
					Object[] result = (Object[]) list.get(i);
					sdocode = String.valueOf(result[0]).split("-")[0];
					// Accno = String.valueOf(result[0]).split("-")[1];

					// accountDetails=String.valueOf(result[0]);
					fullname = String.valueOf(result[0]);
					phoneNo = String.valueOf(result[1]);
					emailid = String.valueOf(result[2]);
					ebill = String.valueOf(result[3]);

				}
				// String queryAcct="SELECT sitecode,rrnum,knum from jvvnlpgrs.ncpt_registerrrno
				
				// WHERE consumerlogin='"+username+"'";
				String queryAcct = "SELECT sitecode,rrnum,knum from meter_data.ncpt_registerrrno WHERE upper(consumerlogin) like upper('"
						+ usernameMob + "')";

				list2 = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(queryAcct)
						.getResultList();
				System.err.println("list2.size()===>" + list2.size());
				if (list2.size() == 0) {
					return "notfoundAcccount==>" + json;
				} else {

					accountDetails = "";
					for (int i = 0; i < list2.size(); i++) {
						Object[] result1 = (Object[]) list2.get(i);
						if (i == 0) {
							accountDetails += String.valueOf(result1[0]) + "-" + String.valueOf(result1[1]) + "-"
									+ String.valueOf(result1[2]);
						} else {
							accountDetails += "," + String.valueOf(result1[0]) + "-" + String.valueOf(result1[1]) + "-"
									+ String.valueOf(result1[2]);
						}

					}
					return "SUCCESS@@@" + accountDetails + "@@@" + fullname + "@@@" + phoneNo + "@@@" + emailid + "@@@"
							+ ebill;
				}
				// list.get(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
    @RequestMapping(value = "/register/recoverPasswordAndroid/{MobileNo}", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public @ResponseBody Object recoverPasswordAndroid_new (@PathVariable String MobileNo, HttpServletResponse response) {
        System.out.println(MobileNo+"==========="+MobileNo);
        List<?> list = new ArrayList();

        try {
            String query="SELECT customer_login_name, password, customer_name, customer_email_id from meter_data.ncpt_customers where customer_contact_no= '"+MobileNo+"'";
            System.out.println(query);
            list = androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

            if(list.size()==0)
            {
                return "This Mobile Number is not Registered.";
            }
            else
            {
                for (int i = 0; i < list.size(); i++)
                {
                    Object[] result = (Object[]) list.get(i);
                    String username =String.valueOf(result[0]);
                    String password=String.valueOf(result[1]);
                    String name=String.valueOf(result[2]);
                    String email=String.valueOf(result[3]);
                    System.out.println("EMAIL : "+email);

                    String message="Your username is ''" +username + "'' and password  is ''" +password + "''. For security reasons we request you to please don't share your credentials with others.";

                    //String message="<b>Username-</b>''" +username + "''</br><b>Password-</b>''" +password + "''</br><b>Note:</b> For security reasons we request you to please don't share your credentials with others.";

                    //SENDING MAIL
                    try {
                        if(email!=null && email.length()>5){
                            new Thread(new SendEmail(email, message)).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    
                    try {
                        //SENDING SMS
                        String targetURL = StaticProperties.domainNameBijliPrabandh+"sendSMSGeneric/"+message+"/"+MobileNo;
                    /*    RestTemplate template = new RestTemplate();
                        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetURL);
                        String serverResponse = template.getForObject(builder.build().encode().toUri(), String.class);*/
                         new AndroidConsumerController.SendSmsNew(targetURL).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                return "Login details have been sent to your registered mobile & email";

            }

        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return "Unable To Recover Password";}
    
    //@Transactional(propagation = Propagation.REQUIRED,value="txManagerpostgre")
    @RequestMapping(value = "/login/addAccount/{UserName}/{kno}" ,method=RequestMethod.GET)
    public @ResponseBody String AddAccount_new(@PathVariable String UserName,@PathVariable String kno)
    {
        String username=UserName;
        List<?> oldAccountData=new ArrayList();
        System.out.println("login");
        List <?>schemaList=null;
        List <?>list=null;
        String prevAcc;
        String data = null;
      /*  if(sitecode.substring(0,4).equals("2104")){
            return "BijliMitra is available only for Non JCC circle consumers.";
        }*/
        try
        {
        	
        	
        	if(kno == null || kno.trim().length() !=12 || username == null ){
        		return "Invalid Data. Please check the entered data and try again.";
        	}
        	
            String query1 = "SELECT sitecode,rrnum,knum from meter_data.ncpt_registerrrno WHERE consumerlogin ='"+username+"' and knum='"+kno+"'";
             oldAccountData= androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query1).getResultList();
             if(oldAccountData.size()==0)
                {
                 try {
					
                        
                             



                                  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                  Date date = new Date();

                                  String current_date = dateFormat.format(date);

                                  String query = "INSERT INTO meter_data.ncpt_registerrrno (consumerlogin,createddate,knum) VALUES "
                                                            + "('"+username+"','"+current_date+"','"+kno+"')";
                                   System.out.println(query + "Queryyyyyyyyyyyyyyyyyyyyyy");
                                   
                                   int inser_cnt= androidService.ncpt_rrno_insertion(query);

                                   if(inser_cnt > 0)
                                   {
System.out.println("Result Set changepass Object is"+inser_cnt);
                                       data="Successfully Updated. Login again to see the changes!";
                                   }
                                   else
                                   {
                                       data="Failed to Update";
                                   }

                            //  }
                          // }

                  }catch (Exception e) {
                      e.printStackTrace();
                      return "connection error";
                  }
                }
                else
                {

                                return "Account alredy exists.";

                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  data;
    }
    @RequestMapping(value="/powerAvailability/{kno}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object powerAvailability(HttpServletRequest request,ModelMap model,@PathVariable String kno)
	{
    	List<Object[]> list=null;
    	//System.err.println(kno);
    	//JSONArray array = new JSONArray();
    	 try
         {
    	 list=androidService.getPowerAvailability(kno);
			/*
			 * for (int i = 0 ; i<list.size();i++) { Object[] columns=list.get(i);
			 * JSONObject obj = new JSONObject(); obj.put("Meter_Number",
			 * String.valueOf(columns[0])); obj.put("Month", String.valueOf(columns[1]));
			 * obj.put("Total_Hours", String.valueOf(columns[2])); obj.put("Power_off",
			 * String.valueOf(columns[3])); obj.put("Power_on", String.valueOf(columns[4]));
			 * 
			 * array.put(obj); }
			 */
    	
	}catch (Exception e) {
		
	}
    	 return list;
	}
    
    @RequestMapping(value="/powerOffEvent/{kno}/{billmonth}",method={RequestMethod.GET,RequestMethod.POST})
   	public @ResponseBody Object powerOffEvent(@PathVariable String kno,HttpServletRequest request,ModelMap model,@PathVariable String billmonth)
   	{
       	List<Object[]> list=null;
    	//JSONArray array = new JSONArray();
   	
       	 try
            {
       	 list=androidService.getPowerOffEvent(billmonth,kno);
			/*
			 * for (int i = 0 ; i<list.size();i++) { Object[] columns=list.get(i);
			 * JSONObject obj = new JSONObject(); obj.put("Meter_Number",
			 * String.valueOf(columns[0])); obj.put("Occurance_Date",
			 * String.valueOf(columns[1])); obj.put("Power_off_Start",
			 * String.valueOf(columns[2])); obj.put("Power_off_End",
			 * String.valueOf(columns[3])); obj.put("Duration", String.valueOf(columns[4]));
			 * 
			 * array.put(obj); }
			 */
       	 
       	
   	}catch (Exception e) {
   		
   	}
       	return  list;
   	}
    
   	
   	@RequestMapping(value="/voltageRegulation/{kno}/{billmonth}",method={RequestMethod.GET,RequestMethod.POST})
   	public @ResponseBody Object voltageRegulation(@PathVariable String kno,HttpServletRequest request,ModelMap model,@PathVariable String billmonth)
   	{
       	List<Object[]> list=null;
       	//System.err.println(kno+"--"+billmonth);
       	JSONArray array = new JSONArray();
       	 try
            {
       	 list=androidService.voltageRegulation(billmonth,kno);
       	for (int i = 0 ; i<list.size();i++) {
			Object[] columns=list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("Phase", String.valueOf(columns[0]));
			obj.put("Feeder_Category", String.valueOf(columns[1]));
			obj.put("Meter_Number", String.valueOf(columns[2]));
			obj.put("Supply_Voltage", String.valueOf(columns[3]));
			obj.put("Account_No", String.valueOf(columns[4]));
			obj.put("Month_Year", String.valueOf(columns[5]));
			obj.put("less_than_6", String.valueOf(columns[7]));
			obj.put("Between_6_to_0", String.valueOf(columns[8]));
			obj.put("Between_0_to_5", String.valueOf(columns[9]));
			obj.put("more_than_5", String.valueOf(columns[10]));
			array.put(obj);
		}
       	 
       	 
       	
   	}catch (Exception e) {
   		
   	}
       	return  array.toString();
   	}
	/*
	 * @RequestMapping(value = "/forgotPasswordAndroid", method = {
	 * RequestMethod.POST, RequestMethod.GET }) public @ResponseBody String
	 * forgotPasswordAndroid(@RequestBody String loginData) throws JSONException {
	 * BCITSLogger.logger.info("in forgotPasswordAndroid JVVNL...........");
	 * List<Object> userList = null; try { JSONObject obj = new
	 * JSONObject(loginData);
	 * 
	 * String mobile = obj.getString("mobile");
	 * 
	 * System.out.println(mobile + " ");
	 * 
	 * // String query =
	 * "SELECT username , password , mobile,empname from  \"vcloudengine\".\"employee\" WHERE   mobile ='"
	 * +mobile+"'"; String query =
	 * "SELECT USER_LOGIN_NAME , PASSWORD , MOBILENO, USER_NAME from BSMARTJVVNL.USERS WHERE   MOBILENO ='"
	 * +mobile+"'"; System.out.println(query);
	 * userList=entityManagerOracle.createNativeQuery(query).getResultList();
	 * 
	 * if (userList.size() > 0) { Object[] res = (Object[]) userList.get(0);
	 * 
	 * String pass=res[1].toString(); byte[] bytesEncoded =
	 * Base64.decodeBase64(pass.getBytes()); String decodedPass = new
	 * String(bytesEncoded);
	 * 
	 * SmsCredentialsDetailsBean smsCredentialsDetailsBean = new
	 * SmsCredentialsDetailsBean();
	 * smsCredentialsDetailsBean.setNumber(res[2].toString());
	 * smsCredentialsDetailsBean.setUserName(res[0].toString());
	 * smsCredentialsDetailsBean.setMessage("Dear "+res[3].toString()
	 * +", Your credentials for the Bijli Prabandh App are "+"\nUsername - "+res[0].
	 * toString()+"\nPassword- "
	 * +decodedPass+"\nPlease change your password immediately for better security."
	 * );
	 * 
	 * new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start(); return
	 * "Credentials sent to your registered mobile number . Please check your message Inbox ."
	 * ;
	 * 
	 * } else { return "No user found with this phone number"; }
	 * 
	 * } catch (Exception exception) { exception.printStackTrace(); return
	 * "FAILED  "+exception.getMessage() ; } }
	 */

    @RequestMapping(value = "/consumerProfile/{kno}", method = RequestMethod.GET)
		public @ResponseBody Object consumerProfile(@PathVariable String kno) {
    	//System.out.println("Kno="+kno);
    	  List<?>   list,list1=null;
    	  JSONArray array = new JSONArray();
    	if(kno == null || kno.trim().length() !=12 ){
    		return "Invalid Data. Please check data and try again.";
    	}
    	try {
//    		String query = "SELECT m.customer_name,m.kno,m.mtrno, m.tariffcode,cm.tadesc,ls.read_time,COALESCE(ls.kwh,0) as kwh_imp,COALESCE(ls.kwh_exp,0) as kwh_exp,\r\n" + 
//    				"(CAST(ls.kwh AS DOUBLE PRECISION) - CAST(ls.kwh_exp AS DOUBLE PRECISION) ) as net_kwh,\r\n" + 
//    				"ls.kw as power,ls.average_voltage,ls.average_current,ls.power_factor,ais.frequency,avg(ls.kwh)\r\n" + 
//    				"FROM meter_data.master_main m,meter_data.consumermaster cm,meter_data.load_survey ls,meter_data.amiinstantaneous ais\r\n" + 
//    				"WHERE m.kno=cm.kno and ais.meter_number=cm.meterno and ls.meter_number=cm.meterno and cm.kno='"+kno+"' and date(ls.read_time)=ls.read_time \r\n" + 
//    				"GROUP BY m.customer_name,m.kno,m.mtrno, m.tariffcode,cm.tadesc,ls.read_time,ls.kwh,ls.kwh_exp,ls.kw,ls.average_voltage,ls.average_current,ls.power_factor,ais.frequency\r\n" + 
//    				"order by ls.read_time desc limit 1";
    		
    		String query ="SELECT M.customer_name,M.kno,M.mtrno,M.tariffcode,cm.tadesc,ls.read_time,COALESCE ( ls.kwh, 0 ) AS kwh_imp,COALESCE ( ls.kwh_exp, 0 ) AS kwh_exp,( COALESCE ( ls.kwh, 0 ) - COALESCE ( ls.kwh_exp, 0 ) ) AS net_kwh,ls.kw AS POWER,ls.average_voltage,ls.average_current,ls.power_factor,ais.frequency,(SELECT \"avg\" ( kwh ) FROM meter_data.load_survey WHERE meter_number = ( SELECT meterno FROM meter_data.consumermaster WHERE kno = '"+kno+"' ) AND DATE ( read_time ) >= DATE (\r\n" + 
    				"date_trunc( 'month', CURRENT_DATE ))) \r\n" + 
    				"FROM meter_data.master_main M,meter_data.consumermaster cm,meter_data.load_survey ls,meter_data.amiinstantaneous ais \r\n" + 
    				"WHERE M.kno = cm.kno AND ais.meter_number = cm.meterno AND ls.meter_number = cm.meterno AND cm.kno = '"+kno+"' \r\n" + 
    				"ORDER BY ls.read_time DESC LIMIT 1";
    		
    		
    		list= androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
        //System.out.println("query==>>" + query + "==>>" + list.size());
          DecimalFormat decmForm= new DecimalFormat("3");
			if (list.size() == 0) {
				return "Not Data Found";
			} else {
				String json = new Gson().toJson(list);
				//System.out.println("fullname==>" + json);

				for (int i = 0; i < list.size(); i++) {

					Object[] columns=(Object[]) list.get(i);
					String customerName=String.valueOf(columns[0]).trim();
					String kno1=String.valueOf(columns[1]).trim();
					String meterNo=String.valueOf(columns[2]).trim();
					String tarrifCode=String.valueOf(columns[3]).trim();
					String cunsumerType=String.valueOf(columns[4]).trim();
					String readTime=String.valueOf(columns[5]).trim();
					String kwh_Imp=String.valueOf(columns[6]).trim();
					String kwh_Exp=String.valueOf(columns[7]).trim();
					String net_Kwh=String.valueOf(columns[8]).trim();
					String power=String.valueOf(columns[9]).trim();
					String voltage=String.valueOf(columns[10]).trim();
					String current=String.valueOf(columns[11]).trim();
					String pf=String.valueOf(columns[12]).trim();
					String frequency=String.valueOf(columns[13]).trim();
					String avh_kwh=String.valueOf(columns[14]);
					
					Integer kwh=Integer.valueOf((int) Math.round( Double.parseDouble(avh_kwh)));
					
					//Integer kwh=(Integer)50;
					//System.out.println("avh_kwh=="+avh_kwh+" kwh "+kwh);

					 JSONObject obj = new JSONObject();
							 obj.put("cunsumerName", customerName);
							 obj.put("kno", kno1);
							 obj.put("meterNo", meterNo);
							 obj.put("cunsumerType", cunsumerType);
							 obj.put("readTime", readTime);
							 obj.put("kwh_Imp", kwh_Imp);
							 obj.put("kwh_Exp", kwh_Exp);
							 obj.put("net_Kwh", net_Kwh==null?"":net_Kwh);
							 obj.put("power", power);
							 obj.put("voltage", voltage);
							 obj.put("current", current);
							 obj.put("pf", pf);
							 obj.put("frequency", frequency);
							 obj.put("avg_kwh", avh_kwh) ;
							 obj.put("creditBalance", "NA") ;
							 obj.put("creditLastFor", "0") ;
							 obj.put("nextRechargeDate", "NA") ;
					 
					  if(!tarrifCode.equals("") && tarrifCode != ""){//!=null
							String query1 = "select ec_slab1 as slab1,ec_rate1 as rate1,ec_slab2 as slab2,ec_rate2  as rate2,ec_slab3 as slab3,ec_rate3  as rate3,ec_slab4 as slab4,ec_rate4  as rate4,ec_slab5 as slab5,ec_rate5  as rate5  \r\n" + 
									"from meter_data.tariff_rate_master where tariffcode='"+tarrifCode+"'";
				    		list1= androidService.getCustomEntityManager("postgresMdas").createNativeQuery(query1).getResultList();
				    		for (int j = 0; j < list1.size(); j++) {
				    			Object[] columns1=(Object[]) list1.get(i);
				    			//System.out.println("slab1Rate=="+columns1[1]+ "slab2Rate=="+columns1[3]+"slab3Rate=="+columns1[5]+"slab4Rate=="+columns1[7]);
				    			String slab1=String.valueOf(columns1[0]);
				    			String slab1Rate=String.valueOf(columns1[1]);
				    			String slab2=String.valueOf( columns1[2]);
				    			String slab2Rate=String.valueOf(columns1[3]);
				    			String slab3=String.valueOf(columns1[4]);
				    			String slab3Rate=String.valueOf(columns1[5]);
				    			String slab4=String.valueOf(columns1[6]);
				    			String slab4Rate=String.valueOf( columns1[7]);
				    			String slab5=String.valueOf(columns1[8]);
				    			String slab5Rate=String.valueOf( columns1[9]);
				    			obj.put("fixedCost", slab1Rate);
								
								
								if( kwh < Integer.valueOf((int) Math.round( Double.parseDouble(slab1)))) {
									//System.out.println("Slab 1 rate=="+String.valueOf(Float.valueOf(slab5Rate) * Float.valueOf(avh_kwh)));
									obj.put("unitConsumptionCharge", slab1Rate);
									obj.put("costperDay", String.valueOf(Float.valueOf(slab1Rate) * Float.valueOf(avh_kwh)));
								}
								if( kwh > Integer.valueOf((int) Math.round( Double.parseDouble(slab1))) && kwh < Integer.valueOf((int) Math.round( Double.parseDouble(slab2)))) {
									System.out.println("Slab 2 rate");
									obj.put("unitConsumptionCharge", slab2Rate);
									obj.put("costperDay", String.valueOf(Float.valueOf(slab2Rate) * Float.valueOf(avh_kwh)));
								}
								if( kwh > Integer.valueOf((int) Math.round( Double.parseDouble(slab2))) && kwh < Integer.valueOf((int) Math.round( Double.parseDouble(slab3)))) {
									System.out.println("Slab 3 rate");
									obj.put("unitConsumptionCharge", slab3Rate);
									obj.put("costperDay", String.valueOf(Float.valueOf(slab3Rate) * Float.valueOf(avh_kwh)));
								}
								if( kwh > Integer.valueOf((int) Math.round( Double.parseDouble(slab3))) && kwh < Integer.valueOf((int) Math.round( Double.parseDouble(slab4)))) {
									System.out.println("Slab 4 rate");
									obj.put("unitConsumptionCharge", slab4Rate);
									obj.put("costperDay", String.valueOf(Float.valueOf(slab4Rate) * Float.valueOf(avh_kwh)));
								}
								if( kwh > Integer.valueOf((int) Math.round( Double.parseDouble(slab4)))) {
									System.out.println("Slab 5 rate");
									obj.put("unitConsumptionCharge", slab5Rate); 
									obj.put("costperDay", String.valueOf(Float.valueOf(slab1Rate) * Float.valueOf(avh_kwh)));
									//obj.put("costperDay", Integer.valueOf((int) Math.round( Double.parseDouble(slab5Rate)))*kwh);
								}
								
								obj.put("slab1Rate", slab1Rate);
								obj.put("slab2Rate", slab2Rate);
								obj.put("slab3Rate", slab3Rate);
								obj.put("slab4Rate", slab4Rate);
								obj.put("slab5Rate", slab5Rate);
								
				    		}
				    		
				    		
						}
					 
					  array.put( obj);
					

				}
			}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	   return array.toString();

    }
    

    @RequestMapping(value = "/currentDailyConsumptionAnalysis/{kno}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object currentDailyConsumptionAnalysis(@PathVariable String kno
			)throws Exception
	{
		String sql="select read_time,to_char(read_time-interval '15 Minutes','HH24:MI')||' to '||to_char(read_time,'HH24:MI') as duration ,kwh/1000 as units,round((kwh/1000)*3.85,2) as cost  from meter_data.load_survey where read_time>to_timestamp(to_char(CURRENT_DATE,'yyyy-MM-DD')||' 00:00:00','yyyy-MM-DD HH24:MI:SS') and meter_number in (select mtrno from meter_data.master_main where kno='"+kno+"') ORDER BY read_time";
		
		return androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	}
    
    @RequestMapping(value = "/previousDayConsumptionAnalysis/{kno}", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Object previousDayConsumptionAnalysis(@PathVariable String kno
			)throws Exception
	{
		String sql="select a.meter_number,a.cdate,case when dp=0 then 'SUNDAY' when a.dp=1 then 'MONDAY' when a.dp=2 then 'TUESDAY' when a.dp=3 then 'WEDNESDAY' when a.dp=4 then 'THURSDAY'  when a.dp=5 then 'FRIDAY' when a.dp=6 then 'SATURDAY' end as dayName, a.kwh,a.kwh*3.85 as cost  from (select meter_number,to_char(tod_gdate,'YYYY-MM-DD') as cdate,EXTRACT(DOW FROM  to_timestamp(to_char(tod_gdate,'YYYY-MM-DD'),'YYYY-MM-DD')) as dp ,(case when kwh1 is null then 0 else kwh1 end +case when kwh2 is null then 0 else kwh2 end+case when kwh3 is null then 0 else kwh3 end+case when kwh4 is null then 0 else kwh4 end+case when kwh5 is null then 0 else kwh5 end+case when kwh6 is null then 0 else kwh6 end)/1000 as kwh   from meter_data.tod_wise_daily_data_aggregation where  meter_number in (select mtrno from meter_data.master_main where kno='"+kno+"') and tod_gdate<CURRENT_DATE and  tod_gdate>CURRENT_DATE -INTERVAL '7 days'   ORDER BY  to_char(tod_gdate,'YYYY-MM-DD') desc) a";
		
		return androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	}
    
    @RequestMapping(value = "/weeklyConsumptionAnalysis/{kno}/{month}", method = { RequestMethod.GET,RequestMethod.POST })
  	public @ResponseBody Object previousDayConsumptionAnalysis(@PathVariable String kno,@PathVariable String month
  			)throws Exception
  	{
  		String sql="select (b.mind||' To '|| b.maxd) as weekd,b.kwh,b.kwh*3.85 as cost from (select a.meter_number,sum((case when a.kwh1 is null then 0 else a.kwh1 end +case when a.kwh2 is null then 0 else a.kwh2 end+case when a.kwh3 is null then 0 else a.kwh3 end+case when a.kwh4 is null then 0 else a.kwh4 end+case when a.kwh5 is null then 0 else a.kwh5 end+case when a.kwh6 is null then 0 else a.kwh6 end)/1000 ) as kwh,min(a.tod_gdate) as mind,max(a.tod_gdate) as maxd,a.wn from (select meter_number,tod1s,tod1e,kwh1,tod2s,tod2e,kwh2,tod3s,tod3e,kwh3,tod4s,tod4e,kwh4,tod5s,tod5e,kwh5,tod6s,tod6e,kwh6,tod_gdate,\n" +
  				"(extract('day' from date_trunc('week', tod_gdate) -date_trunc('week', date_trunc('month', tod_gdate))) / 7 + 1) as wn\n" +
  				"from meter_data.tod_wise_daily_data_aggregation where to_char(tod_gdate,'YYYYMM')='"+month+"' and meter_number in (select mtrno from meter_data.master_main where kno='"+kno+"') ) a GROUP BY a.meter_number,a.wn,a.tod1s,a.tod1e,a.tod2s,a.tod2e,a.tod3s,a.tod3e,a.tod4s,a.tod4e,a.tod5s,a.tod5e,a.tod6s,a.tod6e) b";
  		return androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
  	}
    
    

    @RequestMapping(value = "/todWiseDailyConsumption/{kno}/{month}", method = { RequestMethod.GET,RequestMethod.POST },produces = {
	"application/json; charset=UTF-8" })
 	public @ResponseBody Object todWiseDailyConsumption(@PathVariable String kno,@PathVariable String month
 			)throws Exception
 	{
		/*
		 * String
		 * sql="select meter_number	,tod1s	,tod1e	,kwh1	,tod2s	,tod2e	,kwh2	,tod3s	,tod3e	,kwh3	,tod4s	,tod4e	,kwh4	,tod5s	,tod5e	,kwh5	,tod6s	,tod6e	,kwh6	,tod7s	,\n"
		 * +
		 * "tod7e	,kwh7	,tod8s	,tod8e	,kwh8	from meter_data.tod_wise_daily_data_aggregation where meter_number in  (select mtrno from meter_data.master_main where kno='"
		 * +kno+"') and tod_gdate='"+date+"'";
		 */
    	String sql="select meter_number	,tod1s	,tod1e	,kwh1	,tod2s	,tod2e	,kwh2	,tod3s	,tod3e	,kwh3	,tod4s	,tod4e	,kwh4	,tod5s	,tod5e	,kwh5	,tod6s	,tod6e	,kwh6	\n" +
 				",tod_gdate	from meter_data.tod_wise_daily_data_aggregation where meter_number in  (select mtrno from meter_data.master_main where kno='"+kno+"') and to_char(tod_gdate,'MMYYYY')='"+month+"' order by tod_gdate ";
 		List<Object[]> l= androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
 		Object[] o=l.get(0);
 		String meter_number=o[0].toString();
 		List<String> todl=new ArrayList<>();
 		String tod1=o[1].toString()+"-"+o[2].toString();
 		String tod2=o[4].toString()+"-"+o[5].toString();
 		String tod3=o[7].toString()+"-"+o[8].toString();
 		String tod4=o[10].toString()+"-"+o[11].toString();
 		String tod5=o[13].toString()+"-"+o[14].toString();
 		String tod6=o[16].toString()+"-"+o[17].toString();
 		todl.add(0,tod1);
 		todl.add(1,tod2);
 		todl.add(2,tod3);
 		todl.add(3,tod4);
 		todl.add(4,tod5);
 		todl.add(5,tod6);
 		Map<String,List<String>> tod=new HashMap<>();
 		tod.put("tod", todl);
 		List<Map<String,List<String>>> datesl= new ArrayList<>();
 		for (Object[] obj : l) {
 			Map<String,List<String>> m=new HashMap<>();
 			List<String> dal=new ArrayList<>();
 			List<String> dl=new ArrayList<>();
 			if(obj[3]==null) {
 				dl.add(0,"0.0");
 			}
 			else dl.add(0,obj[3].toString());
 			if(obj[6]==null) {
 				dl.add(1,"0.0");
 			}
 			else dl.add(1,obj[6].toString());
 			if(obj[9]==null) {
 				dl.add(2,"0.0");
 			}
 			else dl.add(2,obj[9].toString());
 			if(obj[12]==null) {
 				dl.add(3,"0.0");
 			}
 			else dl.add(3,obj[12].toString());
 			if(obj[15]==null) {
 				dl.add(4,"0.0");
 			}
 			else dl.add(4,obj[15].toString());
 			if(obj[18]==null) {
 				dl.add(5,"0.0");
 			}
 			else dl.add(5,obj[18].toString());
 			dal.add(obj[19].toString());
 			m.put("date", dal);
 			m.put("values", dl);
 			datesl.add(m);
		}
List<Object> sl=new ArrayList<>();
sl.add(tod);
sl.add(datesl);

 		return sl;
 		
 		
 	}
    
    @RequestMapping(value = "/todWiseMonthlyConsumption/{kno}", method = { RequestMethod.GET,RequestMethod.POST },produces= {"application/json; charset=UTF-8"})
 	public @ResponseBody Object todWiseMonthlyConsumption(@PathVariable String kno
 			)throws Exception
 	{
		/*
		 * String
		 * sql="select meter_number,tod1s,tod1e,sum(kwh1) as kwh1,tod2s,tod2e,sum(kwh2) as kwh2,tod3s,tod3e,sum(kwh3) as kwh3,tod4s,tod4e,sum(kwh4) as kwh4,tod5s,tod5e,sum(kwh5) as kwh5,tod6s,tod6e,sum(kwh6) as kwh6,tod7s,tod7e,sum(kwh7) as kwh7,tod8s,tod8e,sum(kwh8) as kwh8 from meter_data.tod_wise_daily_data_aggregation where meter_number in  (select mtrno from meter_data.master_main where kno='"
		 * +kno+"')  and to_char(tod_gdate,'MMYYYY')='"
		 * +month+"'  GROUP BY meter_number,tod1s,tod1e,tod2s,tod2e,tod3s,tod3e,tod4s,tod4e,tod5s,tod5e,tod6s,tod6e,tod7s,tod7e,tod8s,tod8e"
		 * ;
		 */
 		String sql="select meter_number,tod1s,tod1e,sum(kwh1) as kwh1,tod2s	,tod2e,sum(kwh2) as kwh2,tod3s,tod3e,sum(kwh3) as kwh3,tod4s	,tod4e,sum(kwh4) as kwh4,tod5s,tod5e,sum(kwh5) as kwh5,tod6s,tod6e,sum(kwh6) as kwh6,to_char(tod_gdate,'MMYYYY') as tod_gdate from meter_data.tod_wise_daily_data_aggregation where meter_number in  (select mtrno from meter_data.master_main where kno='"+kno+"')   \n" +
 				"GROUP BY meter_number,to_char(tod_gdate,'MMYYYY'),tod1s,tod1e,tod2s	,tod2e,tod3s,tod3e,tod4s,tod4e,tod5s,tod5e,tod6s,tod6e";
 		
 		List<Object[]> l= androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
 		Object[] o=l.get(0);
 		String meter_number=o[0].toString();
 		List<String> todl=new ArrayList<>();
 		String tod1=o[1].toString()+"-"+o[2].toString();
 		String tod2=o[4].toString()+"-"+o[5].toString();
 		String tod3=o[7].toString()+"-"+o[8].toString();
 		String tod4=o[10].toString()+"-"+o[11].toString();
 		String tod5=o[13].toString()+"-"+o[14].toString();
 		String tod6=o[16].toString()+"-"+o[17].toString();
 		todl.add(0,tod1);
 		todl.add(1,tod2);
 		todl.add(2,tod3);
 		todl.add(3,tod4);
 		todl.add(4,tod5);
 		todl.add(5,tod6);
 		Map<String,List<String>> tod=new HashMap<>();
 		tod.put("tod", todl);
 		List<Map<String,List<String>>> datesl= new ArrayList<>();
 		for (Object[] obj : l) {
 			Map<String,List<String>> m=new HashMap<>();
 			List<String> dal=new ArrayList<>();
 			List<String> dl=new ArrayList<>();
 			if(obj[3]==null) {
 				dl.add(0,"0.0");
 			}
 			else dl.add(0,obj[3].toString());
 			if(obj[6]==null) {
 				dl.add(1,"0.0");
 			}
 			else dl.add(1,obj[6].toString());
 			if(obj[9]==null) {
 				dl.add(2,"0.0");
 			}
 			else dl.add(2,obj[9].toString());
 			if(obj[12]==null) {
 				dl.add(3,"0.0");
 			}
 			else dl.add(3,obj[12].toString());
 			if(obj[15]==null) {
 				dl.add(4,"0.0");
 			}
 			else dl.add(4,obj[15].toString());
 			if(obj[18]==null) {
 				dl.add(5,"0.0");
 			}
 			else dl.add(5,obj[18].toString());
 			dal.add(obj[19].toString());
 			m.put("month", dal);
 			m.put("values", dl);
 			datesl.add(m);
		}
 		List<Object> sl=new ArrayList<>();
 		sl.add(tod);
 		sl.add(datesl);

 		 		return sl;
 		
 		
 	}
    @RequestMapping(value="/meterLoadProfile/{kno}/{pdate}",method= {RequestMethod.GET},produces= {"application/json; charset=UTF-8"})
    public @ResponseBody Object loadProfile(@PathVariable String kno,@PathVariable String pdate) {
    	String sql="select meter_number,to_char(read_time,'HH24:MI'),(kwh/1000)*(select mf from meter_data.consumermaster where kno='"+kno+"') as kwh   from meter_data.load_survey where meter_number in (select meterno from meter_data.consumermaster where kno='"+kno+"') and to_char(read_time,'YYYY-MM-DD')='"+pdate+"' ORDER BY read_time desc";
      return androidService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
    
    }
    
    public static String sendSMSGUPSHUP(String message,String mobileno) 
	{
			String sent	="";
			//BCITSLogger.logger.info("message --------"+message);
			try 
			{
				HttpClient client=null;
				PostMethod post=null;
		
				//SMS GUPSHUP
				String user		=	"2000162040";
				String password	=	"bcits1234";
				String sURL		= 	"http://enterprise.smsgupshup.com/GatewayAPI/rest";
				String mask		= 	"BCITSI";
				
				//enterprise-support@smsgupshup.com
				
				client 			= 	new HttpClient(new MultiThreadedHttpConnectionManager());
				client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);//set	your time
				post 			= 	new PostMethod(sURL);
				
				//SMS GUPSHUP
				post.addParameter("method", "SendMessage");
				post.addParameter("send_to", mobileno);
				post.addParameter("msg", message);
				post.addParameter("msg_type","TEXT");//post.addParameter("msg_type","UNICODE_TEXT");
				post.addParameter("userid", user);
				post.addParameter("auth_scheme", "plain");
				post.addParameter("password", password);
				post.addParameter("v", "1.1");
				post.addParameter("format", "text");
				
				//MASK ADDED NEW PARMETER ON 
				post.addParameter("mask", mask);
				
				/*BCITSLogger.logger.info("user --------"+user);
				BCITSLogger.logger.info("mobileno ----"+mobileno);*/
				
				/* PUSH THE URL */
				try 
				{
					int statusCode = client.executeMethod(post);
					if (statusCode != HttpStatus.SC_OK) {
						//System.err.println("Method failed: " + post.getStatusLine());
					}
					else{
						//System.err.println("Method success: ");
					}
					
					
					//BCITSLogger.logger.info("statusCode --------"+statusCode);
					
					
					/*BCITSLogger.logger.info("statusCode --------"+statusCode);
					BCITSLogger.logger.info("line 1 ------------"+post.getStatusLine().toString());
					BCITSLogger.logger.info("SC_OK -------------"+HttpStatus.SC_OK);
					BCITSLogger.logger.info("line 2 ------------"+post.getResponseBodyAsString());
					
					if(post.getResponseBodyAsString().contains("Failed"))
					{
						BCITSLogger.logger.info("Failed ------------"+sent);
					}
					else
					{
						BCITSLogger.logger.info("Success ------------"+sent);
						
					}*/
					
					sent	=	post.getResponseBodyAsString().toString();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				finally 
				{
					post.releaseConnection();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return sent;
	}
}
