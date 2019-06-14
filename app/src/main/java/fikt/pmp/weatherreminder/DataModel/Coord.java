package fikt.pmp.weatherreminder.DataModel;

import java.io.Serializable;

public class Coord implements Serializable {
    private float lat;
    private float lon;


    // Getter Methods

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    // Setter Methods

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
