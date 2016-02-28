package APP.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NetUtils {

	public static String get(String pageUrl, String encoding) throws Exception {

		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(pageUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream(), encoding));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			in.close();
		} catch (Exception ex) {
			throw ex;
		}
		return sb.toString();
	}

	public static String post(String postUrl,String param) throws Exception {
		URL url = new URL(postUrl);
		OutputStreamWriter out = null;
		URLConnection urlConnection=null;
		try{
			urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			out = new OutputStreamWriter(urlConnection.getOutputStream());
			out.write(param);
			out.flush();
			out.close();
			// 从服务器读取响应
			InputStream inputStream = urlConnection.getInputStream();
			String encoding = urlConnection.getContentEncoding();
			int i=0;
			StringBuilder r = new StringBuilder();
			while((i=inputStream.read())!=-1){
				r.append((char)i);
			}
			return r.toString();
		}catch(Exception e){
			throw e;
		} finally{
			out.close();
		}
		
	}
	
	
	private static String parseParam(HashMap<String, String> map)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key));
			sb.append("&");
		}
		System.out.println("param:" + sb.substring(0, sb.length() - 1));
		return sb.substring(0, sb.length() - 1);

	}

}
