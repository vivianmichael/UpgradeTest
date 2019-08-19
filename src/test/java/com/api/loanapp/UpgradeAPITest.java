package com.api.loanapp;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpgradeAPITest {
	private static final String Upgrade_URL = "https://credapi.credify.tech/api/loanapp/v1/states";
	Response response=null;
    List<String> statesList = Arrays.asList("Alabama","Alaska","Arizona","Arkansas","California","Connecticut","Delaware","District of Columbia","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","Wisconsin","Wyoming");

	@Test
	public void test_ReturnedStateNamesAndNumberofStates() {
		response = RestAssured.get(Upgrade_URL);
		JsonPath jsonPath = response.jsonPath();
		
		List<String> numberofStates = jsonPath.getJsonObject("states");
		assertEquals(numberofStates.size(), 48);
		
		List<String> stateNames = jsonPath.get("states.label");
		assertEquals(stateNames,statesList );
	}

	@Test
	public void validate_minAgeof19AndDisplayStateName() {
		RestAssured
	    .when()
	      .get(Upgrade_URL)
	    .then()
	      .body("states.findAll { it.minAge == 19 }.label",CoreMatchers. hasItem("Alabama"));
	}

	@Test
	public void validate_georgiaHasloanAmountAs3005() {
		RestAssured
	    .when()
	      .get(Upgrade_URL)
	    .then()
	      .body("states.findAll { it.minLoanAmount == 3005.00 }.label",CoreMatchers. hasItem("Georgia"));
	}
}
