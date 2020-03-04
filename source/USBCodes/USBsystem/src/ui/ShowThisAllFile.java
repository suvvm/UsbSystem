package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import fileWorker.FileTransferClient;


@SuppressWarnings("serial")
public class ShowThisAllFile extends JFrame {
	
	private JPanel contentPane;
	
	private JTable table;

	
	public ShowThisAllFile(File file) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,675,484);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//列出所有文件
		ArrayList<File> allFiles=getAllFile(file);
		Vector<String> columnName = new Vector<>();
		columnName.add("文件标识");
		columnName.add("文件名");
		Vector<Vector< Object>> rowData = new Vector<>();
		String filetype;
		for(File file1 : allFiles)
		{
			Vector<Object> row = new Vector<>();
			String[] tmp=file1.toString().split("\\.");//得到文件后缀名
			if(tmp.length>1)//如果是文件
			{
				filetype=tmp[tmp.length-1];
			}
			else{//如果是文件夹
				filetype="Folder";
			}
			row.add(filetype);
			row.add(file1);
			rowData.add(row);
		}
		
		
		table = new JTable(rowData,columnName);
		
		//添加表格监听事件，选中就打开该文件
		table.addMouseListener(new java.awt.event.MouseAdapter(){
            @SuppressWarnings("unused")
			public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
               //得到选中的行列的索引值
	            int row = table.getSelectedRow();
				File file=(File) table.getValueAt(row, 1);
				String filetype=(String)table.getValueAt(row, 0);
				if(row != -1&&filetype=="Folder")
				{
					
					ArrayList<File>thisAllFiles = new ArrayList<>();//显示该文件的子窗体，一个新的frame
					thisAllFiles= getAllFile(file);
					ShowThisAllFile staf=new ShowThisAllFile(file);
					staf.setVisible(true);
	            }
            }
		});

		table.setForeground(Color.DARK_GRAY);
		table.setFont(new Font("等线 Light", Font.PLAIN, 18));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("黑体", Font.PLAIN, 20));
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.setLayout(null);
		table.setSelectionBackground(Color.LIGHT_GRAY);
		table.setSelectionForeground(Color.white); 
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 657, 393);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("返回");
		btnNewButton.setBounds(0, 391, 220, 46);
		contentPane.add(btnNewButton);
		btnNewButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 17));
		
		JButton selectFile = new JButton("选择");
		selectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		selectFile.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 17));
		selectFile.setBounds(437, 391, 220, 46);
		contentPane.add(selectFile);
		
		JButton searchButton = new JButton("查找同类文件");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchFiles sf=new SearchFiles(file);
				sf.setVisible(true);
			}
		});
		searchButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 17));
		searchButton.setBounds(215, 391, 225, 46);
		contentPane.add(searchButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowThisAllFile.this.dispose();
			}
		});
		selectFile.addMouseListener(new MouseAdapter() {
				@SuppressWarnings({ "unused", "static-access" })
				@Override
				public void mouseClicked(MouseEvent e) {
					String targetPath;
					super.mouseClicked(e);
					int row = table.getSelectedRow();
					File file=(File) table.getValueAt(row, 1);
					String filetype=(String)table.getValueAt(row, 0);
					JFileChooser fChooser = new JFileChooser("选择目标路径");
					fChooser.setDialogTitle("选择目标路径");
					fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int st = fChooser.showOpenDialog(null);
					if(st == fChooser.APPROVE_OPTION){
							targetPath = fChooser.getSelectedFile().getPath();
							new Thread(new ClientWork( file.getPath(), targetPath)).start();
					}
				}
		});
	}
	class ClientWork implements Runnable {
		private String fileP;
		private String targetP;
		public ClientWork(String f, String t) {
			this.fileP = f;
			this.targetP = t;
		}
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			try {
				@SuppressWarnings("resource")
				FileTransferClient ftc = new FileTransferClient();
				ftc.sendFile(fileP, targetP);
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		}
	
	}
	private static  ArrayList<File> getAllFile(File dir) {
		LinkedList<File> list = new LinkedList<>();
		ArrayList<File>allFiles=new ArrayList<>();
		//把文件夹放入队列容器
		list.addFirst(dir);
		//首先判断文件是否存在
		while(!list.isEmpty()){//判断该目录下是否为空文件
			//取出文件夹
			File firstfile = list.removeLast();
			//取出该文件所有文件
			File[] files = firstfile.listFiles();
			//如果该文件夹不是空文件
			if(files!=null){
				for (File file : files) {
					allFiles.add(file);
				}
			}
		}
		return allFiles;
	}

}
