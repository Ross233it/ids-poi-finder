package org.poifinder.dataMappers.municipalities;

import org.poifinder.dataMappers.DataMapper;
import org.poifinder.models.municipalities.Municipality;
import org.springframework.stereotype.Component;

/**
 * Mappa una serie di dati in un oggetto della classe Municipality *
 * @return istanza dell'oggetto
 */
@Component
public class MunicipalityListMapper implements DataMapper{


    private String name;


    private String region;


    private String province;


    private String status;


    public MunicipalityListMapper() { }

    public MunicipalityListMapper(String name,
                                  String region,
                                  String province,
                                  String status) {
        this.name = name;
        this.region = region;
        this.province = province;
        this.status = status;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region;}

    public String getProvince() { return province; }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


