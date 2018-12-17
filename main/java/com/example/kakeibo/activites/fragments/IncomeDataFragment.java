package com.example.kakeibo.activites.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.database.DatabaseManager;
import com.example.kakeibo.activites.item.IncomeListItems;
import com.example.kakeibo.activites.listAdapter.IncomeListAdapter;
import com.example.kakeibo.activites.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeDataFragment extends BaseFragment {

    public static IncomeDataFragment newInstance(String month) {
        IncomeDataFragment fragment = new IncomeDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.total_sun)
    TextView textView;

    private DatabaseManager mDatabase; //データベースクラス
    private IncomeListAdapter mIncomeListAdapter;
    private List<IncomeListItems> list;
    private int i_id;
    private String text;
    private String month; //日付取得
    private String yy;
    private String mm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        month = bundle.getString("month");
        yy = month.substring(0, 4);
        mm = month.substring(5, 7);
        mDatabase = DatabaseManager.getInstance(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getIncomeList();
        showData();

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mIncomeListAdapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        IncomeListItems items = list.get(fromPos);
                        i_id = items.getIncome_id();
                        text = "品目 : " + items.getIncome_category() + "  金額 : " + items.getIncome_price();
                        incomeAlert();
                        list.remove(fromPos);
                        mIncomeListAdapter.notifyItemRemoved(fromPos);
                        LogUtil.debug("ItemTouchHelper", "スワイプしたデータ" + i_id);
                    }
                });
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void showData() {

        //支出合計データ呼び出し
        Cursor sCursor = mDatabase.sunIncomeData(yy, mm);
        StringBuilder incomeText = new StringBuilder();
        if (sCursor.moveToFirst()) {
            do {
                incomeText.append("合計金額 : " + sCursor.getInt(0));
            } while (sCursor.moveToNext());
        }
        textView.setText(incomeText);
    }

    private void getIncomeList() {
        mIncomeListAdapter = new IncomeListAdapter(retrieveData());
        recyclerView.setAdapter(mIncomeListAdapter);
    }

    private List<IncomeListItems> retrieveData() {
        list = new ArrayList<>();
        Cursor cursor = mDatabase.retrieveDataAllI(yy, mm);
        if (cursor.moveToFirst()) {
            do {
                IncomeListItems items = new IncomeListItems();
                items.setIncome_id(cursor.getInt(0));
                items.setIncome_category(cursor.getString(1));
                items.setIncome_price(cursor.getInt(2));
                //items.setImageUri(cursor.getString(6));

                list.add(items);
            } while (cursor.moveToNext());
        }
        return list;
    }

    private void incomeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("以下のデータを削除します");
        builder.setMessage(text + "を削除しますか？");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor delete = mDatabase.incomeData(i_id);
                delete.moveToFirst();
                showData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
