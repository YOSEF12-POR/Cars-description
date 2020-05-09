package com.example.myproject;

public class Car {
    private int id;
    private String model;
    private String color;
    private double dpl;
    private String image;
    private String description;

    public Car(int id, String model, String color, double dpl, String image, String description) {
        this.setId(id);
        this.setModel(model);
        this.setColor(color);
        this.setDpl(dpl);
        this.setImage(image);
        this.setDescription(description);
    }

    public Car(String model, String color, double dpl, String image, String description) {
        this.setModel(model);
        this.setColor(color);
        this.setDpl(dpl);
        this.setImage(image);
        this.setDescription(description);
    }

    public Car(int id, String model, String color, double dpl) {

    }

    public Car(int id, String model, String color, String dp1) {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDpl() {
        return dpl;
    }

    public void setDpl(double dpl) {
        this.dpl = dpl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
