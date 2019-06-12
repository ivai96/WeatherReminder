package fikt.pmp.weatherreminder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fikt.pmp.weatherreminder.DataModel.List;

public class WeatherDataSort {
    private ArrayList<List> mAllData;
    private ArrayList<Date> dates;
    private ArrayList<DateTime> dateTimes;

    public ArrayList<DateTime> getDateTimes() {
        return dateTimes;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    SimpleDateFormat sdf;
    public WeatherDataSort (ArrayList<List> allData){
        mAllData = allData;
        dates = new ArrayList<>();
        dateTimes = new ArrayList<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
    public void findAllDates(){
        Date dateHelper = new Date(); //pomosen objekt za data
        for(int i=0; i<mAllData.size(); i++) {
            DateTime dateTime = DateTime.parse(mAllData.get(i).getDt_txt(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            if (i == 0) {
                try {
                    //pri prviot item od celata lista na zapisi, zacuvaja prvata data vo helper i vnesija vo listata na dati
                    Date date = sdf.parse(dateTime.toString("yyyy-MM-dd"));
                    dateHelper = date;
                    dates.add(date);
                    dateTimes.add(dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    //za sekoj drug element od celata lista sporedi dali datumot na toj item e ponov od onoj vo helperot i ako e vnesi go
                    Date date = sdf.parse(dateTime.toString("yyyy-MM-dd"));
                    if (dateHelper.before(date)) {
                        dateHelper = date;
                        dates.add(date);
                        dateTimes.add(dateTime);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<List> getDayData(int dayNumber){
        ArrayList<List> list = new ArrayList<>();
        Date date = dates.get(dayNumber-1);
        for (List item : mAllData) {
            //zemi go prviot den
            DateTime dateTime = DateTime.parse(item.getDt_txt(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            try {
                Date itemDate = sdf.parse(dateTime.toString("yyyy-MM-dd"));
                //ako denot koj go barame e momentalniot vo foreach, dodaj go vo listata so ke ja vrajkas
                if (date.equals(itemDate))
                    list.add(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
