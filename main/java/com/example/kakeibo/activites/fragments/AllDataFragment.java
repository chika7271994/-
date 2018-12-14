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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.database.DatabaseManager;
import com.example.kakeibo.activites.interfaces.OnRecyclerListener;
import com.example.kakeibo.activites.item.IncomeListItems;
import com.example.kakeibo.activites.item.SpendingListItems;
import com.example.kakeibo.activites.listAdapter.SpendingListAdapter;
import com.example.kakeibo.activites.listAdapter.ListViewAdapter2;
import com.example.kakeibo.activites.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

//ひと月ごとの全データ出力

public class AllDataFragment extends BaseFragment {

    public static AllDataFragment newInstance(String month) {
        AllDataFragment fragment = new AllDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    //@BindView(R.id.total_spending)
    //TextView spending_sun_text;
    //@BindView(R.id.alldata_text_sun_income)
    //TextView income_sun_text;
    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;
    /*@BindView(R.id.list_item)
    ListView listView;
    @BindView(R.id.list_item_income)
    ListView listView2;*/

    private DatabaseManager mDatabase; //データベースクラス
    private SpendingListAdapter mSpendingListAdapter;
    private List<SpendingListItems> list;
    private String month; //日付取得
    private String yy;
    private String mm;
//    private List<SpendingListItems> objects;  //Spending
//    private List<IncomeListItems> objects2;   //Income
//    private SpendingListAdapter adapter;          //Spending
//    private ListViewAdapter2 adapter2;        //Income


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

        //showData();
        //showIncomeData();
        getSpendingList();
        //getIncomeList();

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mSpendingListAdapter.notifyItemMoved(fromPos, toPos);
                        return true;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        SpendingListItems items = list.get(fromPos);
                        final int s_id = items.getSpending_id();
                        mDatabase.spendingData(s_id);
                        list.remove(fromPos);
                        mSpendingListAdapter.notifyItemRemoved(fromPos);
                        LogUtil.debug("ItemTouchHelper", "スワイプしたデータ" + s_id);
                    }
                });
        touchHelper.attachToRecyclerView(recyclerView);
        }

    /*private void showData() {

        //支出合計データ呼び出し
        Cursor sCursor = mDatabase.sunSpendingData(yy, mm);
        StringBuilder spendingText = new StringBuilder();
        if (sCursor.moveToFirst()) {
            do {
                spendingText.append(sCursor.getInt(0) + "\n");
            } while (sCursor.moveToNext());
        }
        spending_sun_text.setText(spendingText);
    }*/

    private void showIncomeData() {

        //収入合計データ呼び出し
        Cursor c = mDatabase.sunIncomeData(yy, mm);
        StringBuilder stringBuilder = new StringBuilder();
        if (c.moveToFirst()) {
            do {
                stringBuilder.append(c.getInt(0) + "\n");
            } while (c.moveToNext());
        }
        //income_sun_text.setText(stringBuilder);
    }

    private void getSpendingList() {
        mSpendingListAdapter = new SpendingListAdapter(retrieveData());
        recyclerView.setAdapter(mSpendingListAdapter);
    }

    private List<SpendingListItems> retrieveData() {
        list = new ArrayList<>();
        Cursor cursor = mDatabase.retrieveDataAll(yy, mm);
        if (cursor.moveToFirst()) {
            do {
                SpendingListItems items = new SpendingListItems();
                items.setSpending_id(cursor.getInt(0));
                items.setSpending_category(cursor.getString(1));
                items.setSpending_price(cursor.getInt(2));
                items.setImageUri(cursor.getString(6));

                list.add(items);
            } while (cursor.moveToNext());
        }
        return list;
    }
}

        /*objects = new ArrayList<SpendingListItems>();
        Cursor listCursor = mDatabase.retrieveDataAll(yy, mm);
        if (listCursor.moveToFirst()){
            do {
                SpendingListItems items = new SpendingListItems();

                StringBuilder id = new StringBuilder();
                id.append(listCursor.getString(listCursor.getColumnIndex("s_id")));
                int s_id = Integer.valueOf(String.valueOf(id));

                StringBuilder category = new StringBuilder();
                category.append("品目 : " + listCursor.getString(listCursor.getColumnIndex("s_category")) + "  金額 : ");
                String s_category = new String(category);

                StringBuilder price = new StringBuilder();
                price.append(listCursor.getString(listCursor.getColumnIndex("s_price")));
                int s_price = Integer.valueOf(String.valueOf(price));

                StringBuilder image = new StringBuilder();
                image.append(listCursor.getString(listCursor.getColumnIndex("location")));
                String location = new String();

                items.setSpending_id(s_id);
                items.setSpending_category(s_category);
                items.setSpending_price(s_price);


                objects.add(items);
            }while (listCursor.moveToNext());
        }
        adapter = new SpendingListAdapter(getContext());
        ((SpendingListAdapter) adapter).setList((ArrayList<SpendingListItems>) objects);
        listView.setAdapter(adapter);*/
    //}

    /*private void getIncomeList(){
        objects2 = new ArrayList<IncomeListItems>();
        Cursor listCursor = mDatabase.retrieveDataAllI(yy, mm);
        if (listCursor.moveToFirst()){
            do {
                IncomeListItems items = new IncomeListItems();

                StringBuilder id = new StringBuilder();
                id.append(listCursor.getString(listCursor.getColumnIndex("i_id")));
                int i_id = Integer.valueOf(String.valueOf(id));

                StringBuilder category = new StringBuilder();
                category.append("品目 : " + listCursor.getString(listCursor.getColumnIndex("i_category")) + "  金額 : ");
                String i_category = new String(category);

                StringBuilder price = new StringBuilder();
                price.append(listCursor.getString(listCursor.getColumnIndex("i_price")));
                int i_price = Integer.valueOf(String.valueOf(price));

                items.setIncome_id(i_id);
                items.setIncome_category(i_category);
                items.setIncome_price(i_price);

                objects2.add(items);
            }while (listCursor.moveToNext());
        }
        adapter2 = new ListViewAdapter2(getContext());
        ((ListViewAdapter2) adapter2).setList((ArrayList<IncomeListItems>) objects2);
        listView2.setAdapter(adapter2);
    }*/

    /*public void onRecyclerClicked(final AdapterView<?> parent, final View view, final int position, final long id) {
        SpendingListItems str = (SpendingListItems) parent.getItemAtPosition(position);
        final int s_id = str.getSpending_id();
        String text = str.getSpending_category() + str.getSpending_price();
        LogUtil.debug("List", "itemは" + str);
        LogUtil.debug("List", "getItemは" + text);
        LogUtil.debug("List", "itemIdは" + s_id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("以下のデータを削除します");
        builder.setMessage(text + "を削除しますか？");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor delete = mDatabase.spendingData(s_id);
                delete.moveToFirst();
                list.remove(position);
                mSpendingListAdapter.notifyDataSetChanged();

                //支出合計データ更新
                //showData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}*/

    /*@OnItemClick(R.id.list_item_income)
    public void onListItemClick2(final AdapterView<?> parent, View view, final int position, long id) {
        IncomeListItems str = (IncomeListItems) parent.getItemAtPosition(position);
        final int i_id = str.getIncome_id();
        String text = str.getIncome_category() + str.getIncome_price();
        LogUtil.debug("incomeList", "itemは"+ str);
        LogUtil.debug("incomeList", "getItemは"+ text);
        LogUtil.debug("incomeList", "itemIdは"+ i_id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("以下のデータを削除します");
        builder.setMessage(text + "を削除しますか？");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor delete = mDatabase.incomeData(i_id);
                delete.moveToFirst();
                objects2.remove(position);
                adapter2.notifyDataSetChanged();

                //収入合計データ更新
                showIncomeData();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    //CalendarFragmentページに戻る
    /*@OnClick(R.id.alldata_back)
    void btnBackClick(){
        navigateToFragment(CalendarFragment.newInstance());
    }*/

    //ImageFragmentページ
    //@OnClick(R.id.image_button)
    //void btnImageClick() { navigateToFragment(ImageFragment.newInstance()); }

//}
