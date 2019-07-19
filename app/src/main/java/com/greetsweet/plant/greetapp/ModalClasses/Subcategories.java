package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 3/7/2019.
 */

public class Subcategories {
    private String subcategory_id;

    private String category_title;

    private String category_id;

    private String subcategory_title;

    public String getSubcategory_id ()
    {
        return subcategory_id;
    }

    public void setSubcategory_id (String subcategory_id)
    {
        this.subcategory_id = subcategory_id;
    }

    public String getCategory_title ()
    {
        return category_title;
    }

    public void setCategory_title (String category_title)
    {
        this.category_title = category_title;
    }

    public String getCategory_id ()
    {
        return category_id;
    }

    public void setCategory_id (String category_id)
    {
        this.category_id = category_id;
    }

    public String getSubcategory_title ()
    {
        return subcategory_title;
    }

    public void setSubcategory_title (String subcategory_title)
    {
        this.subcategory_title = subcategory_title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subcategory_id = "+subcategory_id+", category_title = "+category_title+", category_id = "+category_id+", subcategory_title = "+subcategory_title+"]";
    }
}
