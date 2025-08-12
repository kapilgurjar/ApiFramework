package com.qa.api.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.manager.ConfigManager;

import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class SpotifyTest extends BaseTest {

	private String access_Token_Api;

	@BeforeMethod
	public void getAccessToken() {

		Response response = restClient.post(base_URL_Test, oauth_token,"d51a424d279045ed884a43a4e4a098c7","c4680ed457dc41d18060245bed317da3","client_credentials",
				 ContentType.URLENC);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		access_Token_Api = response.jsonPath().getString("access_token");
		System.out.println(access_Token_Api);
		ConfigManager.set("bearertoken", access_Token_Api);

	}
	@Test
	public void verifySpotifyToken() {
		
		System.out.println("Access token is "+access_Token_Api);
	}
}

		