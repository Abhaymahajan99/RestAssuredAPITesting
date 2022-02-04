package DataDrivenTesting;

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
	void PostNewEmployee(String ename , String ejob)
	{
		//Specify base URI
		RestAssured.baseURI="https://reqres.in/api";
		
		//Request object
		RequestSpecification httpRequest = RestAssured.given();
		
		//We create the data which we can send along with the post request.
		JSONObject requestParams = new JSONObject ();
		
		requestParams.put("name", ename);
		requestParams.put("job", ejob);
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
		 
		
		 Assert.assertEquals(ResponseBody.contains(ename),true);
		 Assert.assertEquals(ResponseBody.contains(ejob),true);
		 
		 int StatusCode = response.getStatusCode();
		 Assert.assertEquals(StatusCode, 201);
	}

	@DataProvider(name="empdataprovider")
	String[][] getEmpData() 
	
	{
		String empdata [][]= {{ "Abhay" , "Automation Tester"},{"dilip" , "Manual Tester"}, {"Ravinder","Developer"}};
		return (empdata);
		
	}

}
