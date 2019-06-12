package fikt.pmp.weatherreminder;

import java.util.ArrayList;

import fikt.pmp.weatherreminder.DataModel.List;

public class TemperatureWorker {

    private int mDayMinTemp;
    private int mDayMaxTemp;
    private boolean mIsCelsius;

    public TemperatureWorker(boolean isCelsius) {
        mIsCelsius = isCelsius;
    }

    public int getmDayMinTemp() {
        return mDayMinTemp;
    }

    public int getmDayMaxTemp() {
        return mDayMaxTemp;
    }

    public void findDayMinMax(ArrayList<List> list) {
        float minSum = list.get(0).getMain().getTemp_min();
        float maxSum = list.get(0).getMain().getTemp_max();
        for (List item : list
        ) {
            if (minSum > item.getMain().getTemp_min()){
                minSum = item.getMain().getTemp_min();}
            if (maxSum < item.getMain().getTemp_max()){
                maxSum = item.getMain().getTemp_max();}
        }
        mDayMinTemp = kelvinConverter(minSum);
        mDayMaxTemp = kelvinConverter(maxSum);

    }

    public int kelvinConverter(float tempInKelvin) {
        if (mIsCelsius)
            return Math.round(tempInKelvin - 273.15f);
        else
            return Math.round((tempInKelvin - 273.15f) * 9 / 5 + 32);
    }

}
