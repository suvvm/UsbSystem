package ui;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import fileWorker.FileChoose;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SearchFiles extends JFrame {
	private JTextField textField;
	public SearchFiles(File file) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,300,200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("输入文件后缀名");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 18));
		lblNewLabel.setBounds(55, 13, 167, 32);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(55, 58, 178, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("查找");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				String filetype=textField.getText();
				String diskname=file.toString()+"\\" ;
				//System.out.println(diskname+" "+filetype);
				JFileChooser fc = new JFileChooser(" 选择目标路径");
				fc.setDialogTitle("选择目标路径");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int st =  fc.showOpenDialog(null);
				if(st == fc.APPROVE_OPTION){
					FileChoose fChoose=new FileChoose(diskname, filetype);
					fChoose.getAllFiles(diskname, fc.getSelectedFile().getPath());
				}
				SearchFiles.this.dispose();
			}
		});
		btnNewButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		btnNewButton.setBounds(99, 95, 92, 27);
		getContentPane().add(btnNewButton);
	}

}
