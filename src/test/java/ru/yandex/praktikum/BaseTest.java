package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.*;

import java.util.ArrayList;
import java.util.Random;

public class BaseTest {
    protected String token;
    protected String refreshToken;
    protected String username;
    protected String email;
    protected String password;

    final protected String registerEndpoint = "/api/auth/register";
    final protected String userEndpoint = "/api/auth/user";
    final protected String loginEndpoint = "/api/auth/login";
    final protected String logoutEndpoint = "/api/auth/logout";
    final protected String ingredientsEndpoint = "/api/ingredients";
    final protected String orderEndpoint = "/api/orders";

    final String AlreadyExistsError = "User already exists";
    final String EmailNameOrPassWrong = "Email, password and name are required fields";

    @Before
    public void setup() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.useRelaxedHTTPSValidation();
        token = "";
        refreshToken = "";
        username = "";
        email = "";
        password = "";
    }

    @After
    public void teardown() {
        RequestSpecification request = RestAssured.given();
        if (!token.isEmpty()) {
            request.
                    auth().oauth2(token).
                    when().delete(userEndpoint);
            request.then().statusCode(200);
        }
    }

    protected Response makeUser(String name, String email, boolean makePass) {
        RequestSpecification request = RestAssured.given();
        MakeUserRequest makeUser;
        Random random = new Random();
        String password = "pass" + random.nextInt(10000000);

        if (makePass) {
            makeUser = new MakeUserRequest(name, email, password);
        } else {
            makeUser = new MakeUserRequest(name, email, "");
        }
        this.password = password;

        request.header("Content-type", "application/json");
        request.body(makeUser);
        Response response = request.relaxedHTTPSValidation().
                when().post(registerEndpoint);
        return response;
    }

    protected void makeUserAndGetTokens() {
        Random random = new Random();
        String name = "name"+random.nextInt(1000000);
        String email = "email"+random.nextInt(10000000)+"@test.ru";

        Response response = makeUser(name, email, true);
        response.then().statusCode(200);
        UserResponse makeUserResponse = response.body().as(UserResponse.class);
        token = makeUserResponse.getAccessToken();
        refreshToken = makeUserResponse.getRefreshToken();
        this.username = name;
        this.email = email;
    }

    protected RequestSpecification makeUserAndLogin() {
        makeUserAndGetTokens();
        LoginRequest loginRequest = new LoginRequest(this.email, this.password);
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.body(loginRequest);
        Response response = request.
                when().post(loginEndpoint);
        response.then().assertThat().statusCode(200);

        LoginResponse loginResponse = response.body().as(LoginResponse.class);
        Assert.assertEquals(true, loginResponse.isSuccess());
        this.token = loginResponse.getAccessToken();
        this.refreshToken = loginResponse.getRefreshToken();
        return request;
    }

    protected IngredientsResponse getIgredients() {
        RequestSpecification request = RestAssured.given();
        Response ing = request.
                auth().oauth2(token).
                when().get(ingredientsEndpoint);
        ing.then().statusCode(200);
        IngredientsResponse ingResponse = ing.body().as(IngredientsResponse.class);
        Assert.assertEquals(true, ingResponse.isSuccess());
        return ingResponse;
    }

    protected OrderResponse makeOrder(boolean isAuth, boolean incorrectHash, ArrayList<String> ingredients) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        if (isAuth) {
            request.header("Authorization", token);
        }
        OrderRequest orderRequest = new OrderRequest(ingredients);
        request.body(orderRequest);
        Response ingResponse = request.when().post(orderEndpoint);
        if (isAuth) {
            if (ingredients.isEmpty()) {
                ingResponse.then().statusCode(400);
            } else if (incorrectHash) {
                ingResponse.then().statusCode(500);
            } else {
                ingResponse.then().statusCode(200);
            }
        } else {
            ingResponse.then().statusCode(401);
        }

        OrderResponse orderResponse = ingResponse.body().as(OrderResponse.class);
        if (isAuth) {
            if (ingredients.isEmpty()) {
                ingResponse.then().statusCode(400);
                Assert.assertEquals(false, orderResponse.isSuccess());
            } else if (incorrectHash) {
                ingResponse.then().statusCode(500);
                Assert.assertEquals(false, orderResponse.isSuccess());
            } else {
                ingResponse.then().statusCode(200);
                Assert.assertEquals(true, orderResponse.isSuccess());
            }
        } else {
            ingResponse.then().statusCode(401);
            Assert.assertEquals(false, orderResponse.isSuccess());
        }
        return orderResponse;
    }

}
