package com.qa.reqress.api.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.livebarn.api.base.BaseTest;
import com.qa.livebarn.api.constants.AuthType;
import com.qa.livebarn.api.pojo.User;
import com.qa.livebarn.api.pojo.User.UserData;
import com.qa.livebarn.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAllUserTest extends BaseTest{
	
	
	
	
	@Test
	public void getAllUser() {
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
	
	

}
