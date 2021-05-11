package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Activity.eAssessActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.TaskAssItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class TaskAssAdapter extends RecyclerView.Adapter<TaskAssAdapter.DataHolder> {

    List<TaskAssItem> DataItemList;
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

    public TaskAssAdapter(Context context, List<TaskAssItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        DataItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public TaskAssAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_assess_task, parent, false);

        return new TaskAssAdapter.DataHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TaskAssAdapter.DataHolder holder, @SuppressLint("RecyclerView") final int position) {

        final TaskAssItem taskAssItem = DataItemList.get(position);
        holder.tvCour_title.setText(taskAssItem.getCourseTitle());

        holder.tvDesc.setText(taskAssItem.getAdminName());
        holder.tvDate.setText(taskAssItem.getCreatedAt());


        holder.btnBaca.setOnClickListener(view -> {
            //loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);


            try {
                pref.put(PrefContract.id_detail, taskAssItem.getCourseId());
                pref.put(PrefContract.author, taskAssItem.getAdminName());
                pref.put(PrefContract.title, taskAssItem.getCourseTitle());
                pref.put(PrefContract.img_url, taskAssItem.getCourseImage());
                pref.put(PrefContract.cour_id, taskAssItem.getCourseId());
                pref.put(PrefContract.cour_name, taskAssItem.getCourseTitle());

                //pref.put(PrefContract.vid_url_pre,item.ge)

            } catch (AppException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, eAssessActivity.class);
            intent.putExtra("id_detail", taskAssItem.getCourseId());
            mContext.startActivity(intent);
        });

        holder.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    @Override
    public int getItemCount() {
        return DataItemList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvCour_title, tvStats, tvExp, tvDesc, tvDate;
        GifImageView load_gif;
        LinearLayout linear_data_adapter;
        Button btnBaca, btnFeedback;


        private DataHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            mApiService = UtilsApi.getAPIService();
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

            try {
                passingImg = pref.getString(PrefContract.img_url, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            tvCour_title = itemView.findViewById(R.id.item_title);
            tvDesc = itemView.findViewById(R.id.item_desc);
            tvDate = itemView.findViewById(R.id.item_date);
            btnBaca = itemView.findViewById(R.id.btnBaca);
            btnFeedback = itemView.findViewById(R.id.btnAssign);

            load_gif = itemView.findViewById(R.id.gif_load);
            linear_data_adapter = itemView.findViewById(R.id.linear_data_adapter);
            tvExp = itemView.findViewById(R.id.tv_recyclerview_expired);
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<TaskAssItem> getItems() {
        return DataItemList;
    }

    public TaskAssItem getItem(int position) {
        return DataItemList.get(position);
    }



}
