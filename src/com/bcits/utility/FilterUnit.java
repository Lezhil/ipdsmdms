package com.bcits.utility;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class FilterUnit implements Filter {
	
	
	public  static String logFilesPath;

	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) res;
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin,  Content-Type, Accept,  x-requested-with ");
		chain.doFilter(req, res);		
	}

	public void init(FilterConfig arg0) {
		
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("configuration.properties"));
			logFilesPath = prop.getProperty("logFilesPath");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	public void destroy() {
		
	}
	
}
