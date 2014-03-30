package com.sciters.mytheater.fragments;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.sciters.mytheater.MemberActivity;
import com.sciters.mytheater.R;
import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.helper.ServiceReceiver;
import com.sciters.mytheater.helper.adapter.MembersListViewAdapter;
import com.sciters.mytheater.helper.utilities.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Gilang on 10/6/13.
 */
public class MembersFragment extends Fragment {
    ProgressBar progressBarMembers;
    ListView listViewMembers;
    Button buttonRefresh;
    ArrayList<Member> membersArrayList = new ArrayList<Member>();
    MembersListViewAdapter membersListViewAdapter;

    private WeakReference<MemberTask> memberTaskWeakReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.listfragment_members, container, false);
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

        progressBarMembers = (ProgressBar) V.findViewById(R.id.progressBar_members);
        progressBarMembers.setVisibility(View.GONE);

        listViewMembers = (ListView) V.findViewById(R.id.listview_members);

        final View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
        ((ViewGroup)listViewMembers.getParent()).addView(emptyView);

        listViewMembers.setEmptyView(emptyView);

        buttonRefresh = (Button) V.findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)listViewMembers.getParent()).removeView(emptyView);
                progressBarMembers.setVisibility(View.VISIBLE);
                startRetrievingOnlineData(v);
            }
        });
    }

    private void startNewAsyncTask() {
        MemberTask memberTask = new MemberTask(this);
        this.memberTaskWeakReference = new WeakReference<MemberTask>(memberTask);
        memberTask.execute();
    }

    private class MemberTask extends AsyncTask<Void, Void, ArrayList<Member>>
    {
        private WeakReference<MembersFragment> fragmentWeakRef;

        private MemberTask (MembersFragment fragment) {
            this.fragmentWeakRef = new WeakReference<MembersFragment>(fragment);
        }

        @Override
        protected ArrayList<Member> doInBackground(Void... voids)
        {
            try {
            Intent intent = getActivity().getIntent();
            } catch (Exception e) {
                startNewAsyncTask();
            }
            return ServiceReceiver.GetMembers();
        }

        @Override
        protected void onPostExecute(ArrayList<Member> members)
        {
            try {
            progressBarMembers = (ProgressBar) getView().findViewById(R.id.progressBar_members);
            progressBarMembers.setVisibility(View.GONE);

            if (!members.isEmpty())
            {
                membersArrayList = members;
                GlobalEntities.MembersArrayList = members;

                membersListViewAdapter = new MembersListViewAdapter(getActivity(),R.layout.listitem_members,membersArrayList);
                listViewMembers = (ListView) getView().findViewById(R.id.listview_members);

                View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_list, null);
                ((ViewGroup)listViewMembers.getParent()).addView(emptyView);
                listViewMembers.setEmptyView(emptyView);
                listViewMembers.setAdapter(membersListViewAdapter);

                listViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                        Intent intent = new Intent(getActivity(),MemberActivity.class);
                        intent.putExtra("ID", Integer.toString(membersArrayList.get(position).getID()));
                        startActivity(intent);

                        //Toast.makeText(getActivity().getApplicationContext(), membersArrayList.get(position).getMemberName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {}
        }
    }


}
