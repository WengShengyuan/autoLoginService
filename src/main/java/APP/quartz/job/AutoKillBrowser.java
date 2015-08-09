package APP.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import APP.utils.MySysClient;

public class AutoKillBrowser implements Job{

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String command = "ps -ef | grep chromium  | awk '{print $2}' | xargs kill -9";
		try {
			new MySysClient().linuxCommand(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
