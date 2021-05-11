package com.digimaster.digicourse.digicyber.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.FinishedTaskItem;
import com.digimaster.digicourse.digicyber.Model.ScoreUserItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class FinishedTaskAdapter extends RecyclerView.Adapter<FinishedTaskAdapter.MyScoreHolder> {

    List<FinishedTaskItem> finishedTaskItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    ScoreUserItem scoreUserItem;
    private FinishedTaskAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    String passingImg;
    SecuredPreference pref;


    public interface OnItemClicked {
        void onItemClick(int position);
    }


    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<ScoreUserItem> DataList

    public FinishedTaskAdapter(Context context, List<FinishedTaskItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        finishedTaskItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public FinishedTaskAdapter.MyScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);

        return new FinishedTaskAdapter.MyScoreHolder(itemView, mListener);
    }


    @Override
    public void onBindViewHolder(final FinishedTaskAdapter.MyScoreHolder holder, final int position) {
        holder.llData.setBackgroundColor(Color.TRANSPARENT);

        if(finishedTaskItemList.size() > 0) {
            //Log.d("logggASDASDASDASD", "onBindViewHolder: "+scoreUserItemList);
            final FinishedTaskItem scoreUserItem = finishedTaskItemList.get(position);
            holder.tvBab.setText(scoreUserItem.getMaterialName());
            holder.tvScore.setText(scoreUserItem.getSubmission());
            holder.tvMaterial.setText(scoreUserItem.getCreatedAt());

        }
        else{
           holder.rvScore.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public int getItemCount() {
        return finishedTaskItemList.size();
    }

    public class MyScoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvBab, tvScore, tvMaterial;
        Button btnAdd;
        TextView tvQty;
        RelativeLayout llData;
        RelativeLayout rvScore;


        private MyScoreHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
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

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<FinishedTaskItem> getItems() {
        return finishedTaskItemList;
    }

    public FinishedTaskItem getItem(int position) {
        return finishedTaskItemList.get(position);
    }


}
