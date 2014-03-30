package com.sciters.mytheater.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sciters.mytheater.R;
import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.MemberSetList;

import java.util.ArrayList;

/**
 * Created by Gilang on 11/9/13.
 */
public class MemberSetListListViewAdapter extends ArrayAdapter<MemberSetList> {

    Context context;
    int layoutResourceId;
    ArrayList<MemberSetList> data = new ArrayList<MemberSetList>();

    public MemberSetListListViewAdapter(Context context, int layoutResourceId, ArrayList<MemberSetList> data)
    {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.memberSetListID = (TextView) row.findViewById(R.id.member_setlist_id);
            holder.memberSetListName = (TextView) row.findViewById(R.id.member_setlist_name);
            holder.memberSetListInfo = (TextView) row.findViewById(R.id.member_setlist_info);
            holder.memberSetListImage = (ImageView) row.findViewById(R.id.member_setlist_image);

            row.setTag(holder);
        }
        else {
            holder = (RecordHolder) row.getTag();
        }
        MemberSetList item = data.get(position);
        holder.memberSetListID.setText(Integer.toString(item.getSetList().getID()));
        String judul = item.getSetList().getName();
//        if (judul.contains("K3")) {
//            judul = judul.substring(0,judul.length() - 9);
//        } else if (judul.contains("J")) {
//            judul = judul.substring(0,judul.length() - 8);
//        }
        holder.memberSetListName.setText(item.getSetList().getName());
//        holder.memberSetListName.setText(judul);
        holder.memberSetListInfo.setText("Perform: " + item.getPerformCount() + " kali & " + item.getBdCount() + " BD | Rating: " + item.getAvgRating());
        if (judul.contains("Matahari")) {
            holder.memberSetListImage.setImageResource(R.drawable.mataharimilikku);
        } else if (judul.contains("Demi")) {
            holder.memberSetListImage.setImageResource(R.drawable.demiseseorang);
        } else if (judul.contains("Aturan")) {
            holder.memberSetListImage.setImageResource(R.drawable.aturanarticinta);
        } else if (judul.contains("Pajama")) {
            holder.memberSetListImage.setImageResource(R.drawable.pajamadrive);
        }
        //holder.memberSetListImage.setImageResource(GlobalEntities.ImageReferenceList.get(item.getSetList().getID()));

        return row;

    }

    static class RecordHolder {
        TextView memberSetListID;
        TextView memberSetListName;
        TextView memberSetListInfo;

        TextView memberSetListAvgRating;
        TextView memberSetListPerformCount;
        TextView memberSetListBDCount;
        ImageView memberSetListImage;
    }
}
