package fikt.pmp.weatherreminder;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private static String ICON_BASE_URL = "http://openweathermap.org/img/w/";

    private TextView weatherData;
    private TextView cityName;
    private TextView country;
    private ImageView weatherIcon;
    private TextView weatherDescription;
    private TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherData = findViewById(R.id.weatherData);
        cityName = findViewById(R.id.cityName);
        country = findViewById(R.id.countryName);
        weatherIcon = findViewById(R.id.weatherIcon);
        weatherDescription = findViewById(R.id.weatherDescription);
        temperature = findViewById(R.id.temperature);

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
            public void onResponse(@NonNull Call<OpenWeatherMapData> call, @NonNull Response<OpenWeatherMapData> response) {
                if (response.code() == 200) {
                    OpenWeatherMapData weatherResponse = response.body();
                    assert weatherResponse != null;

                    cityName.setText(weatherResponse.getCity().getName());
                    country.setText(", " + weatherResponse.getCity().getCountry());
                    String iconCode = weatherResponse.getList().get(0).getWeather().get(0).getIcon();
                    String url = ICON_BASE_URL+iconCode+".png";

                    weatherIcon.setImageResource(R.drawable.clearskyn);
                    weatherDescription.setText(weatherResponse.getList().get(0).getWeather().get(0).getDescription());

                    temperature.setText(Float.toString(weatherResponse.getList().get(0).getMain().getTemp()-273.15f));
                    String stringBuilder = "" ;
                    for (int i = 0; i < weatherResponse.getCnt(); i++) {
                        List item = weatherResponse.getList().get(i);
                        stringBuilder += "Time: " +
                                item.getDt_txt()+
                                "\n" +
                                "Temperature: " +
                                item.getMain().getTemp();
                    }

                    weatherData.setText(stringBuilder);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherMapData> call, @NonNull Throwable t) {
                weatherData.setText("Грешка");
            }
        });
    }
}
