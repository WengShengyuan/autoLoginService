package APP.worker;

import java.text.SimpleDateFormat;
import java.util.Date;

import APP.gui.UIParamClass;
import APP.utils.NetUtils;

public class Prob {
	
	public static boolean probe () throws Exception{
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

}
