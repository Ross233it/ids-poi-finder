package org.poifinder.models.users;

import lombok.Getter;
import lombok.Setter;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;

import java.util.List;

@Setter
@Getter
public class Author extends RegisteredUser{

    public List<Poi> writtenPois;

    public List<Activity> writtenActivities;

    public List<Municipality> writtenMunicipalities;


}
