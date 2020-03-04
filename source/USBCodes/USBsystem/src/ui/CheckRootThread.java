package ui;

import java.io.File;

import javax.swing.JOptionPane;

public class CheckRootThread extends Thread {
	// 获取系统盘符
	private File[] sysRoot = File.listRoots();
	String string=sysRoot[0].getPath();
	
	public void run() {
		File[] currentRoot = null;
	
		while (true) {
			// 当前的系统盘符
			currentRoot = File.listRoots();
			if (currentRoot.length > sysRoot.length) {//如果有新盘符出现
				DisplayNewRoot snr=new DisplayNewRoot(currentRoot[currentRoot.length-1]);
				snr.setVisible(true);
//				NewRoot nRoot=new NewRoot();//弹出提示框
				JOptionPane.showMessageDialog(null, "有新U盘插入",  "New Message",JOptionPane.INFORMATION_MESSAGE);
			}
			else if(currentRoot.length < sysRoot.length)
			{
				@SuppressWarnings("unused")
				USBMain uMain = new USBMain();
				JOptionPane.showMessageDialog(null, "U盘已拔出","New Message", JOptionPane.INFORMATION_MESSAGE);
			}
			sysRoot = File.listRoots();
			//每3秒时间检查一次系统盘符
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
