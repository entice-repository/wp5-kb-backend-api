package org.ul.entice.webapp.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Geolocation extends MyEntry {

    private String countryName;
    private String countryNameShort;
    private String continent;
    private double latitude;
    private double longitude;
    private double altitude;
    private String timezone;

    public Geolocation(String id) {
        super(id);
    }

    public Geolocation() {
    }

    public Geolocation(String id, String countryName,String countryNameShort, String continent, double latitude, double longitude, double
            altitude, String timezone) {
        super(id);
        this.countryName = countryName;
        this.countryNameShort = countryNameShort;
        this.continent = continent;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameShort() {
        return countryNameShort;
    }

    public void setCountryNameShort(String countryNameShort) {
        this.countryNameShort = countryNameShort;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
