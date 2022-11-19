package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

public class LoginTest extends BaseTest {
    @Test
    @DisplayName(value = "Вход в систему с существующей учетной записью пользователя")
    public void loginExistsUser() {
        makeUserAndGetTokens();

        LoginRequest loginRequest = new LoginRequest(this.email, this.password);

        RequestSpecification request = RestAssured.given();
        request.relaxedHTTPSValidation().
                header("Content-type", "application/json").
                body(loginRequest).
                when().post(loginEndpoint).
                then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName(value = "Вход в систему с некорректным логином")
    public void loginWithIncorrectLogin() {
        makeUserAndGetTokens();

        LoginRequest loginRequest = new LoginRequest("ASD" + this.email, this.password);

        RequestSpecification request = RestAssured.given();
        Response response = request.relaxedHTTPSValidation().
                header("Content-type", "application/json").
                body(loginRequest).
                when().post(loginEndpoint);
        response.then().statusCode(401);
    }

    @Test
    @DisplayName(value = "Вход в систему с некорректным паролем")
    public void loginWithIncorrectPassword() {
        makeUserAndGetTokens();

        LoginRequest loginRequest = new LoginRequest(this.email, this.password + "ASD");

        RequestSpecification request = RestAssured.given();
        Response response = request.relaxedHTTPSValidation().
                header("Content-type", "application/json").
                body(loginRequest).
                when().post(loginEndpoint);
        response.then().statusCode(401);
    }
}
