package org.poifinder.models.users;

import lombok.Getter;
import lombok.Setter;
import org.poifinder.models.activities.Activity;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.poi.Poi;

import java.util.List;

/**
 * Rappresenta l'utente registrato nell'ambito dell'approvazione dei
 * contenuti
 */
@Setter
@Getter
public class Approver extends RegisteredUser{

    public List<Poi> approvedPois;

    public List<Activity> approvedActivities;

    public List<Municipality> approvedMunicipalities;


}
