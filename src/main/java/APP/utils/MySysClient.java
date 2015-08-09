package APP.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MySysClient {
	
	public void excute(String url) throws Exception {
		String execStr = String.format("rundll32 url.dll,FileProtocolHandler %s", url);
		Runtime.getRuntime().exec(execStr);
		
	}
	
	public void linuxExcute(String url) throws Exception {
//		String execStr = String.format("epiphany-browser -n %s", url);
		String execStr = String.format("chromium %s", url);
		System.out.println("Executing:" + execStr);
		
		Process p = Runtime.getRuntime().exec(execStr);  //调用Linux的相关命令  
        
        InputStreamReader ir = new InputStreamReader(p.getInputStream());    
        LineNumberReader input = new LineNumberReader (ir);      //创建IO管道，准备输出命令执行后的显示内容  
          
         String line="";    
         while ((line = input.readLine ()) != null){     //按行打印输出内容  
          System.out.println(line);    
        }    
	}
	
	public void linuxCommand(String command) throws Exception {
		
		System.out.println("Executing:" + command);
		
		Process p = Runtime.getRuntime().exec(command);  //调用Linux的相关命令  
        
        InputStreamReader ir = new InputStreamReader(p.getInputStream());    
        LineNumberReader input = new LineNumberReader (ir);      //创建IO管道，准备输出命令执行后的显示内容  
          
         String line="";    
         while ((line = input.readLine ()) != null){     //按行打印输出内容  
          System.out.println(line);    
        }    
	} 

}
