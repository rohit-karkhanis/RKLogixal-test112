package com.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Xpath_To_CSS  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String NewId = null;

		ArrayList <String> css = new ArrayList<>() ; 

		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Enter Xpath");

		String userName = myObj.nextLine();  // Read user input

		List<String> items = Arrays.asList(userName.split("/"));
		int count = items.size();
		int lastitem =count-1;
		Iterator iter = items.iterator();

		for (int p=0;p<count;p++) {


			String it = items.get(p);


			if(!(it.equals(""))) {


				if(it.contains("id")) {

					String t1 [] = it.split("\"");
					String rr = t1[1] ;
					NewId="#"+rr;
					css.add(NewId+" > ");

				}
				else if(it.contains("[")) {

					String[] DOMPostionHandling =it.split("\\[|\\]");
					String DOM1=DOMPostionHandling[0];
					String DOM2=DOMPostionHandling[1];

					//System.out.println(DOM1+": nth-of-type("+DOM2+") >");

					if (!(p==lastitem)) {
						css.add(DOM1+":nth-of-type("+DOM2+") > ");
					}
					else{
						css.add(DOM1+":nth-of-type("+DOM2+")");
					}

					//	css.add(DOM1+":nth-of-type("+DOM2+") > ");

				}
				else {

					if (!(p==lastitem)) {
						css.add(it+" > ");
					}
					else{
						css.add(it);
					}
				}

			}

		}
		
		System.out.println("Find css below :");
		for (int i = 0; i < css.size();i++) 
		{ 		     

			System.out.print(css.get(i)); 		
		} 


	}
}

