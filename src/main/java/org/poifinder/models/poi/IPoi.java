package org.poifinder.models.poi;


import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.GeoLocation;

/**
 * Astrae il concetto di "punto di interesse" inteso come entità
 * correlata ad un punto del territorio ed ad un Comune Italiano
 */
public interface IPoi  {

    GeoLocation getGeoLocation();

    void setGeoLocation(GeoLocation geoLocation);

    Municipality getMunicipality();

    void setMunicipality(Municipality municipality);
}
