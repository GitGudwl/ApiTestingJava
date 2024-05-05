import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DeleteApiTest {

    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "6636324525a5b823ed8a1d31";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }

    @Test(groups = { "Delete" })
    public void DeleteUserTest() {
        // Read user id from saved_id_user.txt file
        String userId = null;
        String usersID = null;
        try {
            usersID = new String(
                    java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_user.txt")));
            userId = usersID.split("\n")[usersID.split("\n").length - 1];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Send DELETE request to delete user
        Response response = given()
                .header("app-id", APP_ID)
                .delete("/user/" + userId);

        // Assert the response status code
        assertEquals(200, response.getStatusCode());
    }

    @Test(groups = { "Delete" })
    public void DeletePostTest() {
        // Read post id from saved_id_post.txt file
        String postId = null;
        String postsID = null;
        try {
            postsID = new String(
                    java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_post.txt")));
            postId = postsID.split("\n")[postsID.split("\n").length - 1];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Send DELETE request to delete post
        Response response = given()
                .header("app-id", APP_ID)
                .delete("/post/" + postId);

        // Assert the response status code
        assertEquals(200, response.getStatusCode());
    }

    @Test(groups = { "Delete" })
    public void DeleteCommentTest() {
        // Read comment id from saved_id_comment.txt file
        String commentId = null;
        String commentsID = null;
        try {
            commentsID = new String(
                    java.nio.file.Files
                            .readAllBytes(java.nio.file.Paths.get("src/test/java/Data/saved_id_comment.txt")));
            commentId = commentsID.split("\n")[commentsID.split("\n").length - 1];
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Send DELETE request to delete comment
        Response response = given()
                .header("app-id", APP_ID)
                .delete("/comment/" + commentId);

        // Assert the response status code
        assertEquals(200, response.getStatusCode());
    }

}
