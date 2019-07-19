package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 3/14/2019.
 */

public class Intro_screen {
    private String image;

    private String id;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", id = "+id+"]";
    }
}
