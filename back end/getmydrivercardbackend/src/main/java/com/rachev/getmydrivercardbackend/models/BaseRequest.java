package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseRequest extends BaseSqlEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true)
    private int id;
    
    @NotNull
    @Column(name = "type")
    private String type;
    
    @NotNull
    @Column(name = "status")
    private String status;
    
    @Nullable
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_renewal_id", referencedColumnName = "renewal_id")
    private CardRenewalsDetails renewalsDetails;
    
    @Nullable
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_replacement_id", referencedColumnName = "replacement_id")
    private CardReplacementsDetails replacementsDetails;
    
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_details_id", referencedColumnName = "details_id")
    private ApplicantDetails applicantDetails;
    
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_attachment_id", referencedColumnName = "attachment_id")
    private ImageAttachment imageAttachment;
    
    @NotNull
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_user_id", referencedColumnName = "user_id")
    private User user;
}
