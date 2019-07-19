package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 2/6/2019.
 */

public class greetingspojo {

    int id;

    String title;
    String image;
    int views;
    int downloads;
    String created;
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

}
