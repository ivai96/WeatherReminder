package fikt.pmp.weatherreminder;

import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapCurrent;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("data/2.5/forecast?")
    Call<OpenWeatherMapFiveDays> getForecastWeatherData(@Query("id") String ID, @Query("APPID") String app_id);

    @GET("data/2.5/weather?")
    Call<OpenWeatherMapCurrent> getCurrentWeatherData(@Query("id") String ID, @Query("APPID") String app_id);
}
