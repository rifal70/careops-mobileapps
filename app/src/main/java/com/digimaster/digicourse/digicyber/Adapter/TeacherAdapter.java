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

import com.digimaster.digicourse.digicyber.Model.ListGroupItem;
import com.digimaster.digicourse.digicyber.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherHolder> {

    List<ListGroupItem> usersItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    ListGroupItem listGroupItem;
    private TeacherAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public TeacherAdapter(Context context, List<ListGroupItem> wishList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        usersItemList = wishList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public TeacherAdapter.TeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);

        return new TeacherAdapter.TeacherHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TeacherAdapter.TeacherHolder holder, @SuppressLint("RecyclerView") final int position) {


        final ListGroupItem listGroupItem = usersItemList.get(position);
        holder.tvNamaGur.setText(listGroupItem.getGroupName());
        //holder.tvNamaDosen.setText(listGroupItem.());
        holder.rvTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, DetailTeacherActivity.class));
//                ((Activity)mContext).finish();

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
        return usersItemList.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen;
        TextView tvNamaGur;
        RelativeLayout rvTeach;



        private TeacherHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNamaGur = itemView.findViewById(R.id.tvNamaTeach);
            tvNamaDosen = itemView.findViewById(R.id.tvNamaDosen);
            rvTeach = itemView.findViewById(R.id.rvRootTeacher);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<ListGroupItem> getItems() {
        return usersItemList;
    }

    public ListGroupItem getItem(int position) {
        return usersItemList.get(position);
    }


}
