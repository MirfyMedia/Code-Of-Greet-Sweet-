package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/12/2019.
 */

public class FilterGetModalClass {
    private Filter_tags[] filter_tags;

    private String message;

    private String status;
    public ArrayList<Filter_tags> filterget = new ArrayList<>();

    public ArrayList<Filter_tags> getFilterget() {
        return filterget;
    }

    public void setFilterget(ArrayList<Filter_tags> filterget) {
        this.filterget = filterget;
    }




    public Filter_tags[] getFilter_tags ()
    {
        return filter_tags;
    }

    public void setFilter_tags (Filter_tags[] filter_tags)
    {
        this.filter_tags = filter_tags;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
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
        return "ClassPojo [filter_tags = "+filter_tags+", message = "+message+", status = "+status+"]";
    }
}
