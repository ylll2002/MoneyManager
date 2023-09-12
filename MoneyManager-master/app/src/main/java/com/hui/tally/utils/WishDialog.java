package com.hui.tally.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.application.R;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.WishBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class WishDialog extends Dialog implements View.OnClickListener{
    OnEnsureListener onEnsureListener;
    Button cancelBtn, ensureBtn;
    EditText nameEt,tolmoneyEt,curmoneyEt,permoneyEt,timeEt;
    TextView timeTv;
    Spinner sp_cycle;
    WishBean wishbean;
    // 设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }
    public WishDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wish);
        cancelBtn = findViewById(R.id.dialog_wish_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_wish_btn_ensure);
        nameEt=findViewById(R.id.dialog_wish_name_et);
        tolmoneyEt=findViewById(R.id.dialog_wish_total_money_et);
        curmoneyEt=findViewById(R.id.dialog_wish_money_et);
        permoneyEt=findViewById(R.id.dialog_wish_per_money_et);
        timeEt=findViewById(R.id.dialog_wish_time_et);
        sp_cycle=findViewById(R.id.sp_cycle);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
        wishbean = new WishBean();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_wish_btn_cancel) {
            cancel();
        } else if (v.getId() == R.id.dialog_wish_btn_ensure){
            if (nameEt.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "待办名称不能为空！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                wishbean.setWishName(nameEt.getText().toString());
            }
            if (Float.parseFloat(tolmoneyEt.getText().toString()) <= 0) {
                Toast.makeText(getContext(), "目标金额必须大于0", Toast.LENGTH_SHORT).show();
                return;
            } else {
                wishbean.setTotalMoney(Float.parseFloat(tolmoneyEt.getText().toString()));
            }
            if (Float.parseFloat(curmoneyEt.getText().toString()) < 0) {
                Toast.makeText(getContext(), "已攒金额必须大于等于0", Toast.LENGTH_SHORT).show();
                return;
            } else if (Float.parseFloat(curmoneyEt.getText().toString()) > 0) {
                wishbean.setCurMoney(Float.parseFloat(curmoneyEt.getText().toString()));
            }
            if (Float.parseFloat(permoneyEt.getText().toString()) <= 0) {
                Toast.makeText(getContext(), "单次存入金额必须大于0", Toast.LENGTH_SHORT).show();
                return;
            } else {
                wishbean.setPerMoney(Float.parseFloat(permoneyEt.getText().toString()));
            }
            //编辑下次提醒的日期以及每轮存储的日期
            Date date=new Date();
            GregorianCalendar gc =new GregorianCalendar();
            gc.setTime(date);
            if(sp_cycle.getSelectedItem().toString().equals("年")){
                wishbean.setCycleDay(365*Integer.parseInt(timeEt.getText().toString()));
                gc.add(1,+wishbean.getCycleDay()/365);
            } else if (sp_cycle.getSelectedItem().toString().equals("月")) {
                wishbean.setCycleDay(30*Integer.parseInt(timeEt.getText().toString()));
                gc.add(2,+wishbean.getCycleDay()/30);
            }else {
                wishbean.setCycleDay(7*Integer.parseInt(timeEt.getText().toString()));
                gc.add(3,+wishbean.getCycleDay()/7);
            }
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            wishbean.setDate(sdf.format(gc.getTime()));
            DBManager.insertItemToWishtb(wishbean);
            cancel();
            if (onEnsureListener != null) {
                //将备注传给实现WishDialog实例的类的函数
                onEnsureListener.onEnsure();
            }
        }
    }
}
