package fikt.pmp.weatherreminder;

import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherData = findViewById(R.id.weatherData);
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
                    String stringBuilder = "Location: " ;
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
