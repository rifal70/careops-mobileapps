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
import com.digimaster.digicourse.digicyber.Model.MateriItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;

import java.util.List;

public class DetailOnAdapter extends RecyclerView.Adapter<DetailOnAdapter.DetailOnHolder> {
    List<MateriItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    RecyclerView.LayoutManager layoutManager;


    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<AndroidItem> orderList

    public  DetailOnAdapter(Context context, List<MateriItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public DetailOnAdapter.DetailOnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.oncourse_detail_item, parent, false);

        return new DetailOnAdapter.DetailOnHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final DetailOnHolder holder, @SuppressLint("RecyclerView") final int position) {

        final MateriItem item = dataItemList.get(position);

            holder.title.setText(item.getSubTaskName());
            holder.writer.setText(item.getSubTaskName());

//      pref = new SecuredPreference(mContext, PrefContract.PREF_EL);


    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class DetailOnHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        TextView title;
        TextView writer;
        RecyclerView rv;


        private DetailOnHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.iv_header);
            title = itemView.findViewById(R.id.tvMaterialJuds);
            writer = itemView.findViewById(R.id.tvSubMats);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<MateriItem> getItems() {
        return dataItemList;
    }

    public MateriItem getItem(int position) {
        return dataItemList.get(position);
    }
}