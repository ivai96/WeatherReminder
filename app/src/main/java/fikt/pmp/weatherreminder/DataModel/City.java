package fikt.pmp.weatherreminder.DataModel;

import java.io.Serializable;

public class City implements Serializable {
    private int id;
    private String name;
    Coord coord;
    private String country;
    private int timezone;


    // Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public int getTimezone() {
        return timezone;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoord(Coord coordObject) {
        this.coord = coordObject;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }
}
