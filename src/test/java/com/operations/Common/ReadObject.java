package com.operations.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObject {

	Properties p1 = new Properties();
	
	public Properties getObjectRepository() throws IOException{
		//Read object repository file
		InputStream stream = new FileInputStream(new File(System.getProperty("user.dir")+"\\resources\\CSSLocators.properties"));
		//load all objects
		p1.load(stream);
		return p1;
	}

}
