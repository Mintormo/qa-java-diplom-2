package ru.yandex.praktikum;

import java.util.ArrayList;

public class OrderRequest {
    private ArrayList<String> ingredients;

    public OrderRequest() {
    }

    public OrderRequest(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
