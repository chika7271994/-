package com.example.kakeibo.activites.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.item.IncomeListItems;

import java.util.ArrayList;

public class ListViewAdapter2 extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<IncomeListItems> list;

    public ListViewAdapter2(Context context) {

        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<IncomeListItems> list){
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
        return list.get(position).getIncome_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = layoutInflater.inflate(R.layout.item_list,parent,false);

        ((TextView)convertView.findViewById(R.id.category)).setText(list.get(position).getIncome_category());
        ((TextView)convertView.findViewById(R.id.price)).setText(String.valueOf(list.get(position).getIncome_price()));

        return convertView;
    }
}
