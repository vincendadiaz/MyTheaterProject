package com.sciters.mytheater;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.adapter.ComingShowsListViewAdapter;
import com.sciters.mytheater.helper.adapter.RecentShowsListViewAdapter;
import com.sciters.mytheater.helper.parser.JSONParser;
import com.sciters.mytheater.services.MyTheaterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ProgressBar progressBarComingShows;
    ProgressBar progressBarRecentShows;
    ListView listViewComingShows;
    ListView listViewRecentShows;
    ArrayList<Show> comingShowsArrayList = new ArrayList<Show>();
    ArrayList<Show> recentShowsArrayList = new ArrayList<Show>();
    ComingShowsListViewAdapter comingShowsListViewAdapter;
    RecentShowsListViewAdapter recentShowsListViewAdapter;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = this;

        // use this to start and trigger a service
        Intent i= new Intent(myContext, MyTheaterService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        myContext.startService(i);

        //new ComingShowTask().execute();
        //new RecentShowTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
