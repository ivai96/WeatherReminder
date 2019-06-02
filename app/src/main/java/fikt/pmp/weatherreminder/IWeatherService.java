package fikt.pmp.weatherreminder;

import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("data/2.5/forecast?")
    Call<OpenWeatherMapData> getCurrentWeatherData(@Query("id") String ID, @Query("APPID") String app_id);
}
