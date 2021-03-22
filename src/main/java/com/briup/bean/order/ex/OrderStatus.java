package com.briup.bean.order.ex;

public enum OrderStatus {
    GENERATED("generated"),
    PAID("generated");
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    OrderStatus(String name) {
        this.name = name;
    }
}
