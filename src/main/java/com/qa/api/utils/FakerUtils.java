package com.qa.api.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
	
	private FakerUtils() {
		
	}
	
	private static final Faker faker = new Faker();
	
	static int getNumber(int startValue,int endValue) {
		return faker.number().numberBetween(startValue, endValue);
	}
	
	static String getAnimalName() {
		return faker.animal().name();
	}
	
	static String getColour() {
		return faker.color().name();
	}
	
	static String getCity() {
		return faker.address().city();
	}
	
	

}
