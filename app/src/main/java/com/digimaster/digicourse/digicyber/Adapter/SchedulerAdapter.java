package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class SchedulerAdapter extends RecyclerView.Adapter<SchedulerAdapter.ScheduleHolder> {

    List<com.digimaster.digicourse.digicyber.Model.ScheduleItem> scheduleItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    com.digimaster.digicourse.digicyber.Model.ScheduleItem ScheduleItem;
    private SchedulerAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public SchedulerAdapter(Context context, List<com.digimaster.digicourse.digicyber.Model.ScheduleItem> scheduleItems, AdapterOnItemClickListener listener) {
        this.mContext = context;
        scheduleItemList = scheduleItems;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public SchedulerAdapter.ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);

        return new SchedulerAdapter.ScheduleHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final SchedulerAdapter.ScheduleHolder holder, @SuppressLint("RecyclerView") final int position) {


        final com.digimaster.digicourse.digicyber.Model.ScheduleItem scheduleItem = scheduleItemList.get(position);
        holder.ngentot.setText(scheduleItem.getStartDate()+" "+scheduleItem.getEndDate());

        
    }




    @Override
    public int getItemCount() {
        return scheduleItemList.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen;
        TextView tvNamaGur;
        RelativeLayout rvTeach;
        TextView ngentot;



        private ScheduleHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;

            ngentot = itemView.findViewById(R.id.wewewe);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<com.digimaster.digicourse.digicyber.Model.ScheduleItem> getItems() {
        return scheduleItemList;
    }

    public com.digimaster.digicourse.digicyber.Model.ScheduleItem getItem(int position) {
        return scheduleItemList.get(position);
    }


}
