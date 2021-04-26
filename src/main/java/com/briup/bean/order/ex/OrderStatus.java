package com.briup.bean.order.ex;

public enum OrderStatus {
    GENERATED("generated"),
    REJECT("reject"),
    SUBMIT("submission"),
    APPROVAL("approval"),
    PAID("paid"),
    CANCEL("cancel"),
    DELETE("deleted");
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
