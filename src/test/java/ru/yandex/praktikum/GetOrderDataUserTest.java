package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GetOrderDataUserTest extends BaseTest {

    @Test
    @DisplayName(value = "Получение списка заказов авторизованного пользователя")
    public void getOrdersOfUserWithAuth() {
        makeUserAndLogin();
        IngredientsResponse ingredients = getIgredients();
        ArrayList<String> ings = new ArrayList<String>();
        ings.add(ingredients.getData().get(0).getId());
        makeOrder(true, false, ings);

        RequestSpecification request = RestAssured.given();
        request.header("Authorization", token);

        Response response = request.when().get(orderEndpoint);
        response.then().statusCode(200);
        OrderDataUserResponse orderDataUserResponse = response.body().as(OrderDataUserResponse.class);
        Assert.assertEquals(true, orderDataUserResponse.isSuccess());
    }

    @Test
    @DisplayName(value = "Получение списка заказов не авторизованного пользователя")
    public void getOrdersOfUserWithoutAuth() {
        makeUserAndLogin();
        IngredientsResponse ingredients = getIgredients();
        ArrayList<String> ings = new ArrayList<String>();
        ings.add(ingredients.getData().get(0).getId());
        makeOrder(true, false, ings);

        RequestSpecification request = RestAssured.given();
        request.header("Authorization", token);

        LogoutRequest logout = new LogoutRequest(refreshToken);
        request.header("Content-type", "application/json");
        request.body(logout);
        Response response = request.when().post(logoutEndpoint);
        response.then().statusCode(200);

        request = RestAssured.given();
        response = request.when().get(orderEndpoint);
        response.then().statusCode(401);
    }

}
