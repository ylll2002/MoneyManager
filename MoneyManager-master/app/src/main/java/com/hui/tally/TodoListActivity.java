package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.application.R;
import com.hui.tally.adapter.AccountAdapter;
import com.hui.tally.adapter.TodoAdapter;
import com.hui.tally.db.AccountBean;
import com.hui.tally.db.DBManager;
import com.hui.tally.db.TodoBean;
import com.hui.tally.utils.BeiZhuDialog;
import com.hui.tally.utils.CalendarDialog;
import com.hui.tally.utils.TodoDialog;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {
    ListView todoLv;
    List<TodoBean> mDatas;
    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        todoLv = findViewById(R.id.todo_lv);
        mDatas = new ArrayList<>();
        // 设置适配器
        adapter = new TodoAdapter(this,mDatas);
        todoLv.setAdapter(adapter);
        loadData();
        setLVClickListener();
    }



    private void loadData() {
        List<TodoBean> list = DBManager.getAccountListFromTodotb();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }
    /*设置ListView每一个item的长按事件*/
    private void setLVClickListener() {
        todoLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoBean todobean = mDatas.get(position);
                deleteItem(todobean);
                return false;
            }
        });
    }
    private void deleteItem(final TodoBean todobean) {
        final int delId = todobean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteItemFromTodotbById(delId);
                        mDatas.remove(todobean);   //实时刷新，从数据源删除
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }
    public void onClick(View view) {
        if (view.getId()==R.id.todo_iv_back) {
            finish();
        } else if (view.getId()==R.id.todo_iv_new) {
            TodoDialog dialog = new TodoDialog(this);
            dialog.show();
            dialog.setOnEnsureListener(new TodoDialog.OnEnsureListener() {
                @Override
                public void onEnsure() {
                    loadData();
                }
            });
        }
    }
}