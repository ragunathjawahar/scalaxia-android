package com.scalaxia.app.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Network {
	
	public static String httpGet(String targeturl) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		
		StringBuffer buffer = null;
		try {
			URL url = new URL(targeturl);
			URLConnection uc = url.openConnection();
			connection = (HttpURLConnection) uc;
			connection.setRequestMethod("GET");
			
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			
			buffer = new StringBuffer();
			String line = null;
			String lineSeparator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {
				buffer.append(line);
				buffer.append(lineSeparator);
			}
		} catch (Exception e) {
			//Do nothing
		} finally {
			//Cleanup
			try {
				if(br != null) br.close();
				if(is != null) is.close();
				if(connection != null) connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return buffer == null ? null : buffer.toString();
	}
}
