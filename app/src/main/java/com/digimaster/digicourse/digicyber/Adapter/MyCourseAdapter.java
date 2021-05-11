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
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.DataHolder> {

    List<com.digimaster.digicourse.digicyber.Model.DataItem> DataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    com.digimaster.digicourse.digicyber.Model.DataItem DataItem;
    private MyCourseAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    String passingImg;
    SecuredPreference pref;
    ProgressDialog loading;
    Date dateExp,today;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public MyCourseAdapter(Context context, List<com.digimaster.digicourse.digicyber.Model.DataItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        DataItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public MyCourseAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_itemso, parent, false);

        return new MyCourseAdapter.DataHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyCourseAdapter.DataHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.llData.setBackgroundColor(Color.TRANSPARENT);

        final com.digimaster.digicourse.digicyber.Model.DataItem dataItem = DataItemList.get(position);
        holder.tvCour_title.setText(dataItem.getTaskName());

        holder.tvStats.setText(dataItem.getTaskDateStart()+" - "+dataItem.getTaskDateEnd());



//        if(dateExp.after(new Date())) {

            //holder.tvExp.setText("Expired at : " + dataItem.getExpiredAt());

            holder.llData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



//                        if(dataItem.getTransactionStatus().equals("settlement")){
                            try {
                                pref.put(PrefContract.cour_id, dataItem.getTaskId());
                                pref.put(PrefContract.title,dataItem.getTaskName());
                                //pref.put(PrefContract.img_url,dataItem.getCourseImage());
                            } catch (AppException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(mContext, CourseActivity.class);
                            mContext.startActivity(intent);
//                        }else{
//                            Snackbar snackbar = Snackbar.make(holder.llData, "Please complete your payment.", Snackbar.LENGTH_SHORT);
//                            View sbView = snackbar.getView();
//                            sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
//                            snackbar.show();
//                        }


                }

            });
//        }else{
//
//            if(dataItem.getCourseTypeId().equals("1")) {
//
//                holder.llData.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        final TextView editText = new TextView(mContext);
////                        editText.setText("Course title : " + dataItem.getCourseTitle() + "\nName : " + dataItem.getCertificateName() +
////                                "\n" +
////                                "Status  : " + dataItem.getTransactionStatus());
////
////
////                        new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
////                                .setTitleText("Onsite Course Detail")
////                                .setConfirmText("Okay")
////                                .setCustomView(editText)
////                                .show();
////                        try {
////                            pref.put(PrefContract.cour_id, dataItem.getCourseId());
////                            pref.put(PrefContract.title,dataItem.getCourseTitle());
////                            pref.put(PrefContract.img_url,dataItem.getCourseImage());
////                            pref.put(PrefContract.price,dataItem.getCoursePrice());
////                        } catch (AppException e) {
////                            e.printStackTrace();
////                        }
//                        try {
//                            pref.put(PrefContract.id_detail, dataItem.getCourseId());
//                            pref.put(PrefContract.title, dataItem.getCourseTitle());
//                            pref.put(PrefContract.img_url, dataItem.getCourseImage());
//                            pref.put(PrefContract.price, dataItem.getCoursePrice());
//                            pref.put(PrefContract.cour_type, dataItem.getCourseTypeId());
//                            pref.put(PrefContract.cour_id,dataItem.getCourseId());
//                        } catch (AppException e) {
//                            e.printStackTrace();
//                        }
//
//                        Intent intent = new Intent(mContext, DetailTaskActivity.class);
//                        intent.putExtra("id_detail",dataItem.getCourseId());
//                        mContext.startActivity(intent);
//                    }
//
//
//                });
//
//
//            }else {
//                //holder.tvExp.setText("Masa berlaku kursus sudah habis");
//                holder.tvExp.setTextColor(Color.RED);
//                holder.tvStats.setVisibility(View.INVISIBLE);
//
//                holder.llData.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            pref.put(PrefContract.title,dataItem.getCourseTitle());
//                        } catch (AppException e) {
//                            e.printStackTrace();
//                        }
//
//                        Toast.makeText(mContext, "Masa berlaku kursus sudah habis, mohon perpanjang berlangganan",
//                                Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(mContext, DetailItemActivity.class);
//                        intent.putExtra("id_detail", dataItem.getCourseId());
//                        intent.putExtra("price", dataItem.getCoursePrice());
//                        intent.putExtra("cour_id",dataItem.getCourseId());
//                        mContext.startActivity(intent);
//                    }
//                });
//
//            }

        //}

//        Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset)+"image/digicom/" + dataItem.getCourseImage()).resize(360,240)
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

    public List<com.digimaster.digicourse.digicyber.Model.DataItem> getItems() {
        return DataItemList;
    }

    public com.digimaster.digicourse.digicyber.Model.DataItem getItem(int position) {
        return DataItemList.get(position);
    }



}
