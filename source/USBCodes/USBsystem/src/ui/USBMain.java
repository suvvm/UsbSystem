package ui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.FlowLayout;

import fileWorker.FileTransferServer;


@SuppressWarnings("serial")
public class USBMain extends JFrame{

	// 界面
	public USBMain() {
		setSize(675,484);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel panel=new JPanel();
		panel.setLayout(null);
		// 点击按钮后隐藏窗口事件监听
		JButton hide = new JButton("点击隐藏窗口");
		hide.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 17));
		hide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		JLabel tip=new JLabel("当前暂无U盘插入...");
		tip.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 22));
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getContentPane().add(tip);
		getContentPane().add(hide);
		setVisible(true);
		//开启盘符检查线程
		
		CheckRootThread crt=new CheckRootThread();
		crt.start();
	}
	public static void main(String[] args) {
		USBMain uMain= new USBMain();
		uMain.setVisible(true);
		 try {
	         @SuppressWarnings("resource")
			FileTransferServer server = new FileTransferServer(); // 启动服务端
	         server.load();
	    } catch (Exception e) {
	          e.printStackTrace();
	    }
	}
}
