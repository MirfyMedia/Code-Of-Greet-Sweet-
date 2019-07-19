package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 2/21/2019.
 */

public class GetLogoModalClass {

    private String message;

    private Logos[] logos;

    private String status;

    public ArrayList<Logos> logoarray = new ArrayList<>();

    public ArrayList<Logos> getLogo() {
        return logoarray;
    }

    public void setLogo(ArrayList<Logos> logo) {
        this.logoarray = logo;
    }


    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Logos[] getLogos ()
    {
        return logos;
    }

    public void setLogos (Logos[] logos)
    {
        this.logos = logos;
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
        return "ClassPojo [message = "+message+", logos = "+logos+", status = "+status+"]";
    }
}
