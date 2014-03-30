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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.ReminderService;
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

public class RecentShowActivity extends Activity {
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
        setContentView(R.layout.activity_recent_show);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        String id = intent.getStringExtra("showID");

        progressBarShowInfo = (ProgressBar) findViewById(R.id.progressBar_show_info);
        progressBarShowPerformers = (ProgressBar) findViewById(R.id.progressBar_show_performers);
        layoutShowInfo = (LinearLayout) findViewById(R.id.layout_show_info);
        layoutShowPerformers = (LinearLayout) findViewById(R.id.layout_show_performers);
        progressBarShowInfo.setVisibility(View.VISIBLE);
        progressBarShowPerformers.setVisibility(View.INVISIBLE);
        layoutShowInfo.setVisibility(View.INVISIBLE);
        layoutShowPerformers.setVisibility(View.INVISIBLE);

        context = this;
        startNewAsyncTask(Integer.valueOf(id));
    }

    private void startNewAsyncTask(int id) {
        Handler handler = new Handler();

        final ShowTask showTask = new ShowTask(this);
        showTask.showIDvalue = id;
        this.showTaskWeakReference = new WeakReference<ShowTask>(showTask);
        showTask.execute(id);

        final ShowPerformersTask showPerformersTask = new ShowPerformersTask(this);
        showPerformersTask.showIDvalue = id;
        this.showPerformersTaskWeakReference = new WeakReference<ShowPerformersTask>(showPerformersTask);
        showPerformersTask.execute(id);
    }


    private class ShowTask extends AsyncTask<Integer, Void, Show>
    {
        public int showIDvalue = 0;

        private WeakReference<RecentShowActivity> showActivityWeakReference;

        private ShowTask (RecentShowActivity activity) {
            this.showActivityWeakReference = new WeakReference<RecentShowActivity>(activity);
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

            if (show.getShowTitle().contains("Aturan"))
            {
                switch(randomPic)
                {
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
            }
            else if (show.getShowTitle().contains("Matahari"))
            {
                switch(randomPic)
                {
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
            }
            else if (show.getShowTitle().contains("Pajama"))
            {
                switch(randomPic)
                {
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
            }
            else if (show.getShowTitle().contains("Demi")) {
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
            } else {
                showImage.setImageResource(R.drawable.rkj2);
            }

            showTitle.setText(show.getShowTitle());
            showTeamName.setText(show.getShowTeamName());

            //ArrayList<Member> randomPerformers = show.getRandomPerformers();
            //populateRandomPerformers(randomPerformers);
            layoutShowApplySchedulePlaceholder = new LinearLayout(context);
            layoutShowApplySchedulePlaceholder = (LinearLayout)findViewById(R.id.show_apply_schedule_placeholder);
            layoutShowComingShowDatePlaceholder = new LinearLayout(context);
            layoutShowComingShowDatePlaceholder = (LinearLayout)findViewById(R.id.show_coming_show_date_placeholder);
            layoutApplyInfoPlaceholder = new LinearLayout(context);
            layoutApplyInfoPlaceholder  = (LinearLayout)findViewById(R.id.layout_apply_info_placeholder);

            layoutShowApplySchedulePlaceholder.setVisibility(View.GONE);
            layoutShowComingShowDatePlaceholder.setVisibility(View.GONE);
            layoutApplyInfoPlaceholder.setVisibility(View.GONE);

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

            if (show.getShowTopPerformer() != null)
            {
                showTopPerformerText.setText(show.getShowTopPerformer().getMemberName() + " (" + show.getShowTopPerformer().getMemberRating() + ")");
                showTopPerformerImage.setImageResource(GlobalEntities.ImageReferenceList.get(show.getShowTopPerformer().getID()));
            }
            else
            {
                showTopPerformerText.setText("Tidak ada");
                showTopPerformerImage.setImageResource(R.drawable.review);
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

            layoutShowInfo = (LinearLayout) findViewById(R.id.layout_show_info);
            layoutShowInfo.setVisibility(View.VISIBLE);
            progressBarShowInfo.setVisibility(View.GONE);
            progressBarShowPerformers.setVisibility(View.VISIBLE);
        }
    }

    private class ShowPerformersTask extends AsyncTask<Integer, Void, ArrayList<Member>>
    {
        public int showIDvalue = 0;

        private WeakReference<RecentShowActivity> showActivityWeakReference;

        private ShowPerformersTask (RecentShowActivity activity) {
            this.showActivityWeakReference = new WeakReference<RecentShowActivity>(activity);
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

                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

                        Calendar cur_cal = new GregorianCalendar();
                        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

                        Calendar firstNotification = Calendar.getInstance();
                        firstNotification.setTime(start);
                        firstNotification.set(Calendar.HOUR, firstNotification.get(firstNotification.HOUR) - 2);
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
                        httpIntent.setData(Uri.parse(url.replace("\\/", "/")));

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
                        Intent intent = new Intent(RecentShowActivity.this, ApplyTicketActivity.class);
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
