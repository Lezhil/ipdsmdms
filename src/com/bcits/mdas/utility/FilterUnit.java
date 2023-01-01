package com.bcits.mdas.utility;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter(filterName = "FilterUnit", urlPatterns = {"/x-frame-options/protectedForm.jsp", "/all/all.jsp"})
public class FilterUnit implements Filter {

	public static FilterConfig filterConfig;
	
	public static String mqttURL;
	public static String genusOnDemandUrl;
	public static String analogicsOnDemandUrl;
	
	public static String genusMeterChangeUrl;
	public static String analogicsMeterChangeUrl;
	public static String ftpURL, ftpUser, ftpPass, ftpServerFolder, ftpSourceFolder, logFolder, logErrorFolder, nppDataSourceFolder,service_order_savePDF;
	public static String sldUploadFolder;
	public static String sldImageServerPath;
	
	
	public static String jasperUrl;
	private String mode = "DENY";
	private String srvName = "";
	

	public  static String logFilesPath;
	
	public static String projectName;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("X-FRAME-OPTIONS", mode);
		response.setHeader("X-Frame-Options", mode); 
		//response.setHeader("Server", srvName); 
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-stale=0"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "-1"); // Proxies.
		/*String sessionid = ((HttpServletRequest) req).getSession().getId();
        response.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; HttpOnly;Secure");*/
		 /*HttpSession session = request.getSession(false);
		 System.out.println("sessgsdgs==>"+session);
         if (session != null && session.isNew()) {
             response.setHeader("Pragma", "no-cache");
             response.setHeader("Cache-Control", "no-cache");
             response.setDateHeader("Expires", 0);
         } else {
             response.sendRedirect("/bsmartmdm");
         }*/
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin,  Content-Type, Accept,  x-requested-with ");
		
		
		//Added By Amit
		response.setHeader("Server", srvName);
		response.setHeader("X-Powered-By", mode);
		response.addHeader("X-FRAME-OPTIONS", mode);
		response.setHeader("X-Frame-Options", mode);
		
		
	//	chain.doFilter(req, res);	
		

		
		response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            // response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Max-Age", "1");
        }
        chain.doFilter(request, response);	
	
		
		return;
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			//mqttURL = prop.getProperty("mqttURL");
			//ftpURL = prop.getProperty("ftpURL");
			//ftpUser = prop.getProperty("ftpUser");
			//ftpPass = prop.getProperty("ftpPass");
			//ftpServerFolder = prop.getProperty("ftpServerFolder");
			ftpSourceFolder = prop.getProperty("ftpSourceFolder");
			logFolder=prop.getProperty("logFolder");
			logErrorFolder=prop.getProperty("logErrorFolder");
			nppDataSourceFolder = prop.getProperty("nppDataSourceFolder"); 
			service_order_savePDF=prop.getProperty("service_order_savePDF"); 
			genusOnDemandUrl=prop.getProperty("genusOnDemandUrl");
			analogicsOnDemandUrl=prop.getProperty("analogicsOnDemandUrl");
			genusMeterChangeUrl=prop.getProperty("genusMeterChangeUrl");
			analogicsMeterChangeUrl=prop.getProperty("analogicsMeterChangeUrl");

			jasperUrl = prop.getProperty("JasperServerUrl");

			sldUploadFolder=prop.getProperty("sldUploadFolder");
			sldImageServerPath=prop.getProperty("sldImageServerPath");
			

			prop.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
			projectName=prop.getProperty("project.name");
			this.filterConfig=filterConfig;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try {//CREATING FOLDERS
			if (folderExists(FilterUnit.ftpSourceFolder)){
				folderExists(FilterUnit.logFolder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        // new Subscriber(filterConfig).getMeterData();
		return;
	}
	public void destroy() {
		
	}
	
	public static boolean folderExists(String rtfolder) {
		File rootFolder= new File(rtfolder);
		if(!rootFolder.exists())
		{
			try {
				rootFolder.mkdir();
				System.out.println("Created new folder");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Folder creation failed");
				return false;
			}
		}else
		{
			return true;
		}
	}
}
