package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.ArrayList;

public class MakeOrderTest extends BaseTest {
    @Test
    @DisplayName(value = "Создание заказа с ингредиентами авторизованным пользователем")
    public void makeOrderWithAuthAndIgredients() {
        makeUserAndLogin();
        IngredientsResponse ingredients = getIgredients();
        ArrayList<String> ings = new ArrayList<String>();
        ings.add(ingredients.getData().get(0).get_id());
        makeOrder(true, false, ings);
    }
    @Test
    @DisplayName(value = "Создание заказа с пустым списком ингредиентов авторизованным пользователем")
    public void makeOrderWithAuthAndWithoutIgredients() {
        makeUserAndLogin();
        ArrayList<String> ings = new ArrayList<String>();
        makeOrder(true, false, ings);
    }
    @Test
    @DisplayName(value = "Создание заказа не авторизованным пользователем со списком ингредиентов")
    public void makeOrderWithoutAuthAndWithIgredients() {
        makeUserAndGetTokens();
        IngredientsResponse ingredients = getIgredients();
        ArrayList<String> ings = new ArrayList<String>();
        ings.add(ingredients.getData().get(0).get_id());
        makeOrder(false, false, ings);
    }
    @Test
    @DisplayName(value = "Создание заказа не авторизованным пользователем без указания ингредиентов")
    public void makeOrderWithoutAuthAndWithoutIgredients() {
        makeUserAndGetTokens();
        ArrayList<String> ings = new ArrayList<String>();
        makeOrder(false, false, ings);
    }
    @Test
    @DisplayName(value = "Создание заказа авторизованным пользователем. Хэш ингредиента некорректен")
    public void makeOrderWithAuthAndIncorrectHash() {
        makeUserAndLogin();
        IngredientsResponse ingredients = getIgredients();
        ArrayList<String> ings = new ArrayList<String>();
        StringBuilder incorrectHash = new StringBuilder(ingredients.getData().get(0).get_id());
        incorrectHash.setCharAt(0,'d');
        ings.add(incorrectHash.toString());
        makeOrder(true, true, ings);
    }

}
