package org.poifinder.models.activities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Prize {

    @Id
    private Long id;

    private Integer value;

    private String description;

    private String sponsor;


    public Prize() {  }

    public Prize(Integer value, String description, String sponsor){
        this.value = value;
        this.description= description;
        this.sponsor = sponsor;
    }

    /** setters **/

    public void setId(Long id) { this.id = id; }

    public void setValue(Integer value) { this.value = value; }

    public void setDescription(String description) { this.description = description; }

    public void setSponsor(String sponsor) { this.sponsor = sponsor; }

    /** getters **/

    public Long getId() { return id; }

    public Integer getValue() { return value; }

    public String getDescription() { return description; }

    public String getSponsor() { return sponsor; }
}
