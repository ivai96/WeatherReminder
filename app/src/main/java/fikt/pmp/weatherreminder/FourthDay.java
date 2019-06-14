package fikt.pmp.weatherreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;


public class FourthDay extends AppCompatActivity {

    RecyclerView mWeatherList;
    ForecastAdapter mForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_day);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("fiveDays")) {
            OpenWeatherMapFiveDays openWeatherMapFiveDays = (OpenWeatherMapFiveDays) intentThatStartedThisActivity.getSerializableExtra("fiveDays");

            WeatherDataSort weatherDataSort = new WeatherDataSort(openWeatherMapFiveDays.getList());
            weatherDataSort.findAllDates();
            OpenWeatherMapFiveDays my5days = new OpenWeatherMapFiveDays();
            my5days.setCity(openWeatherMapFiveDays.getCity());
            my5days.setCnt(openWeatherMapFiveDays.getCnt());
            my5days.setCod(openWeatherMapFiveDays.getCod());
            my5days.setMessage(openWeatherMapFiveDays.getMessage());
            my5days.setList(weatherDataSort.getDayData(4));
            mForecastAdapter = new ForecastAdapter(my5days.getList().size(), my5days, R.layout.forecast_list_item_horizontal);
        }


        mWeatherList = findViewById(R.id.recyclerDayFour);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWeatherList.setLayoutManager(layoutManager);
        mWeatherList.setAdapter(mForecastAdapter);
    }
}
