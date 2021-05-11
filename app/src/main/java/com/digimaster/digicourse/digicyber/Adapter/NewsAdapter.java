package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.NewsItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.WishlistHolder> {

    List<NewsItem> newsItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private NewsAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public NewsAdapter(Context context, List<NewsItem> wishList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        newsItemList = wishList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public NewsAdapter.WishlistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new NewsAdapter.WishlistHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final NewsAdapter.WishlistHolder holder, @SuppressLint("RecyclerView") final int position) {


        final NewsItem newsItem = newsItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.tvNewsTitle.setText(newsItem.getTitle());
        holder.tvContent.setText(newsItem.getIsi());
        holder.tvStatus.setText("Posted at :"+newsItem.getTimePosted());
//        Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset)+"image/" + newsItem.getCourseImage()).into(new Target(){
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                holder.ivTextDrawable.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(final Drawable errorDrawable) {
//                Log.d("TAG", "FAILED");
//            }
//
//            @Override
//            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                Log.d("TAG", "Prepare Load");
//            }
//        });
        //holder.tvNamaMatkul.setText(LovedByItem.getNormalPrice().toString());
        //sharedPrefManager = new SharedPrefManager(mContext);

        holder.linearLayoutWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItemId(position);



            }
        });


    }




    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class WishlistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNewsTitle, tvStatus;
        ReadMoreTextView tvContent;
        LinearLayout linearLayoutWishlist;



        private WishlistHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvNewsTitle = itemView.findViewById(R.id.tvNamaDosen);
            tvContent = itemView.findViewById(R.id.tvSeeMoreContentNews);
            tvStatus = itemView.findViewById(R.id.tvStatusReqTeach);

            linearLayoutWishlist = itemView.findViewById(R.id.linear_layout_wishlist);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<NewsItem> getItems() {
        return newsItemList;
    }

    public NewsItem getItem(int position) {
        return newsItemList.get(position);
    }


}
