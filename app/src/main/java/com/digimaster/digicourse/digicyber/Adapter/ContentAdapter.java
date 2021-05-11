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
import androidx.core.content.FileProvider;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.MateriItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyCoursesHolder> {
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
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public ContentAdapter(Context context, List<MateriItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ContentAdapter.MyCoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_content_adapter, parent, false);

        return new ContentAdapter.MyCoursesHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ContentAdapter.MyCoursesHolder holder, @SuppressLint("RecyclerView") final int position) {

        int roll = position-1;
        if(roll < 0){
            roll = 0;
        }

        final MateriItem item = dataItemList.get(position);
        final MateriItem item2 = dataItemList.get(roll);

        //holder.title.setText(item.getMaterial());
        holder.title.setText(item.getSubTaskName());

        if(position > 0) {
            if (item2.getSubTaskName().equals(item.getSubTaskName())) {
                holder.bab.setVisibility(View.GONE);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)holder.rvRoot.getLayoutParams();
                params.setMargins(0,-40,0,0);
                holder.rvRoot.setLayoutParams(params);

            } else {
                holder.bab.setText(item.getSubTaskName());
            }
        }else{
            holder.bab.setText(item.getSubTaskName());
        }
//      pref = new SecuredPreference(mContext, PrefContract.PREF_EL);



//        holder.don.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ShowDemo.class);
//                //String keyIdentifer  = null;
//                intent.putExtra("mtrl_id", item.getMaterialId());
//                //Log.d("logggMTRLMTRL", "onClick: "+item.getMaterialId());
//                mContext.startActivity(intent);
//
////                Uri uri = Uri.parse(mContext.getResources().getString(R.string.base_url_asset) + "pdf/" + item.getPdf()); // missing 'http://' will cause crashed
////                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
////                mContext.startActivity(intent);
//
//            }
//        });

        //Log.d("logggIMPORTANTANT", "onBindViewHolder: "+item);


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

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
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
        }else{

        }
    }

    public void view(String v)
    {
        //Toast.makeText(mContext, ""+v, Toast.LENGTH_SHORT).show();
        File pdfFile = new File(Environment.getExternalStorageDirectory() +"/"+v);  // -> filename = maven.pdf
        Uri path = FileProvider.getUriForFile(mContext, mContext.getApplicationContext() + "com.digimaster.digicourse.digilearn.provider"
                , pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        try{

            mContext.startActivity(pdfIntent);


        }catch(ActivityNotFoundException e){

            Toast.makeText(mContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class MyCoursesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView,don;
        LinearLayout linearLayout;
        TextView title, writer, bab;
        RelativeLayout rvRoot;
        LinearLayout rvmyCour;
        CardView cardViewMyCourse;

        private MyCoursesHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            pref = new SecuredPreference(context, PrefContract.PREF_EL);

            mListener = listener;
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.iv_header);
            title = itemView.findViewById(R.id.tvTitle);
            writer = itemView.findViewById(R.id.tvMateri);
            rvmyCour = itemView.findViewById(R.id.rvCourseAdap);
            bab = itemView.findViewById(R.id.tv_bab_my_course);
            cardViewMyCourse = itemView.findViewById(R.id.cd_my_course_adapter);
            rvRoot = itemView.findViewById(R.id.rv_root_mycourse_adapter);
            don = itemView.findViewById(R.id.ivDonCon);
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