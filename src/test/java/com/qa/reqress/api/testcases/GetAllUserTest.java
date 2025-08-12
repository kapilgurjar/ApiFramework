package com.qa.reqress.api.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.pojo.User.UserData;
import com.qa.api.utils.JsonPathValidatorUtil;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAllUserTest extends BaseTest{
	
	
	
	
	@Test
	public void getAllUserWithPOJO() {
		Response response =restClient.get(Base_URL_Reqres, getUser_EndPiont, ContentType.JSON, AuthType.API_KEY, null, null);
		Assert.assertEquals(response.getStatusCode(), 200);
		User allUser=JsonUtils.deserialize(response, User.class);
		System.out.println(allUser.getTotal_pages());
		System.out.println(allUser.getPage());
		System.out.println(allUser.getTotal());
		System.out.println(allUser.getPer_page());
		
		
		//Get First id 
		
		System.out.println("Get firts id: "+allUser.getData().get(0).getId());
		
		List<UserData> data =allUser.getData();
		for (int i=0;i<data.size();i++) {
			int id=data.get(i).getId();
			Assert.assertNotNull(id);
			System.out.println("id is :" +id);
			String firtsName=data.get(i).getFirst_name();
			Assert.assertNotNull(firtsName);
			System.out.println("firtsName is :" +firtsName);
			String lsName=data.get(i).getLast_name();
			Assert.assertNotNull(lsName);
			System.out.println("firtsName is :" +lsName);
		}
		
		String getText=allUser.getSupport().getText();
		System.out.println(getText);
		
		
	}
	
	@Test
	public void getAllUserWithJsonPathQuery() {
		Response response =restClient.get(Base_URL_Reqres, getUser_EndPiont, ContentType.JSON, AuthType.API_KEY, null, null);
		Assert.assertEquals(response.getStatusCode(), 200);
		String email=JsonPathValidatorUtil.read(response,"$.data[0].email");
		Assert.assertNotNull(email);
		System.out.println("Email of first user" +email);
		
		String firstName=JsonPathValidatorUtil.read(response,"$.data[0].first_name");
		Assert.assertNotNull(firstName);
		System.out.println("FirstName is :" +firstName);
		
		String text=JsonPathValidatorUtil.read(response, "$.support.text");
		System.out.println("Support text is "+text);
		Assert.assertTrue(text.contains("Content Caddy generate it for you"));
		
		//d51a424d279045ed884a43a4e4a098c7
		//c4680ed457dc41d18060245bed317da3
		
		//MyAPP
	}
	
	

}
