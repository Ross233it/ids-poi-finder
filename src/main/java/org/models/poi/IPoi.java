package org.models.poi;

import org.models.Municipality;
import org.models.GeoLocation;

/**
 * Astrae il concetto di "punto di interesse" inteso come entità
 * correlata ad un punto del territorio ed ad un Comune Italiano
 */
public interface IPoi {

    public GeoLocation getGeoLocation();

    public void setGeoLocation(GeoLocation geoLocation);

    public Municipality getMunicipality();

    public void setMunicipality(Municipality municipality);
}
