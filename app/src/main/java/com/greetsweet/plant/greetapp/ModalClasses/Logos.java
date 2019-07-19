package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 2/21/2019.
 */

public class Logos {

    private String id;

    private String logo_image;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLogo_image ()
    {
        return logo_image;
    }

    public void setLogo_image (String logo_image)
    {
        this.logo_image = logo_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", logo_image = "+logo_image+"]";
    }
}
