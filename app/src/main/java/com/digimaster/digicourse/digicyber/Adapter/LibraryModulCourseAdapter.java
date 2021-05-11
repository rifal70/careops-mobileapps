package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailModulLibraryActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseListModuleItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class LibraryModulCourseAdapter extends RecyclerView.Adapter<LibraryModulCourseAdapter.OnlineHolder> {
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

    public LibraryModulCourseAdapter(Context context, List<CourseListModuleItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;

    }

    @Override
    public LibraryModulCourseAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.librarymodulcourseitem, parent, false);

        return new LibraryModulCourseAdapter.OnlineHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final LibraryModulCourseAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            cour_id = pref.getString(PrefContract.cour_id, "");
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
                    pref.put(PrefContract.module_title, item.getModuleName());
//                    pref.put(PrefContract.module_img, item.getModuleImage());
                    pref.put(PrefContract.module_id_from_module_adapter, item.getModuleId());
                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, DetailModulLibraryActivity.class);
                intent.putExtra("module_id", item.getModuleId());

                intent.putExtra("module_name", item.getModuleName());
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
