package DataDrivenTesting;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DataDrivenAddUser_DataProvider {
	
	@Test(dataProvider="empdataprovider")
	void PostNewEmployee(String Ename , String Ejob)
	{
		//Specify base URI
		RestAssured.baseURI="https://reqres.in/api";
		
		//Request object
		RequestSpecification httpRequest = RestAssured.given();
		
		//We create the data which we can send along with the post request.
		JSONObject requestParams = new JSONObject ();
		
		requestParams.put("name", Ename);
		requestParams.put("job", Ejob);
		//requestParams.put("age", "35");
		
		//add a header stating the request body in Json
		 httpRequest.header("Content-Type","application/json");
		 
		 //add json to the body of the request
		 httpRequest.body(requestParams.toJSONString());
		 
		 //Post request
		 Response response = httpRequest.request(Method.POST,"/users");
		 
		 //capture response body to perform validation
		 String ResponseBody = response.getBody().asString();
		 System.out.println("Response Boday :"+ ResponseBody);
		 
		
		 Assert.assertEquals(ResponseBody.contains(Ename),true);
		 Assert.assertEquals(ResponseBody.contains(Ejob),true);
		 
		 int StatusCode = response.getStatusCode();
		 Assert.assertEquals(StatusCode, 201);
	}

	@DataProvider(name="empdataprovider")
	String[][] getEmpData() throws IOException 
	
	{
		//Read data from excel
		String Path =System.getProperty("user.dir")+"/src/test/java/DataDrivenTesting/empdata.xls";
		
		

		//String Path ="/Users/activemac03/git/RestAssuredAPITesting/src/test/java/DataDrivenTesting/empdata.xlsx";
		int rownum = XLUtility.getRowCount(Path, "Sheet1");
		
		int colcount=XLUtility.getCellCount(Path, "Sheet1", 1);
		
		
		String empdata [][] = new String[rownum][colcount];
		
		for(int i=1 ; i<=rownum ; i++){
			for(int j=0; j<colcount ; j++) {
				
				empdata [i-1][j] =XLUtility.getCellData(Path, "Sheet1", i, j);
			}
			
		}
		
		
		
		
		//String empdata [][]= {{ "Abhay" , "Automation Tester"},{"dilip" , "Manual Tester"},{"Ravinder","Developer"}};
		return (empdata);
		
	}

}
