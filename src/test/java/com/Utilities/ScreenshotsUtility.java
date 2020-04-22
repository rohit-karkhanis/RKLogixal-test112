package com.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.testcases.driverscripts.Execute_MainScript;



public class ScreenshotsUtility {


	String scr_output_dir;
	File scr_screenshotfile;
	//Readconfig rc = new Readconfig();

	public void Screenshots(WebDriver driver,String path,String dirname) throws Throwable {


		if (scr_screenshotfile==null){
			CreateScreenshotDir(path,dirname);
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
			String formattedDate = dateFormat.format(date);
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File Destfile = new File(scr_screenshotfile+"/"+formattedDate +".png");

			try {
				FileUtils.copyFile(scrFile,Destfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			UpdateScreenshotDir(scr_screenshotfile, driver);
		}

	}

	public  File CreateScreenshotDir(String path,String dirname) throws IOException{

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		String formattedDate = dateFormat.format(date);
		scr_output_dir=path+dirname+"/All_screenshots";
		
		scr_screenshotfile= new File(scr_output_dir);
		scr_screenshotfile.getParentFile().mkdirs();
			
			/*	if (scr_screenshotfile.getParentFile().mkdir()) {
				scr_screenshotfile.createNewFile();
			
		boolean sucess=scr_screenshotfile.mkdirs();
		if (sucess) {
			scr_screenshotfile.delete();
*/
		
		return scr_screenshotfile;


	}
	public void UpdateScreenshotDir(File file,WebDriver driver){

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		String formattedDate = dateFormat.format(date);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		File Destfile = new File(scr_screenshotfile+"/"+formattedDate +".png");
		try {
			FileUtils.copyFile(scrFile,Destfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
