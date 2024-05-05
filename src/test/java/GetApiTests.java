
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetApiTests {

    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "6636324525a5b823ed8a1d31";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }

    @Test
    public void Get10Users() {
        Response response = given()
                .header("app-id", APP_ID)
                .param("limit", 10)
                .when()
                .get("/user");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200, "Unexpected status code");

        // Add more assertions based on the response content
        // For example, validate the number of users returned
        int userCount = response.jsonPath().getList("data").size();
        assertEquals(userCount, 10, "Unexpected number of users");
    }

    @Test
    public void GetUserById() {

        String userId = "60d0fe4f5311236168a109ca";

        Response response = given()
                .header("app-id", APP_ID)
                .when()
                .get("/user/" + userId);

        // Verify the response status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode, "Unexpected status code: " + statusCode);

        // check the user id
        String id = response.jsonPath().getString("id");
        assertEquals(id, userId, "Unexpected user id");
    }

    @Test
    public void Get10Post() {
        Response response = given()
                .header("app-id", APP_ID)
                .param("limit", 10)
                .when()
                .get("/post");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200, "Unexpected status code");

        // Add more assertions based on the response content
        // For example, validate the number of users returned
        int userCount = response.jsonPath().getList("data").size();
        assertEquals(userCount, 10, "Unexpected number of users");
    }

    @Test
    public void GetUserPost() {

        String userId = "60d0fe4f5311236168a109ca";

        Response response = given()
                .header("app-id", APP_ID)
                .param("limit", 10)
                .param("userId", userId)
                .when()
                .get("/post");

        // Verify the response status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode, "Unexpected status code: " + statusCode);

        // check the user id
        String id = response.jsonPath().getString("data[0].owner.id");
        assertEquals(id, userId, "Unexpected user id");
    }

    @Test
    public void GetpostbyID() {
        String postId = "60d21af267d0d8992e610b8d";

        Response respone = given()
                .header("app-id", APP_ID)
                .when()
                .get("/post/" + postId);

        int statusCode = respone.getStatusCode();
        assertEquals(200, statusCode, "Unexpected status code: " + statusCode);

        // check the post id
        String id = respone.jsonPath().getString("id");
        assertEquals(id, postId, "Unexpected post id");
    }

    @Test
    public void getpostbyTag(){
        String tag = "water";

        Response response = given()
                .baseUri(BASE_URL)
                .header("app-id", APP_ID)
                .param("limit", 10)
                .when()
                .get("/tag/{tag}/post", tag);
        
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode, "Unexpected status code: " + statusCode);

        // check the limit on respone bodys
        int limit = response.jsonPath().getInt("limit");
        assertEquals(10, limit, "Unexpected number of POSTS");
    }

    @Test
    public void Getcommentperpost() {
        // Specify the post ID for which you want to retrieve comments
        String postId = "60d21af267d0d8992e610b8d";

        // Send GET request to retrieve comments for a specific post
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("app-id", APP_ID)
                .queryParam("limit", 10)
                .when()
                .get("/post/{postId}/comment", postId);

        // Verify the response status code
        int statusCode = response.getStatusCode();
        if (statusCode == 404) {
            // Handle 404 Not Found error
            System.out.println("Error: Post not found.");
            assertTrue(false, "Post not found. Post ID: " + postId);
        } else {
            // For successful response (status code 200)
            assertEquals(200, statusCode, "Unexpected status code: " + statusCode);

        }
    }

    @Test
    public void getcommentbyUser() {
        // Specify the user ID for which you want to retrieve comments
        String userId = "60d0fe4f5311236168a109ca";

        // Send GET request to retrieve comments for a specific user
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("app-id", APP_ID)
                .when()
                .get("/user/{userId}/comment", userId);

        // Verify the response status code
        int statusCode = response.getStatusCode();
        if (statusCode == 404) {
            // Handle 404 Not Found error
            System.out.println("Error: User not found.");
            assertTrue(false, "User not found. User ID: " + userId);
        } else {
            // For successful response (status code 200)
            assertEquals(200, statusCode, "Unexpected status code: " + statusCode);
        }
    }

    @Test
    public void get10Comment(){
        Response response = given()
                .header("app-id", APP_ID)
                .param("limit", 10)
                .when()
                .get("/comment");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200, "Unexpected status code");

        // Add more assertions based on the response content
        // For example, validate the number of users returned
        int commentcount = response.jsonPath().getList("data").size();
        assertEquals(commentcount, 10, "Unexpected number of users");
    }

    @Test
    public void getTag(){
        Response response = given()
                .header("app-id", APP_ID)
                .param("limit", 10)
                .when()
                .get("/tag");

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 200, "Unexpected status code");
    }
}
