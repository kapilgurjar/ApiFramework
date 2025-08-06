package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.expect;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {

	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);

	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

	private RequestSpecification setUpRequest(String baseUrl, ContentType contentType, AuthType authType) {

		RequestSpecification request = RestAssured.given().log().all().baseUri(baseUrl).contentType(contentType)
				.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " +ConfigManager.get("bearertoken"));
			break;

		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken());
			break;

		case API_KEY:
			request.header("x-api-key", ConfigManager.get("api-key"));
			break;

		case NO_AUTH:
			System.out.println("Auth is not required...");
			break;

		default:
			System.out.println("this auth is not supported....please pass the right AuthType...");
			throw new APIException("===Invalid Auth====");
		}

		return request;

	}

	private String generateBasicAuthToken() {

		String credentials = ConfigManager.get("username") + ":" + ConfigManager.get("password");
		return Base64.getEncoder().encodeToString(credentials.getBytes());

	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams,
			Map<String, String> pathParams) {

		if (queryParams != null) {
			request.queryParams(queryParams);
		}

		if (pathParams != null) {
			request.pathParams(pathParams);
		}

	}

	public Response get(String baseUrl, String endPoint, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {
		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T> Response post(String baseUrl, String endPoint, T body, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {
		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}

	public Response post(String baseUrl, String endPoint, String clientId, String clientSecret, String grantType,
			ContentType contentType) {
		Response response = RestAssured.given().contentType(contentType).formParam("grant_type", grantType)
				.formParam("client_id", clientId).formParam("client_secret", clientSecret).when()
				.post(baseUrl + endPoint);
		response.prettyPrint();
		return response;
	}

	public Response post(String baseUrl, String endPoint, String username, String password, String deviceId,
			String basicAuthHeader, String grantType, ContentType contentType) {

		Response response = RestAssured.given().contentType(contentType).formParam("grant_type", grantType)
				.formParam("username", username).formParam("password", password).formParam("device_id", deviceId)
				.header("Authorization", basicAuthHeader).post(baseUrl + endPoint);

		response.prettyPrint();
		return response;
	}

	public <T> Response post(String baseUrl, String endPoint, File file, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {
		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T> Response put(String baseUrl, String endPoint, T body, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {
		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T> Response patch(String baseUrl, String endPoint, T body, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {
		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T> Response delete(String baseUrl, String endPoint, ContentType contentType, AuthType authType,
			Map<String, String> queryParams, Map<String, String> pathParams) {

		RequestSpecification request = setUpRequest(baseUrl, contentType, authType);
		applyParams(request, queryParams, pathParams);
		Response response = request.delete(endPoint).then().spec(responseSpec204).extract().response();
		response.prettyPrint();
		return response;
	}

}
