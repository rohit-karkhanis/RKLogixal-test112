package com.operations.Common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class FireClass {

	ReadUserconfig uc =new ReadUserconfig();
	String failmsg;
	public static int failcounter;
	
	
		
	public void VerifyUserLoginforLogout(WebDriver webdriver) throws InterruptedException {

		String User = "//*[@class='logout-link']";
		String LogOut = "//*[@id='logout-button']";

		int CheckUserLogin = webdriver.findElements(By.xpath(User)).size();

		if(CheckUserLogin > 0){

			WebElement ele = webdriver.findElement(By.xpath("//span[@class='capitalize']"));
			//ele.click();
			Actions Mouseaction = new Actions(webdriver);
			//WebElement Mh = webdriver.findElement(ele);
			Mouseaction.moveToElement(ele).build().perform();
			Thread.sleep(2000);
			webdriver.findElement(By.xpath(LogOut)).click();
			Thread.sleep(2000);
		}

	}

	public void FailedTCOperation(String Object,Script_executor screxe,WebDriver webdriver,Xls_writer xls_writer,Map<Integer, Object[]> Testscase_failresults ,String browser_name,String Functionality,String Testcasenumber, 
			String Severity,SimpleDateFormat StartTime,Date Startdate,SoftAssert softAssert,ExtentTest test,ExtentReports extent) throws Exception {

		uc.getUserConfig();

		Object=screxe.Object;
		//VerifyUserLoginforLogout(webdriver);


		if(uc.ExcelReports.equalsIgnoreCase("Yes")) {

			xls_writer.GenerateFailReport(Testscase_failresults, uc.SiteName, browser_name, Functionality, Testcasenumber, Severity,System.getProperty("user.dir")+Constants.Windows_FailedFileLocation+StartTime.format(Startdate)+"/"+uc.SiteName+"/"+browser_name+"/");

		}

		//xls_writer.GenerateFailReport(Testscase_failresults, uc.SiteName, browser_name, Functionality, Testcasenumber, Severity,"./Failed_Reports/"+StartTime.format(Startdate)+"/"+uc.SiteName+"/"+browser_name+"/");



		softAssert.assertAll();

		failmsg="NOT able to find element within given time frame...!!! Element name: " +"'" + Object + "." ;
		test = extent.createTest(browser_name+"_"+Testcasenumber);	
		test.fail(MarkupHelper.createLabel(failmsg,ExtentColor.RED));
		failcounter=failcounter+1;
		
		/*Actions Mouseaction = new Actions(webdriver);
		WebElement ele = webdriver.findElement(By.xpath("//div[text()='My Account']"));
		Mouseaction.moveToElement(ele).build().perform();
		webdriver.findElement(By.xpath("//*[@id='logout-button']")).click();
		//test.fail(MarkupHelper.createLabel(Testcasenumber+" has been failed....", ExtentColor.RED));
		*/Assert.fail(failmsg);
		
	}



	public void ExecuteTestcasesWindows(String Testcasenumber,Script_executor scre,String Sitename,String browser_name,SimpleDateFormat StartTime,Date Startdate,WebDriver webdriver,String Functionality,String Section,
			String Testcase_description,String Executionmode, String Severity ,ExtentReports extent,Logger Applog ) throws Throwable {

		uc.getUserConfig();
		System.out.println("Currently running Testcase : " + Testcasenumber);
		scre.Execute_script(Sitename,browser_name,Constants.Windows_InputFileLocation+uc.SiteName+"/",Constants.Windows_OutputFileLocation+StartTime.format(Startdate)+"/"+Sitename+"/"+browser_name+"/",
				Constants.Windows_ScreenshotsLocation+StartTime.format(Startdate)+"/"+uc.SiteName+"/"+browser_name+"/", webdriver,Section,Functionality, Testcasenumber, Testcase_description, Executionmode, Severity,uc.Scr,uc.ExcelReports,extent,Applog);


	}
	
	public void ExecuteTestcasesLinux(String Testcasenumber,Script_executor scre,String Sitename,String browser_name,SimpleDateFormat StartTime,Date Startdate,WebDriver webdriver,String Functionality,String Section,
			String Testcase_description,String Executionmode, String Severity ,ExtentReports extent,Logger Applog ) throws Throwable {

		uc.getUserConfig();
		System.out.println("Currently running Testcase : " + Testcasenumber);
		scre.Execute_script(Sitename,browser_name,Constants.Linux_InputFileLocation+uc.SiteName+"/",Constants.Linux_OutputFileLocation+StartTime.format(Startdate)+"/"+Sitename+"/"+browser_name+"/",
				Constants.Linux_ScreenshotsLocation+StartTime.format(Startdate)+"/"+uc.SiteName+"/"+browser_name+"/", webdriver,Section,Functionality, Testcasenumber, Testcase_description, Executionmode, Severity,uc.Scr,uc.ExcelReports,extent,Applog);


	}
	
}
