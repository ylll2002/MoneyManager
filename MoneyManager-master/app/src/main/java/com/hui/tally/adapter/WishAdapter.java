package com.hui.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.application.R;
import com.hui.tally.db.WishBean;

import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

public class WishAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    List<WishBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;

    private BottomBtnOnclick bottomBtnOnclick;


    public void setBottomBtnOnclick(BottomBtnOnclick bottomBtnOnclick) {
        this.bottomBtnOnclick = bottomBtnOnclick;
    }
    public interface BottomBtnOnclick{
        public void click(View view,int index);
    }
    @Override
    public void onClick(View view) {
        bottomBtnOnclick.click(view,(int)view.getTag());
    }

    public WishAdapter(Context context, List<WishBean> mDatas) {
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
        WishAdapter.ViewHolder holder = null;
        if (convertView == null) {
            //把item_mainlv布局的单个条目插入到listview中
            convertView = inflater.inflate(R.layout.item_wish_lv,viewGroup,false);
            holder = new WishAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (WishAdapter.ViewHolder) convertView.getTag();
        }
        WishBean bean = mDatas.get(position);
        holder.nameTv.setText(bean.getWishName());
        if(bean.getCurMoney()==0){
            holder.percentTv.setText("0%");
        }else {
            float d=(bean.getCurMoney()/ bean.getTotalMoney())*100;
            holder.percentTv.setText(new Formatter().format("%.1f", d).toString()+"%");
        }
        holder.totalTv.setText(bean.getCurMoney()+"/"+bean.getTotalMoney());
        if(bean.getDone()==1){
            holder.doneIv.setImageResource(R.drawable.selected);
            holder.doneTv.setText("本存储周期目标已完成");

        }else{
            holder.doneIv.setImageResource(R.drawable.unselected);
            holder.doneTv.setText("本存储周期目标未完成");
        }
        holder.processBar.setMax((int)bean.getTotalMoney());
        holder.processBar.setProgress((int)bean.getCurMoney());
        if(bean.getCycleDay()/365>0){
            holder.scheduleTv.setText("每"+bean.getCycleDay()/365+"年存"+bean.getPerMoney()+"元");
        } else if (bean.getCycleDay()/30>0) {
            holder.scheduleTv.setText("每"+bean.getCycleDay()/30+"月存"+bean.getPerMoney()+"元");
        }else if (bean.getCycleDay()/7>0) {
            holder.scheduleTv.setText("每"+bean.getCycleDay()/7+"周存"+bean.getPerMoney()+"元");
        }
        holder.doneIv.setOnClickListener(this);
        holder.doneIv.setTag(position);
        return convertView;
    }
    class ViewHolder{
        TextView nameTv,percentTv,totalTv,scheduleTv,doneTv;
        ImageView doneIv;
        ProgressBar processBar;
        public ViewHolder(View view){
            nameTv = view.findViewById(R.id.wish_tv_name);
            processBar = view.findViewById(R.id.wish_bar);
            percentTv = view.findViewById(R.id.wish_tv_percent);
            totalTv = view.findViewById(R.id.wish_tv_total);
            scheduleTv = view.findViewById(R.id.wish_tv_schedule);
            doneTv = view.findViewById(R.id.wish_tv_done);
            doneIv = view.findViewById(R.id.wish_iv_done);
        }
    }
}
