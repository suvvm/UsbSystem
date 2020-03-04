package fileWorker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FileTransferServer extends ServerSocket {

    private static final int SERVER_PORT = 8899; // 服务端端口

    public FileTransferServer() throws Exception {
        super(SERVER_PORT);
    }

    public void load() throws Exception {
        while (true) {
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            Socket socket = this.accept();

            // 每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Task(socket)).start();
        }
    }


     //处理客户端传输过来的文件线程类

    class Task implements Runnable {

        private Socket socket;

        private DataInputStream dis;

        private FileOutputStream fos;

        private DataOutputStream dos;

        private RandomAccessFile rad;

        private JFrame frame;

    	private Container contentPanel;

    	private JProgressBar progressbar;

    	private JLabel label;

        public Task(Socket socket) {
        	frame=new JFrame("文件传输");
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                dis = new DataInputStream(socket.getInputStream());

                dos = new DataOutputStream(socket.getOutputStream());

                //dos.writeUTF("ok");

                String targetPath = dis.readUTF();
                //接收目标路径

                String fileName = dis.readUTF();
                //接收文件名
                //System.out.println("服务器：接收文件名");
                long fileLength = dis.readLong();
                //接收文件长度
                //System.out.println("服务器：接收文件长度");

                File directory = new File(targetPath);
                //目标地址


                if(!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName + ".temp");
                //System.out.println("服务器：加载temp文件");
                rad = new RandomAccessFile(directory.getAbsolutePath() + File.separatorChar + fileName + ".temp", "rw");

                long size = 0;

                if(file.exists() && file.isFile()){
					size = file.length();
				}
                //System.out.println("服务器：获的当前已接收长度");
                dos.writeLong(size);
                dos.flush();
                //发送当前已接收文件长度
                //System.out.println("服务器：发送当前以接收文件长度");


				int barSize=(int)(fileLength/1024);
				int barOffset=(int)(size/1024);


                //传输界面
				frame.setSize(300,120);
				contentPanel =frame.getContentPane();
				contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
				progressbar = new JProgressBar();//进度条

				label=new JLabel(fileName+" 接收中");
				contentPanel.add(label);

				progressbar.setOrientation(JProgressBar.HORIZONTAL);
				progressbar.setMinimum(0);
				progressbar.setMaximum(barSize);
				progressbar.setValue(barOffset);
			    progressbar.setStringPainted(true);
			    //服务器显示进度条
			    progressbar.setPreferredSize(new Dimension(150, 20));
			    progressbar.setBorderPainted(true);
			    progressbar.setBackground(Color.pink);

			    JButton cancel=new JButton("取消");

			    JPanel barPanel=new JPanel();
			    barPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			    barPanel.add(progressbar);
			    barPanel.add(cancel);

			    contentPanel.add(barPanel);

			    cancel.addActionListener(new cancelActionListener());

			    frame.setDefaultCloseOperation(
			    		JFrame.EXIT_ON_CLOSE);
			    frame.setVisible(true);


			    rad.seek(size);
			    //移动文件指针
			    //System.out.println("服务器：文件定位完成");
				int length;
				byte[] bytes=new byte[1024];
				while((length = dis.read(bytes, 0, bytes.length)) != -1){
					rad.write(bytes,0, length);

					progressbar.setValue(++barOffset);
				}


				if (barOffset >= barSize) {

					if(rad != null)
	                 	rad.close();

					if(!file.renameTo(new File(directory.getAbsolutePath() + File.separatorChar + fileName))) {
						file.delete();
						//删除临时文件
					}

					//System.out.println("服务器：临时文件重命名完成");

				}



            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                	if(rad != null)
	                 	rad.close();
                	if(dis != null)
                        dis.close();
                    if(dos != null)
                        dos.close();
                    frame.dispose();
                    socket.close();
                } catch (Exception e) {}
            }
        }
        class cancelActionListener implements ActionListener{
    		public void actionPerformed(ActionEvent e){
    			try {
    				System.out.println("服务器：接收取消");
    				if(dis != null)
    					dis.close();
    				if(dos != null)
    					dos.close();
    				if(rad != null)
    					rad.close();
    				if(fos != null)
    					fos.close();
    				frame.dispose();
    				socket.close();
    				JOptionPane.showMessageDialog(frame, "已取消接收，连接关闭！", "提示：", JOptionPane.INFORMATION_MESSAGE);
    				label.setText(" 取消接收,连接关闭");
    			} catch (IOException e1) {

    			}
    		}
    	}
    }




}