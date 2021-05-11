package com.digimaster.digicourse.digicyber.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.battleent.ribbonviews.RibbonLayout;
import com.digimaster.digicourse.digicyber.Activity.TitleCourseHalamanAchievement;
import com.digimaster.digicourse.digicyber.Activity.HomeActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Fragment.ListMemberFragment;
import com.digimaster.digicourse.digicyber.Model.LibraryItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;

import java.util.List;

public class AchievementCourseAdapter extends RecyclerView.Adapter<AchievementCourseAdapter.OnlineHolder> {
    //bawah ini
    List<LibraryItem> dataItemList;

    //tryjuardisenin

    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private LibraryAdapter.OnItemClicked onClick;
    private static AdapterOnItemClickListener mListener;
    static BaseApiService mApiService;
    ProgressDialog loading;
    static Context context;
    SecuredPreference pref;
    String role;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public AchievementCourseAdapter(Context context, List<LibraryItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public AchievementCourseAdapter.OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_achievement, parent, false);

        return new AchievementCourseAdapter.OnlineHolder(itemView, mListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final AchievementCourseAdapter.OnlineHolder holder, @SuppressLint("RecyclerView") final int position) {

        final LibraryItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);


        try {
            role = pref.getString(PrefContract.role, "false");
        } catch (AppException e) {
            e.printStackTrace();
        }

        if (role.equals("admin")) {
            holder.btnAssign.setVisibility(View.VISIBLE);
            holder.btnBaca.setVisibility(View.GONE);

            holder.btnAssign.setOnClickListener(view -> {
                Fragment fragment = null;
                Bundle bundle = new Bundle();

                //initryyangcom  bundle.putString("task_id", item.getId()); // Put anything what you want
                bundle.putString("task_type", "2"); // Put anything what you want

                fragment = new ListMemberFragment();
                fragment.setArguments(bundle);

                //Log.d("logI","harusnya udah masuk intent extra");
                fragmentManager = ((HomeActivity) mContext).getSupportFragmentManager();
                ft = fragmentManager.beginTransaction();

                ft.replace(R.id.screen_area, fragment);
                ft.commit();
            });
        }

//        Picasso.with(context).load(mContext.getResources().getString(R.string.base_url_asset) + "library/image/" + item.getImage()).resize(360, 300)
//                .into(new Target() {
//
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        //holder.load_gif.setVisibility(View.GONE);
//                        holder.imageView.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
//                    }
//
//                    @Override
//                    public void onBitmapFailed(final Drawable errorDrawable) {
//                        //Log.d("TAG", "FAILED");
//                    }
//
//                    @Override
//                    public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                        holder.imageView.setBackgroundResource(R.drawable.load_gambar);
//                    }
//                });


        holder.cardView.setRadius(40);

        holder.title.setText(item.getCourseName());



        holder.writer.setText("Created By: " + item.getInstitutName());
        //holder.desc.setText(item.getDesc());

        holder.btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemId(position);
                //Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);


//                Intent intent = new Intent(context, DetailItemActivity.class);
//                intent.putExtra("id_detail", item.getId());
//                intent.putExtra("price", item.getPrice());
//                context.startActivity(intent);
//                loading.dismiss();

                try {
                    pref.put(PrefContract.cour_id, item.getCourseId());

                    pref.put(PrefContract.title, item.getCourseName());
                    //  pref.put(PrefContract.img_url, item.getImage());
                    //initryyangcomment    pref.put(PrefContract.id_detail, item.getId());

                } catch (AppException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent (mContext, TitleCourseHalamanAchievement.class);
                //  Intent intent = new Intent(mContext, DetailLibActivity.class);
                //initryyangcomment intent.putExtra("id_detail", item.getId());
                mContext.startActivity(intent);
                loading.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {

        return  dataItemList == null ? 0 : dataItemList.size();

    }

    public static class OnlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RibbonLayout ribbonLayout;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView,btnBaca, cvOuter;
        TextView title,action,modul,topic;
        TextView writer;
        TextView desc;
        Button btnAssign;


        public OnlineHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
            //ribbonLayout = itemView.findViewById(R.id.ribbonLayout_item_online);
            imageView = itemView.findViewById(R.id.ivTaskLibrary);
            //linearLayout = itemView.findViewById(R.id.linearFeatured);
            title = itemView.findViewById(R.id.item_title);
            writer = itemView.findViewById(R.id.item_writer2);//inidiganti2 tryjuardi
            cardView = itemView.findViewById(R.id.card0);
            cvOuter = itemView.findViewById(R.id.card_view_outer);
            btnBaca = itemView.findViewById(R.id.card0);
            btnAssign = itemView.findViewById(R.id.btnAssign);
            action = itemView.findViewById(R.id.tv_action);
            modul = itemView.findViewById(R.id.tv_module);
            topic = itemView.findViewById(R.id.tv_session);
            desc = itemView.findViewById(R.id.item_desc);

            //linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<LibraryItem> getItems() {
        return dataItemList;
    }

    public LibraryItem getItem(int position) {
        return dataItemList.get(position);
    }


}
