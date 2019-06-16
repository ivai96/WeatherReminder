package fikt.pmp.weatherreminder.DataModel;

public class User {
    private String username;
    private String email;
    public Boolean rain;
    public Boolean snow;
    public Boolean thunder;
    public Boolean mist;

    public User(){}
    public User(String username, String email, Boolean rain, Boolean snow,
                Boolean thunder, Boolean mist) {
        this.email = email;
        this.username = username;
        this.rain = rain;
        this.snow = snow;
        this.thunder = thunder;
        this.mist = mist;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
