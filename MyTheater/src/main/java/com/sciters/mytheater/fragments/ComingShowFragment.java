package com.sciters.mytheater.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sciters.mytheater.MainActivity;
import com.sciters.mytheater.R;
import com.sciters.mytheater.ShowActivity;
import com.sciters.mytheater.TabHomeActivity;
import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.adapter.ComingShowsListViewAdapter;
import com.sciters.mytheater.helper.adapter.RecentShowsListViewAdapter;
import com.sciters.mytheater.helper.parser.JSONParser;
import com.sciters.mytheater.helper.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Gilang on 10/3/13.
 */
public class ComingShowFragment extends Fragment {
    ProgressBar progressBarComingShows;
    ListView listViewComingShows;
    Button buttonRefresh;
    ArrayList<Show> comingShowsArrayList = new ArrayList<Show>();
    ComingShowsListViewAdapter comingShowsListViewAdapter;
    public static final String COMING_SHOW_TAG = "comingShowTag";

    private WeakReference<ComingShowTask> comingShowTaskWeakReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.listfragment_coming_shows, container, false);

        setRetainInstance(true);
        startRetrievingOnlineData(V);
        return V;
    }

    private void startRetrievingOnlineData(View V){
        if (Utils.isOnline(getActivity()))
        {
            startNewAsyncTask();
        }
        else
        {
            showNotOnlineNotification(V);
        }
    }

    private void showNotOnlineNotification(View V) {

        if (getView() != null)
        {
            V = getView();
        }

        progressBarComingShows = (ProgressBar) V.findViewById(R.id.progressBar_coming_shows);
        progressBarComingShows.setVisibility(View.GONE);

        listViewComingShows = (ListView) V.findViewById(R.id.listview_coming_shows);

        final View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
        ((ViewGroup)listViewComingShows.getParent()).addView(emptyView);

        listViewComingShows.setEmptyView(emptyView);

        buttonRefresh = (Button) V.findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)listViewComingShows.getParent()).removeView(emptyView);
                progressBarComingShows.setVisibility(View.VISIBLE);
                startRetrievingOnlineData(v);
            }
        });

    }

    private void startNewAsyncTask() {
        ComingShowTask comingShowTask = new ComingShowTask(this);
        this.comingShowTaskWeakReference = new WeakReference<ComingShowTask>(comingShowTask);
        comingShowTask.execute();
    }

    private class ComingShowTask extends AsyncTask<Void, Void, ArrayList<Show>>
    {
        private WeakReference<ComingShowFragment> fragmentWeakRef;

        private ComingShowTask (ComingShowFragment fragment) {
            this.fragmentWeakRef = new WeakReference<ComingShowFragment>(fragment);
        }

        @Override
        protected ArrayList<Show> doInBackground(Void... voids)
        {
            Intent intent = null;
            ArrayList<Show> shows = null;

            try {
                intent = getActivity().getIntent();
            } catch (Exception e) {
                startNewAsyncTask();
            }

            shows = intent.getParcelableArrayListExtra("comingShowArrayListParcel");

            if (shows == null)
            {
                return getArrayListOfShows();
            }

            return shows;
        }

        @Override
        protected void onPostExecute(ArrayList<Show> shows) {
            try {
                progressBarComingShows = (ProgressBar) getView().findViewById(R.id.progressBar_coming_shows);
                progressBarComingShows.setVisibility(View.GONE);

                if (!shows.isEmpty()) {
                    comingShowsArrayList = shows;
                    GlobalEntities.ComingShowArrayList = shows;

                    comingShowsListViewAdapter = new ComingShowsListViewAdapter(getActivity(), R.layout.listitem_coming_shows, comingShowsArrayList);
                    listViewComingShows = (ListView) getView().findViewById(R.id.listview_coming_shows);

                    View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
                    ((ViewGroup) listViewComingShows.getParent()).addView(emptyView);
                    listViewComingShows.setEmptyView(emptyView);
                    listViewComingShows.setAdapter(comingShowsListViewAdapter);

                    listViewComingShows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                            Intent intent = new Intent(getActivity(), ShowActivity.class);
                            intent.putExtra("showID", Integer.toString(comingShowsArrayList.get(position).getID()));
                            startActivity(intent);

                            //Toast.makeText(getActivity().getApplicationContext(), comingShowsArrayList.get(position).getShowTitle(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
            }
        }
    }

    private ArrayList<Show> getArrayListOfShows() {
        ArrayList<Show> shows = new ArrayList<Show>();

        if (GlobalEntities.ComingShowArrayList == null)
        {
            JSONParser jParser = new JSONParser();
            JSONArray jsonArray = jParser.getJSONArrayfromURL(URL_COMING_SHOWS);

            try{
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject showJSONObject = jsonArray.getJSONObject(i);

                    // Storing each json item in variable
                    String id = showJSONObject.getString(TAG_ID);
                    String setlist = showJSONObject.getString(TAG_SETLIST);
                    String time = showJSONObject.getString(TAG_TIME);
                    //String image = showJSONObject.getString(TAG_IMAGE);
                    String team = showJSONObject.getString(TAG_TEAM);

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

                    //String rating = showJSONObject.getString(TAG_RATING);
                    //String audienceCount = showJSONObject.getString(TAG_AUDIENCE_COUNT);
                    //String reviewCount = showJSONObject.getString(TAG_REVIEW_COUNT);
                    //String applyURL = show.getString(TAG_APPLY_URL);

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

                    Show show = new Show(Integer.parseInt(id),setlist,time,"","","","","",team,
                            farStart,farEnd,farURL,
                            ofcStart,ofcEnd,ofcURL,
                            genStart,genEnd,genURL,
                            femStart,femEnd,femURL,
                            randomPerformers);
                    shows.add(show);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return shows;
        }
        else
        {
            return GlobalEntities.ComingShowArrayList;
        }
    }

    private static final String URL_COMING_SHOWS = "http://apimytheater.klasemenjkt48.com/api/shows/coming/";
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
}
