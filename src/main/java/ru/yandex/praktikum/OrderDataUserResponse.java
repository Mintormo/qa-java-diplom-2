package ru.yandex.praktikum;

import java.util.ArrayList;

public class OrderDataUserResponse {
    private boolean success;
    private int total;
    private int totalToday;
    private ArrayList<OrderData> orders;

    public OrderDataUserResponse() {
    }

    public OrderDataUserResponse(boolean success, int total, int totalToday, ArrayList<OrderData> orders) {
        this.success = success;
        this.total = total;
        this.totalToday = totalToday;
        this.orders = orders;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }

    public ArrayList<OrderData> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderData> orders) {
        this.orders = orders;
    }
}
