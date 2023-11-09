package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.classification.Trash;

import java.util.List;

public class TrashAdapter extends BaseAdapter {
    private Context context;
    private List<Trash> trashList;

    public TrashAdapter(Context context, List<Trash> trashList){
        this.context = context;
        this.trashList = trashList;
    }

    @Override
    public int getCount() {
        return trashList != null ? trashList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_trash, parent, false);

        TextView textName = rootView.findViewById(R.id.name);

        textName.setText(trashList.get(position).getName());

        return rootView;
    }
}
