package com.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.operations.Common.Constants;
import com.operations.Common.ReadUserconfig;
import com.operations.Common.Readconfig;
import com.operations.Common.Xls_Reader;

public class Master_data {

	static Xls_Reader Readexcel = null;
	static XSSFWorkbook Master_Workbook = null;
	static ReadUserconfig uc =new ReadUserconfig();

	@DataProvider(name = "Fetch_Master_data")
	public static Object [][] passdata() throws IOException  {
		uc.getUserConfig();
		Object[][]Sheet_data = null;
		Object[][]Total_data=null;
		int Final_rows=0;
		int Final_columns=0;
		String Filepath="./Testcase_Input";
		String output_dir = null;
		String Suites = uc.Suite;

		int Total_Master_sheets = 0;
		XSSFSheet sh1;


		int rows_temp = 0;	
		int cols_temp;
		int Total_rows;

		String[] DefineSuite =Suites.split(",");

		int lnt = DefineSuite.length;
		System.out.println("Total "+ lnt + " Suites Found..." );
		Total_rows=0;
		for (int h=0;h<lnt;h++) {

			String str = DefineSuite[h];
			Readexcel(str);
			cols_temp=0;
			Total_Master_sheets=Readexcel.getNumberOfSheets(Master_Workbook);

			for (int k = 0; k <Total_Master_sheets; k++) {

				sh1 = Master_Workbook.getSheetAt(k);
				int rows_currentsheet =Readexcel.getRowCountBySheetnumber(k);
				rows_temp=rows_currentsheet;


			}
			Total_rows=Total_rows+rows_temp;
		}

		cols_temp=Readexcel.getColumnCountBySheetnumber(0);
		Total_data=new Object[Total_rows-lnt][cols_temp];


		for (int p=0;p<lnt;p++) {

			String str1 = DefineSuite[p];
			Readexcel(str1);

			for (int k = 0; k <Total_Master_sheets; k++) {

				sh1 = Master_Workbook.getSheetAt(k);
				rows_temp = Readexcel.getRowCountBySheetnumber(k);

				Total_rows=rows_temp+rows_temp;

				int i;
				int j = 0;

				Sheet_data = new Object[rows_temp][cols_temp];

				for (i = 1; i<= rows_temp-1; i++) {


					for (j = 0; j < cols_temp; j++) {

						Sheet_data[i][j]=sh1.getRow(i).getCell(j).getStringCellValue();
						//System.out.println(sh1.getRow(i).getCell(j).getStringCellValue());


						Total_data[Final_rows][Final_columns]=Sheet_data[i][j];
						Final_columns++;
					}

					Final_rows++;
					if(Final_columns==cols_temp){
						Final_columns=0;
					}

				}

			}
		}

		return Total_data;
	}
	private static void Readexcel(String str) {
		
		//System.out.println(System.getProperty("user.dir"));
		// TODO Auto-generated method stub
		File Master_file = null;
		
		if(uc.OS.equalsIgnoreCase("Windows")) {

			 Master_file =	new File(Constants.Windows_MEFileLocation+"_"+str+".xlsx");
		}
		
		else if (uc.OS.equalsIgnoreCase("Linux")) {
			
			
			 Master_file =	new File("/Input_files/Master_executors/MasterExecutor"+"_"+str+".xlsx");
			// (System.getProperty("user.dir")+"/Resources/Config.properties")
		}
		
		else {
			
			System.out.println("Please Specify OS correctly i.e. either Windows or Linux...!!!!");
		}
		//File Master_file =	new File("./Input_files/Master_executors/MasterExecutor_"+str+".xlsx");

		FileInputStream Master_inputStream = null;
	
		Readexcel = new Xls_Reader(System.getProperty("user.dir")+Master_file);
		
		try {
		
			Master_inputStream = new FileInputStream(System.getProperty("user.dir")+Master_file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			Master_Workbook = new XSSFWorkbook(Master_inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
