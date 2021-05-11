package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailOnsiteItemActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.OnsiteItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Random;

public class OnsiteAdapter extends RecyclerView.Adapter<OnsiteAdapter.OnsiteHolder> {
    List<OnsiteItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private OnsiteAdapter.OnItemClicked onClick;
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

    public OnsiteAdapter(Context context, List<OnsiteItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public OnsiteAdapter.OnsiteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_rev, parent, false);

        return new OnsiteAdapter.OnsiteHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final OnsiteAdapter.OnsiteHolder holder, @SuppressLint("RecyclerView") final int position) {

        final OnsiteItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);


        //Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset)+"image/carefast/"+item.getImage()).resize(360,300).
                Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset_quiz)+"event/image/" + item.getImage()).resize(360,300)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        //holder.load_gif.setVisibility(View.GONE);
                        holder.cvOnsite.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(final Drawable errorDrawable) {
                        //Log.d("TAG", "FAILED");
                    }

                    @Override
                    public void onPrepareLoad(final Drawable placeHolderDrawable) {
                        holder.cvOnsite.setBackgroundResource(R.drawable.load_gambar);
                    }
                });


        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getAuthor());

        holder.btnDaftar.setOnClickListener(new View.OnClickListener() {
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
                    pref.put(PrefContract.cour_type, item.getCourseType());
                    pref.put(PrefContract.cour_id,item.getId());
                } catch (AppException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, DetailOnsiteItemActivity.class);
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
        return dataItemList.size();
    }

    public class OnsiteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        CardView linearLayout;
        TextView title;
        TextView writer;
        CardView cvOnsite;
        Button btnDaftar;



        private OnsiteHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
            imageView = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.linearFeatured);
            title = itemView.findViewById(R.id.item_title);
            writer = itemView.findViewById(R.id.item_writer);
            cvOnsite = itemView.findViewById(R.id.card0);
            btnDaftar = itemView.findViewById(R.id.btnDaftarEvent);

            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<OnsiteItem> getItems() {
        return dataItemList;
    }

    public OnsiteItem getItem(int position) {
        return dataItemList.get(position);
    }
}