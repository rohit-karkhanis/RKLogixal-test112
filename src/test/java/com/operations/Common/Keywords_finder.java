package com.operations.Common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.testcases.driverscripts.Execute_MainScript;

public class Keywords_finder {

	WebDriver driver;
	String parentWindowHandle;
	ExtentTest test;
	//ExtentReports extent;
	Logger log;
	//ExtentTest logger;


	public Keywords_finder(WebDriver driver,Logger log ){
		this.driver = driver;
		this.log=log;
		this.test=test;
		//this.extent=extent;
		//this.logger=logger;
	}

	Keywords key = new Keywords();
	Execute_MainScript exe = new Execute_MainScript();

	public void perform(Properties p,String operation,String objectName,String objectType,String value,String Sitename,String browser) throws Exception{

		switch (operation.toUpperCase()) {
		case "CLICK":

			key.CLICK(driver, p, objectName, objectType,test,log);

			break;

		case "CLICK_PRE_ENTERTEXT":

			key.CLICK_PRE_ENTERTEXT(driver, p, objectName, objectType,test,log);

			break;

		case "PRESS_ENTER" :

			key.PRESS_ENTER(driver, p, objectName, objectType);

			break;

		case "PRESS_BACKSPACE" :

			key.PRESS_BACKSPACE(driver, p, objectName, objectType);

			break;

		case "NAVIGATE_URL":

			key.NAVIGATE_URL(driver, value, test, log);

			break;

		case "MAXIMIZE_BROWSER":

			key.MAXIMIZE_BROWSER(driver);

			break;

		case "VERIFY_PAGE_URL" :

			key.VERIFY_PAGE_URL(driver, value, test, log);

			break;

		case "RESIZE_BROWSER" :

			key.RESIZE_BROWSER(driver, p, value, test, log);

			break;

		case "ENTERTEXT":

			key.ENTERTEXT(driver, p, objectName, objectType, value, test, log);

			break;

		case "CLEAR_CART_ITEMS":

			key.CLEAR_CART_ITEMS(driver, p, objectName, objectType, value, test, log); 

			break;

		case "VERIFY_PAGE_TITLE" :

			key.VERIFY_PAGE_TITLE(driver, test, log, value);

			break;


		case "SWITCH_TO_FRAME" :

			driver.switchTo().frame(0);

			break;

		case "SWITCH_TO_DEFAULT" :

			driver.switchTo().defaultContent();

			break;
		case "GETTEXT" :

			//key.GETATTRIBUTE(driver, p, objectName, objectType, value, test, log);

			break;

		case "VERIFY_TEXT_PRESENT":

			key.VERIFY_TEXT_PRESENT(driver, p, objectName, objectType, test, log, value);

			break;

		case "ENTER_RANDOM_VALUE":

			key.ENTER_RANDOM_VALUE(driver, p, objectName, objectType, value, test, log);

			break;

		case "PRESS_ESC_BUTTON" :

			key.PRESS_ESC(driver, p, objectName, objectType, value, test, log);

			break;

		case "WAIT":

			key.WAIT(driver);

			break;

		case "SLEEPWAIT":

			//key.SLEEPWAIT();

			break;

		case "WAITFORLOAD":

			//	key.WAITFORLOAD(driver);

			break;


		case "BACK":

			key.NAVIGATE_BACK(driver);

			break;

		case "REMOVE_COOKIE":

			key.REMOVE_COOKIE(driver);

			break;

		case "REFRESH_PAGE":

			key.REFRESH_PAGE(driver);

			break;

		case "CLOSE":

			key.CLOSE_BROWSER(driver);

			break;

		case "QUIT":

			driver.quit();

			break;

		case "VERIFY_WEBELEMENT_PRESENT":

			key.VERIFY_WEBELEMENT_PRESENT(driver, p, objectName, objectType,value);

			break;
			
		case "ELEMENT_FROM_LIST":

			key.ELEMENT_FROM_LIST(driver, p, objectName, objectType,value);

			break;
			

		case "CLEAR_TEXT":

			key.CLEAR_TEXT(driver, p, objectName, objectType, test, log);

			break;

		case "RIGHT_CLICK":

			key.RIGHT_CLICK(driver, p, objectName, objectType, test, log);

			break;

		case "DROPDOWN_DESELECT" :

			key.DROPDOWN_DESELECTALL(driver, p, objectName, objectType, value, test, log);

			break;

		case "DROPDOWN_SELECT" :

			key.DROPDOWN_SELECT(driver, p, objectName, objectType, value, test, log);

			break;

		case "CONTROL_BUTTON":
			Actions act = new Actions(driver);
			Keys.chord(Keys.CONTROL);

			break;

		case "SCROLL_UP" :

			key.SCROLL_UP(driver, test, log);

			break;

		case "VERIFY_BROKEN_IMAGE" :

			key.VERIFY_BROKEN_IMAGE(driver, test, log, Sitename, browser);

			break;


		case"VERIFY_BROKEN_LINK" :

			key.VERIFY_BROKEN_LINK(driver, test, log, Sitename, browser,value);

			break;

		case "SCROLL_DOWN":

			key.SCROLL_DOWN(driver, test, log);

			break;

		case "RADIO_BUTTON_SELECT" :
			List<WebElement> radiolist = driver.findElements(By.xpath(".//input[contains(@type,'radio')]"));	
			//System.out.println(radiolist.size());
			for (int i=0;i<radiolist.size();i++){
				// System.out.println(dr.get(i).getAttribute("id"));
				String temp_radio = radiolist.get(i).getAttribute("id");
				if (temp_radio.equals(value)){
					radiolist.get(i).click();
					if(radiolist.get(i).isSelected()==false)
					{
						radiolist.get(i).click();
					}
				}
			}
			break;

		case "MOUSEOVER" :

			key.MOUSEOVER(driver, p, objectName, objectType, value, test, log);

			break;	


		case "MOUSEOVER_CLICK" :

			key.MOUSEOVER_CLICK(driver, p, objectName, objectType, value, test, log);

			break;

			/*	case "COMPARE_STRING" :
			List <WebElement> lst = driver.findElements(this.getObject(p,objectName,objectType));
			for (int j=0;j<lst.size();j++){
				String SS =  lst.get(j).getText();
				if(SS.startsWith("$"))
				{
					String[] parts = SS.split("");
					String part1 = parts[0]; 
					String part2 = parts[1]; 

					if(!part2.equals(value))
					{
						//	return false;

					}

				}
				else {
					String[] parts = SS.split(" ");
					String part1 = parts[0]; 
					String part2 = parts[1]; 
					//System.out.println(part1);
					if(!part1.equals(value))
					{
						//return false;

					}

				}
			}
			break;*/

		case "MATH_VERIFICATION":

			//	key.MATH_VERIFCATION(driver, test, log, Sitename, browser, value);

			break;


		case "SWITCH_CHILD_WINDOW":
			parentWindowHandle = driver.getWindowHandle(); // save the current window handle.

			WebDriver popup = null;
			Iterator<String> windowIterator = driver.getWindowHandles().iterator();
			while(windowIterator.hasNext()) { 
				String windowHandle = windowIterator.next(); 
				popup = driver.switchTo().window(windowHandle);
			}
			/*String Getpagettitle=driver.getTitle();
				if (!Getpagettitle.equals(value)){
					//return false;

				}
				driver.close();
				driver.switchTo().window(parentWindowHandle);

			 */
			break;



			/*	case "SWITCH_WIN_CLOSE_POPUP":
			parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
			WebDriver Cpopup = null;
			Iterator<String> windowIterator_close = driver.getWindowHandles().iterator();
			while(windowIterator_close.hasNext()) { 
				String windowHandle_close = windowIterator_close.next(); 
				popup = driver.switchTo().window(windowHandle_close);

			}
			Boolean close =driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
			driver.findElement(this.getObject(p,objectName,objectType)).click();
			driver.switchTo().window(parentWindowHandle);
			break;
			 */
		case "SWITCH_CLOSE_ALERT":

			key.SWITCH_CLOSE_ALERT(driver, p, objectName, objectType, test, log);

			break;

		case "GETDATA" :

			key.GETDATA(driver, p, objectName, objectType, test, log,value);

			break;

		default:


			break;
		}
		//return true;
	}

}
