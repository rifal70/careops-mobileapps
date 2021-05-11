package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.digimaster.digicourse.digicyber.Model.ListGroupItem;
import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class ListGroupAdminAdapter extends RecyclerView.Adapter<ListGroupAdminAdapter.TeacherHolder> {

    List<ListGroupAdminItem> listGroupAdminItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    ListGroupItem listGroupItem;
    private ListGroupAdminAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public ListGroupAdminAdapter(Context context, List<ListGroupAdminItem> listGroupAdminItemList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        this.listGroupAdminItemList = listGroupAdminItemList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ListGroupAdminAdapter.TeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);

        return new ListGroupAdminAdapter.TeacherHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ListGroupAdminAdapter.TeacherHolder holder, @SuppressLint("RecyclerView") final int position) {


        final ListGroupAdminItem listGroupAdminItem = listGroupAdminItemList.get(position);
        holder.tvNamaGur.setText(listGroupAdminItem.getGroupName());
        //holder.tvNamaDosen.setText(listGroupItem.());
        holder.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ListMemberGroupAdminActivity.class));
                ((Activity)mContext).finish();

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
        Button btnInvite;


        private TeacherHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNamaGur = itemView.findViewById(R.id.tvNamaTeach);
            tvNamaDosen = itemView.findViewById(R.id.tvNamaDosen);
            rvTeach = itemView.findViewById(R.id.rvRootTeacher);
            btnInvite = itemView.findViewById(R.id.btnInviteMember);

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