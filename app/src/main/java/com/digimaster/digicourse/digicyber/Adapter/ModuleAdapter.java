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

import com.digimaster.digicourse.digicyber.Activity.DetailModuleActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.ModuleDetailItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyCoursesHolder> {
    List<ModuleDetailItem> dataItemList;
    Context mContext;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String title;


    public ModuleAdapter(Context context, List<ModuleDetailItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.title = title;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ModuleAdapter.MyCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_courses_adapter, parent, false);

        return new ModuleAdapter.MyCoursesHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ModuleAdapter.MyCoursesHolder holder, @SuppressLint("RecyclerView") final int position) {

        final ModuleDetailItem item = dataItemList.get(position);

        //pref harus taro disini biar jalan anjenggg
        pref = new SecuredPreference(context, PrefContract.PREF_EL);

        holder.bab.setText(item.getModuleName());

        //Toast.makeText(mContext, item.getModuleId(), Toast.LENGTH_SHORT).show();

        if (item.getCourseAccess().equals("Random")) {
            holder.rvRoot.setEnabled(true);

        } else if (item.getCourseAccess().equals("Lock")) {
            if (position == 0) {
                holder.rvRoot.setEnabled(true);

            } else {

                if (position + 1 < dataItemList.size()) {
                    if (getModuleNextStatus(position + 1).equals("finish")) {
                        holder.rvRoot.setEnabled(true);
                    } else if (getModuleNextStatus(position + 1).equals("none")) {
                        if (getModuleNextStatus(position - 1).equals("finish")) {

                            holder.rvRoot.setEnabled(true);

                        } else {
                            holder.rvRoot.setEnabled(false);
                        }
                    } else {
                        holder.rvRoot.setEnabled(false);
                    }
                } else if (position == dataItemList.size() - 1) {
                    if (item.getModuleFinish().equals("finish")) {

                        holder.rvRoot.setEnabled(true);
                    } else if (item.getModuleFinish().equals("none")) {
                        if (getModuleNextStatus(dataItemList.size() - 2).equals("finish")) {
                            holder.rvRoot.setEnabled(true);
                            //-2 soalnya ngambil yg sebelum terakhir cek status finish apa belom
                        } else if (getModuleNextStatus(dataItemList.size() - 2).equals("none")) {
                            holder.rvRoot.setEnabled(false);

                        }
                    }
                }
            }
        }

        holder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    pref.put(PrefContract.module_id, item.getModuleId());
                    pref.put(PrefContract.module_id_from_module_adapter, item.getModuleId());
//                    pref.put(PrefContract.course_id_from_module_adapter, item.getC());
                    pref.put(PrefContract.module_title, item.getModuleName());
                    pref.put(PrefContract.module_desc, item.getModuleDesc());
                    pref.put(PrefContract.module_img, item.getModuleImage());
                } catch (AppException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(mContext, DetailModuleActivity.class);
                intent.putExtra("title", item.getModuleName());
                intent.putExtra("topic", item.getTotalTopic());
                intent.putExtra("author", item.getCourseAuthor());
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class MyCoursesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, writer, bab;
        RelativeLayout rvRoot;
        RelativeLayout rvmyCour;
        LinearLayout rvBab;
        CardView cardViewMyCourse;
        ExpandableLayout expandableLayout;
        Button btnFeedback;
        ImageView imageView;


        private MyCoursesHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();

            mListener = listener;
            context = itemView.getContext();
            //title = itemView.findViewById(R.id.tvTitle);
            writer = itemView.findViewById(R.id.tvMateri);
            rvmyCour = itemView.findViewById(R.id.rvCourseAdap);
            bab = itemView.findViewById(R.id.tv_bab_my_course);
            cardViewMyCourse = itemView.findViewById(R.id.cd_my_course_adapter);
            rvRoot = itemView.findViewById(R.id.rv_root_mycourse_adapter);
            //btnFeedback = itemView.findViewById(R.id.btnFeedBack);
            rvBab = itemView.findViewById(R.id.rvBab);
            imageView = itemView.findViewById(R.id.toggleButtonDetailLib);

            //expandableLayout = itemView.findViewById(R.id.expandable_layout);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());

        }
    }

    public List<ModuleDetailItem> getItems() {
        return dataItemList;
    }

    public ModuleDetailItem getItem(int position) {
        return dataItemList.get(position);
    }

    private String getModuleNextStatus(int position) {
        return dataItemList.get(position).getModuleFinish();
    }


}