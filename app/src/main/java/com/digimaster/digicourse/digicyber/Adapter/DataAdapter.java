package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Activity.CourseActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.DataItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

    List<DataItem> DataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    DataItem DataItem;
    private DataAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    String passingImg;
    SecuredPreference pref;
    ProgressDialog loading;
    Date dateExp,today;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<DataItem> DataList

    public DataAdapter(Context context, List<DataItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        DataItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public DataAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_itemso, parent, false);

        return new DataAdapter.DataHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final DataAdapter.DataHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.llData.setBackgroundColor(Color.TRANSPARENT);

        final DataItem dataItem = DataItemList.get(position);
        holder.tvCour_title.setText(dataItem.getTaskName());

        holder.tvStats.setText(dataItem.getTaskDateStart());

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            dateExp = sdf.parse(dataItem.getExpiredAt());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


            holder.tvExp.setText("Expired at : " + dataItem.getTaskDateEnd());

            holder.llData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            try {
                                pref.put(PrefContract.cour_id, dataItem.getTaskId());
                                pref.put(PrefContract.title,dataItem.getTaskName());
                                //pref.put(PrefContract.img_url,dataItem.get());
                            } catch (AppException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(mContext, CourseActivity.class);
                            mContext.startActivity(intent);



                    }

            });

//        Picasso.with(mContext).load("https://assets.digicourse.id/image/" + dataItem.g()).resize(360,240)
//                .into(new Target() {
//
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        //holder.load_gif.setVisibility(View.GONE);
//                        holder.linear_data_adapter.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
//                        }
//
//                        @Override
//                        public void onBitmapFailed(final Drawable errorDrawable) {
//                        //Log.d("TAG", "FAILED");
//                        }
//
//                        @Override
//                        public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                        holder.linear_data_adapter.setBackgroundResource(R.drawable.load_gambar);
//                        }
//                });




    }


    @Override
    public int getItemCount() {
        return DataItemList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvCour_title, tvStats, tvExp;
        Button btnAdd;
        TextView tvQty;
        LinearLayout llData;
        GifImageView load_gif;
        LinearLayout linear_data_adapter;



        private DataHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

            try {
                passingImg = pref.getString(PrefContract.img_url, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvCour_title = itemView.findViewById(R.id.tvCour_ttl);
            tvStats = itemView.findViewById(R.id.tvStatusMyCour);
            tvQty = itemView.findViewById(R.id.qty);
            llData = itemView.findViewById(R.id.ll_data);
            load_gif = itemView.findViewById(R.id.gif_load);
            linear_data_adapter = itemView.findViewById(R.id.linear_data_adapter);
            tvExp = itemView.findViewById(R.id.tv_recyclerview_expired);
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<DataItem> getItems() {
        return DataItemList;
    }

    public DataItem getItem(int position) {
        return DataItemList.get(position);
    }



}
