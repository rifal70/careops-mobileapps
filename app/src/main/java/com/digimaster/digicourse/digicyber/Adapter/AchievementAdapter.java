package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.digimaster.digicourse.digicyber.Activity.DetailTopicHalamanAchievement;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.AchievementItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.DataHolder> {

    List<AchievementItem> achievementItemList;
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

    public AchievementAdapter(Context context, List<AchievementItem> achievementItemList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        this.achievementItemList = achievementItemList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public AchievementAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_achievement, parent, false);

        return new AchievementAdapter.DataHolder(itemView, mListener);



    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final AchievementAdapter.DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        final AchievementItem achievementItem = achievementItemList.get(position);


        holder.tvCour_title.setText(achievementItem.getTopicName());

        //achievementItem.getC

        //holder.tvStats.setText(achievementItem.getTopicName());

        holder.cvAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf(achievementItem.getTopicId());
            }
        });

    }

    private void generatePdf(String topic_id) {

        try {
            uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.generatePdfAchieve("api/apireport/pdf?user_id="+uid+"&topic_id="+topic_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("info");
                                if (pesanError.equals("success")) {
//
                                    loading.dismiss();
//                                    Uri uri = Uri.parse("https://digicourse.id/digilearn/member/assets.digilearn/pdf/" + jsonRESULTS.getString("Message")); // missing 'http://' will cause crashed
//
//                                    DownloadManager.Request r = new DownloadManager.Request(uri);
//
//// This put the download in the same Download dir the browser uses
//                                    r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");
//
//// When downloading music and videos they will be listed in the player
//// (Seems to be available since Honeycomb only)
//                                    r.allowScanningByMediaScanner();
//
//// Notify user when download is completed
//// (Seems to be available since Honeycomb only)
//                                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//// Start download
//                                    DownloadManager dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
//                                    dm.enqueue(r);

                                    Uri uri = Uri.parse("http://18.141.179.90/member_careops/assets.careops/pdf/" + jsonRESULTS.getString("Message")); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    mContext.startActivity(intent);
                                    ((DetailTopicHalamanAchievement) mContext).finish();

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }



    @Override
    public int getItemCount() {
        return achievementItemList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCour_title, tvStats, tvExp;
        TextView tvQty;
        LinearLayout llData;
        GifImageView load_gif;
        LinearLayout linear_data_adapter;
        Button btnBaca, btnFeedback;
        ImageView ivTaskLibrary;
        RoundCornerProgressBar progressbar1;
        TextView txtpersen;
        CardView cvAchieve;


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
            txtpersen = itemView.findViewById(R.id.textViewpersenan);
            progressbar1 = itemView.findViewById(R.id.progress_bar1);
            cvAchieve = itemView.findViewById(R.id.cardAchievement);

            int pro = (int) progressbar1.getProgress();
            txtpersen.setText(pro + "%");

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<AchievementItem> getItems() {
        return achievementItemList;
    }

    public AchievementItem getItem(int position) {
        return achievementItemList.get(position);
    }


}
