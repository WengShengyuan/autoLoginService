package APP.gui;

import org.quartz.SchedulerException;

public class UIParamClass {
	
	private volatile static UIParamClass instance;
	
	public static UIParamClass getInstance() throws SchedulerException {
		if(instance == null) {
			synchronized(UIParamClass.class) {
				if(instance == null) {
					instance = new UIParamClass();
				}
			}
		}
		return instance;
	}
	
	public static final String STATUS_ERR="未在线";
	public static final String STATUS_SUCCESS="在线中...";
	public static final String STATUS_LOGGING="登录中...";
	
	private String checkStatus = STATUS_ERR;
	private String lastCheckDate = "";
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getLastCheckDate() {
		return lastCheckDate;
	}
	public void setLastCheckDate(String lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}
}
