package com.digimaster.digicourse.digicyber.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.AssessmentActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.TopicDetailItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.digimaster.digicourse.digicyber.util.FileDownloader;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TopicLibraryAdapter extends RecyclerView.Adapter<TopicLibraryAdapter.MyCoursesHolder> {
    List<TopicDetailItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String title;
    RecyclerView.LayoutManager layoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String uid, cour_id;

    public TopicLibraryAdapter(Context context, List<TopicDetailItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.title = title;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public TopicLibraryAdapter.MyCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_adapter_item, parent, false);

        return new TopicLibraryAdapter.MyCoursesHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TopicLibraryAdapter.MyCoursesHolder holder, @SuppressLint("RecyclerView") final int position) {

        final TopicDetailItem item = dataItemList.get(position);

        if (position == 0) {
            holder.bab.setText(item.getTopicName());
        } else {
            holder.bab.setVisibility(View.GONE);
        }
        holder.action.setText(item.getActionName());

        holder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (item.getActionTipe().equals("Material")) {
//                    if (item.getActionMaterialTipe().equals("Read")) {
//
//                    } else if (item.getActionMaterialTipe().equals("Watch")) {
//
//                    }
//                } else if (item.getActionTipe().equals("Quiz")) {
//                    Intent intent = new Intent(mContext, AssessmentActivity.class);
//                    intent.putExtra("quiz_id", item.getActionId());
//                    mContext.startActivity(intent);
//                }
                Intent intent = new Intent(mContext, AssessmentActivity.class);
                intent.putExtra("quiz_id", item.getModuleId());
                mContext.startActivity(intent);
                ((Activity)context).finish();
            }

        });

//      pref = new SecuredPreference(mContext, PrefContract.PREF_EL);


    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

            File folder = new File(extStorageDirectory, "digicourse");


            File pdfFile = new File(extStorageDirectory, fileName);
            folder.mkdirs();

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            Toast.makeText(mContext, "Download Failed", Toast.LENGTH_SHORT).show();

        }
    }

    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void view(String v) {
        //Toast.makeText(mContext, ""+v, Toast.LENGTH_SHORT).show();
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + v);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            mContext.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
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
        ImageView imageView;


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

    private void submit_task(String submission, String task_id, String sub_task_id, String sub_task_name) {

        try {
            uid = pref.getString(PrefContract.user_id, "");
            cour_id = pref.getString(PrefContract.material_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);


    }
}