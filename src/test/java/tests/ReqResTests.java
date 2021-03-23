package tests;

import common.requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;


public class ReqResTests extends Base{

    @Test
    void createTest(){
        String name = "testName";
        String job = "testJob";
        String payload =String.format("{\"name\":\"%s\", \"job\":\"%s\" }", name, job);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post("/api/users")
        .then()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    void changeTest(){
        String changingName = "newName";
        String changingJob = "newJob";
        String payload =String.format("{\"name\":\"%s\", \"job\":\"%s\" }", changingName, changingJob);
        Response response = requests.create();
        String user_id = response.jsonPath().getString("id");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .put("/api/users/" + user_id)
        .then()
                .statusCode(200)
                .body("name", is(changingName))
                .body("job", is(changingJob));
    }

    @Test
    void deleteTest(){
        Response response = requests.create();
        String user_id = response.jsonPath().getString("id");

        given()
        .when()
                .delete("/api/users/" + user_id)
        .then()
                .statusCode(204);
    }

    @Test
    void successfulRegisterTest(){
        String data = readStringFromFile("./src/test/resources/loginData.txt");

        given()
                .contentType(ContentType.JSON)
                .body(data)
        .when()
                .post("/api/register")
        .then()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("token", is(notNullValue()));

    }

    @Test
    void successfulLoginTest(){
        String data = readStringFromFile("./src/test/resources/loginData.txt");

        given()
                .contentType(ContentType.JSON)
                .body(data)
        .when()
                .post("/api/login")
        .then()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }
}
