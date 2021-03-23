package common;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class requests {

     public static Response create(){
        String payload =String.format("{\"name\":\"%s\", \"job\":\"%s\" }", "Testov", "Test");
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post("/api/users")
                        .then()
                        .statusCode(201)
                        .extract().response();
         return response;
    }
}
