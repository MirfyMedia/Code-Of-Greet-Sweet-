package com.greetsweet.plant.Interface;

public class OrderCreatePojo {
    private String plan_type;

    private String total_amount;

    private String type;

    private String message;

    private String order_id;

    private String value;

    private String status;

    public String getPlan_type ()
    {
        return plan_type;
    }

    public void setPlan_type (String plan_type)
    {
        this.plan_type = plan_type;
    }

    public String getTotal_amount ()
    {
        return total_amount;
    }

    public void setTotal_amount (String total_amount)
    {
        this.total_amount = total_amount;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
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
        return "ClassPojo [plan_type = "+plan_type+", total_amount = "+total_amount+", type = "+type+", message = "+message+", order_id = "+order_id+", value = "+value+", status = "+status+"]";
    }
}
