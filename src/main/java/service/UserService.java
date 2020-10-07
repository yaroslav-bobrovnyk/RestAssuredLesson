package service;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class UserService {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String GET_USER = "/user/";

    RequestSpecification spec=given()
            .contentType(ContentType.JSON)
            .baseUri(BASE_URL);

    public static ResponseSpecification responseSpec = expect()
            .time(lessThan(5000L));

    public UserService() {
    }

    public Response addUserRequest(User user,String basePath) {
        return given()
                .spec(spec)
                .with()
                .body(user)
                .when()
                .log().body()
                .post(basePath);
    }

    public Response getUserRequest(String userName){
        return given()
                .spec(spec)
                .when().log().body()
                .get(GET_USER+userName);
    }
}