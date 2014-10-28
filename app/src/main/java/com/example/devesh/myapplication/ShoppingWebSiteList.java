
package com.example.devesh.myapplication;

        import android.os.Handler;

        import java.util.ArrayList;
        import java.util.Timer;
        import java.util.TimerTask;

/**
 * Created by Devesh on 9/15/2014.
 */
public class ShoppingWebSiteList {

    // Current state info
    private int mCurrentIndex = 0;
    private int mNumTotal = 5;
    private ArrayList<Merchandise> mMercahndises = new ArrayList<Merchandise>(mNumTotal);
    private Merchandise mCurrent;

    // Defailt timer
    private Timer mPlayTimer = new Timer();
    private TimerTask mPlayTask = new mTimerTask();

    // References to the UI class and threads
    private ShoppingWebSiteListListener mListener;
    private Handler mListenerHandler;

    // Listener to be implemented by the observer UI class
    public interface ShoppingWebSiteListListener {
        public void updateProgress(int playheadPosition);
        public void startedNext();
        public Handler getHandler();
    }

    public ShoppingWebSiteList(ShoppingWebSiteListListener listener) {
        mListener = listener;
        mListenerHandler = mListener.getHandler();
        initializePlaylistInfo();
        mCurrent = mMercahndises.get(mCurrentIndex);
    }

    private void initializePlaylistInfo(){
        mMercahndises.add(new Merchandise("Google", "Rs. 900", "google.png", 30));
        mMercahndises.add(new Merchandise("Flipkart", "Rs. 1200", "flipkart.jpg", 70));
        mMercahndises.add(new Merchandise("Snapdeal", "Rs. 1200", "snapdeal.png", 150));
        mMercahndises.add(new Merchandise("Amazon", "Rs. 1200", "amazon.png", 180));
        mMercahndises.add(new Merchandise("Ebay", "Rs. 1200", "ebay.png", 200));
    }

    public void playNext(){
        stopCurrent();
        mCurrentIndex++;
        mCurrentIndex %= mNumTotal;
        mCurrent = mMercahndises.get(mCurrentIndex);
        playCurrent();
    }

    public void playPrevious(){
        stopCurrent();
        mCurrentIndex--;
        mCurrentIndex = (mCurrentIndex + mNumTotal) % mNumTotal;
        mCurrent = mMercahndises.get(mCurrentIndex);
        playCurrent();
    }

    public void pauseCurrent(){
        mPlayTask.cancel();
        mPlayTimer.cancel();
    }

    public void stopCurrent(){
        mPlayTask.cancel();
        mPlayTimer.cancel();
        mCurrent.mCurrentPlayheadPosition=0;
    }

    public void playCurrent(){
        mPlayTimer = new Timer();
        mPlayTask = new mTimerTask();

        mPlayTimer.schedule(mPlayTask, 0, 1000);
    }

    public Merchandise getCurrentInfo(){
        return mCurrent;
    }

    private class mTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrent.mCurrentPlayheadPosition < mCurrent.mExtra){
                mCurrent.mCurrentPlayheadPosition++;
                mListener.updateProgress(mCurrent.mCurrentPlayheadPosition);
            }else{
                playNext();
                mListenerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.startedNext();
                    }
                });
            }
        }
    }
}

