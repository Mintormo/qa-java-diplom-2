package ru.yandex.praktikum;

public class OrderResponse {
    private boolean success;
    private String name;
    private OrderDataResponse order;

    public OrderResponse() {
    }

    public OrderResponse(boolean success, String name, OrderDataResponse order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderDataResponse getOrder() {
        return order;
    }

    public void setOrder(OrderDataResponse order) {
        this.order = order;
    }
}
