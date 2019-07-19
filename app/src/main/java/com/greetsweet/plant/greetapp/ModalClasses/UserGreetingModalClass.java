package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/7/2019.
 */

public class UserGreetingModalClass {
    private UserGreetings[] greetings;

    private String message;

    private String status;
    public ArrayList<UserGreetings> usergreet = new ArrayList<>();
    public ArrayList<UserGreetings> getUsergreet() {
        return usergreet;
    }

    public void setUsergreet(ArrayList<UserGreetings> usergreet) {
        this.usergreet = usergreet;
    }





    public UserGreetings[] getGreetings ()
    {
        return greetings;
    }

    public void setGreetings (UserGreetings[] greetings)
    {
        this.greetings = greetings;
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
        return "ClassPojo [greetings = "+greetings+", message = "+message+", status = "+status+"]";
    }
}
