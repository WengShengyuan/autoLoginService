package APP;

import java.util.Date;

import APP.quartz.scheduler.AutoLoginScheduler;



public class APP {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String ip ;
		String port ;
		String userName;
		String passwd ;		
		String writePath;
		boolean debug = false;
//		if(args.length == 2) {
			try {
				ip = "192.168.100.253";
				port = "5280";
				userName = args[0];
				passwd = args[1];
				writePath = args[2];
				debug = false;
				
				StringBuilder argsStr = new StringBuilder("args: ");
				for(String o : args) {
					argsStr.append(o + "; ");
				}
				System.out.println(argsStr);
				System.out.println(String.format("Program Start Time:%s", new Date()));
				AutoLoginScheduler.getInstance().scheduleProbe(ip, port, userName, passwd, debug, writePath);
//				AutoLoginScheduler.getInstance().schedulePost(probeIp, port, userName, passwd);
//				AutoLoginScheduler.getInstance().scheduleAutoKillBrowser();
				
				AutoLoginScheduler.getInstance().getScheduler().start();
				
			} catch (Exception e) {
				System.out.println(String.format("PARAM ERROR:%s\n exit!", e));
				return ;
//				AutoLoginScheduler.getInstance().scheduleProbe("192.168.100.253", "5280", "S1307", "123123", false);
//				AutoLoginScheduler.getInstance().getScheduler().start();
			}
//		} else if(args.length == 3 ){
//			System.out.println("**** Debug Mode ****");
//			ip = "192.168.100.253";
//			port = "5280";
//			userName = args[0];
//			passwd = args[1];	
//			debug = true;
//			if(args[2].equals("browser")){
//				System.out.println("**** Opening Browser ****");
//				new MySysClient().linuxExcute(String.format("http://192.168.100.253:7755/login.cgi?act=login&s=%s&p=%s&usrname=%s&usrpwd=%s",
//						ip, port, userName.toUpperCase(), passwd));
//			}
//			
//		} else  {
//			System.out.println("exit");
//			return;
//		}
		
		
		
	}

}
