package com.example.devesh.myapplication;

/**
 * Created by Devesh on 9/15/2014.
 */
public class Merchandise {
    public String mCoverPath;
    public int mExtra;
    public int mCurrentPlayheadPosition;
    public String mTitle;
    public String mType;

    public Merchandise(String type, String title, String path, int extra){
        mTitle = title;
        mType = type;
        mCoverPath = path;
        mExtra = extra;
        mCurrentPlayheadPosition = 0;
    }
}
