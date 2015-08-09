package APP.utils;

import java.util.HashMap;

public class HTTPT {

	public static void main(String[] args) throws Exception{
		String s = NetUtils.get("http://192.168.100.253:7755/login.cgi?act=login&s=192.168.100.253&p=5280&usrname=S1307&usrpwd=123123", "UTF-8");
		System.out.println(s);
		
		
		String ss = NetUtils.post("http://192.168.100.253:5280/login", "usrname=S1307&usrpwd=123123");
		System.out.println(ss);
		
	}
	
}
