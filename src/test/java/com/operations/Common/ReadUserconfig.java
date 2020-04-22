package com.operations.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class ReadUserconfig {
	public String SiteName;
	public String Suite;
	public String Domain;
	public String Scr;
	public String ExcelReports;
	public String HistoricalReports;
	public String OS;
	public String Username;
	public String SMTPHost;
	public String SMTPUser;
	public String SMTPPassword;
	public String emailFrom;
	public String emailTo;
	//Properties c = new Properties();
 
@Test
	public void getUserConfig() throws IOException{
		//Read object repository file
		
		File configFile = new File(System.getProperty("user.dir")+"/Resources/UserConfig.properties");
		InputStream inputStream = new FileInputStream(configFile);
		Properties prop1 = new Properties();
		prop1.load(inputStream);
		SiteName = prop1.getProperty("Site");
		Suite = prop1.getProperty("Suite");
		Domain =prop1.getProperty("Domain");
		Scr =prop1.getProperty("Screenshots");
		ExcelReports =prop1.getProperty("Excel_Reports");
		HistoricalReports = prop1.getProperty("Historical_Reports");
		OS = prop1.getProperty("OS");
		Username=prop1.getProperty("Username");
		SMTPHost=prop1.getProperty("SMTPHost");
		SMTPPassword=prop1.getProperty("SMTPPassword");
		SMTPUser=prop1.getProperty("SMTPUser");
		emailFrom=prop1.getProperty("emailFrom");
		emailTo=prop1.getProperty("emailTo");
		}

	
}
