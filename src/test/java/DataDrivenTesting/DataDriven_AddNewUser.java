package DataDrivenTesting;

import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DataDriven_AddNewUser {
	
	@Test
	void PostNewEmployee()
	{
		//Specify base URI
		RestAssured.baseURI="https://reqres.in/api";
		
		//Request object
		RequestSpecification httpRequest = RestAssured.given();
		
		//We create the data which we can send along with the post request.
		JSONObject requestParams = new JSONObject ();
		
		requestParams.put("name", "Abhhi");
		requestParams.put("job", "leader");
		//requestParams.put("age", "35");
		
		//add a header stating the request body in Json
		 httpRequest.header("Content-Type","application/json");
		 
		 //add json to the body of the request
		 httpRequest.body(requestParams.toJSONString());
		 
		 //Post request
		 Response response = httpRequest.request(Method.POST,"/users");
		 
		 //capture response body to perform validation
		 String ResponseBody = response.getBody().asString();
		 
		
		 Assert.assertEquals(ResponseBody.contains("Abhhi"),true);
		 Assert.assertEquals(ResponseBody.contains("leader"),true);
		 
		 int StatusCode = response.getStatusCode();
		 Assert.assertEquals(StatusCode, 201);
	}

}
