package com.operations.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class Readconfig {
	public String Testcase;
	public String mozilla;
	public String output_dir;
	public String Screenshot_file;
	public String hub;
	public String Username;
	//Properties c = new Properties();
 
@Test
	public void getObjectRepository() throws IOException{
		//Read object repository file
		
		File configFile = new File(System.getProperty("user.dir")+"/Resources/Config.properties");
		InputStream inputStream = new FileInputStream(configFile);
		Properties prop1 = new Properties();
		prop1.load(inputStream);
		Testcase = prop1.getProperty("Testcase");
		mozilla = prop1.getProperty("Mozilla");
		output_dir = prop1.getProperty("output_dir");
		Screenshot_file = prop1.getProperty("Screenshot_dir");
		hub = prop1.getProperty("hub");
		Username = prop1.getProperty("Username");
		
		
		}

	
}
