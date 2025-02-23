package org.poifinder.dataMappers.activities;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.poifinder.dataMappers.DataMapper;
import org.springframework.stereotype.Service;

/**
 * Mappa una serie di dati in un oggetto della classe Activity senza rappresentare
 * oggetti esterni.
 * @return istanza dell'oggetto
 */

@Getter
@Service
@MappedSuperclass
public class ActivityListMapper implements DataMapper {

    private Long id;

    private String name;

    private String description;

    private String type;

    public ActivityListMapper() { }

    public ActivityListMapper(String name,
                              String description,
                              String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    //todo remove

//    public String getName() { return name; }
//
//    public void setName(String name) { this.name = name; }
//
//    public String getDescription() { return description; }
//
//    public void setDescription(String description) { this.description = description;}
//
//    public String getType() { return type; }
//
//    public void setType(String type) {
//        this.type = type;
//    }

}
