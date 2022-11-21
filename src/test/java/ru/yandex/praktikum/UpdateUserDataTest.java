package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class UpdateUserDataTest extends BaseTest {

    private void makeRequestToUpdateData(RequestSpecification request,
                                         UpdateUserDataRequest updateUserData,
                                         boolean isSuccess) {
        request.header("Content-type", "application/json");
        if (isSuccess) {
            request.header("authorization", token);
        }
        request.body(updateUserData);

        Response updateResponse = request.relaxedHTTPSValidation().
                when().patch(userEndpoint);

        if (isSuccess) {
            updateResponse.then().assertThat().statusCode(200);
            updateResponse.then().body("success", equalTo(true));
        } else {
            updateResponse.then().assertThat().statusCode(401);
        }
    }

    @Test
    @DisplayName(value = "Успешная смена почтового адреса авторизованным пользователем")
    public void emailDataChangeWithAuthSuccess() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newEmail = "new-test-email" + random.nextInt(10000000)+ "@yandex.ru";
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(newEmail, this.username, this.password);
        makeRequestToUpdateData(request, updateUserData, true);
    }

    @Test
    @DisplayName(value = "Успешная смена имени авторизованным пользователем")
    public void nameChangeWithAuthSuccess() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newName = "newName" + random.nextInt(10000000);
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(this.email, newName, this.password);
        makeRequestToUpdateData(request, updateUserData, true);
    }

    @Test
    @DisplayName(value = "Успешная смена пароля авторизованным пользователем")
    public void passwordChangeWithAuthSuccess() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newPassword = "pass" + random.nextInt(10000000);
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(this.email, this.username, newPassword);
        makeRequestToUpdateData(request, updateUserData, true);
    }

    @Test
    @DisplayName(value = "Неудачная смена почтового адреса не авторизованным пользователем")
    public void emailChangeWithoutAuthFailure() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newEmail = "new-test-email" + random.nextInt(10000000)+ "@yandex.ru";
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(newEmail, this.username, this.password);
        makeRequestToUpdateData(request, updateUserData, false);
    }

    @Test
    @DisplayName(value = "Неудачная смена имени не авторизованным пользователем")
    public void nameChangeWithoutAuthFailure() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newName = "newName" + random.nextInt(10000000);
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(this.email, newName, this.password);
        makeRequestToUpdateData(request, updateUserData, false);
    }

    @Test
    @DisplayName(value = "Неудачная смена пароля не авторизованным пользователем")
    public void passwordChangeWithoutAuthFailure() {
        RequestSpecification request = makeUserAndLogin();
        Random random = new Random();
        String newPassword = "pass" + random.nextInt(10000000);
        UpdateUserDataRequest updateUserData =
                new UpdateUserDataRequest(this.email, this.username, newPassword);
        makeRequestToUpdateData(request, updateUserData, false);
    }
}
