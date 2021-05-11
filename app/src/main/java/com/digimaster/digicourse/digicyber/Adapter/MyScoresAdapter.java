package com.digimaster.digicourse.digicyber.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ScoreUserItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScoresAdapter extends RecyclerView.Adapter<MyScoresAdapter.MyScoreHolder> {

    List<ScoreUserItem> scoreUserItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    ScoreUserItem scoreUserItem;
    private MyScoresAdapter.OnItemClicked onClick;

    private AdapterOnItemClickListener mListener;
    String passingImg;
    SecuredPreference pref;
    BaseApiService mApiService;
    ProgressDialog loading;
    String uid, cour_id;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<ScoreUserItem> DataList

    public MyScoresAdapter(Context context, List<ScoreUserItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        scoreUserItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public MyScoresAdapter.MyScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);

        return new MyScoresAdapter.MyScoreHolder(itemView, mListener);
    }


    @Override
    public void onBindViewHolder(final MyScoresAdapter.MyScoreHolder holder, final int position) {
        holder.llData.setBackgroundColor(Color.TRANSPARENT);

        if(scoreUserItemList.size() > 0) {
            //Log.d("logggASDASDASDASD", "onBindViewHolder: "+scoreUserItemList);
            final ScoreUserItem scoreUserItem = scoreUserItemList.get(position);
            holder.tvBab.setText(scoreUserItem.getCourseName());
            holder.tvScore.setText("Score : "+scoreUserItem.getScore()+"/100");
            holder.tvMaterial.setText(scoreUserItem.getMaterialName());
            holder.btnFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadFeedback(scoreUserItem.getUserId(),scoreUserItem.getCourseId(),scoreUserItem.getMaterialId());
                }
            });

        }
        else{
           holder.rvScore.setVisibility(View.VISIBLE);
        }


    }

    private void loadFeedback(String id, String task_id, String sub_task_id){
        //loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.getFeedback(id, task_id, sub_task_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    loading.dismiss();
                    //Log.d("logggN", "" + response.body());
                    JSONObject jsonRESULTS = null;
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        String feedback = jsonRESULTS.getString("feedback");

                        final TextView textView = new TextView(mContext);
                        textView.setText(feedback);
                        textView.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);

                        new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Task Feedback")
                                .setConfirmText("Okay")
                                .setCustomView(textView)
                                .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                                .show();

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    //String pesan = jsonRESULTS.getString("success");



                } else {
                    loading.dismiss();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return scoreUserItemList.size();
    }

    public class MyScoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvBab, tvScore, tvMaterial;
        Button btnFeedback;
        RelativeLayout rvScore,llData;


        private MyScoreHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            mApiService = UtilsApi.getAPIService();
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

            try {
                passingImg = pref.getString(PrefContract.img_url, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            //tvCour_title = itemView.findViewById(R.id.tvCour_ttl);
            tvScore = itemView.findViewById(R.id.tvScore_mycour);
            tvMaterial = itemView.findViewById(R.id.tvMaterial_score_item);
            llData = itemView.findViewById(R.id.ll_data);
            tvBab = itemView.findViewById(R.id.tvBab);
            rvScore = itemView.findViewById(R.id.rv_score_item);
            btnFeedback = itemView.findViewById(R.id.btnFeedbackScore);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<ScoreUserItem> getItems() {
        return scoreUserItemList;
    }

    public ScoreUserItem getItem(int position) {
        return scoreUserItemList.get(position);
    }


}
