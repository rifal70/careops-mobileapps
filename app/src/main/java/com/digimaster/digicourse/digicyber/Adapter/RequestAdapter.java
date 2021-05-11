package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.GroupItem;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {

    List<GroupItem> groupItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    com.digimaster.digicourse.digicyber.Model.TeacherRequestItem TeacherRequestItem;
    private RequestAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    SecuredPreference pref;
    BaseApiService mApiService;
    ProgressDialog loading;
    String id;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<TeacherRequestItem> orderList

    public RequestAdapter(Context context, List<GroupItem> wishList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        groupItemList = wishList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public RequestAdapter.RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wishlist_adapter, parent, false);

        return new RequestAdapter.RequestHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RequestAdapter.RequestHolder holder, @SuppressLint("RecyclerView") final int position) {


        final GroupItem groupItem = groupItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.tvNamaDosen.setText(groupItem.getName());
        holder.tvJadwal.setText(groupItem.getAdminName());

//        Picasso.with(mContext).load("https://assets.digicourse.id/image/" + TeacherRequestItem.getCourseImage()).into(new Target(){
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
        //holder.tvNamaMatkul.setText(TeacherRequestItem.getNormalPrice().toString());
        //sharedPrefManager = new SharedPrefManager(mContext);

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptReq(groupItem.getId());
            }
        });


    }

    private void acceptReq(String gid) {
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);


        try {
            id =  pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        mApiService.joinGroup(id,gid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Accept Request Berhasil")) {

                                    loading.dismiss();
                                    Toast.makeText( mContext, "" + pesanError, Toast.LENGTH_SHORT).show();

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
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    private void declineReq(String x) {
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.declineRequestTeacher(x)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Decline Request Berhasil")) {

                                    loading.dismiss();
                                    Toast.makeText( mContext, "" + pesanError, Toast.LENGTH_SHORT).show();

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
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return groupItemList.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen, tvJadwal, tvStatus;
        LinearLayout linearLayoutWishlist;
        Button btnAccept, btnDecline;


        private RequestHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNamaDosen = itemView.findViewById(R.id.tvNamaDosen);
            tvJadwal = itemView.findViewById(R.id.tvJadwalReqTeach);
            tvStatus = itemView.findViewById(R.id.tvStatusReqTeach);
            linearLayoutWishlist = itemView.findViewById(R.id.linear_layout_wishlist);
            btnAccept = itemView.findViewById(R.id.btnAcceptRequestTeach);
            mApiService = UtilsApi.getAPIService();
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<GroupItem> getItems() {
        return groupItemList;
    }

    public GroupItem getItem(int position) {
        return groupItemList.get(position);
    }


}
