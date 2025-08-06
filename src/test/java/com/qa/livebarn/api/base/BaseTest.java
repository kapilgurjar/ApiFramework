package com.qa.livebarn.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.qa.livebarn.api.client.RestClient;
import com.qa.livebarn.api.manager.ConfigManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	
	protected RestClient restClient;
	
	
	//***API base URL***//
	
	
	protected static String base_URL_Livebarn;
	protected static String Base_URL_Reqres;
	
	
	//***API end point***//
	
	protected static final String oauth_token="/oauth/token";
	protected static final String vod_Media_EndPoint="/api/v2.0.0/media/day/surfaceid/{SURFACE_ID}/date/{VOD_START_DATE}";
	
	
	
	//***Reqres Api end points****///
	
	protected static final String getUser_EndPiont="/users";
	
	
	@BeforeSuite
	public void initSetUp() {
		RestAssured.filters(new AllureRestAssured());
		base_URL_Livebarn=ConfigManager.get("baseurl.livebarn").trim();
		Base_URL_Reqres= ConfigManager.get("baseurl.reqres").trim();
	}
	
	
	@BeforeTest
	public void setUp() {
		restClient= new RestClient();
	}

}
