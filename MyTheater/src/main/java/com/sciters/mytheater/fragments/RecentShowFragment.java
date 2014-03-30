package com.sciters.mytheater.fragments;


import android.content.Intent;
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

import com.sciters.mytheater.R;
import com.sciters.mytheater.ShowActivity;
import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.adapter.ComingShowsListViewAdapter;
import com.sciters.mytheater.helper.adapter.RecentShowsListViewAdapter;
import com.sciters.mytheater.helper.parser.JSONParser;
import com.sciters.mytheater.helper.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by Gilang on 10/3/13.
 */
public class RecentShowFragment extends Fragment {
    ProgressBar progressBarRecentShows;
    ListView listViewRecentShows;
    Button buttonRefresh;
    ArrayList<Show> recentShowsArrayList = new ArrayList<Show>();
    RecentShowsListViewAdapter recentShowsListViewAdapter;
    public static final String RECENT_SHOW_TAG = "recentShowTag";

    private WeakReference<RecentShowTask> recentShowTaskWeakReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.listfragment_recent_shows, container, false);

        setRetainInstance(true);
        startRetrievingOnlineData(V);
        //startNewAsyncTask();
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

        progressBarRecentShows = (ProgressBar) V.findViewById(R.id.progressBar_recent_shows);
        progressBarRecentShows.setVisibility(View.GONE);

        listViewRecentShows = (ListView) V.findViewById(R.id.listview_recent_shows);

        final View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
        ((ViewGroup)listViewRecentShows.getParent()).addView(emptyView);

        listViewRecentShows.setEmptyView(emptyView);

        buttonRefresh = (Button) V.findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)listViewRecentShows.getParent()).removeView(emptyView);
                progressBarRecentShows.setVisibility(View.VISIBLE);
                startRetrievingOnlineData(v);
            }
        });

    }

    private void startNewAsyncTask() {
        RecentShowTask task = new RecentShowTask(this);
        this.recentShowTaskWeakReference = new WeakReference<RecentShowTask>(task);
        task.execute();
    }

    private class RecentShowTask extends AsyncTask<Void, Void, ArrayList<Show>>
    {
        private WeakReference<RecentShowFragment> fragmentWeakRef;

        private RecentShowTask (RecentShowFragment fragment) {
            this.fragmentWeakRef = new WeakReference<RecentShowFragment>(fragment);
        }

        @Override
        protected ArrayList<Show> doInBackground(Void... voids)
        {
            Intent intent = getActivity().getIntent();
            ArrayList<Show> shows = intent.getParcelableArrayListExtra("recentShowArrayListParcel");

            if (shows == null)
            {
                return getArrayListOfShows();
            }
            return shows;
        }

        @Override
        protected void onPostExecute(ArrayList<Show> shows)
        {
            try {
            progressBarRecentShows = (ProgressBar) getView().findViewById(R.id.progressBar_recent_shows);
            progressBarRecentShows.setVisibility(View.GONE);

            if (!shows.isEmpty())
            {
                recentShowsArrayList = shows;
                GlobalEntities.RecentShowArrayList = shows;

                recentShowsListViewAdapter = new RecentShowsListViewAdapter(getActivity(),R.layout.listitem_recent_shows,
                        recentShowsArrayList);
                listViewRecentShows = (ListView) getView().findViewById(R.id.listview_recent_shows);

                View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
                ((ViewGroup)listViewRecentShows.getParent()).addView(emptyView);
                listViewRecentShows.setEmptyView(emptyView);
                listViewRecentShows.setAdapter(recentShowsListViewAdapter);

                listViewRecentShows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                        Intent intent = new Intent(getActivity(),ShowActivity.class);
                        intent.putExtra("showID", Integer.toString(recentShowsArrayList.get(position).getID()));
                        startActivity(intent);

                        //Toast.makeText(getActivity().getApplicationContext(), recentShowsArrayList.get(position).getShowTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {}
        }
    }


    private ArrayList<Show> getArrayListOfShows() {
        ArrayList<Show> shows = new ArrayList<Show>();

        if (GlobalEntities.RecentShowArrayList == null)
        {
            JSONParser jParser = new JSONParser();
            JSONArray jsonArray = jParser.getJSONArrayfromURL(URL_RECENT_SHOWS);

            try{
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject showJSONObject = jsonArray.getJSONObject(i);

                    // Storing each json item in variable
                    String id = showJSONObject.getString(TAG_ID);
                    String title = showJSONObject.getString(TAG_SETLIST);
                    String time = showJSONObject.getString(TAG_TIME);
                    //String image = showJSONObject.getString(TAG_IMAGE);
                    String team = showJSONObject.getString(TAG_TEAM);
                    String rating = showJSONObject.getString(TAG_RATING);
                    String audienceCount = showJSONObject.getString(TAG_AUDIENCE_COUNT);
                    String reviewCount = showJSONObject.getString(TAG_REVIEW_COUNT);

                    Show show = new Show(Integer.parseInt(id),title,time,rating,"",audienceCount,reviewCount,team);
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
            return GlobalEntities.RecentShowArrayList;
        }
    }



    private static final String URL_RECENT_SHOWS = "http://apimytheater.klasemenjkt48.com/api/shows/recent/1";
    private static final String TAG_ID = "id";
    private static final String TAG_SETLIST = "setlist";
    private static final String TAG_TIME = "time";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_TEAM = "team";
    private static final String TAG_RATING = "rating";
    private static final String TAG_AUDIENCE_COUNT = "audience_count";
    private static final String TAG_REVIEW_COUNT = "review_count";
}