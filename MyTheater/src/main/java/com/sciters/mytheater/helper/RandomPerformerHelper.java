package com.sciters.mytheater.helper;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sciters.mytheater.R;
import com.sciters.mytheater.entities.Member;

import java.util.ArrayList;

/**
 * Created by Gilang on 10/15/13.
 */
public class RandomPerformerHelper{

//random performers
private RelativeLayout showRandomPerformerRelativeLayout1;
private TextView showRandomPerformerID1;
private ImageView showRandomPerformerImage1;
private TextView showRandomPerformerName1;
private TextView showRandomPerformerUnitSong1;
private TextView showRandomPerformerRating1;

private RelativeLayout showRandomPerformerRelativeLayout2;
private TextView showRandomPerformerID2;
private ImageView showRandomPerformerImage2;
private TextView showRandomPerformerName2;
private TextView showRandomPerformerUnitSong2;
private TextView showRandomPerformerRating2;

private RelativeLayout showRandomPerformerRelativeLayout3;
private TextView showRandomPerformerID3;
private ImageView showRandomPerformerImage3;
private TextView showRandomPerformerName3;
private TextView showRandomPerformerUnitSong3;
private TextView showRandomPerformerRating3;

private RelativeLayout showRandomPerformerRelativeLayout4;
private TextView showRandomPerformerID4;
private ImageView showRandomPerformerImage4;
private TextView showRandomPerformerName4;
private TextView showRandomPerformerUnitSong4;
private TextView showRandomPerformerRating4;

private RelativeLayout showRandomPerformerRelativeLayout5;
private TextView showRandomPerformerID5;
private ImageView showRandomPerformerImage5;
private TextView showRandomPerformerName5;
private TextView showRandomPerformerUnitSong5;
private TextView showRandomPerformerRating5;

    private void populateRandomPerformers(ArrayList<Member> randomPerformers) {
/*

        showRandomPerformerID1 = new TextView(context);
        showRandomPerformerID1 = (TextView)findViewById(R.id.member_id1);
        showRandomPerformerID2 = new TextView(context);
        showRandomPerformerID2 = (TextView)findViewById(R.id.member_id2);
        showRandomPerformerID3 = new TextView(context);
        showRandomPerformerID3 = (TextView)findViewById(R.id.member_id3);
        showRandomPerformerID4 = new TextView(context);
        showRandomPerformerID4 = (TextView)findViewById(R.id.member_id4);
        showRandomPerformerID5 = new TextView(context);
        showRandomPerformerID5 = (TextView)findViewById(R.id.member_id5);

        showRandomPerformerName1 = new TextView(context);
        showRandomPerformerName1 = (TextView)findViewById(R.id.member_name1);
        showRandomPerformerName2 = new TextView(context);
        showRandomPerformerName2 = (TextView)findViewById(R.id.member_name2);
        showRandomPerformerName3 = new TextView(context);
        showRandomPerformerName3 = (TextView)findViewById(R.id.member_name3);
        showRandomPerformerName4 = new TextView(context);
        showRandomPerformerName4 = (TextView)findViewById(R.id.member_name4);
        showRandomPerformerName5 = new TextView(context);
        showRandomPerformerName5 = (TextView)findViewById(R.id.member_name5);

        showRandomPerformerUnitSong1 = new TextView(context);
        showRandomPerformerUnitSong1 = (TextView)findViewById(R.id.member_show_unit_song1);
        showRandomPerformerUnitSong2 = new TextView(context);
        showRandomPerformerUnitSong2 = (TextView)findViewById(R.id.member_show_unit_song2);
        showRandomPerformerUnitSong3 = new TextView(context);
        showRandomPerformerUnitSong3 = (TextView)findViewById(R.id.member_show_unit_song3);
        showRandomPerformerUnitSong4 = new TextView(context);
        showRandomPerformerUnitSong4 = (TextView)findViewById(R.id.member_show_unit_song4);
        showRandomPerformerUnitSong5 = new TextView(context);
        showRandomPerformerUnitSong5 = (TextView)findViewById(R.id.member_show_unit_song5);

        showRandomPerformerRating1 = new TextView(context);
        showRandomPerformerRating1 = (TextView)findViewById(R.id.member_rating1);
        showRandomPerformerRating2 = new TextView(context);
        showRandomPerformerRating2 = (TextView)findViewById(R.id.member_rating2);
        showRandomPerformerRating3 = new TextView(context);
        showRandomPerformerRating3 = (TextView)findViewById(R.id.member_rating3);
        showRandomPerformerRating4 = new TextView(context);
        showRandomPerformerRating4 = (TextView)findViewById(R.id.member_rating4);
        showRandomPerformerRating5 = new TextView(context);
        showRandomPerformerRating5 = (TextView)findViewById(R.id.member_rating5);

        showRandomPerformerImage1 = new ImageView(context);
        showRandomPerformerImage1 = (ImageView)findViewById(R.id.member_image1);
        showRandomPerformerImage2 = new ImageView(context);
        showRandomPerformerImage2 = (ImageView)findViewById(R.id.member_image2);
        showRandomPerformerImage3 = new ImageView(context);
        showRandomPerformerImage3 = (ImageView)findViewById(R.id.member_image3);
        showRandomPerformerImage4 = new ImageView(context);
        showRandomPerformerImage4 = (ImageView)findViewById(R.id.member_image4);
        showRandomPerformerImage5 = new ImageView(context);
        showRandomPerformerImage5 = (ImageView)findViewById(R.id.member_image5);


        Member member1 = randomPerformers.get(0);
        showRandomPerformerName1.setText(member1.getMemberName());
        showRandomPerformerUnitSong1.setText(member1.getMemberShowUnitSong());
        showRandomPerformerRating1.setText(member1.getMemberRating());
        //imageLoader.DisplayImage(member1.getMemberImagePath(),holder.showRandomPerformerImage1);
        Member member2 = randomPerformers.get(1);
        showRandomPerformerName2.setText(member2.getMemberName());
        showRandomPerformerUnitSong2.setText(member2.getMemberShowUnitSong());
        showRandomPerformerRating1.setText(member2.getMemberRating());
        //imageLoader.DisplayImage(member2.getMemberImagePath(),holder.showRandomPerformerImage2);
        Member member3 = randomPerformers.get(2);
        showRandomPerformerName3.setText(member3.getMemberName());
        showRandomPerformerUnitSong3.setText(member3.getMemberShowUnitSong());
        showRandomPerformerRating3.setText(member3.getMemberRating());
        //imageLoader.DisplayImage(member3.getMemberImagePath(),holder.showRandomPerformerImage3);
        Member member4 = randomPerformers.get(3);
        showRandomPerformerName4.setText(member4.getMemberName());
        showRandomPerformerUnitSong4.setText(member4.getMemberShowUnitSong());
        showRandomPerformerRating4.setText(member4.getMemberRating());
        //imageLoader.DisplayImage(member4.getMemberImagePath(),holder.showRandomPerformerImage4);
        Member member5 = randomPerformers.get(4);
        showRandomPerformerName5.setText(member5.getMemberName());
        showRandomPerformerUnitSong5.setText(member5.getMemberShowUnitSong());
        showRandomPerformerRating5.setText(member5.getMemberRating());
        //imageLoader.DisplayImage(member5.getMemberImagePath(),holder.showRandomPerformerImage5);
*/

    }

}
