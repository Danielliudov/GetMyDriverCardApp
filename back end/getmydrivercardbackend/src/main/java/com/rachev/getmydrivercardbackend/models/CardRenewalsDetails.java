package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_renewals_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardRenewalsDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "renewal_id", unique = true)
    private int id;
    
    @NotNull
    @Column(name = "renewal_reason")
    private String renewalReason;
    
    @Nullable
    @Column(name = "renewal_first_name")
    private String renewalFirstName;
    
    @Nullable
    @Column(name = "renewal_middle_name")
    private String renewalMiddleName;
    
    @Nullable
    @Column(name = "renewal_last_name")
    private String renewalLastName;
    
    @Nullable
    @Column(name = "renewal_address")
    private String renewalAddress;
}
