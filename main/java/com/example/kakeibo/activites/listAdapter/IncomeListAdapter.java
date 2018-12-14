package com.example.kakeibo.activites.listAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.item.IncomeListItems;
import com.example.kakeibo.activites.utils.BitmapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.ViewHolder> {

    private List<IncomeListItems> mIncomeList;

    public IncomeListAdapter(List<IncomeListItems> incomeList) {
        mIncomeList = incomeList;
    }

    @NonNull
    @Override
    public IncomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_list, parent, false);
        IncomeListAdapter.ViewHolder viewHolder = new IncomeListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeListAdapter.ViewHolder holder, int position) {
        final IncomeListItems items = mIncomeList.get(position);

        holder.mCategory.setText(items.getIncome_category());
        String price = String.format("¥%d", items.getIncome_price());
        holder.mPrice.setText(price);

        String imageUri = items.getImageUri();
        if (TextUtils.isEmpty(imageUri)){
            holder.mImage.setImageResource(android.R.drawable.ic_menu_gallery);
        } else {
            BitmapUtil.loadBitmap(imageUri, holder.mImage);
        }
    }

    @Override
    public int getItemCount() { return mIncomeList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView mCategory;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.list_image)
        ImageView mImage;
        //@BindView(R.id.item_list_layout)
        //LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
