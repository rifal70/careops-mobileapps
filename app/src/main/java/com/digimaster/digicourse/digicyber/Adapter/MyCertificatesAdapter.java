package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.Activity.SimpleTabsActivity;
import com.digimaster.digicourse.digicyber.Activity.WebViewActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.CertItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.digimaster.digicourse.digicyber.util.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyCertificatesAdapter extends RecyclerView.Adapter<MyCertificatesAdapter.MyCertificatesViewHolder>  {

    Context mContext;
    List<CertItem> certItemList;
    BaseApiService mApiService;
    SecuredPreference pref;
    String uid,name, cour_id;
    private AdapterOnItemClickListener mListener;



    public MyCertificatesAdapter(Context context, List<CertItem> certItems, AdapterOnItemClickListener listener) {
        this.mContext = context;
        certItemList = certItems;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    public MyCertificatesAdapter.MyCertificatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.activity_my_certificates_adapter, parent, false);
        return new MyCertificatesAdapter.MyCertificatesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyCertificatesAdapter.MyCertificatesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CertItem item = certItemList.get(position);

        holder.title.setText(item.getTitle());
        holder.tvFinish.setText("Finished at "+item.getCreatedAt());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Position ="+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new  Intent(mContext, WebViewActivity.class);
                intent.putExtra("id",uid);
                intent.putExtra("cour_id",cour_id);
                mContext.startActivity(intent);
                ((SimpleTabsActivity) mContext).finish();

            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//            File path = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DOCUMENTS);
//            path.mkdirs();
            File folder = new File(extStorageDirectory, "digicourse_certificate");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

    /*public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 7: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
                    view();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(mContext, "Please Allow Permission", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

    public void view(String tangkap)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/digicourse_certificate/" +tangkap);  // -> filename = maven.pdf
        Uri path = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() +
                ".provider", pdfFile);
                //Uri.fromFile(pdfFile);

        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try{
            mContext.startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(mContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return certItemList.size();
    }

    public class MyCertificatesViewHolder extends RecyclerView.ViewHolder {
        TextView title, tvFinish;
        ImageView ivDownload;

        public MyCertificatesViewHolder(View view) {
            super(view);

            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mApiService = UtilsApi.getAPIService();

            try {
                uid = pref.getString(PrefContract.user_id, "");
                name = pref.getString(PrefContract.USER_FULL_NAME, "");
                cour_id = pref.getString(PrefContract.cour_id, "");
            } catch (AppException e) {
                e.printStackTrace();
            }

            title = view.findViewById(R.id.txtCerti);
            tvFinish = view.findViewById(R.id.tvFinishAtMyCert);
            ivDownload = view.findViewById(R.id.ivDownload);
        }
    }
}
