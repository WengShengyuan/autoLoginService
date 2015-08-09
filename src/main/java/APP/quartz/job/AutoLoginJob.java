package APP.quartz.job;

import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import APP.utils.MySysClient;

public class AutoLoginJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String ip = (String) jobDataMap.get("ip");
		String port = (String) jobDataMap.get("port");
		String userName = (String) jobDataMap.get("userName");
		String passwd = (String) jobDataMap.get("passwd");
		
		String url = String.format("http://192.168.100.253:7755/login.cgi?act=login&s=%s&p=%s&usrname=%s&usrpwd=%s",
				ip, port, userName.toUpperCase(), passwd);
		
		try {
			System.out.println("Job excuting...: "+ url);
			new MySysClient().excute(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
