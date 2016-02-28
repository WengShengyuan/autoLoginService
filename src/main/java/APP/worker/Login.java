package APP.worker;

import java.util.Date;

import APP.gui.UIParamClass;
import APP.utils.NetUtils;

public class Login {
	
	public static void postLogin(String name, String pwd, String writePath) throws Exception{
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
				Writer.writePath(writePath,r);
			}
		}
	}

}
