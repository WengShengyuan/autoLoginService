package APP.worker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import APP.gui.UIParamClass;

public class Writer {
	
	public static void writePath(String writePath,String content) {
		OutputStream out=null;
		try {
			content = content.replace("<title>Login</title>", String.format("<title>createDate[%s]</title>", new Date()));
			File outFile = new File(writePath);
			out= new FileOutputStream(outFile,false);
			IOUtils.write(content+"\n", out, "UTF-8");
			out.close();
			UIParamClass.getInstance().setOutputLine("写入文件成功:"+writePath);
		} catch (Exception e) {
			UIParamClass.getInstance().setOutputLine("写入文件失败："+writePath+"."+e.toString());
			System.out.println("****  ["+new Date()+"]  write HTML fail  ****");
		} 
	}

}
