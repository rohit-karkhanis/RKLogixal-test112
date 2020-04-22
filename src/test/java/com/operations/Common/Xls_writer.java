package com.operations.Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xls_writer {

	String Skip_output_dir;
	String fail_output_dir;
	String Skip_op_file;
	File skipfile;
	File failfile;
	FileOutputStream skipout=null;
	FileOutputStream failout=null;
	XSSFWorkbook skip_workbook;
	XSSFWorkbook fail_workbook;
	XSSFSheet skip_sheet = null;
	XSSFSheet fail_sheet = null;
	int skipcount=1;
	int failcount;
	int skip_rownum = 0;
	int skip_cellnum = 0;
	int fail_rownum = 0;
	int fail_cellnum = 0;

	//String WriteOutput;



	public void GenearateSkipFile(Map<Integer, Object[]> ResultSkipmap,String Functionality,String TestcaseName,String Severity,String WriteSkipOutput) throws IOException{


		if(skipfile==null){

			skip_sheet = CreateSkipfile(WriteSkipOutput);
			ResultSkipmap.put(skipcount, new Object[] {"Functionality","TestcaseName","Severity","Actual_results"});
			ResultSkipmap.put(skipcount+1, new Object[] {Functionality, TestcaseName,Severity,"Skipped"});
			skipcount++;
			Set<Integer> skip_keyset = ResultSkipmap.keySet();

			for (Integer key : skip_keyset) {
				Row row1 = skip_sheet.createRow(skip_rownum++);
				Object [] objArr = ResultSkipmap.get(key);

				for (Object obj : objArr) {
					Cell cell = row1.createCell(skip_cellnum);
					if(obj instanceof Date) 
						cell.setCellValue((Date)obj);
					else if(obj instanceof Boolean)
						cell.setCellValue((Boolean)obj);
					else if(obj instanceof String)
						cell.setCellValue((String)obj);
					else if(obj instanceof Double)
						cell.setCellValue((Double)obj);		
					skip_cellnum++;
				}
				skip_cellnum=0;
			}

			skip_workbook.write(skipout);
			skipout.close();
			ResultSkipmap.clear();
		}
		else{
			UpdateSkipFile(skipfile,skip_workbook ,skip_sheet, ResultSkipmap, Functionality, TestcaseName, Severity, skipcount,skip_rownum);

		}


	}

	public void UpdateSkipFile(File file,XSSFWorkbook workbook,XSSFSheet sheet,Map<Integer, Object[]> Resultsmap,String Functionality,String TestcaseName,String Severity,int count,
			int LastRownum) throws IOException{
		Resultsmap.put(count, new Object[] {Functionality, TestcaseName,Severity,"Skipped"});
		count++;
		Set<Integer> skip_keyset = Resultsmap.keySet();
		skip_cellnum=0;
		for (Integer key : skip_keyset) {
			Row row1 = sheet.createRow(skip_rownum++);
			Object [] objArr = Resultsmap.get(key);
			//int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row1.createCell(skip_cellnum);
				if(obj instanceof Date) 
					cell.setCellValue((Date)obj);
				else if(obj instanceof Boolean)
					cell.setCellValue((Boolean)obj);
				else if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Double)
					cell.setCellValue((Double)obj);		
				skip_cellnum++;
			}


		}
		FileOutputStream outputStream = new FileOutputStream(file);
		skip_workbook.write(outputStream);
		outputStream.close();
		Resultsmap.clear();
	}

	public  XSSFSheet CreateSkipfile(String WriteOutput) throws IOException{
		//Date date = new Date() ;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
		//output_dir="./Testcase_output/dateFormat.format(date)+"/Master_executor_skippedTC's.xlsx";
		Skip_output_dir=WriteOutput+"/Skip_testcases/Master_executor_skippedTC's.xlsx";
		skipfile= new File(Skip_output_dir);
		if (skipfile.getParentFile().mkdirs()) {
			skipfile.createNewFile();
		} else {
			throw new IOException("Failed to create directory " + skipfile.getParent());
		}

		//File newfile= new File(name);

		skipout=new FileOutputStream(skipfile);
		skip_workbook = new XSSFWorkbook();
		skip_sheet = skip_workbook.createSheet("Test Results");
		return skip_sheet;

	}


	public void GenerateFailReport(Map<Integer, Object[]> ResultsFailmap,String Sitename, String Browser, String Functionality,String TestcaseNumber,String Severity,String WriteFailedOutput) throws IOException{

		if(failfile==null){
			fail_sheet = Createfailfile(ResultsFailmap,WriteFailedOutput);
			failcount++;
			//ResultsFailmap.put(failcount, new Object[] {"Sitename","Browser","Functionality","TestcaseNumber","Severity","Actual_results"});
			ResultsFailmap.put(failcount, new Object[] {Sitename,Browser,Functionality,TestcaseNumber,Severity,"Failed"});
			
			Set<Integer> fail_keyset = ResultsFailmap.keySet();

			for (Integer key : fail_keyset) {
				Row row1 = fail_sheet.createRow(fail_rownum++);
				Object [] objArr = ResultsFailmap.get(key);

				for (Object obj : objArr) {
					Cell cell = row1.createCell(fail_cellnum);
					if(obj instanceof Date) 
						cell.setCellValue((Date)obj);
					else if(obj instanceof Boolean)
						cell.setCellValue((Boolean)obj);
					else if(obj instanceof String)
						cell.setCellValue((String)obj);
					else if(obj instanceof Double)
						cell.setCellValue((Double)obj);		
					fail_cellnum++;
				}
				fail_cellnum=0;
			}

			fail_workbook.write(failout);
			failout.close();
			ResultsFailmap.clear();
		}
		else{
			UpdateFailFile(failfile,fail_workbook ,fail_sheet, ResultsFailmap,Sitename,Browser, Functionality, TestcaseNumber, Severity, failcount,fail_rownum);

		}


	}

	public  XSSFSheet Createfailfile(Map<Integer, Object[]> ResultsFailmap,String WriteFailedOutput) throws IOException{

		fail_output_dir=WriteFailedOutput+"/Master_executor_FailedTC's.xlsx";
		failfile= new File(fail_output_dir);
		if (failfile.getParentFile().mkdirs()) {
			failfile.createNewFile();
		} else {
			throw new IOException("Failed to create directory " + skipfile.getParent());
		}

		failout=new FileOutputStream(failfile);
		fail_workbook = new XSSFWorkbook();
		fail_sheet = fail_workbook.createSheet("Test Results");
		failcount++;
		ResultsFailmap.put(failcount, new Object[] {"Sitename","Browser","Functionality","TestcaseNumber","Severity","Actual_results"});
		return fail_sheet;

	}

	public void UpdateFailFile(File file,XSSFWorkbook workbook,XSSFSheet sheet,Map<Integer, Object[]> ResultsFailmap,String Sitename, String Browser,String Functionality,String TestcaseName,String Severity,int count,
			int LastRownum) throws IOException{
		ResultsFailmap.put(count, new Object[] {Sitename,Browser,Functionality, TestcaseName,Severity,"Failed"});
		count++;
		Set<Integer> fail_keyset = ResultsFailmap.keySet();
		fail_cellnum=0;
		for (Integer key : fail_keyset) {
			Row row1 = sheet.createRow(fail_rownum++);
			Object [] objArr = ResultsFailmap.get(key);
			//int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row1.createCell(fail_cellnum);
				if(obj instanceof Date) 
					cell.setCellValue((Date)obj);
				else if(obj instanceof Boolean)
					cell.setCellValue((Boolean)obj);
				else if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Double)
					cell.setCellValue((Double)obj);		
				fail_cellnum++;
			}


		}
		FileOutputStream failoutputStream = new FileOutputStream(file);
	    fail_workbook.write(failoutputStream);
		failoutputStream.close();
		ResultsFailmap.clear();
		
	}

	public void Final(){
		
	}
}


