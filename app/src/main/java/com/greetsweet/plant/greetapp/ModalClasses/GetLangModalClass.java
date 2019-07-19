package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 2/28/2019.
 */

public class GetLangModalClass {
    private Languages[] languages;

    private String message;

    private String status;
    public ArrayList<Languages> lang = new ArrayList<>();


    public ArrayList<Languages> getLang() {
        return lang;
    }

    public void setLang(ArrayList<Languages> lang) {
        this.lang = lang;
    }



    public Languages[] getLanguages ()
    {
        return languages;
    }

    public void setLanguages (Languages[] languages)
    {
        this.languages = languages;
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
        return "ClassPojo [languages = "+languages+", message = "+message+", status = "+status+"]";
    }
}
