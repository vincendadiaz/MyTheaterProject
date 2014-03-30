package com.sciters.mytheater.entities;

import java.util.ArrayList;

/**
 * Created by Gilang on 9/30/13.
 */
public class Member{
    private int ID;
    private String memberName;
    private String memberShowUnitSong;
    private String memberTeamName;

    private String memberJikou;
    private String memberAverageRating;
    private String memberPerformCount;
    private String memberBDCount;

    /* ini nanti seharusnya dimasukkan ke child class namanya Performer*/
    private String memberRating;
    private String memberImagePath;
    private String memberRatingVoter;
    private String memberTwitter;

    private ArrayList<MemberSetList> memberSetlists;

    public Member()
    {

    }

    public Member(int id,
                  String name,
                  String showUnitSong,
                  String teamName,
                  String rating,
                  String imagePath,
                  String ratingVoter,
                  String twitter)
    {
        this.ID = id;
        this.memberName = name;
        this.memberShowUnitSong = showUnitSong;
        this.memberTeamName = teamName;
        this.memberRating = rating;
        this.memberImagePath = imagePath;
        this.memberRatingVoter = ratingVoter;
        this.memberTwitter = twitter;
    }

    public Member(int id,
                  String name,
                  String showUnitSong,
                  String teamName,
                  String rating,
                  String imagePath)
    {
        this.ID = id;
        this.memberName = name;
        this.memberShowUnitSong = showUnitSong;
        this.memberTeamName = teamName;
        this.memberRating = rating;
        this.memberImagePath = imagePath;
    }

    public Member(int id,
                  String name,
                  String imagePath)
    {

        this.ID = id;
        this.memberName = name;
        this.memberImagePath = imagePath;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberShowUnitSong() {
        return memberShowUnitSong;
    }

    public void setMemberShowUnitSong(String showUnitSong) {
        this.memberShowUnitSong = showUnitSong;
    }

    public String getMemberTeamName() {
        return memberTeamName;
    }

    public void setMemberTeamName(String teamName) {
        this.memberTeamName = teamName;
    }

    public String getMemberRating() {
        return memberRating;
    }

    public void setMemberRating(String rating) {
        this.memberRating = rating;
    }

    public String getMemberImagePath() {
        return memberImagePath.replace("\\/","/");
    }

    public void setMemberImagePath(String imagePath) {
        this.memberImagePath = imagePath;
    }

    public String getMemberTwitter() {
        return memberTwitter;
    }

    public void setMemberTwitter(String twitter) {
        this.memberTwitter = twitter;
    }

    public String getMemberRatingVoter() {
        return memberRatingVoter;
    }

    public void setMemberRatingVoter(String ratingVoter) {
        this.memberRatingVoter = ratingVoter;
    }

    public int getMemberImageFromID() {
        return GlobalEntities.ImageReferenceList.get(this.getID());
    }

    public String getMemberAverageRating() {
        return memberAverageRating;
    }

    public void setMemberAverageRating(String memberAverageRating) {
        this.memberAverageRating = memberAverageRating;
    }

    public String getMemberPerformCount() {
        return memberPerformCount;
    }

    public void setMemberPerformCount(String memberPerformCount) {
        this.memberPerformCount = memberPerformCount;
    }

    public String getMemberBDCount() {
        return memberBDCount;
    }

    public void setMemberBDCount(String memberBDCount) {
        this.memberBDCount = memberBDCount;
    }

    public ArrayList<MemberSetList> getMemberSetlists() {
        return memberSetlists;
    }

    public void setMemberSetlists(ArrayList<MemberSetList> memberSetlists) {
        this.memberSetlists = memberSetlists;
    }

    public String getMemberJikou() {
        return memberJikou;
    }

    public void setMemberJikou(String memberJikou) {
        this.memberJikou = memberJikou;
    }

    public String getMemberTotalPerform() {
        int perform = Integer.valueOf(this.memberPerformCount);
        int bd = Integer.valueOf(this.memberBDCount);
        int total = perform + bd;
        return Integer.toString(total);
    }

    public String getMemberTeamNameDisplay() {
        return this.getMemberTeamName().equals("Graduated") || this.getMemberTeamName().equals("Trainee") ? this.getMemberTeamName() : "Team " + this.getMemberTeamName();
    }
}
