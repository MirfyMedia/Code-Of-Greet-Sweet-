package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 2/6/2019.
 */

public class home_page_pojo {
            public int id;
            public String title;
    public  ArrayList<subcategories_pojo> subcategories_pojos = new ArrayList<>();
    public String image;
    public  String type;
    public  ArrayList<greetingspojo> greetingspojo = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<greetingspojo> getGreetingspojo() {
        return greetingspojo;
    }

    public void setGreetingspojo(ArrayList<greetingspojo> greetingspojo) {
        this.greetingspojo = greetingspojo;
    }

    public ArrayList<subcategories_pojo> getSubcategories_pojos() {
        return subcategories_pojos;
    }

    public void setSubcategories_pojos(ArrayList<subcategories_pojo> subcategories_pojos) {
        this.subcategories_pojos = subcategories_pojos;
    }
}
