package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.digimaster.digicourse.digicyber.Activity.FaqActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.FaqCatItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;

import java.util.List;

public class faqcategoryadapter extends RecyclerView.Adapter<faqcategoryadapter.WishlistHolder> {

    List<FaqCatItem> faqItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdapterOnItemClickListener mListener;
    SecuredPreference pref;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    //Context context, AdapterOnItemClickListener listener, AdapterOnTextChangeListener listener2, List<LovedByItem> orderList

    public faqcategoryadapter(Context context, List<FaqCatItem> faqItems, AdapterOnItemClickListener listener) {
        this.mContext = context;
        faqItemList = faqItems;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public faqcategoryadapter.WishlistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqcategory_item, parent, false);

        return new faqcategoryadapter.WishlistHolder(itemView, mListener);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final faqcategoryadapter.WishlistHolder holder, @SuppressLint("RecyclerView") final int position) {


        final FaqCatItem faqItem = faqItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        holder.btncat.setText(faqItem.getFaqCategory());
        String val =faqItem.getFaqCategory();
        holder.btncat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FaqCatItem faqItem = faqItemList.get(position);

                Intent intent = new Intent(mContext, FaqActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("cat",faqItem.getFaqCategory());



                mContext.startActivity(intent);
            }
        });
//        holder.btncat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Intent intent = new Intent(mContext, FaqActivity.class);
//                    intent.putExtra("pdf",holder.btncat.getText().toString());
//                    mContext.startActivity(intent);
//
//
//
//            }
//        });


    }




    @Override
    public int getItemCount() {
        return  faqItemList == null ? 0 : faqItemList.size();
    }

    public class WishlistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btncat;


        private WishlistHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;

            btncat = itemView.findViewById(R.id.btncategory);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<FaqCatItem> getItems() {
        return faqItemList;
    }

    public FaqCatItem getItem(int position) {
        return faqItemList.get(position);
    }


}
