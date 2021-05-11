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
import com.digimaster.digicourse.digicyber.Model.FaqItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.WishlistHolder> {

    List<FaqItem> faqItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdapterOnItemClickListener mListener;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public FaqAdapter(Context context, List<FaqItem> faqItems, AdapterOnItemClickListener listener) {
        this.mContext = context;
        faqItemList = faqItems;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public FaqAdapter.WishlistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item, parent, false);

        return new FaqAdapter.WishlistHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final FaqAdapter.WishlistHolder holder, @SuppressLint("RecyclerView") final int position) {


        final FaqItem faqItem = faqItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.tvNamaDosen.setText(faqItem.getFaqQuestion());
        holder.tvNamaMatkul.setText(faqItem.getFaqAnswer());



    }




    @Override
    public int getItemCount() {
        return  faqItemList == null ? 0 : faqItemList.size();
    }

    public class WishlistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTextDrawable;
        TextView tvNamaDosen;
        ReadMoreTextView tvNamaMatkul;
        LinearLayout linearLayoutWishlist;



        private WishlistHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;

            tvNamaDosen = itemView.findViewById(R.id.tvNamaFaq);
            tvNamaMatkul = itemView.findViewById(R.id.tvSeeMoreContentFaq);


        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<FaqItem> getItems() {
        return faqItemList;
    }

    public FaqItem getItem(int position) {
        return faqItemList.get(position);
    }


}
