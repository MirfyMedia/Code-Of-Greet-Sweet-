package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by Nancy on 2/21/2019.
 */

public class LogoUploadModalClass {

    private String logo_url;

    public String getLogo_url ()
    {
        return logo_url;
    }

    public void setLogo_url (String logo_url)
    {
        this.logo_url = logo_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [logo_url = "+logo_url+"]";
    }
}
