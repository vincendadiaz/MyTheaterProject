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
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.utilities.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Gilang on 11/6/13.
 */
public class MembersListViewAdapter extends ArrayAdapter<Member>{
    Context context;
    int layoutResourceId;
    ArrayList<Member> data = new ArrayList<Member>();
    public ImageLoader imageLoader;

    public MembersListViewAdapter(Context context, int layoutResourceId, ArrayList<Member> data)
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
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.memberID = (TextView) row.findViewById(R.id.member_id);
            holder.memberName = (TextView) row.findViewById(R.id.member_name);
            holder.memberTeamName = (TextView) row.findViewById(R.id.member_teamname);
            holder.memberTwitter = (TextView) row.findViewById(R.id.member_twitter);
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
        //holder.memberTwitter.setText(item.getMemberTeamNameDisplay() + " | @" + item.getMemberTwitter());
        holder.memberTeamName.setText(item.getMemberTeamNameDisplay());

        return row;

    }

    static class RecordHolder {
        TextView memberID;
        TextView memberName;
        TextView memberTeamName;
        TextView memberTwitter;
        ImageView memberImage;
    }
}
