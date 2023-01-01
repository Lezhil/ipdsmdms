package com.bcits.firebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class FireMessageCordova {
	private final String SERVER_KEY = "AAAAthOpHgg:APA91bH8C5X9QiUfR18fKT70OTymsbNsSCLTdoLSlmLCEPRP6tvYEjSD4vwtW1A2yFM5VZxmsgtxFTHIL_TRAscMsYUJDv7a4NhzRu2L8juT_OqLgHekpY51qLRxC0TCNDdSSgpI8vRH";
	private final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	private JSONObject root;
	
	public FireMessageCordova(String title, String message, String from, String senderId) throws JSONException {
		root = new JSONObject();
		
		JSONObject noty = new JSONObject();//NOTIFICATION
		noty.put("title", title);
		noty.put("body", message);
		noty.put("sound", "default");
		noty.put("sender", from);
		noty.put("senderId", senderId);
		noty.put("click_action", "FCM_PLUGIN_ACTIVITY");
		noty.put("icon", "fcm_push_icon");
		root.put("notification", noty);
		
		JSONObject data = new JSONObject();//DATA MESSAGE
		data.put("title", title);
		data.put("message", message);
		data.put("sender", from);
		data.put("senderId", senderId);
		root.put("data", data);
	}
	
	
	public String sendToTopic(String topic) throws Exception { //SEND TO TOPIC
		System.out.println("Send to Topic");
		root.put("to", "/topics/"+topic);
		return sendPushNotification(true);
	}

	public String sendToGroup(JSONArray mobileTokens) throws Exception { // SEND TO GROUP OF PHONES - ARRAY OF TOKENS
		root.put("registration_ids", mobileTokens);
		return sendPushNotification(false);
	}

	public String sendToToken(String token) throws Exception {//SEND MESSAGE TO SINGLE MOBILE - TO TOKEN
		root.put("to", token);
		return sendPushNotification(false);
	}
	
 
	
		private String sendPushNotification(boolean toTopic)  throws Exception {
		    URL url = new URL(API_URL_FCM);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		    conn.setUseCaches(false);
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setRequestMethod("POST");
		    
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setRequestProperty("Accept", "application/json");
	    	conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
	    	
	    	root.put("priority", "high");
	    	
            System.out.println(root.toString());
            
		    try {
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        wr.write(root.toString());
		        wr.flush();

		        BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

		        String output;
		        StringBuilder builder = new StringBuilder();
		        while ((output = br.readLine()) != null) {
		        	builder.append(output);
		        }
		        System.out.println(builder);
		        String result = builder.toString();
		        
		        JSONObject obj = new JSONObject(result);
				
		        if(toTopic){
		        	if(obj.has("message_id")){
		        		return  "SUCCESS";
		        	}
			   } else {
				int success = Integer.parseInt(obj.getString("success"));
				if (success > 0) {
					return "SUCCESS";
				}
			}
		        
		        return builder.toString();
		    } catch (Exception e) {
		        e.printStackTrace();
		       return e.getMessage();
		    }

		}
}
