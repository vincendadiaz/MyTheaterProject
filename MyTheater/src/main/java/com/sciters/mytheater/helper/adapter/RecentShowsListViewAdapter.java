package com.sciters.mytheater.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sciters.mytheater.R;
import com.sciters.mytheater.entities.Show;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Gilang on 10/1/13.
 * Edited by Vincenda on 18/1/14.
 */
public class RecentShowsListViewAdapter extends ArrayAdapter<Show> {
    Context context;
    int layoutResourceId;
    ArrayList<Show> data = new ArrayList<Show>();

    public RecentShowsListViewAdapter(Context context, int layoutResourceId, ArrayList<Show> data)
    {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount(){
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Random r = new Random();
        int randomPic = r.nextInt(4-1) + 1;

        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            //holder.showID = (TextView) row.findViewById(R.id.show_id);
            holder.showTitle = (TextView) row.findViewById(R.id.show_title);
            holder.showNightSchedule = (TextView) row.findViewById(R.id.show_night_schedule);
            holder.showDate = (TextView) row.findViewById(R.id.show_date);
            holder.showDateDay = (TextView) row.findViewById(R.id.show_date_day);
            holder.showDateDayOfMonth = (TextView) row.findViewById(R.id.show_date_dayofmonth);
            holder.showDateMonth = (TextView) row.findViewById(R.id.show_date_month);
            holder.showTeamName= (TextView) row.findViewById(R.id.show_team_name);
            holder.showImage = (ImageView) row.findViewById(R.id.show_image);
            holder.showRating = (TextView) row.findViewById(R.id.show_rating);
            holder.showAudienceCount = (TextView) row.findViewById(R.id.show_audience_count);
            holder.showReview = (TextView) row.findViewById(R.id.show_review);
            row.setTag(holder);
        }
        else {
            holder = (RecordHolder) row.getTag();
        }
        Show item = data.get(position);

        Locale indonesia = new Locale("in", "ID");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat targetdf = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", indonesia);
        DateFormat daydf = new SimpleDateFormat("EEEE", indonesia);
        DateFormat datedf = new SimpleDateFormat("d", indonesia);
        DateFormat monthdf = new SimpleDateFormat("MMMM", indonesia);
        DateFormat nightshowdf = new SimpleDateFormat("HH:mm", indonesia);
        Date date;

        try
        {
            date = df.parse(item.getShowDate());
            holder.showDate.setText(targetdf.format(date));
            holder.showDateDay.setText(daydf.format(date));
            holder.showDateDayOfMonth.setText(datedf.format(date));
            holder.showDateMonth.setText(monthdf.format(date));
            String nightShow = nightshowdf.format(date);
            boolean nightFlag = Integer.valueOf(nightShow.substring(0,2)) > 15;

            if (nightFlag)
            {
                holder.showNightSchedule.setText(nightShow + " (Show Malam)");
            }
            else
            {
                holder.showNightSchedule.setText(nightShow + " (Show Siang)");
            }
        }
        catch (ParseException e)
        {
            holder.showDate.setText("Terjadi Kesalahan: " + e.getMessage());
            e.printStackTrace();
        }

        //holder.showID.setText(item.getID());
        String judul = item.getShowTitle();
        if (judul.contains("K3")) {
            judul = judul.substring(0, judul.length()-9);
        } else {
            judul = judul.substring(0, judul.length()-8);
        }
        holder.showTitle.setText("\""+judul+"\"");
        //holder.showTitle.setText("\""+item.getShowTitle()+"\"");

        holder.showTeamName.setText(item.getShowTeamName());
        holder.showRating.setText("Rating: " + item.getShowRating());
        holder.showAudienceCount.setText("Jumlah Penonton: " + item.getShowAudienceCount());
        holder.showReview.setText("Jumlah Review: " + item.getShowReviews());

        if (item.getShowTitle().contains("Aturan"))
        {
            switch(randomPic)
            {
                case 1:
                    holder.showImage.setImageResource(R.drawable.rkjmain);
                    break;
                case 2:
                    holder.showImage.setImageResource(R.drawable.rkjsquall);
                    break;
                case 3:
                    holder.showImage.setImageResource(R.drawable.rkj2);
                    break;
            }
            //holder.showImage.setImageResource(R.drawable.rkj2);
        }
        else if (item.getShowTitle().contains("Matahari"))
        {
            switch(randomPic)
            {
                case 1:
                    holder.showImage.setImageResource(R.drawable.bnt2);
                    break;
                case 2:
                    holder.showImage.setImageResource(R.drawable.bntyupi);
                    break;
                case 3:
                    holder.showImage.setImageResource(R.drawable.bntshow);
                    break;
            }
            //holder.showImage.setImageResource(R.drawable.bntshow);
        }
        else if (item.getShowTitle().contains("Pajama"))
        {
            switch(randomPic)
            {
                case 1:
                    holder.showImage.setImageResource(R.drawable.pajadora);
                    break;
                case 2:
                    holder.showImage.setImageResource(R.drawable.pajadorafaces);
                    break;
                case 3:
                    holder.showImage.setImageResource(R.drawable.ghaida);
                    break;
            }
            //holder.showImage.setImageResource(R.drawable.pajadora);
        }
        else if (item.getShowTitle().contains("Demi")) {
            switch (randomPic) {
                case 1:
                    holder.showImage.setImageResource(R.drawable.demi1);
                    break;
                case 2:
                    holder.showImage.setImageResource(R.drawable.demi2);
                    break;
                case 3:
                    holder.showImage.setImageResource(R.drawable.demi3);
                    break;
            }
        } else {
            holder.showImage.setImageResource(R.drawable.rkj2);
        }

        return row;
    }

    static class RecordHolder {
        TextView showID;
        TextView showTitle;
        TextView showDate;
        TextView showNightSchedule;
        TextView showDateDay;
        TextView showDateDayOfMonth;
        TextView showDateMonth;
        TextView showTeamName;
        TextView showRating;
        ImageView showImage;
        TextView showAudienceCount;
        TextView showReview;
    }
}

