package org.poifinder.models.activities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.poifinder.dataMappers.Views;
import org.poifinder.models.Content;

@Getter
@Setter
@Entity
@Table(name="prizes")
public class Prize extends Content {

    @JsonView(Views.Public.class)
    private Integer value;

    @JsonView(Views.Public.class)
    private String sponsor;

    public Prize() {  }

    public Prize(Integer value, String sponsor){
        this.value = value;
        this.sponsor = sponsor;
    }
}
