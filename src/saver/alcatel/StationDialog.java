package saver.alcatel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class StationDialog extends Thread 
{
	char[] cpu_ident = {0x00, 0x01, 0x53};
	char[] cpu_ident_ok = {0x00, 0x01, 0x50};
	char[] cpu_ident_ok1 = {0x00, 0x01};
	char[] cpu_ident_ok2 = {0x50};
	char[] init_server = {0x00, 0x02, 0x00, 0x00};
	char[] init_ok = {0x03, 0x04};
	char[] test_req = {0x54, 0x45, 0x53, 0x54, 0x5F, 0x52, 0x45, 0x51}; 
	char[] test_ok = {0x00, 0x08, 0x54, 0x45, 0x53, 0x54, 0x5F, 0x52, 0x53, 0x50};
	char[] ticket_ok = {0x00, 0x02, 0x00, 0x03};
	char[] ticket_req = {
							0x00, 0x00, 0x01, 0x00, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
							0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
						}; //772 �����
	private int station_port=2533;
	public String station_addr="192.168.56.100";
	String station_name="test";
	public Logger dialogLog;
	public Logger ticketLog;
	private long sleep_interval=1000;
	  public void run() 
	    {
		    dialogLog = Logger.getLogger(station_addr+"_dialog_logger");
			ticketLog = Logger.getLogger(station_addr+"_ticket_logger");
		  	dialogLog.setLevel(Level.ALL);
		  	ticketLog.setLevel(Level.ALL);
		  	try {
		  		FileHandler fh = new FileHandler(station_addr+"_log.txt");
		  		fh.setFormatter(new SimpleFormatter());
				dialogLog.addHandler(fh);
				FileHandler fh2 = new FileHandler(station_addr+"_tickets_log.txt");
		  		fh2.setFormatter(new SimpleFormatter());
				ticketLog.addHandler(fh2);
			} catch (SecurityException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		  
	        PrintWriter out = null;
			BufferedReader in = null;
			
			try {
				Socket echoSocket = new Socket(station_addr, station_port);
	        	//echoSocket = new Socket("127.0.0.1", 2533);
	            out = new PrintWriter(echoSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(
	                                        echoSocket.getInputStream()));
	            dialogLog.info("Connected to host :"+station_addr);
	           // jTextArea3.append("Connected to host"+'\n');
	            
	            try {
					Thread.sleep(sleep_interval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.print(cpu_ident);
				out.flush();
				dialogLog.info("sent "+cpu_ident.length+" bytes to "+station_addr+": "+new String(cpu_ident)); 
				//dialogLog.info("cpu_ident sent to "+station_addr);
      	} 
      	catch (UnknownHostException e) 
      	{
      		dialogLog.log(Level.WARNING, "Don't know about host"+'\n');
      	} 
      	catch (IOException e) {
      		dialogLog.log(Level.WARNING, "Couldn't get I/O for the connection to host");
      	}
	//    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	    //int fromSocket;
	    boolean rez=true;
	    	
		 while (rez&&!Thread.interrupted()) 
		 {
			 char[] fromSocket=new char[99999];
			 int l=-11;
			 try {
				l = in.read(fromSocket);
			 } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//Thread.currentThread().interrupt();
			 }
			 //int l = 1;
			 if (l>0) 
			 {
				 fromSocket=java.util.Arrays.copyOf(fromSocket, l);
				 dialogLog.info("received "+fromSocket.length+" bytes from "+station_addr+": "+new String(fromSocket));
				 if(java.util.Arrays.equals(fromSocket, cpu_ident_ok)||java.util.Arrays.equals(fromSocket, cpu_ident_ok2))
				 {
					 out.print(init_server);
					 out.flush();
					 dialogLog.info("sent "+init_server.length+" bytes to "+station_addr+": "+new String(init_server));
				 }
				 else
					 if(java.util.Arrays.equals(fromSocket, init_ok))
					 {
						 //	 dialogLog.info("init_ok received from "+station_addr);
		//				 dialogLog.info("received "+fromSocket.length+" bytes to "+station_addr+": "+new String(fromSocket));
					 }
				else	
					if(l==772||l==774)
					{
				//dialogLog.info("ticket received from "+station_addr);
		//				dialogLog.info("received "+fromSocket.length+" bytes to "+station_addr+": "+new String(fromSocket));
						out.print(ticket_ok);
						out.flush();
				//dialogLog.info("ticket_ok sent");
						dialogLog.info("sent "+ticket_ok.length+" bytes to "+station_addr+": "+new String(ticket_ok));
					}
				else
					if(java.util.Arrays.equals(fromSocket, test_req))
					{
						out.print(test_ok);
						out.flush();
						dialogLog.info("sent "+test_ok.length+" bytes to "+station_addr+": "+new String(test_ok));
					}
				else
					{
		//			if(fromSocket!=null) dialogLog.info("received "+fromSocket.length+" bytes to "+station_addr+": "+new String(fromSocket));
					 //dialogLog.info(new String(fromSocket));
					  //ticketLog.
					  ticketLog.fine(new String(fromSocket));
					}
			 }//��������� ������
			 System.gc();
			 try {
				Thread.sleep(sleep_interval);
			 	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			 		e.printStackTrace();
			 		System.exit(0);
				//out.close();
			 	}; //������� 
	    } //����� �����
		out.close();
	}
}
