package fikt.pmp.weatherreminder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;


import fikt.pmp.weatherreminder.ConsumingAPIs.FiveDayThreeHour;
import fikt.pmp.weatherreminder.DataModel.List;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String BASE_URL = "http://api.openweathermap.org/";
    private static String APP_ID = "929c4499e37f7764ce3dda29873feb84";
    private static String ID = "792578";

    private TextView weatherData;
    private TextView cityName;
    private TextView country;
    private ImageView weatherIconIV;
    private TextView weatherDescription;
    private TextView temperature;
    private ImageView celsiusOrFahrenheitIV;
    private TextView firstDateTV;
    private TextView firstTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        weatherData = findViewById(R.id.weatherData);
        cityName = findViewById(R.id.cityName);
        country = findViewById(R.id.countryName);
        weatherIconIV = findViewById(R.id.weatherIcon);
        weatherDescription = findViewById(R.id.weatherDescription);
        temperature = findViewById(R.id.temperature);
        celsiusOrFahrenheitIV = findViewById(R.id.celsiusOrFahrenheit);
        firstDateTV = findViewById(R.id.firstDate);
        firstTimeTv = findViewById(R.id.firstTime);

        getCurrentData();

    }

    void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IWeatherService service = retrofit.create(IWeatherService.class);
        Call<OpenWeatherMapData> call = service.getCurrentWeatherData(ID, APP_ID);
        call.enqueue(new Callback<OpenWeatherMapData>() {
            @Override
            public void onResponse
                    (@NonNull Call<OpenWeatherMapData> call, @NonNull Response<OpenWeatherMapData> response) {
                if (response.code() == 200) {
                    OpenWeatherMapData weatherResponse = response.body();
                    assert weatherResponse != null;

                    cityName.setText(weatherResponse.getCity().getName());
                    country.setText(", " + weatherResponse.getCity().getCountry());

                    String iconCode = weatherResponse.getList().get(0).getWeather().get(0).getIcon();
                    getMainWeatherIcon(iconCode);

                    weatherDescription.setText(weatherResponse.getList().get(0).getWeather().get(0).getDescription());

                    float tempInKelvin = weatherResponse.getList().get(0).getMain().getTemp();
                    calculateTemperature(tempInKelvin, true);

                    String dateTimeString = weatherResponse.getList().get(0).getDt_txt();
                    DateTime dateTime = DateTime.parse(dateTimeString, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
                    firstDateTV.setText(dateTime.toString(DateTimeFormat.mediumDate()));
                    firstTimeTv.setText("Last measured at: " + dateTime.toString(DateTimeFormat.shortTime()));

                    String stringBuilder = "";
                    for (int i = 0; i < weatherResponse.getCnt(); i++) {
                        List item = weatherResponse.getList().get(i);
                        stringBuilder += "Time: " +
                                item.getDt_txt() +
                                "\n" +
                                "Temperature: " +
                                item.getMain().getTemp();
                    }
                    //weatherData.setText(stringBuilder);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherMapData> call, @NonNull Throwable t) {
                weatherData.setText("Greska");
            }


        });


    }

    void getMainWeatherIcon(String iconCode) {
        switch (iconCode) {
            case "01d":
                weatherIconIV.setImageResource(R.drawable.clearskyd);
                break;
            case "01n":
                weatherIconIV.setImageResource(R.drawable.clearskyn);
                break;
            case "02d":
                weatherIconIV.setImageResource(R.drawable.fewcloudsd);
                break;
            case "02n":
                weatherIconIV.setImageResource(R.drawable.fewcloudsn);
                break;
            case "03d":
            case "03n":
                weatherIconIV.setImageResource(R.drawable.clearskyn);
                break;
            case "04d":
            case "04n":
                weatherIconIV.setImageResource(R.drawable.brokenclouds);
                break;
            case "09d":
            case "09n":
                weatherIconIV.setImageResource(R.drawable.showerrain);
                break;
            case "10d":
                weatherIconIV.setImageResource(R.drawable.raind);
                break;
            case "10n":
                weatherIconIV.setImageResource(R.drawable.rainn);
                break;
            case "11d":
                weatherIconIV.setImageResource(R.drawable.thunderstormd);
                break;
            case "11n":
                weatherIconIV.setImageResource(R.drawable.thunderstormn);
                break;
            case "13d":
                weatherIconIV.setImageResource(R.drawable.snowd);
                break;
            case "13n":
                weatherIconIV.setImageResource(R.drawable.snown);
                break;
            case "50d":
            case "50n":
                weatherIconIV.setImageResource(R.drawable.mist);
                break;
        }
    }

    void calculateTemperature(float tempInKelvin, boolean convertToCelsius) {
        if (convertToCelsius == true) {
            temperature.setText(Integer.toString(Math.round(tempInKelvin - 273.15f)));
            celsiusOrFahrenheitIV.setImageResource(R.drawable.celsius);
        } else {
            temperature.setText(Integer.toString(Math.round((tempInKelvin - 273.15f) * 9 / 5 + 32)));
            celsiusOrFahrenheitIV.setImageResource(R.drawable.fahrenheit);
        }
    }
}
