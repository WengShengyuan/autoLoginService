package APP.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestTest {
	public static void main(String[] args){
		try{
			// Configure and open a connection to the site you will send the request
			URL url = new URL("http://192.168.100.253:5280/login");
			URLConnection urlConnection = url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			// 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 得到请求的输出流对象
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			// 把数据写入请求的Body
			out.write("usrname=S1307&usrpwd=123123");
			out.flush();
			out.close();
			
			// 从服务器读取响应
			InputStream inputStream = urlConnection.getInputStream();
			String encoding = urlConnection.getContentEncoding();
			int i=0;
			while((i=inputStream.read())!=-1){
				System.out.print((char)i);
			}
		}catch(IOException e){
			Logger.getLogger(RequestTest.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}