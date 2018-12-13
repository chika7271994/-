package com.example.kakeibo.activites.listAdapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.interfaces.OnRecyclerListener;
import com.example.kakeibo.activites.item.SpendingListItems;
import com.example.kakeibo.activites.utils.BitmapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//public class SpendingListAdapter extends BaseAdapter {
public class SpendingListAdapter extends RecyclerView.Adapter<SpendingListAdapter.ViewHolder> {

    private List<SpendingListItems> mSpendingList;
    private static View.OnClickListener listener;
    //private OnRecyclerListener onRecyclerListener;

    public SpendingListAdapter(List<SpendingListItems> spendingList) {
        mSpendingList = spendingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SpendingListItems items = mSpendingList.get(position);

        //holder.itemPosition.getSpending_id();
        holder.mCategory.setText(items.getSpending_category());
        String price = String.format("¥%d", items.getSpending_price());
        holder.mPrice.setText(price);

        String imageUri = items.getImageUri();
        if (TextUtils.isEmpty(imageUri)){
            holder.mImage.setImageResource(android.R.drawable.ic_menu_gallery);
        } else {
            BitmapUtil.loadBitmap(imageUri, holder.mImage);
        }

        /*holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });*/
    }

    /*public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }*/

    @Override
    public int getItemCount() { return mSpendingList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView mCategory;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.list_image)
        ImageView mImage;
        @BindView(R.id.item_list_layout)
        LinearLayout layout;

        //int position = getAdapterPosition();
        //SpendingListItems itemPosition = mSpendingList.get(position);

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

    /*Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<SpendingListItems> list;

    public SpendingListAdapter(Context context) {

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

        ((TextView)convertView.findViewById(R.id.category)).setText(list.get(position).getSpending_category());
        ((TextView)convertView.findViewById(R.id.price)).setText(String.valueOf(list.get(position).getSpending_price()));

        return convertView;
    }*/
