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

public class CreateApiTest {

    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "6636324525a5b823ed8a1d31";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }

    @Test(groups = { "create" })
    public void CreateUserTest() {
        int randomHash = (int) (Math.random() * 1000);

        // Read user data from JSON file
        JsonObject userData = null;
        try {
            userData = new Gson().fromJson(new FileReader("src/test/java/Data/NewUserData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (userData != null) {
            userData.addProperty("email", "guillermodeltoro" + randomHash + "@mail.com");

            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(userData.toString())
                    .post("/user/create");

            // Assert the response status code
            assertEquals(200, response.getStatusCode());

            // load the response body as a JSON object
            String email = response.jsonPath().getString("email");
            assertEquals(email, "guillermodeltoro" + randomHash + "@mail.com");
            String id = response.jsonPath().getString("id");
            // save the id in a saved_id txt file
            id = id + "\n";
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("src/test/java/Data/saved_id_user.txt"),
                        id.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "create" })
    public void CreatePostTest() {
        JsonObject postdata = null;
        try {
            postdata = new Gson().fromJson(new FileReader("src/test/java/Data/NewPostData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (postdata != null) {

            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(postdata.toString())
                    .post("/post/create");

            // Assert the response status code
            assertEquals(200, response.getStatusCode());

            // load the response body as a JSON object
            String owner = response.jsonPath().get("owner.id");
            assertEquals(owner, postdata.get("owner").getAsString());
            String id = response.jsonPath().getString("id");
            // save the id in a saved_id txt file
            id = id + "\n";
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("src/test/java/Data/saved_id_post.txt"),
                        id.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "create" })
    public void CreateCommentTest() {
        JsonObject CommentData = null;
        try {
            CommentData = new Gson().fromJson(new FileReader("src/test/java/Data/newCommentData.json"),
                    JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (CommentData != null) {

            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(CommentData.toString())
                    .post("/comment/create");

            // Assert the response status code
            assertEquals(200, response.getStatusCode());

            // assert the owner of the comment
            String owner = response.jsonPath().get("owner.id");
            assertEquals(owner, CommentData.get("owner").getAsString());

            // assert the post of the comment
            String post = response.jsonPath().getString("post");
            assertEquals(post, CommentData.get("post").getAsString());

            String id = response.jsonPath().getString("id");
            // save the id in a saved_id txt file
            id = id + "\n";
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("src/test/java/Data/Saved_id_comment.txt"),
                        id.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "create" })
    public void createUserTestwithCapitalEmailData() {
        int randomHash = (int) (Math.random() * 1000);

        // Read user data from JSON file
        JsonObject userData = null;
        try {
            userData = new Gson().fromJson(new FileReader("src/test/java/Data/NewUserData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (userData != null) {
            userData.addProperty("email", "GUILERMODELTORO" + randomHash + "@mail.com");

            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(userData.toString())
                    .post("/user/create");

            // Assert the response status code
            assertEquals(200, response.getStatusCode());

            // load the response body as a JSON object
            String email = response.jsonPath().getString("email");
            assertEquals(email, "GUILERMODELTORO" + randomHash + "@mail.com");
            String id = response.jsonPath().getString("id");
            // save the id in a saved_id txt file
            id = id + "\n";
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("src/test/java/Data/saved_id_user.txt"),
                        id.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }

    @Test(groups = { "create" })
    public void createpostwithoutowner() {
        JsonObject postdata = null;
        try {
            postdata = new Gson().fromJson(new FileReader("src/test/java/Data/NewPostData.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Modify the email address in user data
        if (postdata != null) {
            postdata.remove("owner");

            // Send POST request to create user
            Response response = given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(postdata.toString())
                    .post("/post/create");

            // Assert the response status code
            assertEquals(response.getStatusCode(), 400 );
        } else {
            throw new RuntimeException("User data could not be loaded from JSON file.");
        }
    }


}