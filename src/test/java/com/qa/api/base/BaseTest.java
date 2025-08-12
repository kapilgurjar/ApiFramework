package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	
	protected RestClient restClient;
	
	
	//***API base URL***//
	
	
	protected static String base_URL_Test;
	protected static String Base_URL_Reqres;
	protected static String Base_URL_Pet;
	
	

	
	//***Reqres Api end points****///
	
	protected static final String create_New_Pet="/api/v3/pet";
	
	protected static final String oauth_token="/api/token";
	
	protected static final String getUser_EndPiont="/users";
	
	
	@BeforeSuite
	public void initSetUp() {
		RestAssured.filters(new AllureRestAssured());
		base_URL_Test=ConfigManager.get("baseurl.test").trim();
		Base_URL_Reqres= ConfigManager.get("baseurl.reqres").trim();
		Base_URL_Pet= ConfigManager.get("baseurl.pet");
	}
	
	
	@BeforeTest
	public void setUp() {
		restClient= new RestClient();
	}

}
