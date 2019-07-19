package com.greetsweet.plant.greetapp.ModalClasses;

/**
 * Created by my on 11/30/2018.
 */

public class TimeDayModalClass {

    int Imageid;
    String text;


    public TimeDayModalClass(int Imageid, String text)
    {
        this.Imageid=Imageid;
        this.text=text;



    }

    public int getImageid() {
        return Imageid;
    }

    public void setImageid(int imageid) {
        Imageid = imageid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
