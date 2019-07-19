package com.greetsweet.plant.greetapp.ModalClasses;

import java.util.ArrayList;

/**
 * Created by Nancy on 3/8/2019.
 */

public class FrameModalClass {
    private Frame_list[] frame_list;

    private String message;

    private String status;

    public ArrayList<Frame_list> framelist = new ArrayList<>();
    public ArrayList<Frame_list> getFramelist() {
        return framelist;
    }

    public void setFramelist(ArrayList<Frame_list> framelist) {
        this.framelist = framelist;
    }




    public Frame_list[] getFrame_list ()
    {
        return frame_list;
    }

    public void setFrame_list (Frame_list[] frame_list)
    {
        this.frame_list = frame_list;
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
        return "ClassPojo [frame_list = "+frame_list+", message = "+message+", status = "+status+"]";
    }
}
