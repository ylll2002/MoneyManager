package com.hui.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.application.R;
import com.hui.tally.db.TodoBean;

import java.util.Calendar;
import java.util.List;

public class TodoAdapter extends BaseAdapter {
    Context context;
    List<TodoBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;

    public TodoAdapter(Context context, List<TodoBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);//LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TodoAdapter.ViewHolder holder = null;
        if (convertView == null) {
            //把item_mainlv布局的单个条目插入到listview中
            convertView = inflater.inflate(R.layout.item_todo_lv,viewGroup,false);
            holder = new TodoAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (TodoAdapter.ViewHolder) convertView.getTag();
        }
        TodoBean bean = mDatas.get(position);
        holder.nameIv.setText(bean.getTodoName());
        holder.timeTv.setText(bean.getYear()+"年"+ bean.getMonth()+"月"+bean.getDay()+"日");
        holder.moneyTv.setText("￥ "+bean.getMoney());
        return convertView;
    }
    class ViewHolder{
        TextView nameIv,timeTv,moneyTv;
        public ViewHolder(View view){
            nameIv = view.findViewById(R.id.item_todo_tv_title);
            timeTv = view.findViewById(R.id.item_todo_tv_time);
            moneyTv = view.findViewById(R.id.item_todo_tv_money);

        }
    }
}
