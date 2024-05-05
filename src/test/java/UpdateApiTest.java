import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class UpdateApiTest {
    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "6636324525a5b823ed8a1d31";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }

    @Test(groups = { "Update" })
    public void UpdateUserTest() {
        // Read user id from saved_id_user.txt file
        String userId = null;
        String usersID = null;

        try {
            usersID = new String(
                    java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_user.txt")));
            userId = usersID.split("\n")[0];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Read user data from JSON file
        JsonObject userData = null;
        try {
            userData = new Gson().fromJson(new FileReader("src/test/java/Data/UpdateUserData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (userData != null) {
            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(userData.toString())
                    .put("/user/" + userId);

            // Assert the response status code
            assertEquals(200, response.getStatusCode());

            String id = response.jsonPath().getString("id");
            // assert that the id is the same as the one we updated
            assertEquals(id, userId, "Unexpected user id" + id);

        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "Update" })
    public void UpdatePostTest() {
        // Read post id from saved_id_post.txt file
        String postId = null;
        String postsID = null;

        try {
            postsID = new String(
                    java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_post.txt")));
            postId = postsID.split("\n")[0];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Read post data from JSON file
        JsonObject postData = null;
        try {
            postData = new Gson().fromJson(new FileReader("src/test/java/Data/UpdatePostData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (postData != null) {
            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(postData.toString())
                    .put("/post/" + postId);

            // Assert the response status code
            assertEquals(response.getStatusCode(), 200);

            String id = response.jsonPath().getString("id");
            // assert that the id is the same as the one we updated
            assertEquals(id, postId, "Unexpected post id" + id);
        } else {
            throw new RuntimeException("Post data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "Update" })
    public void UpdateCommentTest() {
        // Read comment id from saved_id_comment.txt file
        String commentId = null;
        String commentsID = null;

        try {
            commentsID = new String(
                    java.nio.file.Files
                            .readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_comment.txt")));
            commentId = commentsID.split("\n")[2];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Read comment data from JSON file
        JsonObject commentData = null;
        try {
            commentData = new Gson().fromJson(new FileReader("src/test/java/Data/UpdateCommentData.json"),
                    JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (commentData != null) {
            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(commentData.toString())
                    .put("/comment/" + commentId);

            // Assert the response status code
            assertEquals(response.getStatusCode(), 200);

            String id = response.jsonPath().getString("id");
            // assert that the id is the same as the one we updated
            assertEquals(id, commentId, "Unexpected comment id" + id);

            String message = response.jsonPath().getString("message");
            assertEquals(message, commentData.get("message").getAsString());
        } else {
            throw new RuntimeException("Comment data could not be loaded from JSON file.");
        }
    }
}
