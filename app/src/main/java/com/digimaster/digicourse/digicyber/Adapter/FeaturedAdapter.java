package com.digimaster.digicourse.digicyber.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.DetailItemActivity;
import com.digimaster.digicourse.digicyber.Model.FeaturedItem;
import com.digimaster.digicourse.digicyber.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carios on 19/02/18.
 */


class FeaturedViewHolder extends RecyclerView.ViewHolder{
    Context context;
    RibbonLayout ribbonLayout;
    ImageView imageView;
    LinearLayout linearLayout;
    TextView title;
    TextView writer;



    public FeaturedViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
        imageView = itemView.findViewById(R.id.imageView);
        linearLayout = itemView.findViewById(R.id.linearFeatured);
        title = itemView.findViewById(R.id.item_title);
        writer = itemView.findViewById(R.id.item_writer);
    }
}

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedViewHolder>{

    Context context;

    public FeaturedAdapter(Context context, List<FeaturedItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    List<FeaturedItem> itemList;
    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, final int position) {
        final FeaturedItem item = itemList.get(position);
        if(item.type==0){

            holder.ribbonLayout.setShowBottom(true);
            holder.ribbonLayout.setShowHeader(true);

            holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2b323a"));
            holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));

            holder.ribbonLayout.setHeaderText(item.headerText);
            holder.ribbonLayout.setBottomText(item.bottomText);

            Picasso.with(context).load(item.imgUrl).into(holder.imageView);

        }else if(item.type==1){
            holder.ribbonLayout.setShowBottom(true);
            holder.ribbonLayout.setShowHeader(true);

            holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#b94948"));
            holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#ffffff"));

            holder.ribbonLayout.setHeaderText(item.headerText);
            holder.ribbonLayout.setBottomText(item.bottomText);

            Picasso.with(context).load(item.imgUrl).into(holder.imageView);
        }else{
            holder.ribbonLayout.setShowBottom(false);
            holder.ribbonLayout.setShowHeader(false);


            Picasso.with(context).load(item.imgUrl).into(holder.imageView);
        }

        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getWriter());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemId(position);
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailItemActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
