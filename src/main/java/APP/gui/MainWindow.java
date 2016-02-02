package APP.gui;

import java.awt.Desktop;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.quartz.SchedulerException;

import APP.quartz.scheduler.AutoLoginScheduler;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frame;
	private JTextField tb_userName;
	private JPasswordField tb_password;
	private JButton button;
	private JTextField tb_lastCheck;
	private JTextField tb_status;

	JLabel lb_output;
	
	private static Desktop desktop;
	private boolean inDetect = false;
	private Timer timeAction = new Timer();
	private JLabel lb_time;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setTitle("自动登陆程序");
		frame.setBounds(100, 100, 440, 165);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("用户名：");
		label.setBounds(33, 13, 54, 15);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("密码：");
		label_1.setBounds(33, 38, 54, 15);
		frame.getContentPane().add(label_1);

		tb_userName = new JTextField();
		tb_userName.setText("S1307");
		tb_userName.setBounds(97, 10, 66, 21);
		frame.getContentPane().add(tb_userName);
		tb_userName.setColumns(10);

		tb_password = new JPasswordField();
		tb_password.setToolTipText("");
		tb_password.setText("123123");
		tb_password.setBounds(97, 35, 66, 21);
		frame.getContentPane().add(tb_password);

		button = new JButton("开始探测");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (inDetect) {
					button.setText("开始探测");
					inDetect = false;
					try {
						setTimerStop();
						AutoLoginScheduler.getInstance().unScheduleProbe();
					} catch (SchedulerException e) {
						JOptionPane errorMsg = new JOptionPane();
						errorMsg.showMessageDialog(null, e, "错误",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					button.setText("探测中...");
					setTimerRefresh();
					inDetect = true;
					try {
						StringBuilder sb = new StringBuilder();
						char[] ps = tb_password.getPassword();
						for(char p : ps){
							sb.append(p);
						}
						AutoLoginScheduler.getInstance().scheduleProbe(
								"192.168.100.253", "5280",
								tb_userName.getText(),
								sb.toString(), false,
								"./logout.html");
						AutoLoginScheduler.getInstance().getScheduler().start();
					} catch (Exception e) {
						setTimerStop();
						button.setText("开始探测");
						JOptionPane errorMsg = new JOptionPane();
						errorMsg.showMessageDialog(null, e, "错误",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		button.setBounds(33, 66, 130, 39);
		frame.getContentPane().add(button);

		JLabel lblNewLabel = new JLabel("最后检测时间");
		lblNewLabel.setBounds(192, 13, 84, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel label_2 = new JLabel("登陆状态");
		label_2.setBounds(192, 35, 84, 15);
		frame.getContentPane().add(label_2);

		tb_lastCheck = new JTextField();
		tb_lastCheck.setEditable(false);
		tb_lastCheck.setBounds(286, 10, 138, 21);
		frame.getContentPane().add(tb_lastCheck);
		tb_lastCheck.setColumns(10);

		tb_status = new JTextField();
		tb_status.setEditable(false);
		tb_status.setColumns(10);
		tb_status.setBounds(286, 35, 138, 21);
		frame.getContentPane().add(tb_status);

		JButton button_1 = new JButton("打开登出窗口");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File f = new File("./logout.html");
				if(!f.exists()){
					JOptionPane errorMsg = new JOptionPane();
					errorMsg.showMessageDialog(null, "登出文件不存在", "错误",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					desktop = Desktop.getDesktop();
					desktop.open(f);
				} catch (Exception e) {
					JOptionPane errorMsg = new JOptionPane();
					errorMsg.showMessageDialog(null, e, "错误",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		button_1.setBounds(286, 66, 138, 39);
		frame.getContentPane().add(button_1);
		
		lb_output = new JLabel("");
		lb_output.setBounds(10, 115, 334, 15);
		frame.getContentPane().add(lb_output);
		
		lb_time = new JLabel("");
		lb_time.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_time.setBounds(354, 115, 70, 15);
		frame.getContentPane().add(lb_time);
	}

	private void setTimerRefresh() {
		timeAction = new Timer();
		final long interval = 1000;
		try {
			UIParamClass.getInstance().setOutputLine("建立UI刷新线程，计时器间隔:"+interval+"ms");
		} catch (SchedulerException e1) {
			lb_output.setText("UI参数实例初始化失败："+e1);
		}
		timeAction.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					tb_lastCheck.setText(UIParamClass.getInstance()
							.getLastCheckDate());
					tb_status.setText(UIParamClass.getInstance()
							.getCheckStatus());
					lb_output.setText(UIParamClass.getInstance().getOutputLine());
					lb_time.setText(getTime());
				} catch (SchedulerException e) {
					lb_output.setText("UI刷新线程失败："+e);
				}
			}
		}, 0,interval);
	}
	private void setTimerStop(){
		this.timeAction.cancel();
	}
	
	private String getTime(){
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
}
