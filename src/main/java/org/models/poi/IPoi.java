package org.models.poi;

import org.models.Content;
import org.models.IModel;
import org.models.Municipality;
import org.models.GeoLocation;

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
