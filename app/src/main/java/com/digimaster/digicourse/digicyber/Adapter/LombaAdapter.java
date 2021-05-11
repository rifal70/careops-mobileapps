package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Activity.eAssessActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.LombaItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LombaAdapter extends RecyclerView.Adapter<LombaAdapter.AssessHolder> {
    List<LombaItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private LombaAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String passCek;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public LombaAdapter(Context context, List<LombaItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public LombaAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new LombaAdapter.AssessHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final LombaAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final LombaItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);



        holder.ribbonLayout.setShowBottom(true);
        holder.ribbonLayout.setShowHeader(true);

        holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2b323a"));
        holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));

        holder.ribbonLayout.setHeaderText(item.getTitle());

        if(item.getPrice().equals("0")){
            holder.ribbonLayout.setBottomText("FREE");

        }else {
            holder.ribbonLayout.setBottomText("Rp. " + item.getPrice());
        }

        Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset)+"image/"+item.getImage()).resize(360,300).into(holder.imageView);


        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getAuthor());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemId(position);

                try {
                    passCek = pref.getString(PrefContract.check_login, "false");
                } catch (AppException e) {
                    e.printStackTrace();
                }

                //Log.d("ngentod", ""+passCek);

                if(passCek.equals("false")){
                    Toast.makeText(mContext, "Please login first.", Toast.LENGTH_SHORT).show();

                    Intent intentSignIn = new Intent (mContext, HomeActivity.class);
                    ActivityOptions optionsSignIn =
                            ActivityOptions.makeCustomAnimation(mContext, R.anim.right, R.anim.exit_left);
                    intentSignIn.putExtra("EXTRA", "openFragmentSign");
                    mContext.startActivity(intentSignIn, optionsSignIn.toBundle());
                }else {
                    //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                    try {
                        pref.put(PrefContract.id_detail, item.getId());
                        pref.put(PrefContract.author, item.getAuthor());
                        pref.put(PrefContract.title, item.getTitle());
                        pref.put(PrefContract.img_url, item.getImage());
                        pref.put(PrefContract.price, item.getPrice());
                        pref.put(PrefContract.cour_id, item.getId());

                        //pref.put(PrefContract.vid_url_pre,item.ge)

                    } catch (AppException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(context, eAssessActivity.class);
                    intent.putExtra("id_detail", item.getId());
                    context.startActivity(intent);
                }
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
        TextView title;
        TextView writer;



        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
            imageView = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.linearFeatured);
            title = itemView.findViewById(R.id.item_title);
            writer = itemView.findViewById(R.id.item_writer);

            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<LombaItem> getItems() {
        return dataItemList;
    }

    public LombaItem getItem(int position) {
        return dataItemList.get(position);
    }
}

