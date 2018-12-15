package com.example.asus.medicinelife;

public class orderRecycleViewHelper {


    private String orderName ;
    private String orderPrice;
    private String orderStatus ;


    public orderRecycleViewHelper(String orderName, String orderPrice, String orderStatus) {
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    public orderRecycleViewHelper() {
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
