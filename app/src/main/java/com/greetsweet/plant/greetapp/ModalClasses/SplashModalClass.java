package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/14/2019.
 */

public class SplashModalClass {

    private Intro_screen[] intro_screen;

    private String message;

    private String status;
    public ArrayList<Intro_screen> splash = new ArrayList<>();
    public ArrayList<Intro_screen> getSplash() {
        return splash;
    }

    public void setSplash(ArrayList<Intro_screen> splash) {
        this.splash = splash;
    }




    public Intro_screen[] getIntro_screen ()
    {
        return intro_screen;
    }

    public void setIntro_screen (Intro_screen[] intro_screen)
    {
        this.intro_screen = intro_screen;
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
        return "ClassPojo [intro_screen = "+intro_screen+", message = "+message+", status = "+status+"]";
    }
}
