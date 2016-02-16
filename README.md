# autoLoginService 

 研究生宿舍网在线状态检测与自动登录服务。
  
## 导入

使用MAVEN import整个项目

## 生成.jar

Export Runnable Jar.

主类: APP.java
新增GUI界面:主类 MainWindow.java

## 使用

### 命令行方式

`java -jar loginService.jar USERNAME PASSWORD [DEBUG]`

*若第三个参数包含`DEBUG`，则会已5秒一次的周期进行探测。若不包含，则以5分钟一次的周期探测。

### 服务方式

Linux环境下，在rc.local中增加自动运行脚本即可。

`java -jar /home/user/java/loginService/loginService.jar USERNAME PASSWORD`
