package org.poifinder.models.activities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.models.Content;

@Getter
@Setter
@Entity
@Table(name="prizes")
public class Prize extends Content {

    private Integer value;

    private String sponsor;

    public Prize() {  }

    public Prize(Integer value, String sponsor){
        this.value = value;
        this.sponsor = sponsor;
    }

    /** setters **/

//    public void setValue(Integer value) { this.value = value; }
//
//    public void setSponsor(String sponsor) { this.sponsor = sponsor; }

    /** getters **/

//    public Integer getValue() { return value; }
//
//    public String getSponsor() { return sponsor; }
}
