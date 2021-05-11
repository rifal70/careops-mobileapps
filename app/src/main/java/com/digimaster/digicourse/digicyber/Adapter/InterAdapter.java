package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
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

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailItemActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.KurikulumInterItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class InterAdapter extends RecyclerView.Adapter<InterAdapter.InterHolder> {
    List<KurikulumInterItem> kurikulumInterItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private InterAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<AndroidItem> orderList

    public InterAdapter(Context context, List<KurikulumInterItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        kurikulumInterItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public InterAdapter.InterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new InterAdapter.InterHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final InterAdapter.InterHolder holder, @SuppressLint("RecyclerView") final int position) {

        final KurikulumInterItem item = kurikulumInterItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.ribbonLayout.setShowBottom(true);
        holder.ribbonLayout.setShowHeader(true);

        holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2b323a"));
        holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));

        holder.ribbonLayout.setHeaderText(item.getTitle());
        holder.ribbonLayout.setBottomText(item.getTitle());

        Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset)+"image/"+item.getImage()).resize(360,370).into(holder.imageView);




        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getAuthor());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemId(position);
                //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                try {
                    pref.put(PrefContract.id_detail, item.getId());
                    pref.put(PrefContract.author, item.getAuthor());
                    pref.put(PrefContract.title, item.getTitle());
                    pref.put(PrefContract.img_url, item.getImage());
                    pref.put(PrefContract.price, item.getPrice());
                    pref.put(PrefContract.cour_id, item.getId());
                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, DetailItemActivity.class);
                intent.putExtra("id_detail", item.getId());
                context.startActivity(intent);
            }
        });

    }



    private int getColor() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }


    @Override
    public int getItemCount() {
        return kurikulumInterItemList.size();
    }

    public class InterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        TextView title;
        TextView writer;



        private InterHolder(View itemView, AdapterOnItemClickListener listener) {

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

    public List<KurikulumInterItem> getItems() {
        return kurikulumInterItemList;
    }

    public KurikulumInterItem getItem(int position) {
        return kurikulumInterItemList.get(position);
    }
}