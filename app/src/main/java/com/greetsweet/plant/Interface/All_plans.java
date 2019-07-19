package com.greetsweet.plant.Interface;

public class All_plans {
    private String price;
    private boolean isSelected;
    private String id;

    private String plan_name;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }
    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    @Override
    public String toString() {
        return "ClassPojo [price = " + price + ", id = " + id + ", plan_name = " + plan_name + "]";
    }
}
