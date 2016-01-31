package APP.quartz.scheduler;

import javax.swing.JFrame;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import APP.gui.UIParamClass;
import APP.quartz.job.AutoLoginJob;
import APP.quartz.job.AutoProbeJob;

public class AutoLoginScheduler {
	
	private volatile static AutoLoginScheduler instance;
	SchedulerFactory schedulerFactory ;
	Scheduler scheduler ;
	
	
	public static AutoLoginScheduler getInstance() throws SchedulerException {
		if(instance == null) {
			synchronized(AutoLoginScheduler.class) {
				if(instance == null) {
					instance = new AutoLoginScheduler();
				}
			}
		}
		return instance;
	}
	
	
	
	private AutoLoginScheduler() throws SchedulerException{
		schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
	}
	Trigger trigger ;
	public void unScheduleProbe() throws SchedulerException{
		scheduler.unscheduleJob(trigger.getKey());
	}
	
	public void scheduleProbe(String ip, String port, String userName, String passwd, boolean debug, String writePath) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob().ofType(AutoProbeJob.class)
				.usingJobData("ip", ip)
				.usingJobData("port", port)
				.usingJobData("userName", userName)
				.usingJobData("passwd", passwd)
				.usingJobData("writePath",writePath)
				.withIdentity("probe", "autoLogin")
				.build();
		
		
		
		if(debug){
			trigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
					.forJob("probe", "autoLogin")
					.build();
		} else {
			trigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
					.forJob("probe", "autoLogin")
					.build();
		}
		
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	public Scheduler getScheduler(){
		return this.scheduler;
	}
	
	public void schedulePost(String ip, String port, String userName, String passwd) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob().ofType(AutoLoginJob.class)
				.usingJobData("ip", ip)
				.usingJobData("port", port)
				.usingJobData("userName", userName)
				.usingJobData("passwd", passwd)
				.withIdentity("autoLogin", "autoLogin")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
//				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.forJob("autoLogin", "autoLogin")
				.build();
		scheduler.scheduleJob(jobDetail, trigger);
		
	}
	
}
