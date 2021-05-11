package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Activity.ListMemberGroupAdminActivity;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminItem;
import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class ListGroupScoreAdminAdapter extends RecyclerView.Adapter<ListGroupScoreAdminAdapter.TeacherHolder> {

    List<ListGroupAdminItem> listGroupAdminItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private ListGroupScoreAdminAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public ListGroupScoreAdminAdapter(Context context, List<ListGroupAdminItem> listGroupAdminItemList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        this.listGroupAdminItemList = listGroupAdminItemList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ListGroupScoreAdminAdapter.TeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group_score_admin, parent, false);

        return new ListGroupScoreAdminAdapter.TeacherHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ListGroupScoreAdminAdapter.TeacherHolder holder, @SuppressLint("RecyclerView") final int position) {

        final ListGroupAdminItem listGroupAdminItem = listGroupAdminItemList.get(position);
        holder.tvNamaGur.setText(listGroupAdminItem.getGroupName());
        //holder.tvNamaDosen.setText(listGroupItem.());
        holder.btnSeeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ListMemberGroupAdminActivity.class).putExtra("group_id",listGroupAdminItem.getGroupId()));

            }
        });


    }




    @Override
    public int getItemCount() {
        return listGroupAdminItemList.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen;
        TextView tvNamaGur;
        RelativeLayout rvTeach;
        Button btnSeeDetail;



        private TeacherHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNamaGur = itemView.findViewById(R.id.tvNamaTeach);
            rvTeach = itemView.findViewById(R.id.rvRootTeacher);
            btnSeeDetail = itemView.findViewById(R.id.btnSeeDetail);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<ListGroupAdminItem> getItems() {
        return listGroupAdminItemList;
    }

    public ListGroupAdminItem getItem(int position) {
        return listGroupAdminItemList.get(position);
    }


}
