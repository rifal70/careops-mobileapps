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
import com.digimaster.digicourse.digicyber.Model.KurikulumIndoItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IndoAdapter extends RecyclerView.Adapter<IndoAdapter.OnlineHolder> {
    List<KurikulumIndoItem> kurikulumIndoItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private IndoAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public IndoAdapter(Context context, List<KurikulumIndoItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        kurikulumIndoItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public IndoAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_indo_adapter, parent, false);

        return new IndoAdapter.OnlineHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final IndoAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {

        final KurikulumIndoItem item = kurikulumIndoItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.ribbonLayout.setShowBottom(true);
        holder.ribbonLayout.setShowHeader(false);

        holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2b323a"));
        holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));

        //holder.ribbonLayout.setHeaderText(item.getTitle());
        holder.ribbonLayout.setBottomText(item.getTitle());

        Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset)+"image/"+item.getImage()).
                resize(360,370).into(holder.imageView);




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


    @Override
    public int getItemCount() {
        return kurikulumIndoItemList.size();
    }

    public class OnlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        TextView title;
        TextView writer;



        private OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

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

    public List<KurikulumIndoItem> getItems() {
        return kurikulumIndoItemList;
    }

    public KurikulumIndoItem getItem(int position) {
        return kurikulumIndoItemList.get(position);
    }
}

