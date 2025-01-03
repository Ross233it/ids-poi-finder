package org.models;

public class GeoLocation extends Model{

    private double latitude;

    private double longitude;

    private String address;

    private String number;

    private String cap;

    public GeoLocation(
                       String address,
                       String number,
                       String cap) {
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = 0.00;
        this.longitude = 0.00;
    }

    public GeoLocation(
                       String address,
                       String number,
                       String cap,
                       double latitude,
                       double longitude) {
        this.address = address;
        this.number = number;
        this.cap = cap;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Restituisce i dati dell'oggetto
     * @return Object[] array di oggetti
     */
    @Override
    public Object[]  getData(){ return new Object[] {
            this.getAddress(),
            this.getNumber(),
            this.getCap(),
            this.getLongitude(),
            this.getLatitude()
        };
    }

    @Override
    public String toString() {
        return String.format("GeoLocation{id=%d, address='%s', number='%s', cap='%s', latitude=%.2f, longitude=%.2f}",
                getId(), address, number, cap, latitude, longitude);
    }

    /** getters **/
    public double getLatitude()  { return latitude; }
    public double getLongitude() { return longitude;}
    public String getAddress()   { return address;  }
    public String getNumber()    { return number;   }
    public String getCap()       { return cap;      }

    /** setters **/
    public void setLatitude(double latitude) {this.latitude = latitude; }
    public void setLongitude(double longitude) {this.longitude = longitude; }
    public void setAddress(String address) {this.address = address;}
    public void setNumber(String number)   {this.number = number;}
    public void setCap(String cap) {this.cap = cap;}
}
