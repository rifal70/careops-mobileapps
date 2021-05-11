package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.AudioActivity;
import com.digimaster.digicourse.digicyber.Activity.DetailLibActivity;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Activity.WebViewActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.LibraryDetailItem;
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

public class DetailLibraryAdapter extends RecyclerView.Adapter<DetailLibraryAdapter.OnlineHolder> {
    List<LibraryDetailItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private DetailLibraryAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String uid, name, cour_id, cour_name;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public DetailLibraryAdapter(Context context, List<LibraryDetailItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public DetailLibraryAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail_lib_adapter, parent, false);

        return new DetailLibraryAdapter.OnlineHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final DetailLibraryAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {

        final LibraryDetailItem item = dataItemList.get(position);


//        holder.cardView.setRadius(40);

        holder.title.setText(item.getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.getType().equals("1")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("pdf", item.getPdf());
                    mContext.startActivity(intent);
                    ((DetailLibActivity) mContext).finish();
                } else if (item.getType().equals("2")) {

                } else {
                    Intent intent = new Intent(mContext, AudioActivity.class);
                    intent.putExtra("audio", item.getPdf());
                    mContext.startActivity(intent);
                    ((DetailLibActivity) mContext).finish();
                }


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
                                submit_task(editText.getText().toString(), item.getLibraryId(), item.getTitle(), item.getSubLibraryId());
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });


    }

    private void submit_task(String submission, String task_id, String task_name, String material_id) {

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        try {
            uid = pref.getString(PrefContract.user_id, "");
            cour_id = pref.getString(PrefContract.cour_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }


        mApiService.submitTaskLib(uid, task_id, material_id, task_name, submission)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Submit feedback berhasil")) {

                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
                                    //PAYMENTTT
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    mContext.startActivity(intent);

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
        return dataItemList.size();
    }

    public class OnlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView, cvOuter;
        TextView title;
        TextView writer;
        TextView desc;
        Button btnBaca, btnFeedback;

        private OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mApiService = UtilsApi.getAPIService();
            context = itemView.getContext();

            //ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
            //imageView = itemView.findViewById(R.id.ivItemOnline);
            //linearLayout = itemView.findViewById(R.id.linearFeatured);
            title = itemView.findViewById(R.id.tv_bab_my_course);
            btnFeedback = itemView.findViewById(R.id.btnFeedbackLib);
//            writer = itemView.findViewById(R.id.item_writer);
//            cardView = itemView.findViewById(R.id.card0);
//            cvOuter = itemView.findViewById(R.id.card_view_outer);
//            btnBaca = itemView.findViewById(R.id.btnBaca);
//            desc = itemView.findViewById(R.id.item_desc);

            try {
                name = pref.getString(PrefContract.USER_FULL_NAME, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            //linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<LibraryDetailItem> getItems() {
        return dataItemList;
    }

    public LibraryDetailItem getItem(int position) {
        return dataItemList.get(position);
    }

}