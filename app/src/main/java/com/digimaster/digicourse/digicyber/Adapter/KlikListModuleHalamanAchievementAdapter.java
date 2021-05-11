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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailTopicHalamanAchievement;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseListModuleItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class KlikListModuleHalamanAchievementAdapter extends RecyclerView.Adapter<KlikListModuleHalamanAchievementAdapter.OnlineHolder> {
    List<CourseListModuleItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private DetailLibraryAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String cour_id;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public KlikListModuleHalamanAchievementAdapter(Context context, List<CourseListModuleItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;

    }

    @Override
    public KlikListModuleHalamanAchievementAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.librarymodulcourseitem, parent, false);

        return new KlikListModuleHalamanAchievementAdapter.OnlineHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final KlikListModuleHalamanAchievementAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            cour_id = pref.getString(PrefContract.cour_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        final CourseListModuleItem item = dataItemList.get(position);

        holder.detail.setText(item.getModuleName());

        holder.btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);


                try {
                    pref.put(PrefContract.module_id, item.getModuleId());
                    pref.put(PrefContract.module_title,item.getModuleName());
                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, DetailTopicHalamanAchievement.class);
                intent.putExtra("module_id", item.getModuleId());
                intent.putExtra("module_name",item.getModuleName());
                mContext.startActivity(intent);
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
        RelativeLayout btnBaca;
        TextView detail;

        private OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mApiService = UtilsApi.getAPIService();
            context = itemView.getContext();
            btnBaca = itemView.findViewById(R.id.rv_librarymodulecourseitem);

            detail = itemView.findViewById(R.id.tv_namamodul);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<CourseListModuleItem> getItems() {
        return dataItemList;
    }

    public CourseListModuleItem getItem(int position) {

        return dataItemList.get(position);
    }


}
