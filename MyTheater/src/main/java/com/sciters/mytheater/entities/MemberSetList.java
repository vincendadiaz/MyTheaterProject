package com.sciters.mytheater.entities;

/**
 * Created by Gilang on 11/9/13.
 */
public class MemberSetList {
    private Member member;
    private SetList setList;

    private String avgRating;
    private int performCount;
    private int bdCount;

    public MemberSetList()
    {

    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public int getPerformCount() {
        return performCount;
    }

    public void setPerformCount(int performCount) {
        this.performCount = performCount;
    }

    public int getBdCount() {
        return bdCount;
    }

    public void setBdCount(int bdCount) {
        this.bdCount = bdCount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public SetList getSetList() {
        return setList;
    }

    public void setSetList(SetList setList) {
        this.setList = setList;
    }
}
