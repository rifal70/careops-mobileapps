package com.digimaster.digicourse.digicyber.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.digimaster.digicourse.digicyber.Activity.AssessmentActivity;
import com.digimaster.digicourse.digicyber.Activity.ScoreActivity;
import com.digimaster.digicourse.digicyber.Contract.PrefContract;
import com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem;
import com.digimaster.digicourse.digicyber.R;
import com.digimaster.digicourse.digicyber.SecuredPreference;
import com.digimaster.digicourse.digicyber.apiHelper.BaseApiService;
import com.digimaster.digicourse.digicyber.apiHelper.UtilsApi;
import com.digimaster.digicourse.digicyber.util.AppException;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.keenfin.audioview.AudioService;
import com.keenfin.audioview.AudioView;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.github.kexanie.library.MathView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActionAdapter extends RecyclerView.Adapter<QuizActionAdapter.QuizHolder> {

    List<AssessmentQuizItem> AssessmentQuizItemList;
    Context mContext;
    private AdapterOnItemClickListener mListener;
    String passingImg;
    String uid;
    String mtrl_id, cour_id, module_id;
    SecuredPreference pref;
    ProgressDialog loading;
    BaseApiService mApiService;
    CountDownTimer detikDemiDetik;
    int pos = 1;
    int length;
    String answer, finalScoreFormat;
    int score;
    double scoreFinal;
    RelativeLayout rvQuiz;
    int soal = 1;
//    PDFView mPdf;
//    WebView pdf;
    String sHtmlTemplate = "<html><head></head><body><img src=\"file:///android_asset/img/mybadge.png\"></body></html>";


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public QuizActionAdapter(Context context, List<AssessmentQuizItem> DataList, AdapterOnItemClickListener listener) {
        this.mContext = context;
        AssessmentQuizItemList = DataList;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public QuizActionAdapter.QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_quiz_adapter, parent, false);


        return new QuizActionAdapter.QuizHolder(itemView, mListener);
    }


    @Override
    public void onBindViewHolder(final QuizActionAdapter.QuizHolder holder, int position) {

        //pos = sakral;
        //cek lagi si sakral ini emang bener2 sakral kayanya

        //mtrl_id == module_id
        Bundle bundle = ((Activity) mContext).getIntent().getExtras();
        if (bundle.getString("mtrl_id") != null) {
            mtrl_id = bundle.getString("mtrl_id");
        }

        answer = ""; //TOMBOL NEXT SUSAH DIPENCET
        length = AssessmentQuizItemList.size();
        //Log.d("logggLength", "length "+length);
        holder.bar.setMax(length);
        //holder.headerQA.setText(""+length);
        //Log.d("logggSAKRAL", "onBindViewHolder: "+holder.sakral);

        if (length < 0) {
            Toast.makeText(mContext, "No available action yet.", Toast.LENGTH_SHORT).show();
        } else {
            AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get(holder.sakral);
            loadAwal(holder, assessmentQuizItem);
        }


        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());

        //Toast.makeText(mContext,datetime, Toast.LENGTH_SHORT).show();

        try {
            pref.put(PrefContract.waktu_mulai, datetime);
        } catch (AppException e) {
            e.printStackTrace();
        }

        AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1);

        //check kondisi type quiz dan categorynya
        if (assessmentQuizItem.getCategory().equals("Quiz")) {
            //ini belom dibindviewholdernya
            if (assessmentQuizItem.getQuizType().equals("Essay")) {

                holder.llAnswer.setVisibility(View.GONE);

                holder.etEssay.setVisibility(View.VISIBLE);

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.sakral < length) {

                            //Kalo belom abis soalnya ini kodingannya

                            String answer = holder.etEssay.getText().toString();
                            //panggil fungsi submit progress

                            try {
                                pref.put(PrefContract.last_id, holder.sakral);
                            } catch (AppException e) {
                                e.printStackTrace();
                            }
                            //Log.d("logggAnsTR", "onClick: " + answer);
                            //Log.d("logggAINGGITULOCH", "pos terakhir setelah dinext " + pos);
                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                            holder.etEssay.getText().clear();

                            int xxx = holder.getAdapterPosition();

                            bindViewHolder(holder, xxx++);
                            //holder.sakral++;

                            //loadQu(holder, assessmentQuizItem, assessmentQuizItem2);
                        } else {
                            //Kalo soalnya udah abis, submit terus intent ke Score Activity

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String datetime_akhir = dateformat.format(c.getTime());

                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                            //detikDemiDetik.cancel();
                            Intent audioService = new Intent(mContext, AudioService.class);
                            mContext.stopService(audioService);

                            Intent intent = new Intent(mContext, ScoreActivity.class);
                            intent.putExtra("mtrl_id", mtrl_id);
                            intent.putExtra("waktu_akhir", datetime_akhir);
                            mContext.startActivity(intent);
                            ((AssessmentActivity) mContext).finish();

                        }

                    }
                });


            } else if (assessmentQuizItem.getQuizType().equals("Single")) {

                //kalo ada 5 pilihan statusnya lucky mate
                if (assessmentQuizItem.getGfStatus().equals("LUCKY MATE")) {

                    holder.llAnswer.setVisibility(View.VISIBLE);

                    holder.etEssay.setVisibility(View.GONE);


                    //posnya benerin -1 apa +2 terus udah deh yeay
                    holder.pil1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil1.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1

                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);
                                //answer = holder.pil1.getText().toString();
                                answer = assessmentQuizItem.getPil1();
                                //str[pos] = answer;

                            } else {


                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);

                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil2.isChecked()) {

                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);

                                //answer = holder.pil2.getText().toString();
                                answer = assessmentQuizItem.getPil2();
                                //str[pos] = answer;

                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);
                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil3.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);
                                //answer = holder.pil3.getText().toString();
                                answer = assessmentQuizItem.getPil3();
                                //str[pos] = answer;
                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil4.setChecked(false);
                                holder.pil5.setChecked(false);
                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil4.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil5.setChecked(false);

                                //answer = holder.pil4.getText().toString();
                                answer = assessmentQuizItem.getPil4();
                                //str[pos] = answer;
                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil5.setChecked(false);

                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });

                    holder.pil5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil5.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);

                                //answer = holder.pil4.getText().toString();
                                answer = assessmentQuizItem.getPil5();
                                //str[pos] = answer;
                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);

                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });

                    holder.btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!answer.equals("")) {
                                if (holder.sakral < length) {


                                    submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                                    holder.pil1.setChecked(false);
                                    holder.pil2.setChecked(false);
                                    holder.pil3.setChecked(false);
                                    holder.pil4.setChecked(false);
                                    holder.pil5.setChecked(false);

                                    int xxx = holder.getAdapterPosition();

                                    bindViewHolder(holder, xxx++);

                                } else {

                                    submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                                    Intent audioService = new Intent(mContext, AudioService.class);
                                    mContext.stopService(audioService);
                                    Intent intent = new Intent(mContext, ScoreActivity.class);
                                    intent.putExtra("mtrl_id", mtrl_id);
                                    mContext.startActivity(intent);
                                    ((AssessmentActivity) mContext).finish();
                                }
                            } else {
                                Snackbar snackbar = Snackbar.make(rvQuiz, "Please select the answer", Snackbar.LENGTH_SHORT);
                                View sbView = snackbar.getView();
                                sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                                snackbar.show();
                            }

                        }
                    });

                }
                //kalo ada 4 pilihan ini statusnya
                else {
                    holder.rvQues.setVisibility(View.VISIBLE);
                    holder.rvQues2.setVisibility(View.INVISIBLE);
                    holder.ivQuiz.setVisibility(View.INVISIBLE);
                    holder.vidTit.setVisibility(View.GONE);
                    holder.linear_lima.setVisibility(View.GONE);

                    holder.llAnswer.setVisibility(View.VISIBLE);

                    holder.etEssay.setVisibility(View.GONE);

                    //posnya benerin -1 apa +2 terus udah deh yeay
                    holder.pil1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil1.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1

                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                //answer = holder.pil1.getText().toString();
                                answer = assessmentQuizItem.getPil1();
                                //str[pos] = answer;

                            } else {


                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil2.isChecked()) {

                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                //answer = holder.pil2.getText().toString();
                                answer = assessmentQuizItem.getPil2();

                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil3.setChecked(false);
                                holder.pil4.setChecked(false);
                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil3.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil4.setChecked(false);
                                //answer = holder.pil3.getText().toString();
                                answer = assessmentQuizItem.getPil3();
                                //str[pos] = answer;
                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil4.setChecked(false);

                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });
                    holder.pil4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.pil4.isChecked()) {
                                com.digimaster.digicourse.digicyber.Model.AssessmentQuizItem assessmentQuizItem = AssessmentQuizItemList.get((holder.sakral) - 1); //harusnya mines 1
                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);
                                //answer = holder.pil4.getText().toString();
                                answer = assessmentQuizItem.getPil4();
                                //str[pos] = answer;
                            } else {


                                holder.pil1.setChecked(false);
                                holder.pil2.setChecked(false);
                                holder.pil3.setChecked(false);

                                answer = "";
//                Log.d("logggAns", "onClick: "+answer);
//                Log.d("logggAnsTR", "onClick: "+ assessmentQuizItem.getAnswer());
                            }
                        }
                    });


                    holder.btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //getItem(xxx);

                            if (!answer.equals("")) {

                                if (holder.sakral < length) {

                                    submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                                    holder.pil1.setChecked(false);
                                    holder.pil2.setChecked(false);
                                    holder.pil3.setChecked(false);
                                    holder.pil4.setChecked(false);

                                    int xxx = holder.getAdapterPosition();

                                    bindViewHolder(holder, xxx++);

                                } else {

                                    submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), answer);

                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    String datetime_akhir = dateformat.format(c.getTime());

                                    Intent audioService = new Intent(mContext, AudioService.class);
                                    mContext.stopService(audioService);
                                    Intent intent = new Intent(mContext, ScoreActivity.class);
                                    intent.putExtra("mtrl_id", mtrl_id);
                                    intent.putExtra("waktu_akhir", datetime_akhir);
                                    mContext.startActivity(intent);
                                    ((AssessmentActivity) mContext).finish();
                                }
                            } else {
                                Snackbar snackbar = Snackbar.make(rvQuiz, "Please select the answer", Snackbar.LENGTH_SHORT);
                                View sbView = snackbar.getView();
                                sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_alert));
                                snackbar.show();
                            }


                        }
                    });

                }
            }
        } else if (assessmentQuizItem.getCategory().equals("Material")) {

            if (assessmentQuizItem.getMaterialType().equals("Read")) {

                //Toast.makeText(mContext, "Material!", Toast.LENGTH_SHORT).show();

                holder.llAnswer.setVisibility(View.GONE);
                holder.etEssay.setVisibility(View.GONE);

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.sakral < length) {

                            try {
                                pref.put(PrefContract.last_id, holder.sakral);
                            } catch (AppException e) {
                                e.printStackTrace();
                            }

                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            int xxx = holder.getAdapterPosition();

                            bindViewHolder(holder, xxx++);
                            //holder.sakral++;

                            //loadQu(holder, assessmentQuizItem, assessmentQuizItem2);
                        } else {

                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String datetime_akhir = dateformat.format(c.getTime());


                            //detikDemiDetik.cancel();
                            Intent audioService = new Intent(mContext, AudioService.class);
                            mContext.stopService(audioService);

                            Intent intent = new Intent(mContext, ScoreActivity.class);
                            intent.putExtra("mtrl_id", mtrl_id);
                            intent.putExtra("waktu_akhir", datetime_akhir);
                            mContext.startActivity(intent);
                            ((AssessmentActivity) mContext).finish();

                        }

                    }
                });
            } else if (assessmentQuizItem.getMaterialType().equals("Watch")) {
//                holder.llAnswer.setVisibility(View.GONE);
//                holder.like.setVisibility(View.VISIBLE);
//                holder.rvQues.setVisibility(View.GONE);
//                holder.rvQues2.setVisibility(View.GONE);
//                holder.llTampung.setVisibility(View.INVISIBLE);
                holder.pertanyaan.setVisibility(View.GONE);
                holder.tvQue3.setVisibility(View.INVISIBLE);
//                holder.headerQAwal.setVisibility(View.GONE);
//                holder.bar.setVisibility(View.GONE);
//                holder.etEssay.setVisibility(View.GONE);
//                holder.vidTit.setVisibility(View.VISIBLE);
//                holder.vidDesc.setVisibility(View.VISIBLE);

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.sakral < length) {

                            try {
                                pref.put(PrefContract.last_id, holder.sakral);
                            } catch (AppException e) {
                                e.printStackTrace();
                            }

//                            if (holder.player != null) {
                                holder.player.release();
//                            }


                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            int xxx = holder.getAdapterPosition();

                            bindViewHolder(holder, xxx++);
                            //holder.sakral++;

                            //loadQu(holder, assessmentQuizItem, assessmentQuizItem2);
                        } else {

                            holder.player.release();

                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String datetime_akhir = dateformat.format(c.getTime());


                            //detikDemiDetik.cancel();
                            Intent audioService = new Intent(mContext, AudioService.class);
                            mContext.stopService(audioService);
                            holder.player.stop();


                            Intent intent = new Intent(mContext, ScoreActivity.class);
                            intent.putExtra("mtrl_id", mtrl_id);
                            intent.putExtra("waktu_akhir", datetime_akhir);
                            mContext.startActivity(intent);
                            ((AssessmentActivity) mContext).finish();

                        }

                    }
                });
            } else if (assessmentQuizItem.getMaterialType().equals("Pdf")) {
                holder.llAnswer.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
                holder.rvQues.setVisibility(View.GONE);
                holder.rvQues2.setVisibility(View.GONE);
                holder.llTampung.setVisibility(View.INVISIBLE);
                holder.pertanyaan.setVisibility(View.GONE);
                holder.headerQAwal.setVisibility(View.GONE);
                holder.bar.setVisibility(View.GONE);
                holder.etEssay.setVisibility(View.GONE);
                holder.vidTit.setVisibility(View.VISIBLE);
                holder.vidDesc.setVisibility(View.VISIBLE);
                holder.pdf.setVisibility(View.VISIBLE);
                holder.refresh.setVisibility(View.VISIBLE);

//                holder.pdf.fromAsset("pdf.pdf")
//                holder.pdf.fromUri(Uri.parse("http://54.251.83.205/careops/admin-organization/assets.admin_master/action/material/pdf/PDF_7_dusting.pdf"))

//                holder.pdf.setVerticalScrollBarEnabled(true);
//                holder.pdf.setHorizontalScrollBarEnabled(true);

                settings(holder);
                ((Activity) mContext).getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
                holder.vidDesc.setText(assessmentQuizItem.getVidDesc());

                holder.vidTit.setText(assessmentQuizItem.getVidTit());
//                holder.pdf.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//                holder.pdf.getSettings().setJavaScriptEnabled(true);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    // chromium, enable hardware acceleration
//                    holder.pdf.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                } else {
//                    // older android version, disable hardware acceleration
//                    holder.pdf.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                }
//                holder.pdf.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

                String link = assessmentQuizItem.getPdf();
                String pdf = "http://54.251.83.205/careops/admin-organization/assets.admin_master/action/material/pdf/"+ link;


//                holder.pdf.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);


                holder.pdf.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);

//                holder.pdf.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                holder.pdf.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//                holder.pdf.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    // chromium, enable hardware acceleration
//                    holder.pdf.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                } else {
//                    // older android version, disable hardware acceleration
//                    holder.pdf.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                }
                holder.refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        settings(holder);
                        holder.pdf.reload();

//                        holder.pdf.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//                        holder.pdf.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                        holder.pdf.setVerticalScrollBarEnabled(true);
//                        holder.pdf.setHorizontalScrollBarEnabled(true);
//                        holder.pdf.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//                        holder.pdf.getSettings().setJavaScriptEnabled(true);
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            // chromium, enable hardware acceleration
//                            holder.pdf.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                        } else {
//                            // older android version, disable hardware acceleration
//                            holder.pdf.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                        }
//                        holder.pdf.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

                        Toast.makeText(mContext, "Please wait..", Toast.LENGTH_SHORT).show();
                    }
                });



//                        .enableSwipe(true) // allows to block changing pages using swipe
//                        .enableDoubletap(true)
//                        .swipeHorizontal(false)
//                        .pageSnap(true)
//                        .autoSpacing(true)
//                        .pageFling(true)
//                        .enableSwipe(true)
//                        .scrollHandle(null)
//                        .swipeHorizontal(false)
//                        .pageSnap(true)
//                        .autoSpacing(true)
//                        .pageFling(true)
//                        .load();
                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.sakral < length) {

                            try {
                                pref.put(PrefContract.last_id, holder.sakral);
                            } catch (AppException e) {
                                e.printStackTrace();
                            }

//                            holder.player.release();


                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            int xxx = holder.getAdapterPosition();

                            bindViewHolder(holder, xxx++);
                            //holder.sakral++;

                            //loadQu(holder, assessmentQuizItem, assessmentQuizItem2);
                        } else {

//                            holder.player.release();

                            submit_progress_action(assessmentQuizItem.getTopicId(), assessmentQuizItem.getActionId(), "");

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String datetime_akhir = dateformat.format(c.getTime());


                            //detikDemiDetik.cancel();
                            Intent audioService = new Intent(mContext, AudioService.class);
                            mContext.stopService(audioService);
//                            holder.player.stop();


                            Intent intent = new Intent(mContext, ScoreActivity.class);
                            intent.putExtra("mtrl_id", mtrl_id);
                            intent.putExtra("waktu_akhir", datetime_akhir);
                            mContext.startActivity(intent);
                            ((AssessmentActivity) mContext).finish();

                        }

                    }
                });
            }
        }
    }


    private void submit_progress_action(String topic_id, String action_id, String answer) {

        try {
            uid = pref.getString(PrefContract.user_id, "");
            cour_id = pref.getString(PrefContract.cour_id, "");
            module_id = pref.getString(PrefContract.module_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.submit_progress(uid, cour_id, module_id, topic_id, action_id, answer)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                //String pesan = jsonRESULTS.getString("success");
                                String pesanError = jsonRESULTS.getString("message");
                                if (pesanError.equals("Submit progress success.")) {
                                    loading.dismiss();

                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();

                                    //PAYMENTTT
//                                    Intent intent = new Intent(ScoreActivity.this, HomeActivity.class);
//                                    startActivity(intent);

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(mContext, "" + pesanError, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }


    private void loadAwal(final QuizActionAdapter.QuizHolder holder, AssessmentQuizItem assessmentQuizItem) {
        try {
            pref.put(PrefContract.last_id, holder.sakral);
        } catch (AppException e) {
            e.printStackTrace();
        }
        holder.bar.setProgress(holder.sakral);

        if (assessmentQuizItem.getQuizAudio().equals("none")) {
            holder.audio.setVisibility(View.GONE);
            holder.llKontenTampung.setVisibility(View.GONE);
        } else {
            holder.llKontenTampung.setVisibility(View.VISIBLE);
            holder.audio.setVisibility(View.VISIBLE);
            Intent audioService = new Intent(mContext, AudioService.class);
            audioService.setAction(AudioService.ACTION_START_AUDIO);
            mContext.startService(audioService);
            try {
                holder.audio.setDataSource(mContext.getResources().getString(R.string.base_url_asset_quiz) + "action/quiz/audio/" + assessmentQuizItem.getQuizAudio());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (assessmentQuizItem.getFile().equals("none")) {
            holder.simpleExoPlayerView.setVisibility(View.GONE);
            holder.llKontenTampung.setVisibility(View.VISIBLE);
            holder.llTampung.setVisibility(View.VISIBLE);


//            holder.pdf.loadDataWithBaseURL("file:///android_asset/", sHtmlTemplate, "text/html", "utf-8", null);

//            holder.pdf.fromAsset("pdf.pdf").load();
//        } else if (holder.pdf != null){
//
//            holder.llKontenTampung.setVisibility(View.GONE);
//            holder.llTampung.setVisibility(View.GONE);
//            holder.simpleExoPlayerView.setVisibility(View.GONE);
//            holder.like.setVisibility(View.VISIBLE);
//            holder.vidTit.setVisibility(View.VISIBLE);
//            holder.vidDesc.setVisibility(View.GONE);
//
//
////            holder.pdf.loadDataWithBaseURL("file:///android_asset/", sHtmlTemplate, "text/html", "utf-8", null);
//
////            holder.pdf.fromAsset("pdf.pdf").load();
        } else {
            holder.llKontenTampung.setVisibility(View.GONE);
            holder.llTampung.setVisibility(View.GONE);
            holder.simpleExoPlayerView.setVisibility(View.VISIBLE);
            holder.like.setVisibility(View.VISIBLE);
            holder.vidTit.setVisibility(View.VISIBLE);
            holder.vidDesc.setVisibility(View.VISIBLE);

//            holder.pdf.loadDataWithBaseURL("file:///android_asset/", sHtmlTemplate, "text/html", "utf-8", null);

//            holder.pdf.fromAsset("pdf.pdf").load();
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            holder.player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            holder.simpleExoPlayerView.setPlayer(holder.player);

            holder.vidDesc.setText(assessmentQuizItem.getVidDesc());
            holder.vidTit.setText(assessmentQuizItem.getVidTit());

            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("Digilearn"))
                    .createMediaSource(Uri.parse(mContext.getResources().getString(R.string.base_url_asset_quiz) + "action/material/video/" + assessmentQuizItem.getFile()));

            holder.player.prepare(mediaSource);


            holder.player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == Player.STATE_ENDED) {
//                        holder.player.release();
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    holder.player.release();
                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
        }

        if (assessmentQuizItem.getQuizImage().equals("none")) {
            holder.ivQuiz.setVisibility(View.GONE);

            if (assessmentQuizItem.getContentImage().equals("none")) {
                holder.ivQuiz.setVisibility(View.GONE);

            } else {
                //base urlnya material/image kalo content image
                holder.ivQuiz.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset_quiz) + "action/material/image/" + assessmentQuizItem.getContentImage())
//                        .resize(1400, holder.ivQuiz.getHeight())
                        .into(holder.ivQuiz);
            }

        } else {
            holder.ivQuiz.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset_quiz) + "action/quiz/image/" + assessmentQuizItem.getQuizImage())
                    .into(holder.ivQuiz);
        }

        holder.tvQue2.config(
                "MathJax.Hub.Config({\n" +
                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                        "         SVG: { linebreaks: { automatic: true } }\n" +
                        "});");

        if (assessmentQuizItem.getQuestion() == null) {
            holder.like.setVisibility(View.VISIBLE);
            holder.tvQue2.setText(assessmentQuizItem.getContent());
            holder.tvQue3.setText(assessmentQuizItem.getVidTit());
            holder.headerQAwal.setVisibility(View.INVISIBLE);
            holder.rvQues.setVisibility(View.GONE);
            holder.rvQues2.setVisibility(View.VISIBLE);
            holder.llAnswer.setVisibility(View.GONE);

        } else {
            holder.tvQue.setText(assessmentQuizItem.getQuestion());

            holder.formulaOne.config(
                    "MathJax.Hub.Config({\n" +
                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                            "         SVG: { linebreaks: { automatic: true } }\n" +
                            "});");
            holder.formulaOne.setText(assessmentQuizItem.getPil1());
            holder.formulaTwo.setText(assessmentQuizItem.getPil2());
            holder.formulaThree.setText(assessmentQuizItem.getPil3());
            holder.formulaFour.setText(assessmentQuizItem.getPil4());
            holder.formulaFive.setText(assessmentQuizItem.getPil5());
        }


        holder.pertanyaan.setText("Pertanyaan " + (holder.sakral + 1));
        holder.headerQAwal.setText("Question " + (holder.sakral + 1) + " of " + length);

        //Log.d("logggScoreAwal", "score awal "+score);
        holder.sakral++;
        pos++;
        soal++;
    }

    private void settings(final QuizActionAdapter.QuizHolder holder) {
        if (Build.VERSION.SDK_INT >= 19) {
            holder.pdf.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            holder.pdf.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        WebSettings web_settings = holder.pdf.getSettings();
        web_settings.setJavaScriptEnabled(true);
        web_settings.setAllowContentAccess(true);
//        web_settings.setUseWideViewPort(true);
//        web_settings.setLoadsImagesAutomatically(true);
        web_settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        web_settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        web_settings.setEnableSmoothTransition(true);
        web_settings.setDomStorageEnabled(true);
    }

    private void loadQu(final QuizActionAdapter.QuizHolder holder, AssessmentQuizItem assessmentQuizItem, AssessmentQuizItem assessmentQuizItem2) {
        holder.bar.setProgress(holder.sakral);

        if (assessmentQuizItem.getQuizAudio().equals("none")) {
            holder.audio.setVisibility(View.INVISIBLE);
        } else {
            holder.audio.setVisibility(View.VISIBLE);
            Intent audioService = new Intent(mContext, AudioService.class);
            audioService.setAction(AudioService.ACTION_START_AUDIO);
            mContext.startService(audioService);
            try {
                holder.audio.setDataSource(mContext.getResources().getString(R.string.base_url_asset_quiz) + "action/quiz/audio/" + assessmentQuizItem.getQuizAudio());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (assessmentQuizItem.getQuizImage().equals("none")) {
            holder.ivQuiz.setVisibility(View.INVISIBLE);

        } else {
            holder.ivQuiz.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mContext.getResources().getString(R.string.base_url_asset) + "action/quiz/image/" + assessmentQuizItem.getQuizImage())
                    .into(holder.ivQuiz);
        }

        holder.formulaOne.config(
                "MathJax.Hub.Config({\n" +
                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                        "         SVG: { linebreaks: { automatic: true } }\n" +
                        "});");
        holder.formulaOne.setText(assessmentQuizItem.getPil1());
        holder.formulaTwo.setText(assessmentQuizItem.getPil2());
        holder.formulaThree.setText(assessmentQuizItem.getPil3());
        holder.formulaFour.setText(assessmentQuizItem.getPil4());
        holder.formulaFive.setText(assessmentQuizItem.getPil5());

        holder.tvQue.config(
                "MathJax.Hub.Config({\n" +
                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                        "         SVG: { linebreaks: { automatic: true } }\n" +
                        "});");

        if (assessmentQuizItem.getQuestion() == null) {
            holder.tvQue.setText(assessmentQuizItem.getContent());
        } else {
            holder.tvQue.setText(assessmentQuizItem.getQuestion());
        }

        holder.pertanyaan.setText("Pertanyaan " + (holder.sakral + 1));
        holder.headerQAwal.setText("Question " + (holder.sakral + 1) + " of " + length);
        if (assessmentQuizItem2.getAnswer().equals(answer)) {
            score++;
            scoreFinal = ((double) score / (double) length) * 100;
            finalScoreFormat = String.format("%.2f", scoreFinal);
            try {
                pref.put(PrefContract.score, finalScoreFormat);
                pref.put(PrefContract.score_cp, score);
            } catch (AppException e) {
                e.printStackTrace();
            }

            //Log.d("logggscore", "Jawaban Benar "+score);
        }


        holder.sakral++;
        pos++;
        soal++;
        answer = "";
        //Log.d("logggPOS", "sakral / pos: "+holder.sakral);

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rvQues, rvQues2;
        ImageView ivTextDrawable, ivQuiz;
        Button btnNext,refresh;
        TextView tvQty, tvNamaMatkul, pertanyaan, headerQAwal,vidDesc,vidTit, tvQue3;
        LinearLayout llData, linear_lima, llAnswer, llKontenTampung, llTampung;
        ToggleButton pil1, pil2, pil3, pil4, pil5;
//        PDFView pdf;
        WebView pdf;
        RoundCornerProgressBar bar;
        EditText etEssay;
        int sakral;
        AudioView audio;
        LikeButton like;
        MathView formulaOne, formulaTwo, formulaThree, formulaFour, formulaFive, tvQue,tvQue2;
        private SimpleExoPlayer player;
        SimpleExoPlayerView simpleExoPlayerView;

        private QuizHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mListener = listener;
            pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
            mApiService = UtilsApi.getAPIService();

            try {
                passingImg = pref.getString(PrefContract.img_url, "");
                uid = pref.getString(PrefContract.user_id, "");
                mtrl_id = pref.getString(PrefContract.material_id, "");
                sakral = pref.getInt(PrefContract.sakral, 0);
                score = pref.getInt(PrefContract.score_cp, 0); //ntar diambil skor terakhirnya berapa
            } catch (AppException e) {
                e.printStackTrace();
            }

            simpleExoPlayerView = itemView.findViewById(R.id.demoPlayerQuiz);
            pdf = itemView.findViewById(R.id.pdfViews);
            vidDesc = itemView.findViewById(R.id.vidDesc);
            vidTit = itemView.findViewById(R.id.vidTit);
            ivQuiz = itemView.findViewById(R.id.iv_quiz_adapter);
            ivTextDrawable = itemView.findViewById(R.id.ivTextDrawable);
            tvQue = itemView.findViewById(R.id.tvQuestion);
            tvQue2 = itemView.findViewById(R.id.tvQuestion2);
            tvQue3 = itemView.findViewById(R.id.tvQuestion3);
            tvNamaMatkul = itemView.findViewById(R.id.tvJadwalReqTeach);
            tvQty = itemView.findViewById(R.id.qty);
            etEssay = itemView.findViewById(R.id.etEssayQuizAdapter);

            llData = itemView.findViewById(R.id.ll_data);
            llAnswer = itemView.findViewById(R.id.ll_answer);
            llKontenTampung = itemView.findViewById(R.id.linearLayTampungKonten);
            llTampung = itemView.findViewById(R.id.linearLayTampung);

            pil1 = itemView.findViewById(R.id.pil1);
            pil2 = itemView.findViewById(R.id.pil2);
            pil3 = itemView.findViewById(R.id.pil3);
            pil4 = itemView.findViewById(R.id.pil4);
            pil5 = itemView.findViewById(R.id.pil5);

            audio = itemView.findViewById(R.id.avQuiz);

            pertanyaan = itemView.findViewById(R.id.tvPertanyaan);
            btnNext = itemView.findViewById(R.id.btnNextQA);
            bar = itemView.findViewById(R.id.progress_bar1);
            rvQuiz = itemView.findViewById(R.id.rvQuiz);
            //headerQA = itemView.findViewById(R.id.header_QA);
            rvQues = itemView.findViewById(R.id.rvQuestion);
            rvQues2 = itemView.findViewById(R.id.rvQuestion2);

            like = itemView.findViewById(R.id.like);



            headerQAwal = itemView.findViewById(R.id.headerQAwal);
            formulaOne = itemView.findViewById(R.id.formula_one);
            formulaTwo = itemView.findViewById(R.id.formula_two);
            formulaThree = itemView.findViewById(R.id.formula_three);
            formulaFour = itemView.findViewById(R.id.formula_four);
            formulaFive = itemView.findViewById(R.id.formula_five);
            refresh = itemView.findViewById(R.id.refresh);
            linear_lima = itemView.findViewById(R.id.linear_lima);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }

    }

    public List<AssessmentQuizItem> getItems() {
        return AssessmentQuizItemList;
    }

    public AssessmentQuizItem getItem(int position) {
        return AssessmentQuizItemList.get(position);
    }


}
