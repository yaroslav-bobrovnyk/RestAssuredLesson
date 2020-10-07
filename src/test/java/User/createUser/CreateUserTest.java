package User.createUser;

import base.BaseClass;
import builder.UserBuilder;
import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static service.UserService.responseSpec;

public class CreateUserTest extends BaseClass {

    Response response;
    UserBuilder userBuilder=new UserBuilder();
    User user;

    @Test
    public void checkCreateUser() {
        user = userBuilder.create(expectedEmail,firstName,lastName,password,phone,userName,userId,userStatus);

        response = userService.addUserRequest(user,"/user");

        response
                .then()
                .log().all()
                .spec(responseSpec)
                .statusCode(200)
                .body("type", equalTo(expectedType))
                .body("message", comparesEqualTo(userId.toString()));
    }

    @Test
    public void checkCreateUserWrongPath() {
        user = userBuilder.create(expectedEmail,firstName,lastName,password,phone,userName,userId,userStatus);

        response = userService.addUserRequest(user,"/wrong");

        response
                .then()
                .log().all()
                .spec(responseSpec)
                .statusCode(404)
                .body("apiResponse.message",startsWithIgnoringCase("null for uri"))
                .contentType(ContentType.XML);
    }

}