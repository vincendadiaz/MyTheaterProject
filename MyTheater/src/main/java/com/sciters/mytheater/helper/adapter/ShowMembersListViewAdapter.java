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
import com.sciters.mytheater.entities.Member;

import java.util.ArrayList;

/**
 * Created by Gilang on 10/1/13.
 */
public class ShowMembersListViewAdapter extends ArrayAdapter<Member> {

    Context context;
    int layoutResourceId;
    ArrayList<Member> data = new ArrayList<Member>();

    public ShowMembersListViewAdapter(Context context, int layoutResourceId, ArrayList<Member> data)
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
            holder.memberID = (TextView) row.findViewById(R.id.member_id);
            holder.memberName = (TextView) row.findViewById(R.id.member_name);
            holder.memberShowUnitSong = (TextView) row.findViewById(R.id.member_show_unit_song);
            //holder.memberTeamName= (TextView) row.findViewById(R.id.member_team_name);
            holder.memberRating= (TextView) row.findViewById(R.id.member_rating);
            holder.memberImage = (ImageView) row.findViewById(R.id.member_image);

            row.setTag(holder);
        }
        else {
            holder = (RecordHolder) row.getTag();
        }
        Member item = data.get(position);
        holder.memberID.setText(Integer.toString(item.getID()));
        holder.memberName.setText(item.getMemberName());
        holder.memberImage.setImageResource(GlobalEntities.ImageReferenceList.get(item.getID()));

        if (!item.getMemberShowUnitSong().equals("null"))
        {
            holder.memberShowUnitSong.setText(item.getMemberShowUnitSong());
            holder.memberRating.setText(item.getMemberRating());

        }
        else
        {
            holder.memberShowUnitSong.setText("@" + item.getMemberTwitter());
            holder.memberRating.setVisibility(View.INVISIBLE);
        }

        //holder.memberTeamName.setText(item.getMemberTeamName());
        return row;
    }

    static class RecordHolder {
        TextView memberID;
        TextView memberName;
        TextView memberShowUnitSong;
        TextView memberTeamName;
        TextView memberRating;
        ImageView memberImage;
    }
}
