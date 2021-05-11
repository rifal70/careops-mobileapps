package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailCourseActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CourseByUidItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.AssessHolder> {
    List<CourseByUidItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private CourseAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String passCek, role;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public  CourseAdapter(Context context, List<CourseByUidItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public CourseAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_task, parent, false);

        return new CourseAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final CourseAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final CourseByUidItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        try {
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(item.getTitle());
        holder.tvModule.setText(item.getTotalModule()+" Modules|");
        holder.tvSession.setText(item.getTotalTopic()+" Topics|");
        holder.tvAction.setText(item.getTotalAction()+" Activities");
        holder.tvCreatedBy.setText("Created By : "+item.getAuthor());

        //progress
        holder.progressBar.setMax(Float.parseFloat(item.getTotalAction()));
        holder.progressBar.setProgress(Float.parseFloat(item.getTotalFinished()));

        //float progressNumber = holder.progressBar.getProgress()*10;
//        String progress = String.valueOf(holder.progressBar.getProgress()*10);
//        String progressFix = progress+"%";

        holder.tvPercentage.setText((int) holder.progressBar.getProgress()+"/"+item.getTotalAction());

        if(holder.progressBar.getProgress() > 0) {
            holder.btnBaca.setBackgroundResource(R.drawable.rounded_button_color);
            holder.btnBaca.setText("Continue");

        }
//        else{
//            holder.btnBaca.setBackgroundResource(R.drawable.rounded_button_color);
//            holder.btnBaca.setText("Continue");
//        }

        holder.btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailCourseActivity.class);
                try {
                    pref.put(PrefContract.cour_id, item.getId());
                    pref.put(PrefContract.detail_course_frag, item.getDetail());

                    pref.put(PrefContract.author, item.getAuthor());
                    pref.put(PrefContract.cour_image, item.getImage());

                    pref.put(PrefContract.tot_action, item.getTotalAction());
                    pref.put(PrefContract.tot_finished, item.getTotalFinished());

                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("author", item.getAuthor());
                    intent.putExtra("module", item.getTotalModule());
                    intent.putExtra("topic", item.getTotalTopic());
                    intent.putExtra("action", item.getTotalAction());

                } catch (AppException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class AssessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        TextView tvTitle;
        TextView tvModule, tvSession, tvAction, tvCreatedBy, tvPercentage;
        Button btnBaca, btnAssign;
        CardView cardView;
        RoundCornerProgressBar progressBar;


        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.ivTaskLibrary);
            btnBaca = itemView.findViewById(R.id.btnBaca);
            linearLayout = itemView.findViewById(R.id.llItemLayout3);
            tvModule = itemView.findViewById(R.id.tv_module);
            tvSession = itemView.findViewById(R.id.tv_session);
            tvAction = itemView.findViewById(R.id.tv_action);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvCreatedBy = itemView.findViewById(R.id.item_writer);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
            cardView = itemView.findViewById(R.id.card_view_outer);
            btnAssign = itemView.findViewById(R.id.btnAssign);
            progressBar = itemView.findViewById(R.id.progress_bar1);


        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<CourseByUidItem> getItems() {
        return dataItemList;
    }

    public CourseByUidItem getItem(int position) {
        return dataItemList.get(position);
    }
}

