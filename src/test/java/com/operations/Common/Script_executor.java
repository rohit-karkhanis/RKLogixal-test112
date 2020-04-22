package com.operations.Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.varia.ExternallyRolledFileAppender;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

import com.Utilities.ScreenshotsUtility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Script_executor {

	public static Xls_Reader Readexcel;
	XSSFWorkbook workbook;
	XSSFSheet skip_sheet;
	public static String Wrk;
	Map<Integer, Object[]> Teststeps_results = null;
	Map<Integer, Object[]> Teststeps_skipresults = new LinkedHashMap<Integer, Object[]>();
	Date date;
	SimpleDateFormat dateFormat;
	String Key;
	String Value;
	String Test_value;
	ScreenshotsUtility scr;
	Xls_writer xls_writer=new Xls_writer();
	//ExtentTest logger;
	File outdir;
	Properties allObjects;
	public static String Object;
	long FinalTime = 0;
	

	public void Execute_script(String Sitename ,String browsername,String Filepath,String WriteOutput,String ScreenshotsPath, WebDriver driver,String Section,String Functionality,String Testcasenumber,
			String Testcase_description , String Executionmode,String Severity,String Screenshots , String ExcelReports,ExtentReports extent,Logger log) throws Throwable{


		Keywords_finder operation = new Keywords_finder(driver,log);
		ReadObject object = new ReadObject();

		allObjects =  object.getObjectRepository();
		
		String InputFilepath=Filepath+Functionality+"/";
		Readexcel = new Xls_Reader(Filepath+Section+"/"+Functionality+"/"+Testcasenumber+".xlsx");
		File file =	new File(Filepath +Section+"/"+Functionality +"/"+ Testcasenumber+".xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook Workbook = null;
		Workbook = new XSSFWorkbook(inputStream);
		int Sheets = Readexcel.getNumberOfSheets(Workbook);
		//Workbook.getNumberOfSheets();
		//for (int j=0;j<Sheets;j++){

		Sheet  Teststep_sheet = Workbook.getSheetAt(0);
		String Teststep_sheet_name=Teststep_sheet.getSheetName();
		//Sheet  Testdata_sheet = Workbook.getSheetAt(1);
		HashMap<String, String> hmap = new HashMap<String, String>();
		int Testdata_row=Readexcel.getRowCountBySheetname(Constants.TEST_DATA_SHEET);
		date = new Date() ;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;
		log.info("Execution Started for Testcase number : " + Testcasenumber+" at time : "+ dateFormat.format(date));
		int array[][]=new int[Testdata_row][2];
		for (int p=1;p<Testdata_row;p++){
			Key=Readexcel.getCellDataBySheetName(Constants.TEST_DATA_SHEET, 0, p);
			Value=Readexcel.getCellDataBySheetName(Constants.TEST_DATA_SHEET, 1, p);
			hmap.put(Key, Value);
		}

		Wrk=Workbook.getSheetName(0);

		int rowCount = Teststep_sheet.getLastRowNum()-Teststep_sheet.getFirstRowNum();
		workbook = new XSSFWorkbook();
		skip_sheet = workbook.createSheet("Test Results");
		Teststeps_results = new LinkedHashMap<Integer, Object[]>();
		Teststeps_results.put(1, new Object[] {"TestCase", "Keyword", "Object","ObjectType","value","Actual Result"});

		for (int i = 1; i < rowCount+1; i++) {

			String str = Teststep_sheet.getRow(i).toString();
			Row row = Teststep_sheet.getRow(i);
			scr=new ScreenshotsUtility();
			String Keyword=Readexcel.getCellDataBySheetName(Teststep_sheet_name, 1, i);
			Object=Readexcel.getCellDataBySheetName(Teststep_sheet_name, 2, i);
			String ObjectType=Readexcel.getCellDataBySheetName(Teststep_sheet_name, 3, i);
			String Data_descriptor=Readexcel.getCellDataBySheetName(Teststep_sheet_name, 4, i);
			
			long start = System.currentTimeMillis();

			if(!(Data_descriptor=="")){
				Test_value=hmap.get(Data_descriptor).toString();
				operation.perform(allObjects, Keyword, Object,ObjectType,Test_value,Sitename,browsername);
				Teststeps_results.put(i+1, new Object[] {Wrk,  Keyword, Object,ObjectType,Test_value,"Pass"});

			}
			else{
				operation.perform(allObjects, Keyword, Object,ObjectType,"",Sitename,browsername);
				Teststeps_results.put(i+1, new Object[] {Wrk,  Keyword, Object,ObjectType,"","Pass"});

			}
			
			if(Screenshots.equalsIgnoreCase("Yes")) {
				try {


					scr.Screenshots(driver,ScreenshotsPath,Wrk);



				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		if(ExcelReports.equalsIgnoreCase("Yes")) {
			
			GenerateTestResults(WriteOutput);
		}
		
		log.info("Execution Ended for testcase number : "+ Testcasenumber+" at time : " + dateFormat.format(date));
		
		scr=null;

	}

	public void GenerateTestResults(String WriteOutput) throws Throwable{

		if(Teststeps_results!=null){

			Set<Integer> keyset = Teststeps_results.keySet();
			int rownum = 0;
			for (Integer key : keyset) {
				Row row1 = skip_sheet.createRow(rownum++);
				Object [] objArr = Teststeps_results.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row1.createCell(cellnum++);
					if(obj instanceof Date) 
						cell.setCellValue((Date)obj);
					else if(obj instanceof Boolean)
						cell.setCellValue((Boolean)obj);
					else if(obj instanceof String)
						cell.setCellValue((String)obj);
					else if(obj instanceof Double)
						cell.setCellValue((Double)obj);
				}
			}
			String op_file=WriteOutput+"TestResults_"+Wrk+".xlsx" ;
			outdir= new File(op_file);
			outdir.getParentFile().mkdirs();
			outdir.createNewFile();

			//	
			FileOutputStream out =new FileOutputStream(new File(op_file));
			workbook.write(out);
			out.close();

		}

	}

}
