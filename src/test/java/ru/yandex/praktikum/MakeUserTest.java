package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MakeUserTest extends BaseTest {

    @Test
    @DisplayName(value = "Успешное создание нового пользователя")
    public void makeUniqueUserSuccess() {
        Random random = new Random();
        Response response = makeUser("name"+random.nextInt(),
                                     "email"+random.nextInt()+"@test.ru", true);
        response.then().statusCode(200);
        UserResponse makeUserResponse = response.body().as(UserResponse.class);
        token = makeUserResponse.getAccessToken();
        refreshToken = makeUserResponse.getRefreshToken();
    }

    @Test
    @DisplayName(value = "Попытка создать пользователя, который уже существует")
    public void makeUserWhoAlreadyExist() {
        Random random = new Random();
        String name = "name" + random.nextInt();
        String email = "email" + random.nextInt() + "@test.ru";

        Response response = makeUser(name, email, true);
        response.then().statusCode(200);

        Response nextResponse = makeUser(name, email, true);
        nextResponse.then().statusCode(403);

        UserErrorResponse makeUserErrorResponse = nextResponse.body().as(UserErrorResponse.class);
        Assert.assertEquals(AlreadyExistsError, makeUserErrorResponse.getMessage());
    }

    @Test
    @DisplayName(value = "Регистрация нового пользователя без указания имени")
    public void makeUserWithoutUsername() {
        Random random = new Random();
        String email = "email" + random.nextInt();

        Response response = makeUser("", email, true);
        response.then().statusCode(403);

        UserErrorResponse makeUserErrorResponse = response.body().as(UserErrorResponse.class);
        Assert.assertEquals(EmailNameOrPassWrong, makeUserErrorResponse.getMessage());
    }

    @Test
    @DisplayName(value = "Регистрация нового пользователя без указания почты")
    public void makeUserWithoutEmail() {
        Random random = new Random();
        String name = "name" + random.nextInt();

        Response response = makeUser(name, "", true);
        response.then().statusCode(403);

        UserErrorResponse makeUserErrorResponse = response.body().as(UserErrorResponse.class);
        Assert.assertEquals(EmailNameOrPassWrong, makeUserErrorResponse.getMessage());
    }

    @Test
    @DisplayName(value = "Регистрация нового пользователя без указания пароля")
    public void makeUserWithoutPassword() {
        Random random = new Random();
        String name = "name" + random.nextInt();
        String email = "email" + random.nextInt() + "@test.ru";

        Response response = makeUser(name, email, false);
        response.then().statusCode(403);

        UserErrorResponse makeUserErrorResponse = response.body().as(UserErrorResponse.class);
        Assert.assertEquals(EmailNameOrPassWrong, makeUserErrorResponse.getMessage());
    }
}
