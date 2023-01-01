package com.bcits.mdas.controller;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.NeteworkNodeEventsParameters;
import com.bcits.mdas.entity.NetworkNodeEvents;
import com.bcits.mdas.entity.SearchNodes;
import com.bcits.mdas.service.NamePlateService;
import com.bcits.mdas.service.NeteworkNodeEventsParametersService;
import com.bcits.mdas.service.NetworkNodeEventsService;
import com.bcits.mdas.service.SearchNodesService;
import com.connode.service.terminal.ws.NodeServices;
import com.connode.service.terminal.ws.NodeServices_Service;
import com.connode.service.terminal.ws.WSNodeException;
import com.connode.service.terminal.ws.WsEvent;
import com.connode.service.terminal.ws.WsNode;
import com.connode.service.terminal.ws.WsNodeEventSearchResult;
import com.connode.service.terminal.ws.WsNodeSearch;
import com.connode.service.terminal.ws.WsNodeSearchResult;
import com.connode.service.terminal.ws.WsNodeTopology;
import com.connode.service.terminal.ws.WsNodeTopologyView;
import com.connode.service.terminal.ws.WsPing;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

@Controller
public class SoapSeriveController {

	public static final String hesuser = "sysadmin";
	public static final String hespass = "sysadmin";
	@Autowired
	NamePlateService nps;
	@Autowired
	NetworkNodeEventsService nnes;
	@Autowired
	NeteworkNodeEventsParametersService nneps;

	@Autowired
	SearchNodesService sns;

	// @RequestMapping(value="/soaptest")
	// @Scheduled(cron = "0 0/5 * * * ?")
	public void pingNode()
			throws MalformedURLException, WSNodeException, KeyManagementException, NoSuchAlgorithmException {
		NodeServices nse = serviceAuth();
		WsPing wp = nse.pingNode("00-1b-c5-0c-60-04-67-33");
		System.out.println(wp.getRoundtripTimeMillis());
	}

	// @RequestMapping(value="/soaptest")
	// @Scheduled(cron = "0/30 * * * * ?")
	public void getNodeById()
			throws MalformedURLException, WSNodeException, KeyManagementException, NoSuchAlgorithmException {
		NodeServices nse = serviceAuth();
		List<Object[]> l = nps.nodeIdList();
		for (Object[] o : l) {
			WsNode wn = nse.getNodeById(o[1].toString());
			System.out.println(wn.getGatewayNodeId());
		}

	}

	Long count = 0L;

	// @RequestMapping(value="/soaptest")
	@Async
	// @Scheduled(cron = "0 0/5 * * * ?")
	public void getNodeEvents()
			throws MalformedURLException, WSNodeException, KeyManagementException, NoSuchAlgorithmException {
		NodeServices nse = serviceAuth();
		try {
			count = (long) nnes.highcount();
			if (count != null) {
				System.out.println("Total count is :" + count);
				count++;
			} else {
				count = 1L;
			}
		} catch (NullPointerException e) {
			count = 1L;
		}
		WsNodeEventSearchResult wesr = nse.getNodeEvents(count, 1000);

		List<WsEvent> li = wesr.getItems();
		for (WsEvent wsEvent : li) {
			NetworkNodeEvents nne = new NetworkNodeEvents();
			nne.setTimestamp(new Timestamp(new Date().getTime()));
			nne.setCreatetime(new Timestamp(XMLGregorianCalendarToDate(wsEvent.getEventTime()).getTime()));
			nne.setSeqId((int) wsEvent.getId());
			nne.setNodeId(wsEvent.getNodeId());
			nne.setSequenceNumber(wsEvent.getSequenceNumber());
			nne.setStatus(wsEvent.getStatus());
			nne.setServerTimestamp(new Timestamp(XMLGregorianCalendarToDate(wsEvent.getTimestamp()).getTime()));
			nne.setDescription(wsEvent.getDescription());
			nne.setEventTime(new Timestamp(XMLGregorianCalendarToDate(wsEvent.getEventTime()).getTime()));
			nne.setNumericCode(Double.parseDouble(wsEvent.getNumericCode()));
			nne.setTextCode(wsEvent.getTextCode());
			List<String> ls = wsEvent.getParameters();
			// List<NeteworkNodeEventsParameters> nl=new
			// ArrayList<NeteworkNodeEventsParameters>();
			for (String string : ls) {
				NeteworkNodeEventsParameters np = new NeteworkNodeEventsParameters();
				np.setParameter(string);
				np.setSeqId((int) wsEvent.getId());
				np.setTimestamp(new Timestamp(new Date().getTime()));
				nneps.save(np);

			}

			nnes.save(nne);
		}

	}

	Long scount = 0L;

	// @Scheduled(cron = "0 0 0/1 * * ?")
	public void searchNodes() throws WSNodeException, KeyManagementException, NoSuchAlgorithmException {
		NodeServices nse = serviceAuth();
		WsNodeSearch sc = new WsNodeSearch();
		try {
			scount = (long) sns.highcount();
			if (scount != null) {

				scount++;
			} else {
				scount = 1L;
			}
		} catch (NullPointerException e) {
			scount = 1L;
		}

		sc.setItemStart(Math.toIntExact(scount));
		sc.setItemCount(10000);
		WsNodeSearchResult wp = nse.searchNodes(sc);
		List<WsNode> l = wp.getItems();
		for (WsNode wsNode : l) {
			SearchNodes sn = new SearchNodes();
			sn.setTimestamp(new Timestamp(new Date().getTime()));
			if (!(wsNode.getAddress()==null)) {
				sn.setAddress(wsNode.getAddress());
			}
			sn.setCreateTime(new Timestamp(XMLGregorianCalendarToDate(wsNode.getCreateTime()).getTime()));
			sn.setCurrentFirmwareVersion(wsNode.getCurrentFirmwareVersion());
			sn.setGatewayNodeId(wsNode.getGatewayNodeId());
			sn.setHardwareVersion(wsNode.getHardwareVersion());
			sn.setSeqId((int) wsNode.getId());
			if (!(wsNode.getLatitude() == null)) {
				sn.setLatitude(wsNode.getLatitude());
			}
			if (!(wsNode.getLongitude() == null)) {
				sn.setLongitude(wsNode.getLongitude());
			}
			sn.setNodeId(wsNode.getNodeId());
			sn.setNodeType(wsNode.getNodeType());
			if (!(wsNode.getParentNodeId()==null)) {
				sn.setParentNodeId(wsNode.getParentNodeId());
			}
			sn.setPort(wsNode.getPort());
			if (!(wsNode.getServerName()==null)) {
				sn.setServerName(wsNode.getServerName());
			}
			sn.setStatus(wsNode.getStatus());
			sn.setUpdateTime(new Timestamp(XMLGregorianCalendarToDate(wsNode.getUpdateTime()).getTime()));
			sns.save(sn);
		}

	}

	// @Scheduled(cron = "0 0 0/1 * * ?")
	public void updateLocations() {
		sns.updateLocationsService();
	}
   @Transactional
	@RequestMapping(value = "/nodeTopologyView/{nodeid}/{type}", method = { RequestMethod.GET })
	public @ResponseBody Object topologyService(@PathVariable String nodeid,@PathVariable String type ,HttpServletRequest req)
			throws KeyManagementException, NoSuchAlgorithmException, WSNodeException {

		NodeServices nse = serviceAuth();
		WsNodeTopologyView tv = nse.getNodeTopologyView(nodeid);
		WsNodeTopology wnt = tv.getOriginNode();
		String gatewayNodeId = wnt.getNode().getNodeId();
		List<WsNodeTopology> lwnt = tv.getTopologies();
		JsonArray jaNodeWise = new JsonArray();
		String ns = "";
		Map<String,String> meterm=new HashMap<>();
		List<String> s=new ArrayList<>();
		for (WsNodeTopology wsNodeTopology : lwnt) {
			List<WsNodeTopology> lwntc = wsNodeTopology.getChildren();
			for (WsNodeTopology wsNodeTopology2 : lwntc) {
				List<WsNodeTopology> lwntci = wsNodeTopology2.getChildren();
				for(WsNodeTopology wsNodeTopology3 : lwntci) {
					JsonArray ja = new JsonArray();
					String parentNodeId = wsNodeTopology3.getNode().getParentNodeId();
					String nodeId = wsNodeTopology3.getNode().getNodeId();
					ja.add(new JsonPrimitive(parentNodeId.substring(parentNodeId.length()-5, parentNodeId.length())));
					ja.add(new JsonPrimitive(nodeId.substring(nodeId.length()-5, nodeId.length())));
					jaNodeWise.add(ja);
					s.add(nodeId);
					meterm.put(nodeId, parentNodeId);
				}
				JsonArray ja = new JsonArray();
				String parentNodeId = wsNodeTopology2.getNode().getParentNodeId();
				String nodeId = wsNodeTopology2.getNode().getNodeId();
				ja.add(new JsonPrimitive(parentNodeId.substring(parentNodeId.length()-5, parentNodeId.length())));
				ja.add(new JsonPrimitive(nodeId.substring(nodeId.length()-5, nodeId.length())));
				jaNodeWise.add(ja);
				s.add(nodeId);
				meterm.put(nodeId, parentNodeId);
			}
		}
		JsonArray meterA=new JsonArray();
		if(type.equalsIgnoreCase("meter")) {
		for (String sn : s) {
			ns+="'"+sn+"',";
		}
		ns=ns.substring(0,ns.length()-1);
		String sb="select meter_serial_number,node_id from meter_data.name_plate where node_id in ("+ns+")";
		List<Object[]> l=sns.getCustomEntityManager("postgresMdas").createNativeQuery(sb).getResultList();
		
		for (String ms : meterm.keySet()) {
		String cn=meterm.get(ms);
		JsonArray metercA=new JsonArray();
		if(gatewayNodeId.equalsIgnoreCase(cn)) {
			for(Object[] o:l){
				if(ms.equalsIgnoreCase(o[1].toString())) {
					metercA.add(new JsonPrimitive(cn.substring(cn.length()-5,cn.length())));
					metercA.add(new JsonPrimitive(o[0].toString()));
				}
			}
		}
		else {
			String pn="";
			String chn="";
			for(Object[] o:l){
				if(ms.equalsIgnoreCase(o[1].toString())) {
					pn=o[0].toString();
				}
				if(cn.equalsIgnoreCase(o[1].toString())) {
					chn=o[0].toString();
				}
			}
			metercA.add(new JsonPrimitive(pn));
			metercA.add(new JsonPrimitive(chn));
		}
		meterA.add(metercA);
		}
		}
		Gson g=new Gson();
		if(type.equalsIgnoreCase("meter")) {
			 return g.toJson(meterA);
		}
		else {
			 return g.toJson(jaNodeWise);
		}
       
	}

	private NodeServices serviceAuth() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				// Trust always
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				// Trust always
			}
		} };
		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		// Create empty HostnameVerifier
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};

		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		NodeServices_Service ns = new NodeServices_Service();
		NodeServices nse = ns.getNodeServices();
		wsdlAuth();
		BindingProvider prov = (BindingProvider) nse;
		prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, hesuser);
		prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, hespass);
		return nse;
	}

	private String wsdlAuth() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("sysadmin", ("sysadmin").toCharArray());
			}
		});
		return "succ";
	}

	public Date XMLGregorianCalendarToDate(XMLGregorianCalendar xgc) {
		GregorianCalendar gCalendar = xgc.toGregorianCalendar();
		Date date = gCalendar.getTime();
		return date;
	}

}
