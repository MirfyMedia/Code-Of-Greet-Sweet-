package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 3/11/2019.
 */

public class Frame_list {
    private String image;

    private String id;
    private boolean is_blueselected = false;
    public boolean isIs_blueselected() {
        return is_blueselected;
    }

    public void setIs_blueselected(boolean is_blueselected) {
        this.is_blueselected = is_blueselected;
    }


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
