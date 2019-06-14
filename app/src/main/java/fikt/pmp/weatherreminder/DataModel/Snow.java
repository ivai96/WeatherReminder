package fikt.pmp.weatherreminder.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Snow implements Serializable {
    @SerializedName("3h")
    private float volume;

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
