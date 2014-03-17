package saver.alcatel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigWrapper 
{
	static File configfile = new File("config.xml");
	public static Properties prop = new Properties();
	
	@SuppressWarnings("deprecation")
	public static void changeProp()
	{
		prop.setProperty("stations_count", "1");
		prop.setProperty("station1_addr","192.168.1.1");
	}
	
	public static void readProp(){
		if(!configfile.exists()) 
			try 
			{
				configfile.createNewFile();
				prop.setProperty("stations_count", "1");
				prop.setProperty("station1_addr","192.168.56.100");
				OutputStream  out = new FileOutputStream(configfile);
				prop.storeToXML(out, "AD Password Notificator");
			} 
			catch (IOException e){}
			else 
			try
			{
				InputStream in = new FileInputStream(configfile);
				prop.loadFromXML(in);
			}
			catch(IOException e) {};
		
	}
	
	public static void saveProp(){
		
		try 
		{
			configfile.createNewFile();				
			OutputStream  out = new FileOutputStream(configfile);
			prop.storeToXML(out, "Alcatel accounting saver");
		} 
		catch (IOException e){}
	
}
}
