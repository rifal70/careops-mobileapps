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

import com.digimaster.digicourse.digicyber.Model.HistoryRequestItem;
import com.digimaster.digicourse.digicyber.Model.RegisteredEventItem;
import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    List<RegisteredEventItem> registeredEventItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    HistoryRequestItem historyRequestItem;
    private HistoryAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public HistoryAdapter(Context context, List<RegisteredEventItem> history, AdapterOnItemClickListener listener) {
        this.mContext = context;
        registeredEventItemList = history;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);

        return new HistoryAdapter.HistoryHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final HistoryAdapter.HistoryHolder holder, @SuppressLint("RecyclerView") final int position) {


        final RegisteredEventItem historyRequestItem = registeredEventItemList.get(position);
        holder.tvNamaGur.setText(historyRequestItem.getName());
        holder.tvStatus.setText("Requested at : "+historyRequestItem.getDate());
//        holder.rvTeach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mContext.startActivity(new Intent(mContext, DetailTeacherActivity.class)
//                        .putExtra("teach_name",HistoryRequestItem.getFirstName())
//                        .putExtra("teach_bidang",HistoryRequestItem.getPengalamanTeach())
//                        .putExtra("teach_address",HistoryRequestItem.getAddress())
//                        .putExtra("teach_edu",HistoryRequestItem.getEducation())
//                        .putExtra("teach_phone",HistoryRequestItem.getPhone())
//                        .putExtra("teach_price",HistoryRequestItem.getPriceTeacher())
//                        .putExtra("teach_id",HistoryRequestItem.getTeacherId())
//
//                );
//                //((Activity)mContext).finish();
//
//            }
//        });

        
    }




    @Override
    public int getItemCount() {
        return registeredEventItemList.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivHistory;
        TextView tvStatus;
        TextView tvNamaGur;
        RelativeLayout rvHistory;



        private HistoryHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivHistory = itemView.findViewById(R.id.ivHistoryTeach);
            tvNamaGur = itemView.findViewById(R.id.tvNamaGuruHis);
            tvStatus = itemView.findViewById(R.id.tvStatusHis);
            rvHistory = itemView.findViewById(R.id.rvRootHistory);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<RegisteredEventItem> getItems() {
        return registeredEventItemList;
    }

    public RegisteredEventItem getItem(int position) {
        return registeredEventItemList.get(position);
    }


}
