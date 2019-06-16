package fikt.pmp.weatherreminder.DataModel;

public class WeatherTodayFirestore
{
    public Boolean rain;
    public Boolean snow;
    public Boolean thunder;
    public Boolean mist;

    public WeatherTodayFirestore(){}

    public WeatherTodayFirestore(Boolean rain, Boolean snow, Boolean thunder, Boolean mist) {
        this.rain = rain;
        this.snow = snow;
        this.thunder = thunder;
        this.mist = mist;
    }

    public Boolean getRain() {
        return rain;
    }

    public Boolean getSnow() {
        return snow;
    }

    public Boolean getThunder() {
        return thunder;
    }

    public Boolean getMist() {
        return mist;
    }
}
