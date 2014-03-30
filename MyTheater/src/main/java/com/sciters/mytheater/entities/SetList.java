package com.sciters.mytheater.entities;

import java.util.ArrayList;

/**
 * Created by Gilang on 9/30/13.
 */
public class SetList {
    private int ID;
    private String Name;
    private String AvgRating;
    private String ImagePath;
    private String DateStart;
    private Show LatestShow;
    private ArrayList<String> Songs;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(String avgRating) {
        AvgRating = avgRating;
    }

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public Show getLatestShow() {
        return LatestShow;
    }

    public void setLatestShow(Show latestShow) {
        LatestShow = latestShow;
    }

    public ArrayList<String> getSongs() {
        return Songs;
    }

    public void setSongs(ArrayList<String> songs) {
        Songs = songs;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
