package com.operations.Common;

import static org.testng.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
//import javax.servlet.http.Cookie;
import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Keywords {

	private SoftAssert softAssert = new SoftAssert();
	private static Object LOCK = new Object();
	Random random = new Random();
	HashMap<String, String> map = new HashMap<String, String>();
	String SetfilePath = "SetAppData.properties";
	String GetfilePath = "GetAppData.properties";

	public By getObject(Properties p,String objectName,String objectType) throws Exception {
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){

			return By.xpath(p.getProperty(objectName));
		}
		//find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){

			return By.className(p.getProperty(objectName));

		}
		//find by name
		else if(objectType.equalsIgnoreCase("NAME")){

			return By.name(p.getProperty(objectName));

		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){

			return By.cssSelector(p.getProperty(objectName));

		}
		//find by link
		else if(objectType.equalsIgnoreCase("LINK")){

			return By.linkText(p.getProperty(objectName));

		}
		//find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){

			return By.partialLinkText(p.getProperty(objectName));

		}
		else if(objectType.equalsIgnoreCase("ID")){

			return By.id(p.getProperty(objectName));

		}
		else
		{
			throw new Exception("Wrong object type");
		}
	}


	public Map<String, String> GetAppData(String filePath) throws Exception {

		String line;

		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		while ((line = reader.readLine()) != null)
		{
			String[] parts = line.split("=", 2);
			if (parts.length >= 2)
			{
				String key = parts[0];
				String hvalue = parts[1];
				map.put(key, hvalue);
			} else {
				//System.out.println("ignoring line: " + line);
			}

		}
		reader.close();
		return map;

	}
	public void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
		Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
		wait.until(driver1 -> String
				.valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
				.equals("complete"));
	}

	public void CLICK(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception{
		//Perform click


		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));

		//waitForPageLoadComplete(driver, 10);
		//((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
		myWaitVar.until(ExpectedConditions.elementToBeClickable(this.getObject(p, objectName, objectType)));
		Boolean Click = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
		if (Click.booleanValue()==true){

			synchronized (LOCK) {
				LOCK.wait(5000);
				//	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
				driver.findElement(this.getObject(p, objectName, objectType)).click();

				//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
				log.info("WebElement "+ objectName+" Successfully identified"  );
			}
		} 
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}
	}

	public void RESIZE_BROWSER(WebDriver driver,Properties p,String value,ExtentTest test,Logger log) {

		String[] Generate_Width_Height =value.split(",");
		String part1 = Generate_Width_Height[0]; 
		String part2 = Generate_Width_Height[1]; 
		int width= Integer.parseInt(part1);
		int height= Integer.parseInt(part2);

		Dimension d = new Dimension(width,height);
		driver.manage().window().setSize(d);
	}
	public void REMOVE_COOKIE (WebDriver driver) throws Exception{

		driver.manage().deleteCookieNamed("JSESSIONID");
	}

	public void PRESS_ENTER (WebDriver driver,Properties p,String objectName,String objectType) throws Exception{

		driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(Keys.RETURN);
	}

	public void PRESS_BACKSPACE (WebDriver driver,Properties p,String objectName,String objectType) throws Exception{

		driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(Keys.BACK_SPACE);
	}


	public void CLICK_PRE_ENTERTEXT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception{
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));

		//waitForPageLoadComplete(driver, 10);
		//((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
		myWaitVar.until(ExpectedConditions.elementToBeClickable(this.getObject(p, objectName, objectType)));
		Boolean Click = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
		if (Click.booleanValue()==true){

			synchronized (LOCK) {
				LOCK.wait(2000);
				//	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
				driver.findElement(this.getObject(p, objectName, objectType)).click();

				//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
				log.info("WebElement "+ objectName+" Successfully identified"  );
			}
		} 
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}
	}

	public void VERIFY_PAGE_URL(WebDriver driver,String value,ExtentTest test,Logger log) throws NoSuchFieldException {

		String url = driver.getCurrentUrl();
		if (url.equalsIgnoreCase(value))	{
			//test.pass(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully..." , ExtentColor.GREEN));
			log.info("URL : "+value+"Which you are looking for Webelement  has been found successfully...");

		}	

		else {
			Assert.fail("URL : "+value+" Which you are looking for Webelement NOT found...!!!!");
			//test.fail("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!");
			throw new NoSuchFieldException("URL : "+value+" Which you are looking for Webelement NOT found...!!!!");

		}

		//System.out.println(url);

	}

	public void NAVIGATE_URL(WebDriver driver,String value,ExtentTest test,Logger log) throws Exception{
		if(value!=null){

			if (value.contains("$")) {

				String Spvalue[] = value.split("\\$");
				String Part1 =Spvalue[0];
				String ActualValue =Spvalue[1];

				if (ActualValue.equalsIgnoreCase("PunchOutURL")) {
					GetAppData(SetfilePath);
				}
				else {
					GetAppData(GetfilePath);
				}


				for (String key : map.keySet())
				{
					if (key.equalsIgnoreCase(ActualValue)) {

					//	System.out.println(map.get(key));
						driver.get(map.get(key));	
						log.info("Successfully navigated to URL : " + map.get(key) );


					}
				}

			}

			else {

				driver.get(value);	

				Thread.sleep(3000);

				log.info("Successfully navigated to URL : " + value );

			}

		}

		else{

			test.fail(MarkupHelper.createLabel("Test step Navigate URL Failed", ExtentColor.RED));
			log.info("No URL Found...");
		}
	}

	public void MAXIMIZE_BROWSER(WebDriver driver) {

		driver.manage().window().maximize();
	}


	public void ENTERTEXT(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));


		if(value!=null){

			if (value.contains("$")) {

				String Spvalue[] = value.split("\\$");
				String Part1 =Spvalue[0];
				String ActualValue =Spvalue[1];


				GetAppData(GetfilePath);


				for (String key : map.keySet())
				{
					if (key.equalsIgnoreCase(ActualValue)) {

						//System.out.println(map.get(key));
						driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(map.get(key));
						log.info("Successfully Entered text into Element objectName : " + map.get(key) );
						//System.out.println(map.get(key));



					}
				}
			}
			else {
				driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			}
		}
		
	}



	public void VERIFY_ENABLED(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{
		boolean b1=Boolean.parseBoolean(value);  
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
		Boolean enb= driver.findElement(this.getObject(p,objectName,objectType)).isEnabled();
		if ((enb.booleanValue()!=b1))	{

			Assert.fail("Element visibily Mismatched with Expected result..!!!!!!");

		}
	}

	public void CLEAR_TEXT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception {

		driver.findElement(this.getObject(p,objectName,objectType)).clear();

	}


	public void SWITCH_CLOSE_ALERT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception {

		driver.switchTo().alert().dismiss();

	}

	public void SCROLL_UP(WebDriver driver,ExtentTest test,Logger log) throws Exception {

		JavascriptExecutor jse_UP = (JavascriptExecutor) driver;
		jse_UP.executeScript("window.scrollTo(500,0)", "");

	}

	public void SCROLL_DOWN(WebDriver driver,ExtentTest test,Logger log) throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		jse.executeScript("window.scrollBy(0,500)", "");

	}

	public void RIGHT_CLICK(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log) throws Exception {

		Actions Right_click = new Actions(driver);
		Right_click.contextClick(driver.findElement(this.getObject(p,objectName,objectType)));
		Right_click.build().perform();

	}

	public void DROPDOWN_SELECT(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception {

		WebElement Drselect = driver.findElement(this.getObject(p,objectName,objectType)); 
		Boolean tt=Drselect.isDisplayed();
		Select mySelect1= new Select(Drselect);
		mySelect1.selectByVisibleText(value);

	}


	public void DROPDOWN_DESELECTALL(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception {

		WebElement mySelectElm = driver.findElement(this.getObject(p,objectName,objectType)); 
		Select mySelect= new Select(mySelectElm);
		mySelect.deselectAll();

	}


	public void MOUSEOVER(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception {


		WebDriverWait myWaitMouse = new WebDriverWait(driver,20);
		myWaitMouse.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p,objectName,objectType)));
		Boolean MOUSEOVER = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
		if (MOUSEOVER.booleanValue()==true){

			Actions Mouseaction = new Actions(driver);
			WebElement ele = driver.findElement(this.getObject(p,objectName,objectType));
			Mouseaction.moveToElement(ele).build().perform();

			//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
			log.info("WebElement "+ objectName+" Successfully identified"  );
		}
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}

	}

	public void MOUSEOVER_CLICK(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception {


		WebDriverWait myWaitmouseClick = new WebDriverWait(driver,20);
		myWaitmouseClick.until(ExpectedConditions.elementToBeClickable(this.getObject(p,objectName,objectType)));
		Boolean MOUSEOVER_CLICK = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
		if (MOUSEOVER_CLICK.booleanValue()==true){

			Actions MouseactionC = new Actions(driver);
			WebElement eleC = driver.findElement(this.getObject(p,objectName,objectType));
			MouseactionC.moveToElement(eleC).click().perform();

			//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
			log.info("WebElement "+ objectName+" Successfully identified"  );
		}
		else{

			test.fail(MarkupHelper.createLabel("Test step failed", ExtentColor.RED));
			log.info("WebElement "+ objectName+" Not found..."  );
			Assert.fail();

		}


	}

	public void ELEMENT_FROM_LIST(WebDriver driver,Properties p,String objectName,String objectType,String value) throws Exception {
		//WebDriverWait myWaitVar = new WebDriverWait(driver,20);

		List <WebElement> ELELIST =driver.findElements(this.getObject(p, objectName, objectType));
		//System.out.println("Total cart items :" + t1.size());

		if(ELELIST.size()>0) {

			for(int i =0;i<=ELELIST.size();i++)
			{
				if (value.equalsIgnoreCase("CLICK")) {
					
					ELELIST.get(0).click();
				}
				
				else if ((value.equalsIgnoreCase("VERIFY"))){
					
					Boolean ELEVERIFY= ELELIST.get(0).isDisplayed();
					
					if ((ELEVERIFY.booleanValue()!=true))	{

						Assert.fail("Element visibily Mismatched with Expected result..!!!!!!");

					}
				}
				
			}
			
		}
		
		else {
			
			Assert.fail(objectName + " Element Not found ..!!!!!!");
			
		}
	}
	
		

	
	
	public void CLEAR_CART_ITEMS(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception {
		//WebDriverWait myWaitVar = new WebDriverWait(driver,20);

		List <WebElement> t1 =driver.findElements(this.getObject(p, objectName, objectType));
		//System.out.println("Total cart items :" + t1.size());

		if(t1.size()>0) {

			for(int i =0;i<=t1.size()-1;i++)
			{

				//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
				Thread.sleep(10000);
				List <WebElement> t2 =driver.findElements(this.getObject(p, objectName, objectType));
				//System.out.println("Current Cart items" + t2.size());
				Boolean Click = driver.findElement(this.getObject(p, objectName, objectType)).isDisplayed();
				if (Click.booleanValue()==true){

					synchronized (LOCK) {

						try {
							LOCK.wait(5000);
							t2.get(0).click();
							t2.remove(0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


						//test.pass(MarkupHelper.createLabel("Test step passed", ExtentColor.GREEN));
						log.info("WebElement "+ objectName+" Successfully identified"  );
					}


				}
			}
		}



	}

	public void ENTER_RANDOM_VALUE(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
		int low = 1;
		int high = 100000;
		int result = random.nextInt(high-low) + low;
		//System.out.println(value+result);
		driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(result+value);
	}


	public void VERIFY_BROKEN_IMAGE(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser) throws Exception{

		Thread.sleep(5000);
		String Imageload=Sitename+"_"+browser+"_"+"Valid_images";
		String ImageBroke=Sitename+"_"+browser+"_"+"Broken_images";
		System.out.println();
		FileWriter writer = new FileWriter("./AutoGendata/Images_verification/"+Imageload+".txt", true);
		FileWriter writer_broke = new FileWriter("./AutoGendata/Images_verification/"+ImageBroke+".txt", true);
		List<WebElement> allImages = driver.findElements(By.tagName("img"));
		int size=allImages.size();
		log.info("Total Images found :" +size);


		for (WebElement image : allImages) {

			String validateimage = image.getAttribute("src");
			String validateimagealt = image.getAttribute("alt");
			String validateimageclass = image.getAttribute("class");

			if (!(validateimage.equalsIgnoreCase(""))){


				Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
				if (!imageLoaded1)
				{

					writer_broke.write("Broken Image : " + validateimage);
					writer_broke.write("\r\n"); 
					//System.out.println("Broken Image : " + validateimage);
				}
				else
				{

					writer.write("Image Successfully loaded :" + validateimage);
					writer.write("\r\n"); 
					//System.out.println("Image Successfully loaded :" + validateimage);
				}
			}
			else if (!(validateimagealt.equalsIgnoreCase(""))) {
				//String validateimagealt = image.getAttribute("alt");
				Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
				if (!imageLoaded1)
				{

					writer_broke.write("Broken Image : " + validateimagealt);
					writer_broke.write("\r\n"); 
					//System.out.println("Broken Image : " + validateimagealt);
				}
				else
				{

					writer.write("Image Successfully loaded :" + validateimagealt);
					writer.write("\r\n"); 
					//System.out.println("Image Successfully loaded :" + validateimagealt);
				}
			}

			else if (!(validateimageclass.equalsIgnoreCase(""))) {
				//String validateimagealt = image.getAttribute("alt");
				Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
				if (!imageLoaded1)
				{

					writer_broke.write("Broken Image : " + validateimageclass);
					writer_broke.write("\r\n"); 
					//System.out.println("Broken Image : " + validateimagealt);
				}
				else
				{

					writer.write("Image Successfully loaded :" + validateimageclass);
					writer.write("\r\n"); 
					//System.out.println("Image Successfully loaded :" + validateimagealt);
				}
			}

			else {

				//System.out.println("None of the properties defined for Image.");
				log.info("None of the properties defined for Image.");
			}
		}

		writer.close();
		writer_broke.close();

	}

	public void VERIFY_BROKEN_LINK(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser,String value) throws Exception{


		FileWriter Valid_links = new FileWriter("./AutoGendata/Links_verification/"+Sitename+"_"+browser+"_"+"validLinks.txt", true);
		FileWriter broken_links = new FileWriter("./AutoGendata/Links_verification/"+Sitename+"_"+browser+"_"+"brokenLinks.txt", true);

		List<WebElement> links=driver.findElements(By.tagName("a"));

		log.info("Total links are : "+links.size());

		//System.out.println(links.size());

		Iterator<WebElement> it = links.iterator();

		while(it.hasNext()){
			String url =  it.next().getAttribute("href");

			if(!(url == null || url.isEmpty())){

				if(!url.startsWith(value)){
					log.info(url + " : This URL belongs to another domain, skipping it.");

				}
				else {
					URL url_1 = new URL(url);

					HttpURLConnection httpURLConnect=(HttpURLConnection)url_1.openConnection();

					httpURLConnect.setConnectTimeout(3000);

					httpURLConnect.connect();

					//httpURLConnect.c
					System.out.println(httpURLConnect.getResponseCode());

					if(httpURLConnect.getResponseCode()==200)
					{

						Valid_links.write(url+" - "+httpURLConnect.getResponseMessage());
						Valid_links.write("\r\n");
						//	System.out.println(url+" - "+httpURLConnect.getResponseMessage());

					}
					if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  

					{
						broken_links.write(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
						broken_links.write("\r\n");
						System.out.println(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
					}

				}
			}


		}	
		Valid_links.flush();
		Valid_links.close();
		broken_links.flush();
		broken_links.close();


	}

	public void MATH_VERIFCATION(WebDriver driver,ExtentTest test,Logger log,String Sitename,String browser,String value){

		String Final_price = driver.findElement(By.xpath(".//dd[contains(@class,'fl total omniTotalPrice')]")).getText();
		Boolean ap= driver.findElement(By.xpath(".//dd[contains(@class,'fl')]/span[contains(@class,'highlight')]")).isDisplayed();
		String Actaul_price =driver.findElement(By.xpath(".//dd[contains(@class,'fl')]/span[contains(@class,'highlight')]")).getText();
		//System.out.println(Final_price);
		//System.out.println(Actaul_price);

		String[] Generate_expected =Actaul_price.split("\\$");
		String part1 = Generate_expected[0]; 
		String part2 = Generate_expected[1]; 
		//System.out.println(part1);
		//System.out.println(part2);

		//String removedecimal_expected=part2.replaceAll(".","");

		String[] Generate_Actual =Final_price.split("\\$");
		String part11 = Generate_Actual[0]; 
		String part21 = Generate_Actual[1]; 
		//System.out.println(part11);
		//System.out.println(part21);

		//String removedecimal_Actual=part21.replaceAll(",","");

		float j= Float.parseFloat(part2);
		float k= Float.parseFloat(part21);
		int i= Integer.parseInt(value);
		double expected=j*i;
		DecimalFormat df = new DecimalFormat("0.00");
		String Formated = df.format(expected);

		log.info("Expected Price is :" + Formated);
		if(expected==k){

			log.info("Actual price is matching with expected price.Testcase passed..!!!");
			//logger.log(LogStatus.PASS, "Test Step Passed");
			//test.pass(MarkupHelper.createLabel("Test Step Passed", ExtentColor.GREEN));
		}
		else {

			log.info("Actual price is not matching with expected price.Testcase Failed..!!!");
			//logger.log(LogStatus.FAIL, "Test Step Failed");
			test.fail(MarkupHelper.createLabel("Math verfication Failed", ExtentColor.RED));

		}



	}

	public void VERIFY_PAGE_TITLE(WebDriver driver,ExtentTest test,Logger log,String value) throws NoSuchFieldException{
		String GetTitle;
		GetTitle = driver.getTitle();
		if (GetTitle.equalsIgnoreCase(value))	{
			//test.pass(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully..." , ExtentColor.GREEN));
			log.info("Successfull navigated to Expected Page i.e. on : "+value);

		}	
		else if(value.equals("")) {

			Assert.fail("Please Specify Page title which you are looking for into Excel under Data_descriptor option!!!!!!");

		}
		else {
			Assert.fail();
			throw new NoSuchFieldException("Page : "+value+"Which you are looking NOT found...!!!!");

		}
	}

	public void VERIFY_TEXT_PRESENT(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log,String value){
		String Gettext;
		try {
			WebDriverWait myWaitVar = new WebDriverWait(driver,20);
			myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			Gettext = driver.findElement(this.getObject(p,objectName,objectType)).getText();
			// Assert.assertEquals(Gettext,value);
			if (Gettext.equalsIgnoreCase(value))	{
				//test.pass(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully..." , ExtentColor.GREEN));
				log.info("TEXT : "+value+"Which you are looking for Webelement "+objectName+" has been found successfully...");

			}	

			else {
				Assert.fail("TEXT : "+value+" Which you are looking for Webelement "+objectName+" NOT found...!!!!");
				//test.fail("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!");
				throw new NoSuchFieldException("TEXT : "+value+" Which you are looking for Webelement "+objectName+" NOT found...!!!!");

			}



		}	catch (Exception e) {
			// TODO Auto-generated catch block
			//test.fail(MarkupHelper.createLabel("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!", ExtentColor.RED));
			log.error("TEXT : "+value+"Which you are looking for Webelement"+objectName+"NOT found...!!!!");
			//log.error(e.printStackTrace());
		}

	}


	public void GETDATA(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log,String value) throws Exception{
		String Gettext;
		Gettext = driver.findElement(this.getObject(p,objectName,objectType)).getText();
		HashMap<String, String> map = new HashMap<String, String>();
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(GetfilePath));
		BufferedWriter bw = new BufferedWriter((new FileWriter(SetfilePath)));
		while ((line = reader.readLine()) != null)
		{
			String[] parts = line.split("=", 2);
			if (parts.length >= 2)
			{
				String key = parts[0];
				String hvalue = parts[1];
				map.put(key, hvalue);
			} else {
				//System.out.println("ignoring line: " + line);
			}
		}

		for (String key : map.keySet())
		{
			if (key.equalsIgnoreCase(value)) {

				map.put(key, Gettext);
				bw.write(key + "=" + map.get(key)+"\n");
				bw.write(System.lineSeparator());
			}
		}
		reader.close();
		bw.close();
	}


	public void WAIT(WebDriver driver) throws InterruptedException{

		//WebDriverWait wait = new WebDriverWait(driver, 100);
		//driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;
		Thread.sleep(5000);

	}

	public void WAITFORLOAD(WebDriver driver) {
		//UnUsed Keyword. No Need of this keyword. soon will deprecate this.
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void SLEEPWAIT() throws InterruptedException {
		//UnUsed Keyword. No Need of this keyword. soon will deprecate this.
		Thread.sleep(5000);
	}



	public void NAVIGATE_BACK(WebDriver driver){

		driver.navigate().back();

	}
	public void CLOSE_BROWSER(WebDriver driver){

		driver.close();

	}

	public void QUIT_BROWSER(WebDriver driver){

		driver.quit();

	}

	public void VERIFY_WEBELEMENT_PRESENT(WebDriver driver,Properties p,String objectName,String objectType,String value) throws Exception{

		boolean WebElement=Boolean.parseBoolean(value);  
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);

		if (value.equalsIgnoreCase("True")) {

			myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			Boolean enbT= driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
			if ((enbT.booleanValue()!=WebElement))	{

				Assert.fail("Element visibily Mismatched with Expected result..!!!!!!");

			}
		}

		else if(value.equalsIgnoreCase("False")) {

			Boolean b1 = new Boolean(true);  
			Boolean enbF =myWaitVar.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			//Boolean enbF= driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
			if ((enbF.booleanValue()!=b1))	{

				Assert.fail("Element visibily Mismatched with Expected result..!!!!!!");

			}
		}

		else if(value.equals("")) {

			Assert.fail("Please Specify Expected visibility of element : " +objectName +" i.e either TRUE or FALSE..!!!!!!");

		}
	}



	public void CLEAR_TEXTBOX(WebDriver driver,Properties p,String objectName,String objectType,ExtentTest test,Logger log){

		try {
			driver.findElement(this.getObject(p,objectName,objectType)).isDisplayed();
			driver.findElement(this.getObject(p,objectName,objectType)).clear();
			test.pass(MarkupHelper.createLabel("Textbox : "+objectName+"Which you are looking for has been found & cleared successfully " , ExtentColor.GREEN));
			log.info("Textbox : "+objectName+"Which you are looking for has been found & cleared successfully ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void GETATTRIBUTE(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{
		//UnUsed Keyword. No Need of this keyword. soon will deprecate this.
		String Verify_text=	driver.findElement(this.getObject(p,objectName,objectType)).getAttribute("value");
		if(Verify_text.equalsIgnoreCase(value)){

		}
	}
	public void PRESS_ESC(WebDriver driver,Properties p,String objectName,String objectType,String value,ExtentTest test,Logger log) throws Exception{

		Actions ESC = new Actions(driver);
		ESC.sendKeys(Keys.ESCAPE).build().perform();

	}

	public void REFRESH_PAGE(WebDriver driver)
	{

		driver.navigate().refresh();
	}


}


