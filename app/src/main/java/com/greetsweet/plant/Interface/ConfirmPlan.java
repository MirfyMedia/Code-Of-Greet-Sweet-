package com.greetsweet.plant.Interface;

public class ConfirmPlan {
    private String total_amount;

    private String messsage;

    private String plan_amount;

    private String plan_id;

    private String status;

    public String getTotal_amount ()
    {
        return total_amount;
    }

    public void setTotal_amount (String total_amount)
    {
        this.total_amount = total_amount;
    }

    public String getMesssage ()
    {
        return messsage;
    }

    public void setMesssage (String messsage)
    {
        this.messsage = messsage;
    }

    public String getPlan_amount ()
    {
        return plan_amount;
    }

    public void setPlan_amount (String plan_amount)
    {
        this.plan_amount = plan_amount;
    }

    public String getPlan_id ()
    {
        return plan_id;
    }

    public void setPlan_id (String plan_id)
    {
        this.plan_id = plan_id;
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
        return "ClassPojo [total_amount = "+total_amount+", messsage = "+messsage+", plan_amount = "+plan_amount+", plan_id = "+plan_id+", status = "+status+"]";
    }
}
