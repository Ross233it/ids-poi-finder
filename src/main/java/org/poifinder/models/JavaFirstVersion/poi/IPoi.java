package org.poifinder.models.JavaFirstVersion.poi;

import org.poifinder.models.JavaFirstVersion.Content;
import org.poifinder.models.JavaFirstVersion.GeoLocation;
import org.poifinder.models.JavaFirstVersion.municipalities.Municipality;

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
