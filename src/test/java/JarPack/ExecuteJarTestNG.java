package JarPack;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class ExecuteJarTestNG {

	public static void main(String[] args) {
		
		ExecuteSuite();
	}
	
	public static void ExecuteSuite() {

		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add("TestRun.xml");//path to xml..
		// suites.add("c:/tests/testng2.xml");
		testng.setTestSuites(suites);
		testng.run();
	}

}
