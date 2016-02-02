package APP.quartz.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import org.apache.commons.io.IOUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import APP.gui.UIParamClass;
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
					UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_LOGGING);
					postLogin(userName, passwd,writePath);
				}
			} catch (UnknownHostException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_LOGGING);
				postLogin(userName, passwd,writePath);
			} catch (IOException e) {
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_LOGGING);
				postLogin(userName, passwd,writePath);
			} catch (Exception e){
				System.out.println("****  ["+new Date()+"]  GET ERROR: Start logging in  ****");
				UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_LOGGING);
				postLogin(userName, passwd,writePath);
				}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		
	}
	
	private boolean probe () throws Exception{
		String s = NetUtils.get("http://www.baidu.com", "UTF-8");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		UIParamClass.getInstance().setLastCheckDate(sdf.format(new Date()));
		UIParamClass.getInstance().setOutputLine("开始检测百度可访问性...");
		if(s.contains("192.168.100.253:7755")){
			UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_ERR);
			UIParamClass.getInstance().setOutputLine("返回HTML包含登陆地址.");
			return false;
		} else {
			UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_SUCCESS);
			UIParamClass.getInstance().setOutputLine("互联网可访问.");
			return true;
		}
	}
	
	private void postLogin(String name, String pwd, String writePath) throws Exception{
		String url = "http://192.168.100.253:5280/login";
		boolean status = false;
		UIParamClass.getInstance().setOutputLine("准备发起登陆请求.URL:"+url);
		String r = NetUtils.post(url, String.format("usrname=%s&usrpwd=%s", name,pwd));
		System.out.println("HTTP POST GET:\n"+r+"\n\n");
		if(r.contains("on-line") || r.contains("Please keep this window opening and press button for exit!")){
			status = true;
			UIParamClass.getInstance().setCheckStatus(UIParamClass.STATUS_SUCCESS);
			UIParamClass.getInstance().setOutputLine("登陆成功");
			System.out.println("****  ["+new Date()+"]  login Success  ****");
			if(r.contains("Please keep this window opening and press button for exit!")){
				UIParamClass.getInstance().setOutputLine("第一次登陆成功，写入文件："+writePath);
				System.out.println("****  ["+new Date()+"]  writting HTML to:"+writePath+"  ****");
				writePath(writePath,r);
			}
		}
	}
	
	private void writePath(String writePath,String content) {
		OutputStream out=null;
		try {
			content = content.replace("<title>Login</title>", String.format("<title>createDate[%s]</title>", new Date()));
			File outFile = new File(writePath);
			out= new FileOutputStream(outFile,false);
			IOUtils.write(content+"\n", out, "UTF-8");
			out.close();
			UIParamClass.getInstance().setOutputLine("写入文件成功:"+writePath);
		} catch (Exception e) {
			try {
				UIParamClass.getInstance().setOutputLine("写入文件失败："+writePath+"."+e.toString());
			} catch (SchedulerException e1) {
			}
			System.out.println("****  ["+new Date()+"]  write HTML fail  ****");
		} 
	}
}
