package com.rachev.getmydrivercardapp.models;

import android.graphics.Color;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseRequest implements Serializable
{
    private int id;
    private String type;
    private String status;
    private User user;
    private CardReplacementsDetails replacementDetails;
    private CardRenewalsDetails renewalDetails;
    private ApplicantDetails applicantDetails;
    private ImageAttachment imageAttachment;
    private String recordCreationDate;
    private String lastEditDate;
    
    public BaseRequest()
    {
    }
    
    public BaseRequest(User user, String type, String status, ApplicantDetails applicantDetails)
    {
        setUser(user);
        setType(type);
        setStatus(status);
        setApplicantDetails(applicantDetails);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public ApplicantDetails getApplicantDetails()
    {
        return applicantDetails;
    }
    
    public void setApplicantDetails(ApplicantDetails applicantDetails)
    {
        this.applicantDetails = applicantDetails;
    }
    
    public LocalDateTime getRecordCreationDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        return LocalDateTime.parse(recordCreationDate, dtf);
    }
    
    public LocalDateTime getLastEditDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        return LocalDateTime.parse(lastEditDate, dtf);
    }
    
    public int getStatusColor()
    {
        switch (RequestStatus.valueOf(getStatus().toUpperCase()))
        {
            case PENDING:
                return Color.rgb(220, 190, 22);
            case APPROVED:
                return Color.rgb(125, 189, 0);
            case DISAPPROVED:
                return Color.rgb(255, 91, 0);
        }
        return Color.rgb(88, 180, 222);
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public ImageAttachment getImageAttachment()
    {
        return imageAttachment;
    }
    
    public void setImageAttachment(ImageAttachment imageAttachment)
    {
        this.imageAttachment = imageAttachment;
    }
    
    public CardRenewalsDetails getRenewalDetails()
    {
        return renewalDetails;
    }
    
    public void setRenewalDetails(CardRenewalsDetails renewalDetails)
    {
        this.renewalDetails = renewalDetails;
    }
    
    public CardReplacementsDetails getReplacementDetails()
    {
        return replacementDetails;
    }
    
    public void setReplacementDetails(CardReplacementsDetails replacementDetails)
    {
        this.replacementDetails = replacementDetails;
    }
}