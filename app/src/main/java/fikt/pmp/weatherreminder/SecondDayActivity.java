package fikt.pmp.weatherreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;

public class SecondDayActivity extends AppCompatActivity {

    RecyclerView mWeatherList;
    ForecastAdapter mForecastAdapter;
    LinearLayout mforecastAdapterLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_day);

        mforecastAdapterLL = findViewById(R.id.forecastAdapterLL);

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
            my5days.setList(weatherDataSort.getDayData(2));
            mForecastAdapter = new ForecastAdapter(my5days.getList().size(), my5days, R.layout.forecast_list_item_horizontal);
        }


        mWeatherList = findViewById(R.id.recyclerDayTwo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWeatherList.setLayoutManager(layoutManager);
        mWeatherList.setAdapter(mForecastAdapter);


    }
}
