package Authorization;

package auth;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class auth {
	
	/*
	 * Authentication:
	 *  1. Basic VS Digest
	 *  2. Preemptive VS Challenged
	 *  
	 *  Example
	 *   URI: https://postman-echo.com
	 *   End-point: /digest-auth
	 */
	
	@Test
	public void digest_authentication() {
		
		given()
			.baseUri("https://postman-echo.com")
			.auth().digest("postman", "password").
		when()
			.get("/digest-auth").
		then()
			.log().all()
			.statusCode(200);
	}
}