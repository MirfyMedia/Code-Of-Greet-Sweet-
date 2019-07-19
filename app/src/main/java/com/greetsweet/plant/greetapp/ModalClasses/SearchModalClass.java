package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/7/2019.
 */

public class SearchModalClass {

    private String message;

    private Subcategories[] subcategories;

    private String status;
    public ArrayList<Subcategories> searchsub = new ArrayList<>();
    public ArrayList<Subcategories> getSearchsub() {
        return searchsub;
    }

    public void setSearchsub(ArrayList<Subcategories> searchsub) {
        this.searchsub = searchsub;
    }



    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Subcategories[] getSubcategories ()
    {
        return subcategories;
    }

    public void setSubcategories (Subcategories[] subcategories)
    {
        this.subcategories = subcategories;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", subcategories = "+subcategories+", status = "+status+"]";
    }
}
