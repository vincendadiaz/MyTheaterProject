package com.sciters.mytheater.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.R;
import com.sciters.mytheater.helper.temporary.ImageHelper;
import com.sciters.mytheater.helper.utilities.ImageLoader;
import com.sciters.mytheater.helper.utilities.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Gilang on 9/30/13.
 * Edited by Vincenda on 1/17/13.
 */
public class ComingShowsListViewAdapter extends ArrayAdapter<Show> {
    Context context;
    int layoutResourceId;
    ArrayList<Show> data = new ArrayList<Show>();
    public ImageLoader imageLoader;

    public ComingShowsListViewAdapter(Context context, int layoutResourceId, ArrayList<Show> data)
    {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Random r = new Random();
        int randomPic = r.nextInt(4-1) + 1;

        View row;

        /*if (!GlobalEntities.CurrentComingShowViews.isEmpty())
        {
            if (position < GlobalEntities.CurrentComingShowViews.size())
            {
                row = GlobalEntities.CurrentComingShowViews.get(position);
            }
            else
            {
                row = convertView;
            }
        }
        else
        {
            row = convertView;
        }*/
        row = convertView;

        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();

            holder.showID = (TextView) row.findViewById(R.id.show_id);
            holder.showTitle = (TextView) row.findViewById(R.id.show_title);
            holder.showDate = (TextView) row.findViewById(R.id.show_date);
            holder.showTeamName= (TextView) row.findViewById(R.id.show_team_name);
            holder.showImage = (ImageView) row.findViewById(R.id.show_image);
            //holder.imageItem = (ImageView) row.findViewById(R.id.show_image);
            holder.showApplySchedule = (TextView) row.findViewById(R.id.show_apply_schedule);
            holder.showTopNotifLabel = (TextView) row.findViewById(R.id.show_top_notif_label);
            holder.showTopColor = (LinearLayout) row.findViewById(R.id.show_top_color);

            holder.showNightSchedule = (TextView) row.findViewById(R.id.show_night_schedule);
            holder.showDateDay = (TextView) row.findViewById(R.id.show_date_day);
            holder.showDateDayOfMonth = (TextView) row.findViewById(R.id.show_date_dayofmonth);
            holder.showDateMonth = (TextView) row.findViewById(R.id.show_date_month);
            holder.showRandomPerformerImage1 = (ImageView) row.findViewById(R.id.show_random_performer_image_1);
            holder.showRandomPerformerImage2 = (ImageView) row.findViewById(R.id.show_random_performer_image_2);
            holder.showRandomPerformerImage3 = (ImageView) row.findViewById(R.id.show_random_performer_image_3);
            holder.showRandomPerformerImage4 = (ImageView) row.findViewById(R.id.show_random_performer_image_4);
            holder.showRandomPerformerImage5 = (ImageView) row.findViewById(R.id.show_random_performer_image_5);

            holder.showRandomPerformerName1 = (TextView) row.findViewById(R.id.show_random_performer_name_1);
            holder.showRandomPerformerName2 = (TextView) row.findViewById(R.id.show_random_performer_name_2);
            holder.showRandomPerformerName3 = (TextView) row.findViewById(R.id.show_random_performer_name_3);
            holder.showRandomPerformerName4 = (TextView) row.findViewById(R.id.show_random_performer_name_4);
            holder.showRandomPerformerName5 = (TextView) row.findViewById(R.id.show_random_performer_name_5);

            holder.showLabelOFC = (TextView) row.findViewById(R.id.show_label_OFC);
            holder.showLabelFAR = (TextView) row.findViewById(R.id.show_label_FAR);
            holder.showLabelFEM = (TextView) row.findViewById(R.id.show_label_FEM);
            holder.showLabelGEN = (TextView) row.findViewById(R.id.show_label_GEN);

            holder.showLabelOFCinfo = (TextView) row.findViewById(R.id.show_label_OFC_info);
            holder.showLabelFARinfo = (TextView) row.findViewById(R.id.show_label_FAR_info);
            holder.showLabelFEMinfo = (TextView) row.findViewById(R.id.show_label_FEM_info);
            holder.showLabelGENinfo = (TextView) row.findViewById(R.id.show_label_GEN_info);

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
            boolean nightFlag = Integer.valueOf(nightShow.substring(0,2)) > 12;

            if (nightFlag)
            {
                holder.showNightSchedule.setText(nightShow + " (Show Malam)");
            }
            else
            {
                holder.showNightSchedule.setText(nightShow + " (Show Siang)");
            }

            String topText = Utils.formatNotificationDateText(date);
            holder.showTopNotifLabel.setText(topText);

            if(topText.equals("TODAY") || topText.equals("TONIGHT"))
            {
                holder.showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
            }
            else if (topText.equals("TOMORROW"))
            {
                holder.showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
            }
            else if (topText.equals("COMING SOON"))
            {
                holder.showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
            }
        }
        catch (ParseException e)
        {
            holder.showDate.setText("Terjadi Kesalahan: " + e.getMessage());
            e.printStackTrace();
        }

        try
        {
            Date now = new Date();

            Date ofcStart = df.parse(item.getShowOFCstart());
            Date ofcEnd = df.parse(item.getShowOFCend());
            Date femStart = df.parse(item.getShowFEMstart());
            Date femEnd = df.parse(item.getShowFEMend());
            Date farStart = df.parse(item.getShowFARstart());
            Date farEnd = df.parse(item.getShowFARend());
            Date genStart = df.parse(item.getShowGENstart());
            Date genEnd = df.parse(item.getShowGENend());

            if (now.before(ofcEnd))
            {
                if (now.after(ofcStart))
                {
                    holder.showLabelOFCinfo.setText("Apply sekarang");
                }
                else
                {
                    holder.showLabelOFCinfo.setText("Ingatkan saya");
                }
                holder.showLabelOFC.setBackgroundColor(Color.parseColor("#3498db"));
                holder.showLabelOFC.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelOFCinfo.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelOFCinfo.setBackgroundColor(Color.parseColor("#3498db"));
            }
            else
            {
                holder.showLabelOFCinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                holder.showLabelOFCinfo.setTextColor(Color.parseColor("#aaaaaa"));
                holder.showLabelOFCinfo.setText("Sudah lewat");
            }

            if (now.before(genEnd))
            {
                if (now.after(genStart))
                {
                    holder.showLabelGENinfo.setText("Apply sekarang");
                }
                else
                {
                    holder.showLabelGENinfo.setText("Ingatkan saya");
                }
                holder.showLabelGEN.setBackgroundColor(Color.parseColor("#2ecc71"));
                holder.showLabelGEN.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelGENinfo.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelGENinfo.setBackgroundColor(Color.parseColor("#2ecc71"));
            }
            else
            {
                holder.showLabelGENinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                holder.showLabelGENinfo.setTextColor(Color.parseColor("#aaaaaa"));
                holder.showLabelGENinfo.setText("Sudah lewat");
            }

            if (now.before(femEnd))
            {
                if (now.after(femStart))
                {
                    holder.showLabelFEMinfo.setText("Apply sekarang");
                }
                else
                {
                    holder.showLabelFEMinfo.setText("Ingatkan saya");
                }
                holder.showLabelFEM.setBackgroundColor(Color.parseColor("#e74c3c"));
                holder.showLabelFEM.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelFEMinfo.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelFEMinfo.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
            else
            {
                holder.showLabelFEMinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                holder.showLabelFEMinfo.setTextColor(Color.parseColor("#aaaaaa"));
                holder.showLabelFEMinfo.setText("Sudah lewat");
            }

            if (now.before(farEnd))
            {
                if (now.after(farStart))
                {
                    holder.showLabelFARinfo.setText("Apply sekarang");
                }
                else
                {
                    holder.showLabelFARinfo.setText("Ingatkan saya");
                }
                holder.showLabelFAR.setBackgroundColor(Color.parseColor("#f1c40f"));
                holder.showLabelFAR.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelFARinfo.setTextColor(Color.parseColor("#ffffff"));
                holder.showLabelFARinfo.setBackgroundColor(Color.parseColor("#f1c40f"));
            }
            else
            {
                holder.showLabelFARinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                holder.showLabelFARinfo.setTextColor(Color.parseColor("#aaaaaa"));
                holder.showLabelFARinfo.setText("Sudah lewat");
            }
        }
        catch (ParseException e)
        {
            holder.showApplySchedule.setText("Terjadi Kesalahan: " + e.getMessage());
            e.printStackTrace();
        }

        holder.showID.setText(String.valueOf(item.getID()));
        //holder.showTitle.setText(item.getShowTitle() + " (" + item.getShowTeamName() + ")");
        String judul = item.getShowTitle();
        if (judul.contains("K3")) {
            judul = judul.substring(0, judul.length()-9);
        } else {
            judul = judul.substring(0, judul.length()-8);
        }
        holder.showTitle.setText("\""+judul+"\"");
//        holder.showTitle.setText("\"" + item.getShowTitle()+ "\"");
        holder.showTeamName.setText(item.getShowTeamName());

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

        ArrayList<Member> randomPerformers = item.getRandomPerformers();

        Member member1 = randomPerformers.get(0);
        holder.showRandomPerformerName1.setText(member1.getMemberName());
        holder.showRandomPerformerImage1.setImageResource(GlobalEntities.ImageReferenceList.get(member1.getID()));
        //imageLoader.DisplayImage(member1.getMemberImagePath(),holder.showRandomPerformerImage1);
        Member member2 = randomPerformers.get(1);
        holder.showRandomPerformerName2.setText(member2.getMemberName());
        holder.showRandomPerformerImage2.setImageResource(GlobalEntities.ImageReferenceList.get(member2.getID()));
            //imageLoader.DisplayImage(member2.getMemberImagePath(),holder.showRandomPerformerImage2);
        Member member3 = randomPerformers.get(2);
        holder.showRandomPerformerName3.setText(member3.getMemberName());
        holder.showRandomPerformerImage3.setImageResource(GlobalEntities.ImageReferenceList.get(member3.getID()));
            //imageLoader.DisplayImage(member3.getMemberImagePath(),holder.showRandomPerformerImage3);
        Member member4 = randomPerformers.get(3);
        holder.showRandomPerformerName4.setText(member4.getMemberName());
        holder.showRandomPerformerImage4.setImageResource(GlobalEntities.ImageReferenceList.get(member4.getID()));
            //imageLoader.DisplayImage(member4.getMemberImagePath(),holder.showRandomPerformerImage4);
        Member member5 = randomPerformers.get(4);
        holder.showRandomPerformerName5.setText(member5.getMemberName());
        holder.showRandomPerformerImage5.setImageResource(GlobalEntities.ImageReferenceList.get(member5.getID()));
            //imageLoader.DisplayImage(member5.getMemberImagePath(),holder.showRandomPerformerImage5);

        return row;
    }

    public static class RecordHolder {
        int holderPosition;
        TextView showID;
        TextView showTitle;
        TextView showDate;
        TextView showTeamName;
        ImageView showImage;
        TextView showApplySchedule;
        TextView showNightSchedule;
        TextView showDateDay;
        TextView showDateDayOfMonth;
        TextView showDateMonth;
        ImageView showRandomPerformerImage1;
        ImageView showRandomPerformerImage2;
        ImageView showRandomPerformerImage3;
        ImageView showRandomPerformerImage4;
        ImageView showRandomPerformerImage5;
        TextView showRandomPerformerName1;
        TextView showRandomPerformerName2;
        TextView showRandomPerformerName3;
        TextView showRandomPerformerName4;
        TextView showRandomPerformerName5;
        TextView showLabelOFC;
        TextView showLabelFAR;
        TextView showLabelFEM;
        TextView showLabelGEN;
        LinearLayout showTopColor;
        TextView showTopNotifLabel;
        TextView showLabelOFCinfo;
        TextView showLabelFARinfo;
        TextView showLabelFEMinfo;
        TextView showLabelGENinfo;
    }
}
