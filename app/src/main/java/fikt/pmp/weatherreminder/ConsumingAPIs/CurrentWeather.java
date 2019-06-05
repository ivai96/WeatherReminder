package fikt.pmp.weatherreminder.ConsumingAPIs;

import android.os.AsyncTask;

import java.io.IOException;

import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapCurrent;
import fikt.pmp.weatherreminder.IWeatherService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeather extends AsyncTask<Void, Void, OpenWeatherMapCurrent> {
    private static String BASE_URL = "http://api.openweathermap.org/";
    private static String APP_ID = "929c4499e37f7764ce3dda29873feb84";
    private static String ID = "792578";
    OpenWeatherMapCurrent weatherResponse;

    @Override
    protected OpenWeatherMapCurrent doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IWeatherService service = retrofit.create(IWeatherService.class);
        Call<OpenWeatherMapCurrent> call = service.getCurrentWeatherData(ID, APP_ID);
        try {

            weatherResponse = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherResponse;
    }
}
