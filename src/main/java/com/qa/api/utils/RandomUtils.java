package com.qa.api.utils;

public class RandomUtils {
	
	private RandomUtils() {
		
	}
	
	public static int getId() {
		return FakerUtils.getNumber(10, 100);
	}
	
	public static String getAnimalName() {
		return FakerUtils.getAnimalName();
	}
	
	public static String GetColourName() {
		return FakerUtils.getColour();
	}


}
