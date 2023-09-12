package com.hui.tally.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.application.R;
import com.hui.tally.SearchActivity;
import com.hui.tally.TodoListActivity;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.TodoBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoDialog extends Dialog implements View.OnClickListener {
    EditText nameEt, moneyEt;
    Button cancelBtn, ensureBtn;
    TextView timeEt;
    TodoBean todobean;
    OnEnsureListener onEnsureListener;

    // 设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    public TodoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_todo);
        nameEt = findViewById(R.id.dialog_todo_name_et);
        moneyEt = findViewById(R.id.dialog_todo_money_et);
        timeEt = findViewById(R.id.dialog_todo_time_et);
        cancelBtn = findViewById(R.id.dialog_todo_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_todo_btn_ensure);
        nameEt.setOnClickListener(this);
        moneyEt.setOnClickListener(this);
        timeEt.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
        setInitTime();
    }

    private void setInitTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todobean = new TodoBean();
        todobean.setYear(year);
        todobean.setMonth(month);
        todobean.setDay(day);
        timeEt.setText(year + "年" + month + "月" + day + "日");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_todo_btn_cancel) {
            cancel();
        } else if (v.getId() == R.id.dialog_todo_btn_ensure) {
            if (nameEt.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "待办名称不能为空！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                todobean.setTodoName(nameEt.getText().toString());
            }
            if (Float.parseFloat(moneyEt.getText().toString()) <= 0) {
                Toast.makeText(getContext(), "代办金额必须大于0", Toast.LENGTH_SHORT).show();
                return;
            } else {
                todobean.setMoney(Float.parseFloat(moneyEt.getText().toString()));
            }
            DBManager.insertItemToTodotb(todobean);
            cancel();
            if (onEnsureListener != null) {
                //将备注传给实现TodoDialog实例的类的函数
                onEnsureListener.onEnsure();
            }
        } else if (v.getId() == R.id.dialog_todo_name_et) {

        } else if (v.getId() == R.id.dialog_todo_money_et) {

        } else if (v.getId() == R.id.dialog_todo_time_et) {
            SelectTimeDialog dialog = new SelectTimeDialog(getContext());
            dialog.show();
            //设定确定按钮被点击了的监听器
            dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
                @Override
                public void onEnsure(String time, int year, int month, int day) {
                    timeEt.setText(year + "年" + month + "月" + day + "日");
                    todobean.setYear(year);
                    todobean.setMonth(month);
                    todobean.setDay(day);
                }
            });
        }
    }


}
