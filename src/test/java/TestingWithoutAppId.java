import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestingWithoutAppId {
    private static final String BASE_URL = "https://dummyapi.io/data/v1";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }

    @Test(groups = { "API access without app_id" })
    public void Get10UsersWithoutAppId() {
        Response response = given()
                .param("limit", 10)
                .when()
                .get("/user");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 403, "Unexpected status code");
    }

    @Test(groups = { "API access without app_id" })
    public void GetUserByIdWithoutAppId() {

        String userId = "60d0fe4f5311236168a109ca";

        Response response = given()
                .when()
                .get("/user/" + userId);

        // Verify the response status code
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 403, "Unexpected status code: " + statusCode);
    }

    @Test(groups = { "API access without app_id" })
    public void createNewUserWithoutAppId() {
        // Read user data from JSON file
        JsonObject userData = null;
        try {
            userData = new Gson().fromJson(new FileReader("src/test/java/Data/NewUserData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Send POST request to create user
        Response response = given()
                .contentType("application/json")
                .body(userData.toString())
                .post("/user");

        // Assert the response status code
        assertEquals(response.getStatusCode(), 403);
    }

    
}
