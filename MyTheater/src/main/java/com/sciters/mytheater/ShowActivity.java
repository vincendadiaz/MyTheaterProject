package com.sciters.mytheater;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.ReminderService;
import com.sciters.mytheater.helper.adapter.ComingShowsListViewAdapter;
import com.sciters.mytheater.helper.adapter.ShowMembersListViewAdapter;
import com.sciters.mytheater.helper.parser.JSONParser;
import com.sciters.mytheater.helper.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class ShowActivity extends Activity {
    private  String id;
    ListView listViewShowMembers;
    ArrayList<Member> showMembersArrayList = new ArrayList<Member>();
    ShowMembersListViewAdapter showMembersListViewAdapter;
    ProgressBar progressBarShowInfo;
    ProgressBar progressBarShowPerformers;
    LinearLayout layoutShowInfo;
    LinearLayout layoutShowPerformers;
    LinearLayout layoutShowDateRecentPlaceholder;
    LinearLayout layoutShowComingShowDatePlaceholder;
    LinearLayout layoutShowRatingPlaceholder;
    LinearLayout layoutShowApplySchedulePlaceholder;
    LinearLayout layoutApplyInfoPlaceholder;
    LinearLayout layoutShowTopNotif;

    final Locale indonesia = new Locale("in", "ID");

    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final DateFormat targetdf = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", indonesia);
    final DateFormat daydf = new SimpleDateFormat("EEEE", indonesia);
    final DateFormat datedf = new SimpleDateFormat("d", indonesia);
    final DateFormat monthdf = new SimpleDateFormat("MMMM", indonesia);
    final DateFormat nightshowdf = new SimpleDateFormat("HH:mm", indonesia);

    private Context context;
    private ImageView showImage;
    private TextView showTitle;
    private TextView showTeamName;
    private LinearLayout showViewMorePerformers;
    private TextView showID;
    private LinearLayout showTopColor;
    private TextView showTopNotifLabel;

    //recent
    private TextView showDate;
    private TextView showAudience;
    private TextView showReview;
    private TextView showRating;
    private RatingBar showRatingBar;
    private ImageView showTopPerformerImage;
    private TextView showTopPerformerText;

    //coming
    private Button buttonApplyTicket;
    private TextView showNightSchedule;
    private TextView showLabelOFC;
    private TextView showLabelFAR;
    private TextView showLabelFEM;
    private TextView showLabelGEN;
    private TextView showLabelOFCinfo;
    private TextView showLabelFARinfo;
    private TextView showLabelFEMinfo;
    private TextView showLabelGENinfo;
    private TextView showLabelOFC2;
    private TextView showLabelFAR2;
    private TextView showLabelFEM2;
    private TextView showLabelGEN2;
    private TextView showLabelOFCtime;
    private TextView showLabelFARtime;
    private TextView showLabelFEMtime;
    private TextView showLabelGENtime;
    private Button buttonApplyOFC;
    private Button buttonApplyFAR;
    private Button buttonApplyFEM;
    private Button buttonApplyGEN;
    private TextView showDateDay;
    private TextView showDateDayOfMonth;
    private TextView showDateMonth;


    private WeakReference<ShowTask> showTaskWeakReference;
    private WeakReference<ShowPerformersTask> showPerformersTaskWeakReference;

    private boolean isApplicable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        id = intent.getStringExtra("showID");

        progressBarShowInfo = (ProgressBar) findViewById(R.id.progressBar_show_info);
        progressBarShowPerformers = (ProgressBar) findViewById(R.id.progressBar_show_performers);
        layoutShowInfo = (LinearLayout) findViewById(R.id.layout_show_info);
        layoutShowPerformers = (LinearLayout) findViewById(R.id.layout_show_performers);
        progressBarShowInfo.setVisibility(View.VISIBLE);
        progressBarShowPerformers.setVisibility(View.INVISIBLE);
        layoutShowInfo.setVisibility(View.INVISIBLE);
        layoutShowPerformers.setVisibility(View.INVISIBLE);

        //LoadShowMembersData();
        context = this;
        startNewAsyncTask(Integer.valueOf(id));

        //LoadShowData();
    }

    private void LoadShowMembersData() {
        final ArrayList<Member> membersList = new ArrayList<Member>();

        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        membersList.add(new Member(1,"Nabilah","Tenshippo","Team J", "9",""));
        ListView listView = (ListView)findViewById(R.id.listview_performers);

        ShowMembersListViewAdapter showMembersListViewAdapter = new ShowMembersListViewAdapter(this,R.layout.listitem_show_members,membersList);

        View emptyView = getLayoutInflater().inflate(R.layout.empty_list, null);
        ((ViewGroup)listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        listView.setAdapter(showMembersListViewAdapter);

        Utils.setListViewHeightBasedOnChildren(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                Toast.makeText(context, membersList.get(position).getMemberName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startNewAsyncTask(int id)
    {
        Handler handler = new Handler();

        final ShowTask showTask = new ShowTask(this);
        showTask.showIDvalue = id;
        this.showTaskWeakReference = new WeakReference<ShowTask>(showTask);
        showTask.execute(id);


        /*handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                if ( showTask.getStatus() == AsyncTask.Status.RUNNING )
                    showTask.cancel(true);
            }
        }, 1000 );*/

        final ShowPerformersTask showPerformersTask = new ShowPerformersTask(this);
        showPerformersTask.showIDvalue = id;
        this.showPerformersTaskWeakReference = new WeakReference<ShowPerformersTask>(showPerformersTask);
        showPerformersTask.execute(id);

        /*handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                if ( showPerformersTask.getStatus() == AsyncTask.Status.RUNNING )
                    showPerformersTask.cancel(true);
            }
        }, 1000 );*/
    }

    private class ShowTask extends AsyncTask<Integer, Void, Show>
    {
        public int showIDvalue = 0;

        private WeakReference<ShowActivity> showActivityWeakReference;

        private ShowTask (ShowActivity activity) {
            this.showActivityWeakReference = new WeakReference<ShowActivity>(activity);
        }
        @Override
        protected Show doInBackground(Integer... integers)
        {
            //android.os.Debug.waitForDebugger();

            progressBarShowInfo = (ProgressBar) findViewById(R.id.progressBar_show_info);
            layoutShowInfo = (LinearLayout) findViewById(R.id.layout_show_info);
            layoutShowPerformers = (LinearLayout) findViewById(R.id.layout_show_performers);
            progressBarShowInfo.setVisibility(View.VISIBLE);
            layoutShowInfo.setVisibility(View.INVISIBLE);
            layoutShowPerformers.setVisibility(View.INVISIBLE);

            Show show = null;

            for(Integer id : integers)
            {
                show = getShowInfo(id);
            }

            return show;
        }

        @Override
        protected void onPostExecute(Show show)
        {
            Log.d("showActivityPostExecute", "entered here");
            boolean comingShow = false;

            showID = new TextView(context);
            showID = (TextView)findViewById(R.id.show_id);
            showImage = new ImageView(context);
            showImage = (ImageView)findViewById(R.id.show_image);
            showTitle = new TextView(context);
            showTitle = (TextView)findViewById(R.id.show_title);
            showTeamName = new TextView(context);
            showTeamName = (TextView)findViewById(R.id.show_team_name);
            showTopNotifLabel = new TextView(context);
            showTopNotifLabel = (TextView) findViewById(R.id.show_top_notif_label);
            showTopColor = new LinearLayout(context);
            showTopColor = (LinearLayout) findViewById(R.id.show_top_color);

            Random r = new Random();
            int randomPic = r.nextInt(4-1) + 1;

            if (show != null) {
                if (show.getShowTitle().contains("Aturan")) {
                    switch (randomPic) {
                        case 1:
                            showImage.setImageResource(R.drawable.rkjmain);
                            break;
                        case 2:
                            showImage.setImageResource(R.drawable.rkjsquall);
                            break;
                        case 3:
                            showImage.setImageResource(R.drawable.rkj2);
                            break;
                    }
                    //showImage.setImageResource(R.drawable.rkj2);
                } else if (show.getShowTitle().contains("Matahari")) {
                    switch (randomPic) {
                        case 1:
                            showImage.setImageResource(R.drawable.bnt2);
                            break;
                        case 2:
                            showImage.setImageResource(R.drawable.bntyupi);
                            break;
                        case 3:
                            showImage.setImageResource(R.drawable.bntshow);
                            break;
                    }
                    //showImage.setImageResource(R.drawable.bntshow);
                } else if (show.getShowTitle().contains("Pajama")) {
                    switch (randomPic) {
                        case 1:
                            showImage.setImageResource(R.drawable.pajadora);
                            break;
                        case 2:
                            showImage.setImageResource(R.drawable.pajadorafaces);
                            break;
                        case 3:
                            showImage.setImageResource(R.drawable.ghaida);
                            break;
                    }
                    //showImage.setImageResource(R.drawable.pajadora);
                } else if (show.getShowTitle().contains("Demi")) {
                    switch (randomPic) {
                        case 1:
                            showImage.setImageResource(R.drawable.demi1);
                            break;
                        case 2:
                            showImage.setImageResource(R.drawable.demi2);
                            break;
                        case 3:
                            showImage.setImageResource(R.drawable.demi3);
                            break;
                    }
                } else {
                    showImage.setImageResource(R.drawable.rkj2);
                }

                comingShow = show.getShowOFCstart() != "";
                String judul = show.getShowTitle();
                if (judul.contains("K3")) {
                    judul = judul.substring(0, judul.length() - 9);
                } else {
                    judul = judul.substring(0, judul.length() - 8);
                }
                showTitle.setText("\"" + judul + "\"");
//            showTitle.setText(show.getShowTitle());
                showTeamName.setText(show.getShowTeamName());
            } else {
                startNewAsyncTask(Integer.valueOf(id));
            }

            //ArrayList<Member> randomPerformers = show.getRandomPerformers();
            //populateRandomPerformers(randomPerformers);

            if (comingShow)
            {
                //bikin layout coming soon
                showDate = new TextView(context);
                showDate = (TextView)findViewById(R.id.show_date);
                showNightSchedule = new TextView(context);
                showNightSchedule = (TextView)findViewById(R.id.show_night_schedule);

                showDateDay = (TextView)findViewById(R.id.show_date_day);
                showDateDayOfMonth = (TextView)findViewById(R.id.show_date_dayofmonth);
                showDateMonth = (TextView)findViewById(R.id.show_date_month);

                showLabelOFC = (TextView)findViewById(R.id.show_label_OFC);
                showLabelGEN = (TextView)findViewById(R.id.show_label_GEN);
                showLabelFAR = (TextView)findViewById(R.id.show_label_FAR);
                showLabelFEM = (TextView)findViewById(R.id.show_label_FEM);

                showLabelOFCinfo = (TextView)findViewById(R.id.show_label_OFC_info);
                showLabelGENinfo = (TextView)findViewById(R.id.show_label_GEN_info);
                showLabelFARinfo = (TextView)findViewById(R.id.show_label_FAR_info);
                showLabelFEMinfo = (TextView)findViewById(R.id.show_label_FEM_info);

                showLabelOFC2 = (TextView)findViewById(R.id.label_OFC_2);
                showLabelGEN2 = (TextView)findViewById(R.id.label_GEN_2);
                showLabelFAR2 = (TextView)findViewById(R.id.label_FAR_2);
                showLabelFEM2 = (TextView)findViewById(R.id.label_FEM_2);

                showLabelOFCtime = (TextView)findViewById(R.id.label_time_ofc);
                showLabelGENtime = (TextView)findViewById(R.id.label_time_gen);
                showLabelFARtime = (TextView)findViewById(R.id.label_time_far);
                showLabelFEMtime = (TextView)findViewById(R.id.label_time_fem);

                buttonApplyOFC = (Button)findViewById(R.id.button_apply_ofc);
                buttonApplyGEN = (Button)findViewById(R.id.button_apply_gen);
                buttonApplyFAR = (Button)findViewById(R.id.button_apply_far);
                buttonApplyFEM = (Button)findViewById(R.id.button_apply_fem);

                Date date;

                try
                {
                    date = df.parse(show.getShowDate());

                    showDateDay.setText(daydf.format(date));
                    showDateDayOfMonth.setText(datedf.format(date));
                    showDateMonth.setText(monthdf.format(date));
                    String nightShow = nightshowdf.format(date);
                    boolean nightFlag = Integer.valueOf(nightShow.substring(0,2)) > 15;

                    if (nightFlag)
                    {
                        showNightSchedule.setText(nightShow + " (Show Malam)");
                    }
                    else
                    {
                        showNightSchedule.setText(nightShow + " (Show Siang)");
                    }

                    String topText = Utils.formatNotificationDateText(date);
                    showTopNotifLabel.setText(topText);

                    if(topText.equals("TODAY") || topText.equals("TONIGHT"))
                    {
                        showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
                    }
                    else if (topText.equals("TOMORROW"))
                    {
                        showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
                    }
                    else if (topText.equals("COMING SOON"))
                    {
                        showTopColor.setBackgroundColor(Color.parseColor("#2c3e50"));
                    }
                }
                catch (ParseException e)
                {
                    showDate.setText("Terjadi Kesalahan: " + e.getMessage());
                    e.printStackTrace();
                }

                try
                {
                    Date now = new Date();

                    final Date ofcStart = df.parse(show.getShowOFCstart());
                    final Date ofcEnd = df.parse(show.getShowOFCend());
                    final Date femStart = df.parse(show.getShowFEMstart());
                    final Date femEnd = df.parse(show.getShowFEMend());
                    final Date farStart = df.parse(show.getShowFARstart());
                    final Date farEnd = df.parse(show.getShowFARend());
                    final Date genStart = df.parse(show.getShowGENstart());
                    final Date genEnd = df.parse(show.getShowGENend());

                    showLabelOFCtime.setText(show.getShowOFCstart().substring(0,show.getShowOFCstart().length() - 3) + "\n" + show.getShowOFCend().substring(0,show.getShowOFCend().length() - 3));
                    showLabelFEMtime.setText(show.getShowFEMstart().substring(0,show.getShowFEMstart().length() - 3) + "\n" + show.getShowFEMend().substring(0,show.getShowFEMend().length() - 3));
                    showLabelFARtime.setText(show.getShowFARstart().substring(0,show.getShowFARstart().length() - 3) + "\n" + show.getShowFARend().substring(0,show.getShowFARend().length() - 3));
                    showLabelGENtime.setText(show.getShowGENstart().substring(0,show.getShowGENstart().length() - 3) + "\n" + show.getShowGENend().substring(0,show.getShowGENend().length() - 3));

                    final Show showToBePassed = show;

                    if (now.before(ofcEnd))
                    {
                        if (now.after(ofcStart))
                        {
                            showLabelOFCinfo.setText("Apply sekarang");
                            buttonApplyOFC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    applyTicket(showToBePassed.getShowOFCURL());
                                }
                            });
                        }
                        else
                        {
                            showLabelOFCinfo.setText("Ingatkan saya");
                            buttonApplyOFC.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getNotified(showToBePassed, "OFC", ofcStart, ofcEnd);
                                }
                            });
                        }
                        showLabelOFCinfo.setTextColor(Color.parseColor("#ffffff"));
                        showLabelOFC.setBackgroundColor(Color.parseColor("#3498db"));
                        showLabelOFC.setTextColor(Color.parseColor("#ffffff"));
                        showLabelOFCinfo.setBackgroundColor(Color.parseColor("#3498db"));
                    }
                    else
                    {
                        showLabelOFCinfo.setText("Sudah lewat");
                        showLabelOFCinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                        showLabelOFCinfo.setTextColor(Color.parseColor("#aaaaaa"));
                        buttonApplyOFC.setEnabled(false);
                    }

                    if (now.before(genEnd))
                    {
                        if (now.after(genStart))
                        {
                            showLabelGENinfo.setText("Apply sekarang");
                            buttonApplyGEN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    applyTicket(showToBePassed.getShowGENURL());
                                }
                            });
                        }
                        else
                        {
                            showLabelGENinfo.setText("Ingatkan saya");
                            buttonApplyGEN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getNotified(showToBePassed, "GEN", genStart, genEnd);
                                }
                            });
                        }
                        showLabelGEN.setBackgroundColor(Color.parseColor("#2ecc71"));
                        showLabelGEN.setTextColor(Color.parseColor("#ffffff"));
                        showLabelGENinfo.setBackgroundColor(Color.parseColor("#2ecc71"));
                        showLabelGENinfo.setTextColor(Color.parseColor("#ffffff"));
                    }
                    else
                    {
                        showLabelGENinfo.setText("Sudah lewat");
                        showLabelGENinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                        showLabelGENinfo.setTextColor(Color.parseColor("#aaaaaa"));
                        buttonApplyGEN.setEnabled(false);
                    }

                    if (now.before(farEnd))
                    {
                        if (now.after(farStart))
                        {
                            showLabelFARinfo.setText("Apply sekarang");
                            buttonApplyFAR.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    applyTicket(showToBePassed.getShowFARURL());
                                }
                            });
                        }
                        else
                        {
                            showLabelFARinfo.setText("Ingatkan saya");
                            buttonApplyFAR.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getNotified(showToBePassed, "FAR", farStart, farEnd);
                                }
                            });
                        }
                        showLabelFAR.setBackgroundColor(Color.parseColor("#f1c40f"));
                        showLabelFAR.setTextColor(Color.parseColor("#ffffff"));
                        showLabelFARinfo.setBackgroundColor(Color.parseColor("#f1c40f"));
                        showLabelFARinfo.setTextColor(Color.parseColor("#ffffff"));
                    }
                    else
                    {
                        showLabelFARinfo.setText("Sudah lewat");
                        showLabelFARinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                        showLabelFARinfo.setTextColor(Color.parseColor("#aaaaaa"));
                        buttonApplyFAR.setEnabled(false);

                    }

                    if (now.before(femEnd))
                    {
                        if (now.after(femStart))
                        {
                            showLabelFEMinfo.setText("Apply sekarang");
                            showLabelFEM.setBackgroundColor(Color.parseColor("#e74c3c"));
                            showLabelFEM.setTextColor(Color.parseColor("#ffffff"));
                            buttonApplyFEM.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    applyTicket(showToBePassed.getShowFEMURL());
                                }
                            });
                        }
                        else
                        {
                            showLabelFEMinfo.setText("Ingatkan saya");
                            buttonApplyFEM.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getNotified(showToBePassed, "FEM", femStart, femEnd);
                                }
                            });
                        }
                    }
                    else
                    {
                        showLabelFEMinfo.setText("Sudah lewat");
                        showLabelFEMinfo.setBackgroundColor(Color.parseColor("#7f8c8d"));
                        showLabelFEMinfo.setTextColor(Color.parseColor("#aaaaaa"));
                        buttonApplyFEM.setEnabled(false);
                    }
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    date = df.parse(show.getShowDate());
                    showDate.setText(targetdf.format(date));
                }
                catch (ParseException e)
                {
                    showDate.setText("Terjadi Kesalahan: " + e.getMessage());
                    e.printStackTrace();
                }

                showDate.setVisibility(View.GONE);

                layoutShowDateRecentPlaceholder = new LinearLayout(context);
                layoutShowDateRecentPlaceholder = (LinearLayout)findViewById(R.id.show_date_recent_placeholder);
                layoutShowRatingPlaceholder = new LinearLayout(context);
                layoutShowRatingPlaceholder = (LinearLayout)findViewById(R.id.show_rating_placeholder);
                layoutShowDateRecentPlaceholder.setVisibility(View.GONE);
                layoutShowRatingPlaceholder.setVisibility(View.GONE);

            }


            else //if recent show
            {
                layoutShowApplySchedulePlaceholder = new LinearLayout(context);
                layoutShowApplySchedulePlaceholder = (LinearLayout)findViewById(R.id.show_apply_schedule_placeholder);
                layoutShowComingShowDatePlaceholder = new LinearLayout(context);
                layoutShowComingShowDatePlaceholder = (LinearLayout)findViewById(R.id.show_coming_show_date_placeholder);
                layoutApplyInfoPlaceholder = new LinearLayout(context);
                layoutApplyInfoPlaceholder  = (LinearLayout)findViewById(R.id.layout_apply_info_placeholder);

                layoutShowApplySchedulePlaceholder.setVisibility(View.GONE);
                layoutShowComingShowDatePlaceholder.setVisibility(View.GONE);
                layoutApplyInfoPlaceholder.setVisibility(View.GONE);

                showNightSchedule = new TextView(context);
                showNightSchedule = (TextView)findViewById(R.id.show_night_schedule);
                showDate = new TextView(context);
                showDate = (TextView)findViewById(R.id.show_date);
                showAudience = new TextView(context);
                showAudience = (TextView)findViewById(R.id.show_audience_count);
                showReview = new TextView(context);
                showReview = (TextView)findViewById(R.id.show_review_count);
                showRating = new TextView(context);
                showRating = (TextView) findViewById(R.id.show_rating);
                showRatingBar = new RatingBar(context);
                showRatingBar = (RatingBar)findViewById(R.id.ratingBar);
                showTopPerformerImage = new ImageView(context);
                showTopPerformerImage = (ImageView)findViewById(R.id.show_top_performer_image);
                showTopPerformerText = new TextView(context);
                showTopPerformerText = (TextView) findViewById(R.id.show_top_performer_text);

                layoutShowTopNotif = (LinearLayout)findViewById(R.id.show_top_notif);
                layoutShowTopNotif.setVisibility(View.GONE);
                showAudience.setText(show.getShowAudienceCount());
                showReview.setText(show.getShowReviews());
                showRating.setText(show.getShowRating());

                Date date;
                try {
                    date = df.parse(show.getShowDate());

                    //showDateDay.setText(daydf.format(date));
                    //showDateDayOfMonth.setText(datedf.format(date));
                    //showDateMonth.setText(monthdf.format(date));
                    String nightShow = nightshowdf.format(date);
                    boolean nightFlag = Integer.valueOf(nightShow.substring(0,2)) > 15;

                    if (nightFlag)
                    {
                        showNightSchedule.setText(nightShow + " (Show Malam)");
                    }
                    else
                    {
                        showNightSchedule.setText(nightShow + " (Show Siang)");
                    }

                    showDate.setText(daydf.format(date)+", " + datedf.format(date) + " " + monthdf.format(date));
                } catch (Exception e) {
                    showDate.setText("gagal dapet" + e.getMessage() + ""+show.getShowDate());
                    e.printStackTrace();
                }

                if (show.getShowTopPerformer() != null)
                {
                    showTopPerformerText.setText(show.getShowTopPerformer().getMemberName() + " (" + show.getShowTopPerformer().getMemberRating() + ")");
                    showTopPerformerImage.setImageResource(GlobalEntities.ImageReferenceList.get(show.getShowTopPerformer().getID()));
                }
                else
                {
                    showTopPerformerText.setText("Tidak ada");
                    showTopPerformerImage.setImageResource(R.drawable.orang);
                }

                try
                {
                    float rating = Float.valueOf(show.getShowRating())/2;
                    showRatingBar.setRating(rating);
                }
                catch (Exception e)
                {
                    showRatingBar.setRating(0);
                }
            }

            layoutShowInfo = (LinearLayout) findViewById(R.id.layout_show_info);
            layoutShowInfo.setVisibility(View.VISIBLE);
            progressBarShowInfo.setVisibility(View.GONE);
            progressBarShowPerformers.setVisibility(View.VISIBLE);
        }
    }

    private class ShowPerformersTask extends AsyncTask<Integer, Void, ArrayList<Member>>
    {
        public int showIDvalue = 0;

        private WeakReference<ShowActivity> showActivityWeakReference;

        private ShowPerformersTask (ShowActivity activity) {
            this.showActivityWeakReference = new WeakReference<ShowActivity>(activity);
        }
        @Override
        protected ArrayList<Member> doInBackground(Integer... integers)
        {
            ArrayList<Member> members = new ArrayList<Member>();

            for(Integer id : integers)
            {
                members = getShowMembers(id);
            }

            return members;
        }

        @Override
        protected void onPostExecute(final ArrayList<Member> membersList)
        {
            ListView listView = (ListView)findViewById(R.id.listview_performers);

            ShowMembersListViewAdapter showMembersListViewAdapter = new ShowMembersListViewAdapter(context,R.layout.listitem_show_members,membersList);

            View emptyView = getLayoutInflater().inflate(R.layout.empty_list, null);
            ((ViewGroup)listView.getParent()).addView(emptyView);
            listView.setEmptyView(emptyView);
            listView.setAdapter(showMembersListViewAdapter);

            Utils.setListViewHeightBasedOnChildren(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {

                    Intent intent = new Intent(context,MemberActivity.class);
                    intent.putExtra("ID", Integer.toString(membersList.get(position).getID()));
                    startActivity(intent);

//Toast.makeText(context, membersList.get(position).getMemberTwitter(), Toast.LENGTH_SHORT).show();
                    //open member page
                }
            });

            progressBarShowPerformers.setVisibility(View.GONE);
            layoutShowPerformers.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<Member> getShowMembers(Integer id) {

        ArrayList<Member> members = new ArrayList<Member>();

        JSONParser jParser = new JSONParser();

        try{
            String url = URL_SHOW_PERFORMER + id;
            JSONObject showPerformersJSONObject = jParser.getJSONFromUrl(url);

            JSONArray mainPerformersJSONArray = showPerformersJSONObject.getJSONArray(TAG_PERFORMER_MAIN);

            for (int i = 0; i < mainPerformersJSONArray.length(); i++)
            {
                JSONObject memberJSONObject = mainPerformersJSONArray.getJSONObject(i);

                String memberID = memberJSONObject.getString(TAG_PERFORMER_ID);
                String name = memberJSONObject.getString(TAG_PERFORMER_NAME);
                String image = memberJSONObject.getString(TAG_PERFORMER_IMAGE);
                String unitsong = memberJSONObject.getString(TAG_PERFORMER_UNITSONG);
                String rating = memberJSONObject.getString(TAG_PERFORMER_RATING);
                String ratingVoter = memberJSONObject.getString(TAG_PERFORMER_RATING_VOTER);
                String twitter = memberJSONObject.getString(TAG_PERFORMER_TWITTER);

                Member member = new Member(Integer.valueOf(memberID),name,unitsong,"",rating,image,ratingVoter,twitter);
                members.add(member);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return members;
    }

    private void getNotified(final Show show, final String type, final Date start, final Date end) {
        new AlertDialog.Builder(this)
                .setTitle("Pesan Tiket")
                .setMessage("Waktu pemesanan untuk tiket " + type + " belum dimulai. Apakah kamu ingin mengaktifkan notifikasi untuk memberitahu jika tiket sudah bisa dipesan?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        int notificationShowID = show.getID();
                        String notificationShowTitle = show.getShowTitle();

                        AlarmManager alarmManager = (AlarmManager) getSystemService(context.ALARM_SERVICE);

                        Calendar cur_cal = new GregorianCalendar();
                        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

                        Calendar firstNotification = Calendar.getInstance();
                        firstNotification.setTime(start);
                        firstNotification.set(Calendar.HOUR, firstNotification.get(firstNotification.HOUR) - 2);
                        //firstNotification.set(Calendar.SECOND, firstNotification.get(Calendar.SECOND) + 5);
                        long whenFirstNotification = firstNotification.getTimeInMillis();

                        /* if debug

                        Calendar cal = cur_cal;

                        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 2);
                        long when = cal.getTimeInMillis();

                        debug end */

                        Intent intent = new Intent(context, ReminderService.class);
                        intent.putExtra("notificationShowID",notificationShowID);
                        intent.putExtra("notificationShowTitle", notificationShowTitle);
                        intent.putExtra("notificationShowType", type);

                        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
                        alarmManager.set(AlarmManager.RTC, whenFirstNotification, pendingIntent);
                        //alarmManager.set(AlarmManager.RTC, when, pendingIntent);


                        Toast.makeText(context, "Notifikasi diset untuk " + targetdf.format(start), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
    }

    private void applyTicket(final String url) {
        new AlertDialog.Builder(this)
                .setTitle("Pesan Tiket")
                .setMessage("Anda akan dialihkan ke halaman situs JKT48.com untuk login. Setelah melakukan pemesanan, silakan kembali ke halaman ini.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        /*Intent intent = new Intent(ShowActivity.this, ApplyTicketActivity.class);
                        intent.putExtra("url",url.replace("\\/","/"));
                        startActivity(intent);*/

                        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                        httpIntent.setData(Uri.parse(url.replace("\\/","/")));

                        startActivity(httpIntent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
    }

    /*private class ShowPerformerTask extends AsyncTask<Integer, Void, ArrayList<Member>>
    {
        @Override
        protected ArrayList<Member> doInBackground(Integer... params) {

            progressBarShowInfo = (ProgressBar) findViewById(R.id.progressBar_coming_shows);
            progressBarShowInfo.setVisibility(View.GONE);

            comingShowsArrayList = shows;
            GlobalEntities.ComingShowArrayList = shows;

            comingShowsListViewAdapter = new ComingShowsListViewAdapter(getActivity(),R.layout.listitem_coming_shows,comingShowsArrayList);
            listViewComingShows = (ListView) getView().findViewById(R.id.listview_coming_shows);

            View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
            ((ViewGroup)listViewComingShows.getParent()).addView(emptyView);
            listViewComingShows.setEmptyView(emptyView);
            listViewComingShows.setAdapter(comingShowsListViewAdapter);

            listViewComingShows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                    Intent intent = new Intent(getActivity(),ShowActivity.class);
                    intent.putExtra("showID", comingShowsArrayList.get(position).getID());
                    startActivity(intent);

                    //Toast.makeText(getActivity().getApplicationContext(), comingShowsArrayList.get(position).getShowTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }*/

    private Show getShowInfo(int showID) {

        Log.d("showInfoTask", "getting show info!");
        Show show = null;
        JSONParser jParser = new JSONParser();

        try{
            JSONObject showJSONObject = jParser.getJSONFromUrl(URL_SHOW_INFO + showID);

            // Storing each json item in variable
            String id = showJSONObject.getString(TAG_ID);
            String setlist = showJSONObject.getString(TAG_SETLIST);
            String time = showJSONObject.getString(TAG_TIME);
            //String image = showJSONObject.getString(TAG_IMAGE);
            String team = showJSONObject.getString(TAG_TEAM);

            ArrayList<Member> randomPerformers = new ArrayList<Member>();

            JSONArray randomPerformerArray = showJSONObject.getJSONArray(TAG_RANDOM_PERFORMER);
            for (int j = 0; j < randomPerformerArray.length(); j++)
            {
                JSONObject randomPerformer = randomPerformerArray.getJSONObject(j);

                String randomPerformerID = randomPerformer.getString(TAG_RANDOM_PERFORMER_ID);
                String randomPerformerName = randomPerformer.getString(TAG_RANDOM_PERFORMER_NAME);
                String randomPerformerImage = randomPerformer.getString(TAG_RANDOM_PERFORMER_IMG);

                Member member = new Member(Integer.valueOf(randomPerformerID),randomPerformerName,randomPerformerImage);
                randomPerformers.add(member);
            }

            if (!showJSONObject.isNull(TAG_TIME_APPLY_FAR))
            {
                //FAR apply
                JSONObject far = showJSONObject.getJSONObject(TAG_TIME_APPLY_FAR);
                String farStart = far.getString(TAG_APPLY_START);
                String farEnd = far.getString(TAG_APPLY_END);
                String farURL = far.getString(TAG_APPLY_URL.replace("\\/","/"));

                //OFC apply
                JSONObject ofc = showJSONObject.getJSONObject(TAG_TIME_APPLY_OFC);
                String ofcStart = ofc.getString(TAG_APPLY_START);
                String ofcEnd = ofc.getString(TAG_APPLY_END);
                String ofcURL = ofc.getString(TAG_APPLY_URL.replace("\\/","/"));

                //GEN apply
                JSONObject gen = showJSONObject.getJSONObject(TAG_TIME_APPLY_GEN);
                String genStart = gen.getString(TAG_APPLY_START);
                String genEnd = gen.getString(TAG_APPLY_END);
                String genURL = gen.getString(TAG_APPLY_URL.replace("\\/","/"));

                //FEM apply
                JSONObject fem = showJSONObject.getJSONObject(TAG_TIME_APPLY_FEM);
                String femStart = fem.getString(TAG_APPLY_START);
                String femEnd = fem.getString(TAG_APPLY_END);
                String femURL = fem.getString(TAG_APPLY_URL.replace("\\/","/"));

                show = new Show(Integer.parseInt(id),setlist,time,"","","","","",team,
                        farStart,farEnd,farURL,
                        ofcStart,ofcEnd,ofcURL,
                        genStart,genEnd,genURL,
                        femStart,femEnd,femURL,
                        randomPerformers);
            }
            else
            {
                String rating = showJSONObject.getString(TAG_RATING);
                String audienceCount = showJSONObject.getString(TAG_AUDIENCE_COUNT);
                String reviewCount = showJSONObject.getString(TAG_REVIEW_COUNT);

                Member topPerformer = null;

                if (!showJSONObject.isNull(TAG_TOP_PERFORMER))
                {
                    JSONObject topPerformerJSONObject = showJSONObject.getJSONObject(TAG_TOP_PERFORMER);
                    String topPerformerID = topPerformerJSONObject.getString(TAG_TOP_PERFORMER_ID);
                    String topPerformerName = topPerformerJSONObject.getString(TAG_TOP_PERFORMER_NAME);
                    String topPerformerRating = topPerformerJSONObject.getString(TAG_TOP_PERFORMER_RATING);
                    String topPerformerUnitSong = topPerformerJSONObject.getString(TAG_TOP_PERFORMER_UNITSONG);
                    topPerformer = new Member(Integer.valueOf(topPerformerID),topPerformerName,topPerformerUnitSong,"",topPerformerRating,"");
                }


                show = new Show(Integer.parseInt(id),setlist,time,rating,"",audienceCount,reviewCount,"",team,
                        "","","",
                        "","","",
                        "","","",
                        "","","",
                        randomPerformers);

                show.setShowTopPerformer(topPerformer);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        Log.d("showInfoTask", "got it!");
        return show;
    }

    private void LoadShowData() {
        showImage = new ImageView(this);
        showTitle = new TextView(this);
        showTitle = (TextView)findViewById(R.id.show_title);
        showDate = new TextView(this);
        showDate = (TextView)findViewById(R.id.show_date);
        showAudience = new TextView(this);
        //showAudience = (TextView)findViewById(R.id.show_audience);
        showReview = new TextView(this);
        showReview = (TextView)findViewById(R.id.show_review);
        buttonApplyTicket = new Button(this);
        //buttonApplyTicket = (Button)findViewById(R.id.button_apply_ticket);

        if(!isApplicable)
        {
            buttonApplyTicket.setEnabled(false);
        }
        else
        {
            buttonApplyTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonApplyTicketClick();
                }
            });
        }
    }

    private void buttonApplyTicketClick() {
        new AlertDialog.Builder(this)
                .setTitle("Pesan Tiket")
                .setMessage("Anda akan dialihkan ke halaman situs JKT48.com untuk login. Setelah melakukan pemesanan, silakan kembali ke halaman sebelumnya.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(ShowActivity.this, ApplyTicketActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.show, menu);
//        return true;
//    }


    private static final String URL_SHOW_INFO = "http://apimytheater.klasemenjkt48.com/api/show_info/";
    private static final String URL_SHOW_PERFORMER = "http://apimytheater.klasemenjkt48.com/api/show_performer/";
    private static final String URL_RECENT_SHOWS = "http://apimytheater.klasemenjkt48.com/api/shows/recent/";
    private static final String TAG_ID = "id";
    private static final String TAG_SETLIST = "setlist";
    private static final String TAG_TIME = "time";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_TEAM = "team";
    private static final String TAG_TIME_APPLY_FAR = "time_apply_far";
    private static final String TAG_TIME_APPLY_OFC = "time_apply_ofc";
    private static final String TAG_TIME_APPLY_FEM = "time_apply_fem";
    private static final String TAG_TIME_APPLY_GEN = "time_apply_gen";
    private static final String TAG_RATING = "rating";
    private static final String TAG_AUDIENCE_COUNT = "audience_count";
    private static final String TAG_REVIEW_COUNT = "review_count";
    private static final String TAG_APPLY_START = "start";
    private static final String TAG_APPLY_END = "end";
    private static final String TAG_APPLY_URL = "url";
    private static final String TAG_RANDOM_PERFORMER = "randomperformer";
    private static final String TAG_RANDOM_PERFORMER_ID = "id";
    private static final String TAG_RANDOM_PERFORMER_NAME = "name";
    private static final String TAG_RANDOM_PERFORMER_IMG = "image";
    private static final String TAG_PERFORMER_MAIN = "main";
    private static final String TAG_PERFORMER_SUPPORTING = "supporting";
    private static final String TAG_PERFORMER_ID = "id";
    private static final String TAG_PERFORMER_NAME = "name";
    private static final String TAG_PERFORMER_IMAGE = "image";
    private static final String TAG_PERFORMER_UNITSONG = "unitsong";
    private static final String TAG_PERFORMER_RATING = "rating";
    private static final String TAG_PERFORMER_RATING_VOTER = "rating_voter";
    private static final String TAG_PERFORMER_TWITTER = "twitter";
    private static final String TAG_TOP_PERFORMER = "topperformer";
    private static final String TAG_TOP_PERFORMER_ID = "id";
    private static final String TAG_TOP_PERFORMER_NAME = "name";
    private static final String TAG_TOP_PERFORMER_IMAGE = "image";
    private static final String TAG_TOP_PERFORMER_RATING = "rating";
    private static final String TAG_TOP_PERFORMER_UNITSONG = "unitsong";





}
