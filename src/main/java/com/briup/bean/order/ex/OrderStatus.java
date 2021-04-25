package com.briup.bean.order.ex;

public enum OrderStatus {
    GENERATED("generated"),
    REJECT("reject"),
    SUBMIT("submission"),
    APPROVAL("approval"),
    PAID("paoid"),
    CANCEL("cancel"),
    DELETE("delete");
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
