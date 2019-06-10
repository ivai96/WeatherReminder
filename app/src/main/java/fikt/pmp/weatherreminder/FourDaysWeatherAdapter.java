package fikt.pmp.weatherreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import fikt.pmp.weatherreminder.DataModel.List;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;

public class FourDaysWeatherAdapter extends RecyclerView.Adapter<FourDaysWeatherAdapter.FourDaysWeatherViewHolder> {
    private int mNumberItems;
    private OpenWeatherMapFiveDays mOpenWeatherMapFiveDays;

    public FourDaysWeatherAdapter(int numberOfItems, OpenWeatherMapFiveDays openWeatherMapFiveDays) {
        mNumberItems = numberOfItems;
        mOpenWeatherMapFiveDays = openWeatherMapFiveDays;

    }

    @Override
    public FourDaysWeatherAdapter.FourDaysWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.forecast_four_days;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        FourDaysWeatherAdapter.FourDaysWeatherViewHolder viewHolder = new FourDaysWeatherAdapter.FourDaysWeatherViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FourDaysWeatherAdapter.FourDaysWeatherViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }




    class FourDaysWeatherViewHolder extends RecyclerView.ViewHolder {
        TextView listDayView;

        public FourDaysWeatherViewHolder(View itemView) {
            super(itemView);

            listDayView = itemView.findViewById(R.id.dayDate);
        }

        void bind(int listIndex) {

            List item = mOpenWeatherMapFiveDays.getList().get(listIndex);

            String dateTimeString = item.getDt_txt();
            DateTime dateTime = DateTime.parse(dateTimeString, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            listDayView.setText(dateTime.toString(DateTimeFormat.mediumDate()));

        }
    }
}
