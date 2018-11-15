package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "card_replacements_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardReplacementsDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replacement_id", unique = true)
    private int id;
    
    @Nullable
    @Column(name = "prev_tach_card_country")
    private String tachCardIssuingCountry;
    
    @Nullable
    @Column(name = "prev_tach_card_num")
    private String tachCardNumber;
    
    @Nullable
    @Column(name = "driving_lic_country")
    private String drivingLicIssuingCountry;
    
    @Nullable
    @Column(name = "driving_lic_num")
    private String drivingLicNumber;
    
    @Nullable
    @Column(name = "replacement_reason")
    private String replacementReason;
    
    @Nullable
    @Column(name = "replacement_incident_date")
    private String replacementIncidentDate;
    
    @Nullable
    @Column(name = "replacement_incident_place")
    private String replacementIncidentPlace;
}
