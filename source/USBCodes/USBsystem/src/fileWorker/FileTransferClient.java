package fileWorker;
/*
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;

/*
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;*/
 

public class FileTransferClient extends Socket {
 
    private static final String SERVER_IP = "127.0.0.1"; // 服务端IP
    private static final int SERVER_PORT = 8899; // 服务端端口
 
    private Socket client;
 
    private FileInputStream fis;
 
    private DataOutputStream dos;
    
    private DataInputStream dis;
    
    private RandomAccessFile rad;
    
    //private Container contentPanel;
    
   // private JFrame frame;
    
   // private JProgressBar progressbar;
    
   // private JLabel label;

    public FileTransferClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        //System.out.println("成功连接服务端");

        //frame = new JFrame("文件传输");
    }
 
    public void sendFile(String filePath, String targetPath) throws Exception {
        try {
        	File file = new File(filePath);
        	
            if(file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());
                dis = new DataInputStream(client.getInputStream());
      
            	 
                dos.writeUTF(targetPath);
                //发送目标路径
                dos.writeUTF(file.getName());
                //发送文件名
                //System.out.println("客户端：发送文件名");
                rad = new RandomAccessFile(file.getPath(), "r");
                
                dos.flush();
                
                dos.writeLong(file.length());
                //发送文件长度
                //System.out.println("客户端：发送文件长度");
                dos.flush();
                
                long size = dis.readLong();
                //读取当前已发送文件长度
                //System.out.println("客户端：接收当前已发送文件长度");
                
                //int barSize = (int) (rad.length() / 1024);
                
				//int barOffset = (int)(size / 1024);
                
				/*
				frame.setSize(380,120);
				//System.out.println("客户端启动进度条窗口");
				contentPanel = frame.getContentPane();
				contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
				progressbar = new JProgressBar();//进度条
				
				label=new JLabel(file.getName()+" 发送中");
				contentPanel.add(label);
				
				progressbar.setOrientation(JProgressBar.HORIZONTAL);
				progressbar.setMinimum(0);
				progressbar.setMaximum(barSize);
				progressbar.setValue(barOffset);
			    progressbar.setStringPainted(true);
			    //System.out.println("客户端显示进度条");
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
				*/

				
				
				
				// 开始传输文件
				//System.out.println("开始传输文件 ");
				long offset = size;
				int length = 0;
				byte[] bytes = new byte[1024];
				
				if (offset < rad.length()) {
					rad.seek(offset);
					//System.out.println("客户端：文件定位完成");
					//移动文件指针
					while((length=rad.read(bytes)) > 0){
						dos.write(bytes, 0, length);							
						dos.flush();
						//progressbar.setValue(++barOffset);
					}
				}
				//label.setText("用户端：" + file.getName()+" 发送完成");
				//System.out.println("文件传输成功 ");
				

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            if(dis != null)
            	dis.close();
            if(rad != null)
            	rad.close();
            //frame.dispose();
            client.close();
        }
        
    }
 
    class cancelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e3){
			try {
				//label.setText(" 取消发送,连接关闭");
				//System.out.println("客户端：文件传输取消");
				//JOptionPane.showMessageDialog(frame, "取消发送给，连接关闭!", "提示：", JOptionPane.INFORMATION_MESSAGE);				
				if(fis != null)
					fis.close();
				if(dis != null)
					dis.close();
				if(dos != null)
					dos.close();
				if(rad != null)
					rad.close();
				//frame.dispose();
				client.close();
			} catch (IOException e1) {
				
			}
		}
	}   
 
}