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
				AutoLoginScheduler.getInstance().getScheduler().start();
			} catch (Exception e) {
				System.out.println(String.format("PARAM ERROR:%s\n exit!", e));
				return ;
			}
	}

}
