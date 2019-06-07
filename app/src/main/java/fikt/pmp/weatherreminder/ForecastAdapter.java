package fikt.pmp.weatherreminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fikt.pmp.weatherreminder.DataModel.List;
import fikt.pmp.weatherreminder.DataModel.OpenWeatherMapFiveDays;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private static final String TAG = ForecastAdapter.class.getSimpleName();
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
        TextView listItemNumberView;

        public ForecastViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = itemView.findViewById(R.id.tv_item_number);

        }

        void bind(int listIndex) {

            String stringBuilder = "";
            List item = mOpenWeatherMapFiveDays.getList().get(listIndex);
            stringBuilder += "Time: " +
                    item.getDt_txt() +
                    "\n" +
                    "Temperature: " +
                    item.getMain().getTemp();
            listItemNumberView.setText(stringBuilder);
        }
    }
}
