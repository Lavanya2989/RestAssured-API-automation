package Getting_started;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
public class NewTest {
  @Test
  public void getRequest() {
	  
	given()
		.baseUri("https://restcountries.eu/rest/v2").
	when()
		.get("/all").
	then()
		.statusCode(200);
  }

  @Test
	public void validate_json_response() {
		
		given()
			.baseUri("https://restcountries.eu/rest/v2").
		when()
			.get("/alpha/USA").
		then()
			.statusCode(200)
			.body(
				"name", equalTo("United States of America"),
				"alpha3Code", equalTo("USA"),
				"altSpellings", hasItem("US"),
				"currencies[0].symbol", equalTo("$"),
				"languages[0].name", equalTo("English")
			);
	}
  public void validate_xml_response() {
		given()
			.baseUri("https://api.openweathermap.org/data/2.5")
			.queryParam("q", "London,uk")
			.queryParam("APPID", "3f7393c569d314987f3b072d260d7142")
			.queryParam("mode", "xml").
		when()
			.get("/weather").
		then()
			.statusCode(200)
			.body(
				"current.city.@name", equalTo("London"),
				"current.city.country", equalTo("GB")
			);
	}
	
	@Test
	public void extract_response_data() {
		Response res = given()
			.baseUri("https://restcountries.eu/rest/v2").
		when()
			.get("/alpha/USA").
		then()
			.extract().response();
		System.out.println(res.asString());
	}
	@Test
	public void extract_single_value() {
		String temperature = given()
			.baseUri("https://api.openweathermap.org/data/2.5")
			.queryParam("q", "London,uk")
			.queryParam("APPID", "48115954d40e05412d7e3c670f15acb1")
			.queryParam("mode", "xml").
		when()
			.get("/weather").
		then()
			.statusCode(200)
			.body(
				"current.city.@name", equalTo("London"),
				"current.city.country", equalTo("GB")
			)
			.extract().path("current.temperature.@value");
		
		System.out.println(temperature);
	}
	
	@Test
	public void verify_status_line() {
		given()
			.baseUri("https://api.printful.com").
		when()
			.get("/variant/1").
		then()
			.statusCode(401)
			.statusLine("HTTP/1.1 401 Unauthorized");
	}

}
