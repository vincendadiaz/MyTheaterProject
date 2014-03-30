package com.sciters.mytheater;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.helper.adapter.ShowMembersListViewAdapter;

import java.util.ArrayList;

public class TestTabActivity extends Activity {
    ListView listViewShowMembers;

    ArrayList<Member> showMembersArrayList = new ArrayList<Member>();
    ShowMembersListViewAdapter showMembersListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        LoadShowMembersData();
    }


    private void LoadShowMembersData() {
        Member dummyMember1 = new Member(1,"Nabilah Ratna Ayu Azalia", "Tenshi No Shippo", "Team J", "8", null);
        Member dummyMember2 = new Member(2,"Melody Nurramdhani Laksani", "Junjou Shugi", "Team J", "7", null);
        Member dummyMember3 = new Member(3,"Ghaida Farisya", "Temodemo No Namida", "Team J", "9", null);
        Member dummyMember4 = new Member(4,"Sendy Ariani", "Kagami No Naka No Jeanne D'Arc", "Team J", "7", null);
        Member dummyMember5 = new Member(5,"Viviyona Apriani", "Pajama Drive", "Team K", "8", null);

        showMembersArrayList.add(dummyMember1);
        showMembersArrayList.add(dummyMember2);
        showMembersArrayList.add(dummyMember3);
        showMembersArrayList.add(dummyMember4);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);
        showMembersArrayList.add(dummyMember5);

        showMembersListViewAdapter = new ShowMembersListViewAdapter(this,R.layout.listitem_show_members,showMembersArrayList);
        listViewShowMembers = (ListView) findViewById(R.id.listview_show_members);
        listViewShowMembers.setAdapter(showMembersListViewAdapter);

        listViewShowMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                //Intent intent = new Intent(ThemePickerActivity.this,QuizActivity.class);
                //Intent intent = new Intent(MainActivity.this,GameActivity.class);
                //intent.putExtra("ThemeID", themeArrayList.get(position).getID());
                //startActivity(intent);
                //finish();

                Toast.makeText(getApplicationContext(), showMembersArrayList.get(position).getMemberName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_tab, menu);
        return true;
    }
    
}
