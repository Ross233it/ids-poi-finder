package org.poifinder.models.poi;

import org.poifinder.models.Content;
import org.poifinder.models.municipalities.Municipality;
import org.poifinder.models.GeoLocation;

/**
 * Astrae il concetto di "punto di interesse" inteso come entità
 * correlata ad un punto del territorio ed ad un Comune Italiano
 */
public abstract class IPoi extends Content {

    public abstract GeoLocation getGeoLocation();

    public abstract void setGeoLocation(GeoLocation geoLocation);

    public abstract Municipality getMunicipality();

    public abstract void setMunicipality(Municipality municipality);
}
