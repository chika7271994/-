package com.example.kakeibo.activites.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.kakeibo.R;
import com.example.kakeibo.activites.item.SpendingListItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<SpendingListItems> list;

    public ListViewAdapter(Context context) {

        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<SpendingListItems> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getSpending_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(R.layout.item_list,parent,false);

        ((TextView)convertView.findViewById(R.id.spending_category)).setText(list.get(position).getSpending_category());
        ((TextView)convertView.findViewById(R.id.spending_price)).setText(String.valueOf(list.get(position).getSpending_price()));

        return convertView;
    }
}
