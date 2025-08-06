package com.qa.api.testcases;

import org.testng.annotations.BeforeMethod;

import com.qa.api.base.BaseTest;
import com.qa.api.manager.ConfigManager;

import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class getVod extends BaseTest {

	private String access_Token_Api;

	@BeforeMethod
	public void getAccessToken() {

		Response response = restClient.post(base_URL_Test, oauth_token, "kapilcsit@gmail.com",
				"7@mobile", "UUID_VALUE", "Basic TGl2ZUJhcm4gQ0ROOjA3MjAxNA==", "password", ContentType.URLENC);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		access_Token_Api = response.jsonPath().getString("access_token");
		System.out.println(access_Token_Api);
		ConfigManager.set("bearertoken", access_Token_Api);

	}
}

		