package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.application.R;
import com.hui.tally.adapter.WishAdapter;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.WishBean;
import com.hui.tally.utils.WishDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class WishActivity extends AppCompatActivity {
    ListView wishLv;
    List<WishBean> mDatas;
    WishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        wishLv = findViewById(R.id.wish_lv);
        mDatas = new ArrayList<>();
        // 设置适配器
        adapter = new WishAdapter(this,mDatas);
        wishLv.setAdapter(adapter);
        loadData();
        setLVClickListener();
        adapter.setBottomBtnOnclick(new WishAdapter.BottomBtnOnclick() {
            @Override
            public void click(View v, int index) {
                if (v.getId()==R.id.wish_iv_done){
                    WishBean wishbean = mDatas.get((Integer) v.getTag());
                        if(wishbean.getDone()==0){
                            float curMoney=wishbean.getCurMoney()+wishbean.getPerMoney();
                            String str = wishbean.getDate();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
                            sdf1.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                            Date date = null;
                            try {
                                date = sdf1.parse(str);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            GregorianCalendar gc =new GregorianCalendar();
                            gc.setTime(date);
                            if(wishbean.getCycleDay()/365>0){
                                gc.add(1,+wishbean.getCycleDay()/365);
                            } else if (wishbean.getCycleDay()/30>0) {
                                gc.add(2,+wishbean.getCycleDay()/30);
                            }else if (wishbean.getCycleDay()/7>0) {
                                gc.add(3,+wishbean.getCycleDay()/7);
                            }
                            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
                            wishbean.setDate(sdf1.format(gc.getTime()));
                            DBManager.wishDone(wishbean.getId(),curMoney,1,sdf1.format(gc.getTime()));
                            //删除已经完成的储蓄心愿
                            if((wishbean.getCurMoney()+wishbean.getPerMoney())>=wishbean.getTotalMoney()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(WishActivity.this);
                                builder.setTitle("提示信息").setMessage("您的待办事项："+wishbean.getWishName()+"已完成！")
                                        .setPositiveButton("好耶！", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                int click_id = wishbean.getId();
                                                //执行删除的操作
                                                DBManager.deleteItemFromWishtbById(click_id);
                                                adapter.notifyDataSetChanged();
                                                loadData();
                                            }
                                        });
                                builder.create().show();   //显示对话框
                            }
                        } else if (wishbean.getDone()==1) {
                            float curMoney=wishbean.getCurMoney()-wishbean.getPerMoney();
                            String str = wishbean.getDate();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
                            sdf1.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                            Date date = null;
                            try {
                                date = sdf1.parse(str);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            GregorianCalendar gc =new GregorianCalendar();
                            gc.setTime(date);
                            if(wishbean.getCycleDay()/365>0){
                                gc.add(1,-wishbean.getCycleDay()/365);
                            } else if (wishbean.getCycleDay()/30>0) {
                                gc.add(2,-wishbean.getCycleDay()/30);
                            }else if (wishbean.getCycleDay()/7>0) {
                                gc.add(3,-wishbean.getCycleDay()/7);
                            }
                            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
                            wishbean.setDate(sdf1.format(gc.getTime()));
                            DBManager.wishDone(wishbean.getId(),curMoney,0,sdf1.format(gc.getTime()));
                        }
                    adapter.notifyDataSetChanged();
                    loadData();

                }
            }
        });
    }
    private void setLVClickListener() {
        wishLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                WishBean wishbean = mDatas.get(position);
                deleteItem(wishbean);
                return false;
            }
        });
    }
    private void deleteItem(final WishBean wishbean) {
        final int delId = wishbean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteItemFromWishtbById(delId);
                        mDatas.remove(wishbean);   //实时刷新，从数据源删除
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }
    private void loadData() {
        List<WishBean> list = DBManager.getAccountListFromWishtb();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        if (view.getId()==R.id.wish_iv_back) {
            finish();
        } else if (view.getId()==R.id.wish_iv_new) {
            WishDialog dialog = new WishDialog(this);
            dialog.show();
            dialog.setOnEnsureListener(new WishDialog.OnEnsureListener() {
                @Override
                public void onEnsure() {
                    loadData();
                }
            });
        }
    }

}