package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digimaster.digicourse.digicyber.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

private Context mContext;
private List<String> mData;

public void add(String s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);
        }

public void remove(int position){
        if (position < getItemCount()  ) {
        mData.remove(position);
        notifyItemRemoved(position);
        }
        }

public static class SubjectViewHolder extends RecyclerView.ViewHolder {
    public final TextView title;
    ImageView ivDownload;

    public SubjectViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.txtCerti);
        ivDownload = view.findViewById(R.id.ivDownload);
    }
}

    public SubjectAdapter(Context context, String[] data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<String>(Arrays.asList(data));
        else mData = new ArrayList<String>();
    }

    public SubjectAdapter.SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.subject_adapter_item, parent, false);
        return new SubjectAdapter.SubjectViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SubjectAdapter.SubjectViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.title.setText(mData.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Position ="+position, Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}