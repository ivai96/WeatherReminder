package fikt.pmp.weatherreminder;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.ExecutionException;

import fikt.pmp.weatherreminder.ConsumingAPIs.CurrentWeather;
import fikt.pmp.weatherreminder.ConsumingAPIs.FiveDayThreeHour;
import fikt.pmp.weatherreminder.DataModel.List;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapCurrent;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;

public class MainActivity extends AppCompatActivity {

    private TextView weatherData;
    private TextView cityName;
    private TextView country;
    private ImageView weatherIconIV;
    private TextView weatherDescription;
    private TextView temperature;
    private ImageView celsiusOrFahrenheitIV;
    private TextView firstDateTV;
    OpenWeatherMapFiveDays openWeatherMapFiveDays;
    OpenWeatherMapCurrent openWeatherMapCurrent;

    private static final int NUM_LIST_ITEMS = 9;
    ForecastAdapter mForecastAdapter;
    RecyclerView mWeatherList;

    private LinearLayout mSecondDayWeather;
    private TextView mSecondDayDate;
    private TextView mSecondDayTemp;
    private LinearLayout mThirdDayWether;
    private TextView mThirdDayDate;
    private TextView mThirdDayTemp;
    private LinearLayout mFourthDayWeather;
    private TextView mFourthDayDate;
    private TextView mFourthDayTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        cityName = findViewById(R.id.cityName);
        country = findViewById(R.id.countryName);
        weatherIconIV = findViewById(R.id.weatherIcon);
        weatherDescription = findViewById(R.id.weatherDescription);
        temperature = findViewById(R.id.temperature);
        celsiusOrFahrenheitIV = findViewById(R.id.celsiusOrFahrenheit);
        firstDateTV = findViewById(R.id.firstDate);
        mSecondDayWeather = findViewById(R.id.secondDayWeather);
        mSecondDayDate = findViewById(R.id.secondDayDate);
        mSecondDayTemp = findViewById(R.id.secondDayTemp);
        mThirdDayWether = findViewById(R.id.thirdDayWeather);
        mThirdDayDate = findViewById(R.id.thirdDayDate);
        mThirdDayTemp = findViewById(R.id.thirdDayTemp);
        mFourthDayWeather = findViewById(R.id.fourthDayWeather);
        mFourthDayDate = findViewById(R.id.fourthDayDate);
        mFourthDayTemp = findViewById(R.id.fourthDayTemp);

        try {
            openWeatherMapFiveDays = new FiveDayThreeHour().execute().get();
            openWeatherMapCurrent = new CurrentWeather().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setWeatherData();

        mWeatherList = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mWeatherList.setLayoutManager(layoutManager);
        mWeatherList.setHasFixedSize(true);

        /*WeatherDataSort weatherDataSort = new WeatherDataSort(openWeatherMapFiveDays.getList());
        weatherDataSort.findAllDates();
        OpenWeatherMapFiveDays my5days = new OpenWeatherMapFiveDays();
        my5days.setCity(openWeatherMapFiveDays.getCity());
        my5days.setCnt(openWeatherMapFiveDays.getCnt());
        my5days.setCod(openWeatherMapFiveDays.getCod());
        my5days.setMessage(openWeatherMapFiveDays.getMessage());
        my5days.setList(weatherDataSort.getDayData(1));*/
        // mForecastAdapter = new ForecastAdapter(my5days.getList().size(), my5days);
        mForecastAdapter = new ForecastAdapter(NUM_LIST_ITEMS, openWeatherMapFiveDays, R.layout.forecast_list_item);

        mWeatherList.setAdapter(mForecastAdapter);


    }

    void setWeatherData() {
        cityName.setText(openWeatherMapFiveDays.getCity().getName());
        country.setText(", " + openWeatherMapFiveDays.getCity().getCountry());

        String iconCode = openWeatherMapFiveDays.getList().get(0).getWeather().get(0).getIcon();
        getMainWeatherIcon(iconCode);

        weatherDescription.setText(openWeatherMapFiveDays.getList().get(0).getWeather().get(0).getDescription());

        float tempInKelvin = openWeatherMapCurrent.getMain().getTemp();
        calculateTemperature(tempInKelvin, true);

        WeatherDataSort weatherDataSort = new WeatherDataSort(openWeatherMapFiveDays.getList());
        weatherDataSort.findAllDates();


//        DateTime dateTime = DateTime.parse(openWeatherMapFiveDays.getList().get(0).getDt_txt(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
//        firstDateTV.setText(dateTime.toString(DateTimeFormat.mediumDate()));
        firstDateTV.setText(weatherDataSort.getDateTimes().get(0).toString(DateTimeFormat.mediumDate()));


//        DateTime secondDayDate = new DateTime().plusDays(1);
//        mSecondDayDate.setText(secondDayDate.toString(DateTimeFormat.mediumDate()));
        TemperatureWorker dayTwo = new TemperatureWorker(true);
        dayTwo.findDayMinMax(weatherDataSort.getDayData(2));

        SimpleDateFormat s = new SimpleDateFormat("EEE, d MMM");

        mSecondDayDate.setText(s.format(weatherDataSort.getDates().get(1)));
        mSecondDayTemp.setText(dayTwo.getmDayMaxTemp() + "° / " + dayTwo.getmDayMinTemp() + "°");

        mSecondDayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondDayActivity.class);
                intent.putExtra("fiveDays", openWeatherMapFiveDays);
                startActivity(intent);
            }
        });

        TemperatureWorker dayThree = new TemperatureWorker(true);
        dayThree.findDayMinMax(weatherDataSort.getDayData(3));
        mThirdDayDate.setText(s.format(weatherDataSort.getDates().get(2)));
        mThirdDayTemp.setText(dayThree.getmDayMaxTemp() + "° / " + dayThree.getmDayMinTemp() + "°");

        mThirdDayWether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdDayActivity.class);
                intent.putExtra("fiveDays", openWeatherMapFiveDays);
                startActivity(intent);
            }
        });

        TemperatureWorker dayFour = new TemperatureWorker(true);
        dayFour.findDayMinMax(weatherDataSort.getDayData(4));
        mFourthDayDate.setText(s.format(weatherDataSort.getDates().get(3)));
        mFourthDayTemp.setText(dayFour.getmDayMaxTemp() + "° / " + dayFour.getmDayMinTemp() + "°");

        mFourthDayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FourthDay.class);
                intent.putExtra("fiveDays", openWeatherMapFiveDays);
                startActivity(intent);
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
                weatherIconIV.setImageResource(R.drawable.scatteredclouds);
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
        if (convertToCelsius) {
            temperature.setText(Integer.toString(Math.round(tempInKelvin - 273.15f)));
            celsiusOrFahrenheitIV.setImageResource(R.drawable.celsius);
        } else {
            temperature.setText(Integer.toString(Math.round((tempInKelvin - 273.15f) * 9 / 5 + 32)));
            celsiusOrFahrenheitIV.setImageResource(R.drawable.fahrenheit);
        }
    }

}
