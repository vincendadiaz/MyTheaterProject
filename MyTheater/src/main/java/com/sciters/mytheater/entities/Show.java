package com.sciters.mytheater.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Gilang on 9/30/13.
 */
public class Show implements Parcelable {
    private int ID;
    private String showTitle;
    private String showDate;
    private String showRating;
    private String showImagePath;
    private String showAudienceCount;
    private String showReviews;
    private String showURL;
    private String showTeamName;
    private String showOFCstart;
    private String showOFCend;
    private String showOFCURL;
    private String showGENstart;
    private String showGENend;
    private String showGENURL;
    private String showFEMstart;
    private String showFEMend;
    private String showFEMURL;
    private String showFARstart;
    private String showFARend;
    private String showFARURL;
    private Member showTopPerformer;
    private ArrayList<Member> randomPerformers;

    public Show()
    {

    }

    public Show(int id,
                String title,
                String date,
                String rating,
                String imagePath,
                String audienceCount,
                String reviews,
                String teamName
                )
    {
        this.ID = id;
        this.showTitle = title;
        this.showDate = date;
        this.showRating = rating;
        this.showImagePath = imagePath;
        this.showAudienceCount = audienceCount;
        this.showReviews = reviews;
        this.showTeamName = teamName;
    }

    public Show(int id, String title, String date, String rating,
                String imagePath, String attendants,
                String reviews, String URL, String team,
                String farStart, String farEnd, String farURL,
                String ofcStart, String ofcEnd, String ofcURL,
                String genStart, String genEnd, String genURL,
                String femStart, String femEnd, String femURL,
                ArrayList<Member> randomPerformers)
    {
        this.ID = id;
//        if (title.contains("Team K3")) {
//            this.showTitle = title.substring(0, title.length()-10);
//        } else {
//            this.showTitle = title.substring(0, title.length()-8);
//        }
//        this.showTitle = title.substring(0, title.length()-8);
        this.showTitle = title;
        this.showDate = date;
        this.showRating = rating;
        this.showImagePath = imagePath;
        this.showAudienceCount = attendants;
        this.showReviews = reviews;
        this.showURL = URL;
        this.showTeamName = team;
        this.showFARstart = farStart;
        this.showFARend = farEnd;
        this.showFARURL = farURL;
        this.showOFCstart = ofcStart;
        this.showOFCend = ofcEnd;
        this.showOFCURL = ofcURL;
        this.showGENstart = genStart;
        this.showGENend = genEnd;
        this.showGENURL = genURL;
        this.showFEMstart = femStart;
        this.showFEMend = femEnd;
        this.showFEMURL = femURL;
        this.randomPerformers = randomPerformers;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getShowTitle() {
//        if (showTitle.contains("K3")) {
//            showTitle = showTitle.substring(0, showTitle.length()-9);
//        } else {
//            showTitle = showTitle.substring(0, showTitle.length()-8);
//        }
        return showTitle;
    }

    public void setShowTitle(String showTitle)
    {
        this.showTitle = showTitle;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate)
    {
        this.showDate = showDate;
    }

    public String getShowRating() {
        return showRating;
    }

    public void setShowRating(String showRating)
    {
        this.showRating = showRating;
    }

    public String getShowImagePath() {
        return showImagePath;
    }

    public void setShowImagePath(String showImagePath)
    {
        this.showImagePath = showImagePath;
    }

    public String getShowAudienceCount() {
        return showAudienceCount;
    }

    public void setShowAudienceCount(String showAudienceCount)
    {
        this.showAudienceCount = showAudienceCount;
    }

    public String getShowReviews() {
        return showReviews;
    }

    public void setShowReviews(String showReviews)
    {
        this.showReviews = showReviews;
    }

    public String getShowURL() {
        return showURL;
    }

    public void setShowURL(String showURL)
    {
        this.showURL = showURL;
    }

    public String getShowTeamName() {
        return showTeamName;
    }

    public void setShowTeamName(String showTeamName)
    {
        this.showTeamName = showTeamName;
    }

    public String getShowOFCstart() {
        return showOFCstart;
    }

    public void setShowOFCstart(String showOFCstart)
    {
        this.showOFCstart = showOFCstart;
    }

    public String getShowOFCend() {
        return showOFCend;
    }

    public void setShowOFCEnd(String showOFCend)
    {
        this.showOFCend = showOFCend;
    }

    public String getShowGENstart() {
        return showGENstart;
    }

    public void setShowGENstart(String showGENstart)
    {
        this.showGENstart = showGENstart;
    }

    public String getShowGENend() {
        return showGENend;
    }

    public void setShowGENend(String showGENend)
    {
        this.showGENend = showGENend;
    }

    public String getShowFEMstart() {
        return showFEMstart;
    }

    public void setShowFEMstart(String showFEMstart)
    {
        this.showFEMstart = showFEMstart;
    }

    public String getShowFEMend() {
        return showFEMend;
    }

    public void setShowFEMend(String showFEMend)
    {
        this.showFEMend = showFEMend;
    }

    public String getShowFARstart() {
        return showFARstart;
    }

    public void setShowFARstart(String showFARstart)
    {
        this.showFARstart = showFARstart;
    }

    public String getShowFARend() {
        return showFARend;
    }

    public void setShowFARend(String showFARend)
    {
        this.showFARend = showFARend;
    }

    public String getShowOFCURL() {
        return showOFCURL;
    }

    public void setShowOFCURL(String showOFCURL)
    {
        this.showOFCURL = showOFCURL;
    }

    public String getShowGENURL() {
        return showGENURL;
    }

    public void setShowGENURL(String showGENURL)
    {
        this.showGENURL = showGENURL;
    }

    public String getShowFEMURL() {
        return showFEMURL;
    }

    public void setShowFEMURL(String showFEMURL)
    {
        this.showFEMURL = showFEMURL;
    }

    public String getShowFARURL() {
        return showFARURL;
    }

    public void setShowFARURL(String showFARURL)
    {
        this.showFARURL = showFARURL;
    }

    public Show(Parcel in)
    {
        String[] data = new String[21];

        in.readStringArray(data);
        this.ID = Integer.parseInt(data[0]);
        this.showTitle = data[1];
        this.showDate = data[2];
        this.showRating = data[3];
        this.showImagePath = data[4];
        this.showAudienceCount = data[5];
        this.showReviews = data[6];
        this.showURL = data[7];
        this.showTeamName = data[8];
        this.showFARstart = data[9];
        this.showFARend = data[10];
        this.showFARURL = data[11];
        this.showOFCstart = data[12];
        this.showOFCend = data[13];
        this.showOFCURL = data[14];
        this.showGENstart = data[15];
        this.showGENend = data[16];
        this.showGENURL = data[17];
        this.showFEMstart = data[18];
        this.showFEMend = data[19];
        this.showFEMURL = data[20];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                {
                    Integer.toString(this.ID),
                    this.showTitle,
                    this.showDate,
                    this.showRating,
                    this.showImagePath,
                    this.showAudienceCount,
                    this.showReviews,
                    this.showURL,
                    this.showTeamName,
                    this.showFARstart,
                    this.showFARend,
                    this.showFARURL,
                    this.showOFCstart,
                    this.showOFCend,
                    this.showOFCURL,
                    this.showGENstart,
                    this.showGENend,
                    this.showGENURL,
                    this.showFEMstart,
                    this.showFEMend,
                    this.showFEMURL
                });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Show createFromParcel(Parcel in)
        {
            return new Show(in);
        }

        public Show[] newArray(int size)
        {
            return new Show[size];
        }
    };

    public ArrayList<Member> getRandomPerformers() {
        return randomPerformers;
    }

    public Member getShowTopPerformer() {
        return showTopPerformer;
    }

    public void setShowTopPerformer(Member showTopPerformer) {
        this.showTopPerformer = showTopPerformer;
    }
}
