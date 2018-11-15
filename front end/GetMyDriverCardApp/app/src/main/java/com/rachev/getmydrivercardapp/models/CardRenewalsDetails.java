package com.rachev.getmydrivercardapp.models;

public class CardRenewalsDetails
{
    private int id;
    private String renewalReason;
    private String renewalNewFirstName;
    private String renewalNewMiddleName;
    private String renewalNewLastName;
    private String renewalNewAddress;
    
    public CardRenewalsDetails(String renewalReason, String renewalNewFirstName, String renewalNewMiddleName,
                               String renewalNewLastName, String renewalNewAddress)
    {
        this.renewalReason = renewalReason;
        this.renewalNewFirstName = renewalNewFirstName;
        this.renewalNewMiddleName = renewalNewMiddleName;
        this.renewalNewLastName = renewalNewLastName;
        this.renewalNewAddress = renewalNewAddress;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getRenewalReason()
    {
        return renewalReason;
    }
    
    public void setRenewalReason(String renewalReason)
    {
        this.renewalReason = renewalReason;
    }
    
    public String getRenewalNewFirstName()
    {
        return renewalNewFirstName;
    }
    
    public void setRenewalNewFirstName(String renewalNewFirstName)
    {
        this.renewalNewFirstName = renewalNewFirstName;
    }
    
    public String getRenewalNewMiddleName()
    {
        return renewalNewMiddleName;
    }
    
    public void setRenewalNewMiddleName(String renewalNewMiddleName)
    {
        this.renewalNewMiddleName = renewalNewMiddleName;
    }
    
    public String getRenewalNewLastName()
    {
        return renewalNewLastName;
    }
    
    public void setRenewalNewLastName(String renewalNewLastName)
    {
        this.renewalNewLastName = renewalNewLastName;
    }
    
    public String getRenewalNewAddress()
    {
        return renewalNewAddress;
    }
    
    public void setRenewalNewAddress(String renewalNewAddress)
    {
        this.renewalNewAddress = renewalNewAddress;
    }
}
