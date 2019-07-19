package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 3/12/2019.
 */

public class Filter_tags {

    private String name;

    private String id;
    private boolean is_blueselected = false;
    public boolean isIs_blueselected() {
        return is_blueselected;
    }

    public void setIs_blueselected(boolean is_blueselected) {
        this.is_blueselected = is_blueselected;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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
        return "ClassPojo [name = "+name+", id = "+id+"]";
    }
}
