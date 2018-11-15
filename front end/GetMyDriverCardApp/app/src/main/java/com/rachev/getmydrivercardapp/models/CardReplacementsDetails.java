package com.rachev.getmydrivercardapp.models;

public class CardReplacementsDetails
{
    private int id;
    private String replacementReason;
    private String tachCardIssuingCountry;
    private String tachCardNumber;
    private String drivingLicIssuingCountry;
    private String drivingLicNumber;
    private String replacementIncidentDate;
    private String replacementIncidentPlace;
    
    public CardReplacementsDetails(String replacementReason, String tachCardIssuingCountry,
                                   String tachCardNumber, String drivingLicIssuingCountry,
                                   String drivingLicNumber, String replacementIncidentDate,
                                   String replacementIncidentPlace)
    {
        this.replacementReason = replacementReason;
        this.tachCardIssuingCountry = tachCardIssuingCountry;
        this.tachCardNumber = tachCardNumber;
        this.drivingLicIssuingCountry = drivingLicIssuingCountry;
        this.drivingLicNumber = drivingLicNumber;
        this.replacementIncidentDate = replacementIncidentDate;
        this.replacementIncidentPlace = replacementIncidentPlace;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getReplacementReason()
    {
        return replacementReason;
    }
    
    public void setReplacementReason(String replacementReason)
    {
        this.replacementReason = replacementReason;
    }
    
    public String getTachCardIssuingCountry()
    {
        return tachCardIssuingCountry;
    }
    
    public void setTachCardIssuingCountry(String tachCardIssuingCountry)
    {
        this.tachCardIssuingCountry = tachCardIssuingCountry;
    }
    
    public String getTachCardNumber()
    {
        return tachCardNumber;
    }
    
    public void setTachCardNumber(String tachCardNumber)
    {
        this.tachCardNumber = tachCardNumber;
    }
    
    public String getDrivingLicIssuingCountry()
    {
        return drivingLicIssuingCountry;
    }
    
    public void setDrivingLicIssuingCountry(String drivingLicIssuingCountry)
    {
        this.drivingLicIssuingCountry = drivingLicIssuingCountry;
    }
    
    public String getDrivingLicNumber()
    {
        return drivingLicNumber;
    }
    
    public void setDrivingLicNumber(String drivingLicNumber)
    {
        this.drivingLicNumber = drivingLicNumber;
    }
    
    public String getReplacementIncidentDate()
    {
        return replacementIncidentDate;
    }
    
    public void setReplacementIncidentDate(String replacementIncidentDate)
    {
        this.replacementIncidentDate = replacementIncidentDate;
    }
    
    public String getReplacementIncidentPlace()
    {
        return replacementIncidentPlace;
    }
    
    public void setReplacementIncidentPlace(String replacementIncidentPlace)
    {
        this.replacementIncidentPlace = replacementIncidentPlace;
    }
}
