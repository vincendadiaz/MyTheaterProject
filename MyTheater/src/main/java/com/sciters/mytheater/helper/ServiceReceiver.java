package com.sciters.mytheater.helper;

import android.graphics.drawable.Drawable;

import com.sciters.mytheater.entities.GlobalEntities;
import com.sciters.mytheater.entities.Member;
import com.sciters.mytheater.entities.MemberSetList;
import com.sciters.mytheater.entities.SetList;
import com.sciters.mytheater.entities.Show;
import com.sciters.mytheater.helper.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gilang on 11/6/13.
 */
public class ServiceReceiver {

    public static ArrayList<Member> GetMembers() {
        ArrayList<Member> members = new ArrayList<Member>();

        if (GlobalEntities.MembersArrayList == null)
        {
            JSONParser jParser = new JSONParser();
            JSONArray jsonArray = jParser.getJSONArrayfromURL(Constants.URL_MEMBERS);

            try{
                for(int i = 0; i < jsonArray.length();i++)
                {
                    JSONObject memberJSONObject = jsonArray.getJSONObject(i);

                    String id = memberJSONObject.getString(Constants.ID);
                    String name = memberJSONObject.getString(Constants.NAME);
                    String twitter = memberJSONObject.getString(Constants.TWITTER);
                    String team = memberJSONObject.getString(Constants.TEAM);
                    String image = memberJSONObject.getString(Constants.IMAGE);

                    Member member = new Member();
                    member.setID(Integer.valueOf(id));
                    member.setMemberName(name);
                    member.setMemberTwitter(twitter);
                    member.setMemberTeamName(team);
                    member.setMemberImagePath(image.replace("\\/","/"));

                    members.add(member);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            finally
            {
                return members;
            }
        }
        else
        {
            return GlobalEntities.MembersArrayList;
        }
    }

    public static Member GetMemberInfo(Integer memberID) {
        Member member = null;
        JSONParser jParser = new JSONParser();

        try{
            JSONObject memberJSONObject = jParser.getJSONFromUrl(Constants.URL_MEMBER_INFO + memberID);

            String id = memberJSONObject.getString(Constants.ID);
            String name = memberJSONObject.getString(Constants.NAME);
            String twitter = memberJSONObject.getString(Constants.TWITTER);
            String team = memberJSONObject.getString(Constants.TEAM);
            String image = memberJSONObject.getString(Constants.IMAGE);
            String jikou = memberJSONObject.getString(Constants.JIKOU);
            int performCount = memberJSONObject.getInt(Constants.PERFORM_COUNT_TEMP);
            int bdCount= memberJSONObject.getInt(Constants.BD_COUNT);
            String avgRating = memberJSONObject.getString(Constants.AVGRATING);

            member = new Member();
            member.setID(Integer.valueOf(id));
            member.setMemberName(name);
            member.setMemberTwitter(twitter);
            member.setMemberTeamName(team);
            member.setMemberJikou(jikou);
            member.setMemberPerformCount(Integer.toString(performCount));
            member.setMemberBDCount(Integer.toString(bdCount));
            member.setMemberAverageRating(avgRating);

            member.setMemberImagePath(image.replace("\\/","/"));

            JSONArray memberSetlistArray = memberJSONObject.getJSONArray(Constants.SETLIST);

            for(int j = 0; j < memberSetlistArray.length(); j++)
            {
                JSONObject memberSetlistObject = memberSetlistArray.getJSONObject(j);

                SetList setList = new SetList();
                setList.setID(Integer.valueOf(memberSetlistObject.getString(Constants.ID_SETLIST)));
                setList.setName(memberSetlistObject.getString(Constants.NAME_SETLIST));
                setList.setImagePath(memberSetlistObject.getString(Constants.IMAGE));

                MemberSetList memberSetList = new MemberSetList();
                memberSetList.setMember(member);
                memberSetList.setSetList(setList);
                memberSetList.setPerformCount(Integer.valueOf(memberSetlistObject.getString(Constants.PERFORM_COUNT)));
                memberSetList.setBdCount(Integer.valueOf(memberSetlistObject.getString(Constants.BD_COUNT)));
                memberSetList.setAvgRating(memberSetlistObject.getString(Constants.AVGRATING));

                if (member.getMemberSetlists() == null)
                {
                    member.setMemberSetlists(new ArrayList<MemberSetList>());
                    member.getMemberSetlists().add(memberSetList);
                }
                else
                {
                    member.getMemberSetlists().add(memberSetList);
                }

            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return member;
    }
}
