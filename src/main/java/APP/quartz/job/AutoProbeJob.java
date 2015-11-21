package APP.quartz.job;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import APP.utils.MySysClient;
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
		
		String url = String.format("http://192.168.100.253:7755/login.cgi?act=login&s=%s&p=%s&usrname=%s&usrpwd=%s",
				ip, port, userName.toUpperCase(), passwd);
		 
		try {
			try {
				boolean status = probe();
				if(!status){
					System.out.println("****  ["+new Date()+"]  Start logging in  ****");
					postLogin(userName, passwd);
				}
			} catch (UnknownHostException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd);
			} catch (IOException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd);
			} catch (Exception e){
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				postLogin(userName, passwd);
				}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		
	}
	
	private boolean probe () throws Exception{
		String s = NetUtils.get("http://www.baidu.com", "UTF-8");
		if(s.contains("192.168.100.253:7755"))
			return false;
		else
			return true;
	}
	
	private void postLogin(String name, String pwd) throws Exception{
		String url = "http://192.168.100.253:5280/login";
		boolean status = false;
		String r = NetUtils.post(url, String.format("usrname=%s&usrpwd=%s", name,pwd));
		System.out.println("HTTP POST GET:\n"+r+"\n\n");
		if(r.contains("on-line") || r.contains("Please keep this window opening and press button for exit!")){
			status = true;
			System.out.println("****  login Success  ["+new Date()+"]  ****");
		}
	}

}
