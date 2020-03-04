package fileWorker;
import java.io.File;

import fileWorker.FileTransferClient;

public class FileChoose 
{
	static String[] rt = new String[1000005];   
	static int cnt = 0;
	public static String pf;
	public static String aftername;
	@SuppressWarnings("static-access")
	public FileChoose(String pf, String aftername)
	{
		this.pf = pf;
		this.aftername = aftername;
	}
	
	public void getAllFiles(String pf, String tPath) 
	{
			//System.out.println(pf);
			String filename;//文件名
			String afternames;
			File file = new File(pf);
			File[] files = file.listFiles();//文件夹下的所有文件或文件夹
			if(files==null||files.length==0)
				return ;
			for (int i = 0; i < files.length; i++) 
			{     
	            if (files[i].isDirectory())
	            {
	                getAllFiles(files[i].getAbsolutePath(), tPath);//目录，则递归文件夹！！！
	            }
	            else 
	            {
	            	//System.out.println("***");
	                filename = files[i].getName(); 
	                int j = filename.lastIndexOf("."); 
	                afternames = filename.substring(j+1);//得到文件后缀
	 
	                if(afternames.equalsIgnoreCase(aftername))//判断是不是aftername后缀的文件
	                {
	                	
	                    String strFileName = files[i].getAbsolutePath();
	                    rt[cnt] = strFileName;
	                    File nowFile = new File(rt[cnt]);
	                    File judge = new File(tPath + "\\" + nowFile.getName());
	                    if(!judge.exists())
	                    	new Thread(new ClientWork( rt[cnt] , tPath)).start();
	                    else{
	                    	//JOptionPane.showMessageDialog(null, "有错！",tPath + "\\" + nowFile.getName() + "已存在", JOptionPane.ERROR_MESSAGE);
	                    }
	                    cnt++;
	                  
	                }              
	            }
			}
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
	public String[] getRt() 
	{
		return rt;
	}
}
