package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.digimaster.digicourse.digicyber.Activity.DetailLibActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.TaskLibItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class TaskLibraryAdapter extends RecyclerView.Adapter<TaskLibraryAdapter.DataHolder> {

    List<TaskLibItem> DataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdapterOnItemClickListener mListener;
    String passingImg;
    SecuredPreference pref;
    ProgressDialog loading;
    String uid;
    BaseApiService mApiService;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public TaskLibraryAdapter(Context context, List<TaskLibItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        DataItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public TaskLibraryAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_task, parent, false);

        return new TaskLibraryAdapter.DataHolder(itemView, mListener);



    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TaskLibraryAdapter.DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        final TaskLibItem taskLibItem = DataItemList.get(position);


        Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset)+"library/image/"+taskLibItem.getLibraryImage())
                .resize(360,300)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        //holder.load_gif.setVisibility(View.GONE);
                        holder.ivTaskLibrary.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(final Drawable errorDrawable) {
                        //Log.d("TAG", "FAILED");
                    }

                    @Override
                    public void onPrepareLoad(final Drawable placeHolderDrawable) {
                        holder.ivTaskLibrary.setBackgroundResource(R.drawable.load_gambar);
                    }
                });

        holder.tvCour_title.setText(taskLibItem.getLibraryName());

        holder.tvStats.setText(taskLibItem.getLibraryDescription());

        holder.btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);


                try {
                    pref.put(PrefContract.cour_id, taskLibItem.getLibraryId());
                    pref.put(PrefContract.title, taskLibItem.getLibraryName());
                    pref.put(PrefContract.img_url, taskLibItem.getLibraryImage());
                    pref.put(PrefContract.id_detail, taskLibItem.getLibraryId());

                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, DetailLibActivity.class);
                intent.putExtra("id_detail", taskLibItem.getLibraryId());
                mContext.startActivity(intent);
                loading.dismiss();
            }
        });

        holder.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(mContext);
                editText.setHint("Put your insight");
                editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);


                new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Before you finish...")
                        .setConfirmText("Finish")
                        .setCustomView(editText)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                //submit_task(editText.getText().toString(), taskLibItem.getTaskId(), taskLibItem.getLibraryName());
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }

//    private void submit_task(String submission, String task_id, String task_name) {
//
//        try {
//            uid = pref.getString(PrefContract.user_id, "");
//        } catch (AppException e) {
//            e.printStackTrace();
//        }
//
//        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
//
//        mApiService.submitTask(uid,task_id, task_name, submission)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()) {
//
//
//                            try {
//                               JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
//                                //String pesan = jsonRESULTS.getString("success");
//                                String pesanError = jsonRESULTS.getString("message");
//                                if (pesanError.equals("Submit feedback berhasil")) {
//
//                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
//                                    //PAYMENTTT
//                                    Intent intent = new Intent(mContext, HomeActivity.class);
//                                    mContext.startActivity(intent);
//
//                                } else {
//                                    loading.dismiss();
//                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            loading.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
//                    }
//                });
//    }



    @Override
    public int getItemCount() {
        return DataItemList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCour_title, tvStats, tvExp;
        TextView tvQty;
        LinearLayout llData;
        GifImageView load_gif;
        LinearLayout linear_data_adapter;
        Button btnBaca, btnFeedback;
        CircleImageView ivTaskLibrary;
        RoundCornerProgressBar progressbar1;
        TextView txtpersen;


        private DataHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mListener = listener;
            mApiService = UtilsApi.getAPIService();


            try {
                passingImg = pref.getString(PrefContract.img_url, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            tvCour_title = itemView.findViewById(R.id.item_title);
            ivTaskLibrary = itemView.findViewById(R.id.ivTaskLibrary);
            tvStats = itemView.findViewById(R.id.item_writer);
            llData = itemView.findViewById(R.id.ll_data);
            load_gif = itemView.findViewById(R.id.gif_load);
            linear_data_adapter = itemView.findViewById(R.id.linear_data_adapter);
            tvExp = itemView.findViewById(R.id.tv_recyclerview_expired);
            btnBaca = itemView.findViewById(R.id.btnBaca);
            btnFeedback = itemView.findViewById(R.id.btnAssign);
            txtpersen = itemView.findViewById(R.id.textViewpersenan);

            progressbar1 = itemView.findViewById(R.id.progress_bar1);

            int pro = (int) progressbar1.getProgress();
            txtpersen.setText(pro + "%");

            if (progressbar1.getProgress()!= 0)
            {

                btnBaca.setBackgroundResource(R.drawable.rounded_button_color);
                btnBaca.setText("continue");




            }

            else {
                btnBaca.setBackgroundResource(R.drawable.rounded_button_color_start);
                btnBaca.setText("start");

            }
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<TaskLibItem> getItems() {
        return DataItemList;
    }

    public TaskLibItem getItem(int position) {
        return DataItemList.get(position);
    }


}
