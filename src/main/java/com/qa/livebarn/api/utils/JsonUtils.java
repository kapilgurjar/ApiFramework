package com.qa.livebarn.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonUtils {
	
	
	public static ObjectMapper objectMapper= new ObjectMapper();
	
	public static <T> T  deserialize(Response response, Class<T> targetClass) {
		
		
		try {
			return objectMapper.readValue(response.getBody().asString(),targetClass);
		} catch(Exception e) {
			throw new RuntimeException("deserilization is failed..."+ targetClass.getName());
		}
	}

}
