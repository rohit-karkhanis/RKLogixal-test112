package com.testcases.driverscripts;

import java.io.IOException;


public class TestJar {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/*ExecuteJarTestNG tg = new ExecuteJarTestNG();
		tg.ExecuteSuite();*/
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM IEDriverServer.exe");
		rt.exec("taskkill /F /IM iexplore.exe");
		System.out.println("Test");
	}

}
