package com.connode.service.terminal.ws;




import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;


public class SoapSeriveController {

	public static void main(String[] args) throws MalformedURLException {
		URL wsdlURL = new URL("https://jvvnl-tnd1.dev.cyanconnode.com/nms/ws/NodeServices?wsdl");
		//check above URL in browser, you should see WSDL file
		
		//creating QName using targetNamespace and name
		//QName qname = new QName("http://ws.terminal.service.connode.com/", "PingNode"); 
		 final QName _PingNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "pingNode");
		Service service = Service.create(wsdlURL, _PingNode_QNAME);  
		WsPing ps = service.getPort(WsPing.class);
		Map<String, Object> req_ctx = ((BindingProvider)ps).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlURL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList("sysadmin"));
        headers.put("Password", Collections.singletonList("sysadmin"));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		
		//We need to pass interface and model beans to client
		
		
		PingNode p1 = new PingNode(); 
		p1.setNodeId("00-1b-c5-0c-60-04-03-be");
		
		
		
		
	}

}
