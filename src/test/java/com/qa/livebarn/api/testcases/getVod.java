package com.qa.livebarn.api.testcases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.apache.groovy.util.Maps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qa.livebarn.api.base.BaseTest;
import com.qa.livebarn.api.constants.AuthType;
import com.qa.livebarn.api.manager.ConfigManager;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import com.qa.livebarn.api.pojo.GetVodMedia;
import com.qa.livebarn.api.utils.JsonPathValidatorUtil;
import com.qa.livebarn.api.utils.JsonUtils;

public class getVod extends BaseTest {

	private String access_Token_Api;

	@BeforeMethod
	public void getAccessToken() {

		Response response = restClient.post(base_URL_Livebarn, oauth_token, "priyanshu.srivastava@rsystems.com",
				"7@mobile", "UUID_VALUE", "Basic TGl2ZUJhcm4gQ0ROOjA3MjAxNA==", "password", ContentType.URLENC);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		access_Token_Api = response.jsonPath().getString("access_token");
		System.out.println(access_Token_Api);
		ConfigManager.set("bearertoken", access_Token_Api);

	}

	@Test
	public void GetVodMedia() throws JsonMappingException, JsonProcessingException {

		Map<String, String> pathParam = Maps.of("SURFACE_ID", "1362", "VOD_START_DATE", "2025-07-16T00:30");

		Response response = restClient.get(base_URL_Livebarn, vod_Media_EndPoint, ContentType.JSON,
				AuthType.BEARER_TOKEN, null, pathParam);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		int size = response.jsonPath().getInt("$.size()");
		System.out.println(size);

		Headers header = response.headers();
		List<Header> headerlist = header.asList();

		for (Header e : headerlist) {
			String name = e.getName();
			String value = e.getValue();
			System.out.println(name + ":" + value);

		}

		GetVodMedia[] media = JsonUtils.deserialize(response, GetVodMedia[].class);

		for (GetVodMedia m : media) {
			System.out.println("Duration time is :" + m.getDuration());
			System.out.println("Begin Date is  : " + m.getBeginDate());
			System.out.println("Current Feed mode is :" + m.getFeedModeId());
			System.out.println(m.getRenditionId());
		}

	}

	@Test
	public void getBlackOutJson() throws IOException {
		String email = "api" + System.currentTimeMillis() + "@gamil.com";
		String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/Data/blackout.json")));
		String newJson = rawJson.replace("{{BLACKOUT_SESSION_UUID}", email);
		System.out.println(newJson);
	}

	@Test
	public void getbeginDateWithJasonPathQuery() {

		Map<String, String> pathParam = Maps.of("SURFACE_ID", "1362", "VOD_START_DATE", "2025-07-16T00:30");

		Response response = restClient.get(base_URL_Livebarn, vod_Media_EndPoint, ContentType.JSON,
				AuthType.BEARER_TOKEN, null, pathParam);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		List<String> beginDate = JsonPathValidatorUtil.readList(response, "$.[*][?(@.feedModeId==5)]['beginDate']");

		for (String date : beginDate) {
			System.out.println("beginDate is:  " + date);
		}

		System.out.println("===============================================");

		// Get As Map

		List<Map<String, Object>> dateAndBeginDate = JsonPathValidatorUtil.readListOfMaps(response,
				"$.[*][?(@.feedModeId==5)]['beginDate','duration']");

		for (Map<String, Object> date : dateAndBeginDate) {
			String beginDate1 = (String) date.get("beginDate");
			System.out.println("begning Date is " + beginDate1);

			int duration = (int) date.get("duration");
			System.out.println("dauration of vod " + duration);
		}

	}

	@Test
	public void getSingleDurationOnly() {

		Map<String, String> pathParam = Maps.of("SURFACE_ID", "1362", "VOD_START_DATE", "2025-07-16T00:30");

		Response response = restClient.get(base_URL_Livebarn, vod_Media_EndPoint, ContentType.JSON,
				AuthType.BEARER_TOKEN, null, pathParam);
		int statuscode = response.getStatusCode();
		System.out.println(statuscode);
		int beginDate = JsonPathValidatorUtil.read(response, "$[0].duration");
		System.out.println("Begning Date is " + beginDate);

	}

}
