package User.getUser;

import base.BaseClass;
import dto.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static service.UserService.responseSpec;

public class GetUserTest extends BaseClass {
    Response response;
    @Test
    public void checkUserCreated(){
        response=userService.getUserRequest(userName);
        JsonPath jsonPath=response.then()
                .log().all()
                .spec(responseSpec)
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertThat(jsonPath.getString("username"),equalTo(userName));
    }

    @Test
    public void checkNonExistentUser(){
        String userName="40";
        response=userService.getUserRequest(userName);
        response.then()
                .log().all()
                .spec(responseSpec)
                .assertThat()
                .statusCode(404)
                .body("type", equalTo(errorExpectedType))
                .body("message", comparesEqualTo(notFoundMessageType));
    }

}
