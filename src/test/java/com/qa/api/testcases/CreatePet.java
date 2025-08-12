package com.qa.api.testcases;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Pet;
import com.qa.api.pojo.Pet.Category;
import com.qa.api.pojo.Pet.Tags;
import com.qa.api.utils.JsonPathValidatorUtil;
import com.qa.api.utils.RandomUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreatePet extends BaseTest {

	// https://jsonpath.com/

	@Description("This api will create a new pet")
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Add jira epic here")
	@Story("Jira Story")
	@Test()
	public void create_Pet() throws JsonMappingException, JsonProcessingException {
		Category cat = new Category();
		cat.setId(10);
		cat.setName(RandomUtils.getAnimalName());

		Tags tag1 = new Tags();
		tag1.setId(15);
		tag1.setName(RandomUtils.GetColourName());

		Tags tag2 = new Tags();
		tag2.setId(17);
		tag2.setName(RandomUtils.GetColourName());

		List<Tags> tag = Arrays.asList(tag1, tag2);

		Pet pet = new Pet();
		pet.setName(RandomUtils.getAnimalName());
		pet.setId(RandomUtils.getId());
		pet.setCategory(cat);
		pet.setStatus("available");
		pet.setTags(tag);
		pet.setPhotoUrls(Arrays.asList("https://ex.com", "https://dog.com"));

		Response res = restClient.post(Base_URL_Pet, create_New_Pet, pet, ContentType.JSON, AuthType.NO_AUTH, null,
				null);
		int statuscode = res.getStatusCode();
		System.out.println("Status code is " + statuscode);

		ObjectMapper mapper = new ObjectMapper();
		Pet petDes = mapper.readValue(res.getBody().asString(), Pet.class);
		List<String> photoUrl = petDes.getPhotoUrls();
		System.out.println("Photo url is " + photoUrl);
		Assert.assertEquals(photoUrl, Arrays.asList("https://ex.com", "https://dog.com"));
		Assert.assertEquals(petDes.getTags(), tag);
		Assert.assertEquals(petDes.getCategory(), cat);

	}

	@Description("This api will create a new pet")
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Add jira epic here")
	@Story("Jira Story")
	@Test()
	public void create_PetWithJsonPathQuery() throws JsonMappingException, JsonProcessingException {
		Category cat = new Category();
		cat.setId(10);
		cat.setName("Mouse");

		Tags tag1 = new Tags();
		tag1.setId(15);
		tag1.setName("Black");

		Tags tag2 = new Tags();
		tag2.setId(17);
		tag2.setName("red");

		List<Tags> tag = Arrays.asList(tag1, tag2);

		Pet pet = new Pet();
		pet.setName("dog1");
		pet.setId(10);
		pet.setCategory(cat);
		pet.setStatus("available");
		pet.setTags(tag);
		pet.setPhotoUrls(Arrays.asList("https://ex.com", "https://dog.com"));

		Response res = restClient.post(Base_URL_Pet, create_New_Pet, pet, ContentType.JSON, AuthType.NO_AUTH, null,
				null);
		int statuscode = res.getStatusCode();
		System.out.println("Status code is " + statuscode);
		// ReadContext ctx=JsonPath.parse(res.getBody().asString());
		// ctx.read("");

		int petId = JsonPathValidatorUtil.read(res, "$.id");
		Assert.assertNotNull(petId);
		List<String> petCategory = JsonPathValidatorUtil.readList(res, "$.category[*]");
		System.out.println("Pet category is :" + petCategory);

		List<Map<String, Object>> petTags = JsonPathValidatorUtil.readListOfMaps(res, "$.tags[*]");
		System.out.println("Pet category is :" + petTags);

		for (Map<String, Object> tagNew : petTags) {
			int id = (int) tagNew.get("id");
			System.out.println("tags id is " + id);
			String name =(String) tagNew.get("name");
			System.out.println("Colour name is " +name);
		}

	}

}
