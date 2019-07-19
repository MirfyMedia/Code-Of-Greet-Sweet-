package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 2/18/2019.
 */

public class UploadImageModalClass {

    private String file;

    public String getFile ()
    {
        return file;
    }

    public void setFile (String file)
    {
        this.file = file;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [file = "+file+"]";
    }
}
