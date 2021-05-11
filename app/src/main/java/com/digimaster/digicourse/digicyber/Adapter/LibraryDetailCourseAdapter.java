package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseDetailItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class LibraryDetailCourseAdapter extends RecyclerView.Adapter<LibraryDetailCourseAdapter.OnlineHolder> {
    List<CourseDetailItem> dataItemList;
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

    public LibraryDetailCourseAdapter(Context context, List<CourseDetailItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;

    }

    @Override
    public LibraryDetailCourseAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.librarymoduledetail, parent, false);

        return new LibraryDetailCourseAdapter.OnlineHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final LibraryDetailCourseAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            cour_id = pref.getString(PrefContract.cour_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }

        final CourseDetailItem item = dataItemList.get(position);


        holder.detail.setText(item.getCourseDescription());


    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class OnlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;

        TextView detail;

        private OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mApiService = UtilsApi.getAPIService();
            context = itemView.getContext();


            detail = itemView.findViewById(R.id.tv_libmoduldetailid);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<CourseDetailItem> getItems() {
        return dataItemList;
    }

    public CourseDetailItem getItem(int position) {

        return dataItemList.get(position);
    }


}
