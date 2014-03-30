package com.sciters.mytheater.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sciters.mytheater.R;

/**
 * Created by Vincenda Diaz on 1/27/14.
 */
public class SetListSongsAdapter extends ArrayAdapter<String> {
    Context context;
    String[] values;

    public SetListSongsAdapter(Context context, String[] values) {
        super(context, R.layout.listitem_setlistsong, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RecordHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listitem_setlistsong, parent, false);
        holder.judulLagu = (TextView) rowView.findViewById(R.id.judullagua);

        return rowView;
    }

    static class RecordHolder {
        TextView judulLagu;
    }

}
