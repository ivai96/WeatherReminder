package fikt.pmp.weatherreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import fikt.pmp.weatherreminder.DataModel.List;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private int mNumberItems;
    private OpenWeatherMapFiveDays mOpenWeatherMapFiveDays;

    public ForecastAdapter(int numberOfItems, OpenWeatherMapFiveDays openWeatherMapFiveDays) {
        mNumberItems = numberOfItems;
        mOpenWeatherMapFiveDays = openWeatherMapFiveDays;

    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }





    class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView listItemTimeView;
        ImageView listItemIconView;
        TextView listItemTempView;

        public ForecastViewHolder(View itemView) {
            super(itemView);

            listItemTimeView = itemView.findViewById(R.id.tv_item_time);
            listItemIconView = itemView.findViewById(R.id.iv_item_icon);
            listItemTempView = itemView.findViewById(R.id.tv_item_temp);
        }

        void bind(int listIndex) {

            String stringBuilder = "";
            List item = mOpenWeatherMapFiveDays.getList().get(listIndex);

            String dateTimeString = item.getDt_txt();
            DateTime dateTime = DateTime.parse(dateTimeString, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            listItemTimeView.setText(dateTime.toString(DateTimeFormat.shortTime()));

            String iconCode = item.getWeather().get(0).getIcon();
            switch (iconCode) {
                case "01d":
                    listItemIconView.setImageResource(R.drawable.clearskyd);
                    break;
                case "01n":
                    listItemIconView.setImageResource(R.drawable.clearskyn);
                    break;
                case "02d":
                    listItemIconView.setImageResource(R.drawable.fewcloudsd);
                    break;
                case "02n":
                    listItemIconView.setImageResource(R.drawable.fewcloudsn);
                    break;
                case "03d":
                case "03n":
                    listItemIconView.setImageResource(R.drawable.scatteredclouds);
                    break;
                case "04d":
                case "04n":
                    listItemIconView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "09d":
                case "09n":
                    listItemIconView.setImageResource(R.drawable.showerrain);
                    break;
                case "10d":
                    listItemIconView.setImageResource(R.drawable.raind);
                    break;
                case "10n":
                    listItemIconView.setImageResource(R.drawable.rainn);
                    break;
                case "11d":
                    listItemIconView.setImageResource(R.drawable.thunderstormd);
                    break;
                case "11n":
                    listItemIconView.setImageResource(R.drawable.thunderstormn);
                    break;
                case "13d":
                    listItemIconView.setImageResource(R.drawable.snowd);
                    break;
                case "13n":
                    listItemIconView.setImageResource(R.drawable.snown);
                    break;
                case "50d":
                case "50n":
                    listItemIconView.setImageResource(R.drawable.mist);
                    break;
            }

            float tempInKelvin = item.getMain().getTemp();
            listItemTempView.setText(Integer.toString(Math.round(tempInKelvin - 273.15f)) + "°C");

            stringBuilder += "Time: " +
                    item.getDt_txt() +
                    "\n" +
                    "Temperature: " +
                    item.getMain().getTemp();
            //listItemNumberView.setText(stringBuilder);
        }
    }
}
