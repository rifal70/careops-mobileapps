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

import com.digimaster.digicourse.digicyber.Activity.MyScoreAdminActivity;
import com.digimaster.digicourse.digicyber.Model.ListMemberAdminItem;
import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class ListMemberAdminAdapter extends RecyclerView.Adapter<ListMemberAdminAdapter.TeacherHolder> {

    List<ListMemberAdminItem> listMemberAdminItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    ListMemberAdminItem listMemberAdminItem;
    private ListMemberAdminAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public ListMemberAdminAdapter(Context context, List<ListMemberAdminItem> listMemberAdminItemList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        this.listMemberAdminItemList = listMemberAdminItemList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ListMemberAdminAdapter.TeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_member_admin, parent, false);

        return new ListMemberAdminAdapter.TeacherHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ListMemberAdminAdapter.TeacherHolder holder, @SuppressLint("RecyclerView") final int position) {


        final ListMemberAdminItem listMemberAdminItem = listMemberAdminItemList.get(position);
        holder.tvNama.setText(listMemberAdminItem.getUserName());
        holder.tvTelpun.setText(listMemberAdminItem.getUserPhone());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, MyScoreAdminActivity.class).putExtra(
                        "user_id",listMemberAdminItem.getMemberId())
                .putExtra("user_name",listMemberAdminItem.getUserName())
                .putExtra("user_phone",listMemberAdminItem.getUserPhone()));

            }
        });
//        Picasso.with(mContext).load("https://assets.digicourse.id/image/" + LovedByItem.getCourseImage()).into(new Target(){
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                holder.ivTextDrawable.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(final Drawable errorDrawable) {
//                Log.d("TAG", "FAILED");
//            }
//
//            @Override
//            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                Log.d("TAG", "Prepare Load");
//            }
//        });
        //holder.tvNamaMatkul.setText(LovedByItem.getNormalPrice().toString());
        //sharedPrefManager = new SharedPrefManager(mContext);


    }




    @Override
    public int getItemCount() {
        return listMemberAdminItemList.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNama, tvTelpun;
        RelativeLayout rvTeach;
        Button btnDetail;

        private TeacherHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNama = itemView.findViewById(R.id.tvNamaMember);
            tvTelpun = itemView.findViewById(R.id.tvTelpunMember);
            rvTeach = itemView.findViewById(R.id.rvRootTeacher);
            btnDetail = itemView.findViewById(R.id.btnSeeDetail);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<ListMemberAdminItem> getItems() {
        return listMemberAdminItemList;
    }

    public ListMemberAdminItem getItem(int position) {
        return listMemberAdminItemList.get(position);
    }


}
