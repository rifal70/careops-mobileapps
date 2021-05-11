package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
import com.digimaster.digicourse.digicyber.Model.MemberItem;
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

public class ListMemberAdapter extends RecyclerView.Adapter<ListMemberAdapter.TeacherHolder> {

    List<MemberItem> memberItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private ListMemberAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    SecuredPreference pref;
    String task_id, task_type;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public ListMemberAdapter(Context context, List<MemberItem> wishList, AdapterOnItemClickListener listener, String task_id, String task_type) {
        this.mContext = context;
        memberItemList = wishList;
        this.mListener = listener;
        this.task_id = task_id;
        this.task_type = task_type;
    }

    @Override
    public ListMemberAdapter.TeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_member_item  , parent, false);

        return new ListMemberAdapter.TeacherHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ListMemberAdapter.TeacherHolder holder, @SuppressLint("RecyclerView") final int position) {

        final MemberItem memberItem = memberItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.tvNamaGur.setText(memberItem.getName());
        holder.tvNamaDosen.setText(memberItem.getGroupName());
        holder.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    assignTask(memberItem.getUserId(),memberItem.getGroupId());
                } catch (AppException e) {
                    e.printStackTrace();
                }
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

    private void assignTask(String uid, String gid) throws AppException {
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        String admin_id = pref.getString(PrefContract.user_id, "false");

        //Toast.makeText(context, ""+token, Toast.LENGTH_SHORT).show();
        mApiService.assignTask(uid, gid, admin_id, task_id, task_type).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        String pesanError = jsonRESULTS.getString("code");
                        if (pesanError.equals("100")) {
                            loading.dismiss();
                            Toast.makeText(mContext, "Assign Task Success.", Toast.LENGTH_SHORT).show();
                        }else{
                            loading.dismiss();

                            Toast.makeText(mContext, "Assign Task Error.", Toast.LENGTH_SHORT).show();

                        }

                        } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    loading.dismiss();

                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //gif_find_teacher.setVisibility(View.GONE);
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public int getItemCount() {
        return memberItemList.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen;
        TextView tvNamaGur;
        RelativeLayout rvTeach;
        Button btnChoose;


        private TeacherHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNamaGur = itemView.findViewById(R.id.tvNamaTeach);
            tvNamaDosen = itemView.findViewById(R.id.tvNamaDosen);
            rvTeach = itemView.findViewById(R.id.rvRootTeacher);
            btnChoose = itemView.findViewById(R.id.button2);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<MemberItem> getItems() {
        return memberItemList;
    }

    public MemberItem getItem(int position) {
        return memberItemList.get(position);
    }


}
