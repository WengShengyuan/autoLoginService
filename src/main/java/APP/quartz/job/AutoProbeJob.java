package APP.quartz.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import APP.utils.NetUtils;

public class AutoProbeJob implements Job{

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String probeIp = (String) jobDataMap.get("probeIp");
		String ip = (String) jobDataMap.get("ip");
		String port = (String) jobDataMap.get("port");
		String userName = (String) jobDataMap.get("userName");
		String passwd = (String) jobDataMap.get("passwd");
		String writePath = (String) jobDataMap.get("writePath");
		
		String url = String.format("http://192.168.100.253:7755/login.cgi?act=login&s=%s&p=%s&usrname=%s&usrpwd=%s",
				ip, port, userName.toUpperCase(), passwd);
		 
		try {
			try {
				boolean status = probe();
				if(!status){
					System.out.println("****  ["+new Date()+"]  Start logging in  ****");
					postLogin(userName, passwd,writePath);
				}
			} catch (UnknownHostException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd,writePath);
			} catch (IOException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd,writePath);
			} catch (Exception e){
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd,writePath);
				}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		
	}
	
	private boolean probe () throws Exception{
		String s = NetUtils.get("http://www.baidu.com", "UTF-8");
//		System.out.println("PROBE BAIDU GET:\n"+s+"\n\n");
		if(s.contains("192.168.100.253:7755"))
			return false;
		else
			return true;
	}
	
	private void postLogin(String name, String pwd, String writePath) throws Exception{
		String url = "http://192.168.100.253:5280/login";
		boolean status = false;
		String r = NetUtils.post(url, String.format("usrname=%s&usrpwd=%s", name,pwd));
		System.out.println("HTTP POST GET:\n"+r+"\n\n");
		if(r.contains("on-line") || r.contains("Please keep this window opening and press button for exit!")){
			status = true;
			System.out.println("****  ["+new Date()+"]  login Success  ****");
			if(r.contains("Please keep this window opening and press button for exit!")){
				System.out.println("****  ["+new Date()+"]  writting HTML to:"+writePath+"  ****");
				writePath(writePath,r);
			}
			
		}
	}
	
	private void writePath(String writePath,String content) {
		OutputStream out=null;
		try {
			File outFile = new File(writePath);
			out= new FileOutputStream(outFile,false);
			IOUtils.write(content+"\n", out, "UTF-8");
			out.close();
		} catch (Exception e) {
			System.out.println("****  ["+new Date()+"]  write HTML fail  ****");
		} 
	}
}
