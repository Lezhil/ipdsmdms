package com.connode.service.terminal.ws.client;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.codec.binary.Base64;

import com.connode.service.terminal.ws.NodeServices;
import com.connode.service.terminal.ws.NodeServices_Service;
import com.connode.service.terminal.ws.WSNodeException;
import com.connode.service.terminal.ws.WsNodeSearch;
import com.connode.service.terminal.ws.WsNodeSearchResult;
import com.connode.service.terminal.ws.WsPing;
import com.sun.xml.internal.ws.client.Stub;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoapSeriveTest {

	public static final String hesuser = "sysadmin";
	public static final String hespass = "sysadmin";
	


	public static void main(String[] args) throws MalformedURLException, WSNodeException {
		String s=wsdlAuth();
		NodeServices_Service ns=new NodeServices_Service();
		NodeServices nse=ns.getNodeServices();
		BindingProvider prov = (BindingProvider)nse;
		prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, hesuser);
		prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, hespass);
		
		WsNodeSearch sc=new WsNodeSearch();
		sc.setItemStart(1);
		sc.setItemCount(50);
				WsNodeSearchResult wp=nse.searchNodes(sc);
		//WsPing wp=nse.pingNode("00-1b-c5-0c-60-04-67-33");
	        System.out.println(wp);
		
		
	}
	public static String wsdlAuth(){
		Authenticator.setDefault(new Authenticator() {
			 @Override
			 protected PasswordAuthentication getPasswordAuthentication() {
			   return new PasswordAuthentication(
			     "sysadmin",
			     ("sysadmin").toCharArray());
			 }
			});
		return "succ";
	}
	
}
