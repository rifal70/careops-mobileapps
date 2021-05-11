package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.amulyakhare.textdrawable.TextDrawable;
import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.CourseActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.OnlineItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.OnlineHolder> {
    List<OnlineItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private OnlineAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public OnlineAdapter(Context context, List<OnlineItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public OnlineAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new OnlineAdapter.OnlineHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final OnlineAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {

        final OnlineItem item = dataItemList.get(position);

            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
//
//            holder.ribbonLayout.setShowBottom(true);
//            holder.ribbonLayout.setShowHeader(true);
//
//            holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2b323a"));
//            holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));
//
//            holder.ribbonLayout.setHeaderText(item.getTitle());
            //holder.ribbonLayout.setBottomText("Rp. "+item.getPrice());

            Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset)+"image/digicom/"+item.getImage()).resize(360,300).into(holder.imageView);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(item.getTitle(), getColor());

        holder.cardView.setRadius(40);

        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getAuthor());
        holder.desc.setText(item.getDesc());

        holder.btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemId(position);
                //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);

                try {
                    pref.put(PrefContract.id_detail, item.getId());
                    pref.put(PrefContract.author, item.getAuthor());
                    pref.put(PrefContract.title, item.getTitle());
                    pref.put(PrefContract.img_url, item.getImage());
                    //pref.put(PrefContract.price, item.getPrice());
                    pref.put(PrefContract.cour_id, item.getId());
                    pref.put(PrefContract.vid_url_pre,item.getVid());
                    pref.put(PrefContract.desc,item.getDesc());

                    //pref.put(PrefContract.vid_url_pre,item.ge)

                } catch (AppException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(context, DetailItemActivity.class);
//                intent.putExtra("id_detail", item.getId());
//                intent.putExtra("price", item.getPrice());
//                context.startActivity(intent);
//                loading.dismiss();

                try {
                    pref.put(PrefContract.cour_id, item.getId());
                    pref.put(PrefContract.title,item.getTitle());
                    pref.put(PrefContract.img_url,item.getImage());
                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext, CourseActivity.class);
                mContext.startActivity(intent);
                loading.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class OnlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView, cvOuter;
        TextView title;
        TextView writer;
        TextView desc;
        Button btnBaca;



        private OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            //ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
            imageView = itemView.findViewById(R.id.ivItemOnline);
            //linearLayout = itemView.findViewById(R.id.linearFeatured);
            title = itemView.findViewById(R.id.item_title);
            writer = itemView.findViewById(R.id.item_writer);
            cardView = itemView.findViewById(R.id.card0);
            cvOuter = itemView.findViewById(R.id.card_view_outer);
            btnBaca = itemView.findViewById(R.id.btnBaca);
            desc = itemView.findViewById(R.id.item_desc);

            //linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<OnlineItem> getItems() {
        return dataItemList;
    }

    public OnlineItem getItem(int position) {
        return dataItemList.get(position);
    }

    public String[] mColors = {
            "#734b6d", // light blue
            "#fd746c", // mauve
    };

    public int getColor() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }
}
