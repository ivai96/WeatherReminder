package fikt.pmp.weatherreminder.DataModel;

import java.io.Serializable;

public class Wind implements Serializable {
    private float speed;
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }
}
