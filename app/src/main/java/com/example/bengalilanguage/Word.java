package com.example.bengalilanguage;

public class Word {

    private int mImage = NO_IMAGE_PROVIDED;
    private String mLocalName;
    private String mBangaliName;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourceId;

    public Word(int mImage, String mLocalName, String mBangaliName, int mAudioResourceId) {
        this.mImage = mImage;
        this.mLocalName = mLocalName;
        this.mBangaliName = mBangaliName;
        this.mAudioResourceId = mAudioResourceId;
    }
    // Without Image
    public Word(String mLocalName,String mBangaliName, int mAudioResourceId){
        this.mLocalName = mLocalName;
        this.mBangaliName = mBangaliName;
        this.mAudioResourceId = mAudioResourceId;
    }

    public int getmImage() {
        return mImage;
    }

    public String getmLocalName() {
        return mLocalName;
    }

    public String getmBangaliName() {
        return mBangaliName;
    }

    public boolean hasImage(){
        return mImage != NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

}
