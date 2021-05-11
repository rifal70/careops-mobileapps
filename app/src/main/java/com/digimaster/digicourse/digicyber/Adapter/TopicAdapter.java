package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.AssessmentActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.TopicDetailItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyCoursesHolder> {
    List<TopicDetailItem> dataItemList;
    Context mContext;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String title;

    public TopicAdapter(Context context, List<TopicDetailItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.title = title;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public TopicAdapter.MyCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_adapter_item, parent, false);

        return new TopicAdapter.MyCoursesHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TopicAdapter.MyCoursesHolder holder, @SuppressLint("RecyclerView") final int position) {

        final TopicDetailItem item = dataItemList.get(position);

//        Log.d("logggD", "position"+position);
//        Log.d("logggD", "size"+dataItemList.size());

        if (position == 0) {
            //Toast.makeText(mContext, "Posisinya 0", Toast.LENGTH_SHORT).show();
            holder.bab.setVisibility(View.VISIBLE);
            holder.bab.setText(item.getTopicName());
        }
        else {
            //ganti jadi <= biar yang terakhir keliatan
            if (position + 1 <= dataItemList.size()) {
                if (item.getTopicName().equals(getTopicNameNext(position - 1))) {
                    holder.bab.setVisibility(View.GONE);
                } else {
                    holder.bab.setVisibility(View.VISIBLE);
                    holder.bab.setText(getTopicNameNext(position));
                }
            } else if (position + 1 <= dataItemList.size() && !item.getTopicName().equals(getTopicNameNext(position - 1))){
                holder.bab.setVisibility(View.VISIBLE);
                holder.bab.setText(getTopicNameNext(position));

            }else {
                holder.bab.setVisibility(View.GONE);

            }
        }

        if (item.getFinished().equals("finish")) {
            holder.ivStatus.setImageResource(R.drawable.tick);
        } else {
            holder.ivStatus.setImageResource(R.drawable.right_arrow);
        }

        holder.action.setText(item.getActionName());


        if (item.getTopicAccess().equals("Random")) {

            holder.rvRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, AssessmentActivity.class);
                    intent.putExtra("quiz_topic_id", item.getTopicId());
                    intent.putExtra("quiz_action_id", item.getActionId());
                    intent.putExtra("sakral", item.getSakral());
                    mContext.startActivity(intent);
                    //((Activity) context).finish();
                }

            });
        } else if (item.getTopicAccess().equals("Lock")) {

            if (position == 0) {
                holder.rvRoot.setEnabled(true);

            } else {
                if (position + 1 < dataItemList.size()) {
                    if (getActionStatusNext(position + 1).equals("finish")) {
                        holder.rvRoot.setEnabled(true);
                    } else if (getActionStatusNext(position + 1).equals("none")) {
                        if(getActionStatusNext( position - 1).equals("finish")){

                            holder.rvRoot.setEnabled(true);

                        }else{
                            holder.rvRoot.setEnabled(false);
                        }
                    } else {
                        holder.rvRoot.setEnabled(false);
                    }
                }else if(position == dataItemList.size()-1){
                    if(item.getFinished().equals("finish")){

                        holder.rvRoot.setEnabled(true);
                    }else if(item.getFinished().equals("none")){
                        if(getActionStatusNext( dataItemList.size() - 2).equals("finish")) {
                            holder.rvRoot.setEnabled(true);
                            //-2 soalnya ngambil yg sebelum terakhir cek status finish apa belom

                        }else if(getActionStatusNext( dataItemList.size() - 2).equals("none")){
                            holder.rvRoot.setEnabled(false);

                        }
                    }
                }
            }
        }

        holder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AssessmentActivity.class);
                intent.putExtra("quiz_topic_id", item.getTopicId());
                intent.putExtra("quiz_action_id", item.getActionId());
                intent.putExtra("sakral", item.getSakral());
                mContext.startActivity(intent);
                //((Activity) context).finish();
            }
        });


    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class MyCoursesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        LinearLayout linearLayout, rvBab;
        TextView title, writer, bab, action;
        RelativeLayout rvRoot;
        RelativeLayout rvmyCour;
        CardView cardViewMyCourse;
        ExpandableLayout expandableLayout;
        Button btnFeedback;
        ImageView imageView, ivStatus;


        private MyCoursesHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            pref = new SecuredPreference(context, PrefContract.PREF_EL);

            mListener = listener;
            context = itemView.getContext();
            //title = itemView.findViewById(R.id.tvTitle);
            writer = itemView.findViewById(R.id.tvMateri);
            action = itemView.findViewById(R.id.tv_name_action);
            rvmyCour = itemView.findViewById(R.id.rvCourseAdap);
            bab = itemView.findViewById(R.id.tv_bab_my_course);
            cardViewMyCourse = itemView.findViewById(R.id.cd_my_course_adapter);
            rvRoot = itemView.findViewById(R.id.rv_root_mycourse_adapter);
            //btnFeedback = itemView.findViewById(R.id.btnFeedBack);
            rvBab = itemView.findViewById(R.id.rvBab);
            imageView = itemView.findViewById(R.id.toggleButtonDetailLib);
            ivStatus = itemView.findViewById(R.id.ivStatusAction);

            //expandableLayout = itemView.findViewById(R.id.expandable_layout);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());

        }
    }

    public List<TopicDetailItem> getItems() {
        return dataItemList;
    }

    public TopicDetailItem getItem(int position) {
        return dataItemList.get(position);
    }

    private String getTopicNameNext(int position) {
        return dataItemList.get(position).getTopicName();
    }

    private String getActionOrderNext(int position) {
        return dataItemList.get(position).getActionOrder();
    }

    private String getActionStatusNext(int position) {
        return dataItemList.get(position).getFinished();
    }

}