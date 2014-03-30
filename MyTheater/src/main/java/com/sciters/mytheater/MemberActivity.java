package com.sciters.mytheater;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.MemberSetList;
import com.sciters.mytheater.helper.ServiceReceiver;
import com.sciters.mytheater.helper.adapter.MemberSetListListViewAdapter;
import com.sciters.mytheater.helper.adapter.ShowMembersListViewAdapter;
import com.sciters.mytheater.helper.temporary.ImageHelper;
import com.sciters.mytheater.helper.utilities.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MemberActivity extends Activity {

    private Context context;
    private LinearLayout layoutMemberInfo;
    private LinearLayout layoutMainInfo;
    private TextView memberID;
    private ImageView memberImage;
    private TextView memberName;
    private TextView memberTeamName;
    private TextView memberTwitter;
    private TextView memberNumberOfShows;
    private TextView memberAverageScore;

    private LinearLayout layoutSetlists;
    private ListView listView;
    private ProgressBar progressBarInfo;

    private WeakReference<MemberInfoTask> memberInfoTaskWeakReference;
    private String id;
    Member member = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        context = this;
        startNewAsyncTask(Integer.valueOf(id));

    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.member, menu);
//        return false;
//    }

    private void startNewAsyncTask(int id)
    {
        final MemberInfoTask memberInfoTask = new MemberInfoTask(this);
        memberInfoTask.memberIDvalue = id;
        this.memberInfoTaskWeakReference = new WeakReference<MemberInfoTask>(memberInfoTask);
        memberInfoTask.execute(id);

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                if ( memberInfoTask.getStatus() == AsyncTask.Status.RUNNING )
                    memberInfoTask.cancel(true);
            }
        }, 10000 );*/
    }

    private class MemberInfoTask extends AsyncTask<Integer, Void, Member>
    {
        public int memberIDvalue = 0;

        private WeakReference<MemberActivity> memberActivityWeakReference;

        private MemberInfoTask(MemberActivity activity) {
            this.memberActivityWeakReference = new WeakReference<MemberActivity>(activity);
        }

        @Override
        protected Member doInBackground(Integer... integers)
        {
            try {
                progressBarInfo = (ProgressBar) findViewById(R.id.progressBar_info);
                layoutMainInfo = (LinearLayout) findViewById(R.id.main_info);

                progressBarInfo.setVisibility(View.VISIBLE);
                layoutMainInfo.setVisibility(View.INVISIBLE);

                for(Integer id : integers)
                {
                    member = ServiceReceiver.GetMemberInfo(id);
                }
            } catch (Exception e) {
                member = null;
            }
            return member;
        }

        @Override
        protected void onPostExecute(final Member member)
        {
            try {

                if (member != null) {
                    memberID = (TextView) findViewById(R.id.member_id);
                    memberImage = (ImageView) findViewById(R.id.member_image);
                    memberName = (TextView) findViewById(R.id.member_name);
                    memberTeamName = (TextView) findViewById(R.id.member_team);
                    memberTwitter = (TextView) findViewById(R.id.member_twitter);
                    memberNumberOfShows = (TextView) findViewById(R.id.member_number_of_shows);
                    memberAverageScore = (TextView) findViewById(R.id.member_average_score);

                    memberImage.setImageResource(GlobalEntities.ImageReferenceList.get(member.getID()));
                    memberName.setText(member.getMemberName());
                    memberTwitter.setText("@" + member.getMemberTwitter());
                    memberTeamName.setText(member.getMemberTeamNameDisplay());
                    //memberNumberOfShows.setText("Perform: " + member.getMemberTotalPerform() + " (" + member.getMemberPerformCount() + " + " + member.getMemberBDCount() + ")");
                    memberNumberOfShows.setText("Perform: " + member.getMemberTotalPerform() + " (" + member.getMemberPerformCount() + " kali & " + member.getMemberBDCount() + " BD)");
                    memberAverageScore.setText("Rating: " + member.getMemberAverageRating());

                    listView = (ListView) findViewById(R.id.listview_setlists);

                    MemberSetListListViewAdapter memberSetListListViewAdapter = new MemberSetListListViewAdapter(context, R.layout.listitem_member_setlist, member.getMemberSetlists());

                    View emptyView = getLayoutInflater().inflate(R.layout.empty_list, null);
                    ((ViewGroup) listView.getParent()).addView(emptyView);
                    listView.setEmptyView(emptyView);
                    listView.setAdapter(memberSetListListViewAdapter);

                    Utils.setListViewHeightBasedOnChildren(listView);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                            //Toast.makeText(context, member.getMemberSetlists().get(position).getSetList().getName(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MemberActivity.this,SetListSong.class);
                            intent.putExtra("ID", member.getMemberSetlists().get(position).getSetList().getName());
                            startActivity(intent);

                            //Toast.makeText(context, member.getMemberSetlists().get(position).getSetList().getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    progressBarInfo.setVisibility(View.GONE);
                    layoutMainInfo.setVisibility(View.VISIBLE);

                } else {
                    progressBarInfo.setVisibility(View.VISIBLE);
                    layoutMainInfo.setVisibility(View.INVISIBLE);
                    //Toast.makeText(context, "error masuk else", Toast.LENGTH_LONG).show();
                    startNewAsyncTask(Integer.valueOf(id));
                }


            } catch (Exception e) {
                Toast.makeText(context, "error masuk exception", Toast.LENGTH_LONG).show();
            }
        }
    }
}













