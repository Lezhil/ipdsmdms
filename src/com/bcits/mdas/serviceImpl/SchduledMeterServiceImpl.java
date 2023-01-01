package com.bcits.mdas.serviceImpl;


import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.mdas.controller.HESController;
import com.bcits.mdas.entity.SchduledMeterTrackEntity;
import com.bcits.mdas.service.SchduledMeterService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class SchduledMeterServiceImpl extends GenericServiceImpl<SchduledMeterTrackEntity> implements SchduledMeterService{
	
	

	

	@Override
	@Transactional
		public  String meterSamples(int count,long startId) {
		System.out.println(count+"-----"+startId);
			String url = HESController.hesurl + "/meterSamples";
			
			System.out.println("Meter Sample URL : "+url);
			
			try {
				//HttpClient httpClient = HttpClientBuilder.create().build();
				HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts();
				HttpPost httpRequest = new HttpPost(url);
				httpRequest.setHeader("Content-Type", "application/json");
				httpRequest.setHeader("Authorization", "Basic " + HESController.authStringenc);

				JSONObject obj = new JSONObject();
				obj.put("count", count);
				obj.put("startId", startId);
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
	
		public HttpClient createHttpClient_AcceptsUntrustedCerts() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
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

	

	@Transactional(propagation=Propagation.REQUIRED)
	public int highcount() 
	{
		BigDecimal hn;
		int i=0;
		
		try
		{
		 hn =(BigDecimal) postgresMdas.createNativeQuery("select max(seq_id) from meter_data.sch_meter_track")
				.getSingleResult();
		 i=hn.intValue();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return i;
	}
	


}
