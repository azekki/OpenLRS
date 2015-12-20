package org.apereo.openlrs.credentials;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;

@Profile("aws")
@Entity(name="CREDENTIALS")
public class Credential {

    public Credential() {
        dateModified = Calendar.getInstance().getTime();
        dateCreated = Calendar.getInstance().getTime();
    }

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="ORGANIZATION")
    private String organization;
    
    @NotNull
    @Column(name="`KEY`", nullable=false)
    private String key;
    
    @NotNull
    @Column(name="SECRET", nullable=false)
    private String secret;
    
    @Column(name="ACTIVE")
    private boolean active;
    
    @Column(name="DATEMODIFIED")
    private Date dateModified;
    
    @Column(name="DATECREATED")
    private Date dateCreated;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Credential [id=" + id + ", organization=" + organization + ", key=" + key + ", secret=" + secret
                + ", active=" + active + ", dateModified=" + dateModified + ", dateCreated=" + dateCreated + "]";
    }
}
