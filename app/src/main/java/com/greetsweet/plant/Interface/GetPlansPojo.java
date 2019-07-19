package com.greetsweet.plant.Interface;

public class GetPlansPojo {
    private String current_plan;

    private String repeat_user;

    private String messsage;

    private All_plans[] all_plans;

    private String status;

    public String getCurrent_plan ()
    {
        return current_plan;
    }

    public void setCurrent_plan (String current_plan)
    {
        this.current_plan = current_plan;
    }

    public String getRepeat_user ()
    {
        return repeat_user;
    }

    public void setRepeat_user (String repeat_user)
    {
        this.repeat_user = repeat_user;
    }

    public String getMesssage ()
    {
        return messsage;
    }

    public void setMesssage (String messsage)
    {
        this.messsage = messsage;
    }

    public All_plans[] getAll_plans ()
    {
        return all_plans;
    }

    public void setAll_plans (All_plans[] all_plans)
    {
        this.all_plans = all_plans;
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
        return "ClassPojo [current_plan = "+current_plan+", repeat_user = "+repeat_user+", messsage = "+messsage+", all_plans = "+all_plans+", status = "+status+"]";
    }
}
