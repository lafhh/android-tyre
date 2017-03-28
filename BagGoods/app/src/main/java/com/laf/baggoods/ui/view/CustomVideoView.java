package com.laf.baggoods.ui.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by apple on 2017/3/18.
 *
 * <a href="https://developer.android.com/reference/android/media/MediaPlayer.html">
 * MediaPlayer</a>
 *
 */
public class CustomVideoView {}
//public class CustomVideoView extends RelativeLayout implements OnClickListener,
//MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener,
//MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
//TextureView.SurfaceTextureListener {
//
//    private static final String TAG = "CustomVideoView";
//
//    private static final int TIME_MSG = 0x01;
//    private static final int TIME_INVAL = 1000;
//
//    /**
//     * MediaPlayer state
//     */
//    private static final int STATE_ERROR = -1;
//    private static final int STATE_IDLE = 0;
//    private static final int STATE_PLAYING = 1;
//    private static final int STATE_PAUSING = 2;
//
//
//    private static final int LOAD_TOTAL_COUNT = 3;
//
//    private int mPlayerState = STATE_IDLE;
//
//    /**
//     * UI
//     */
//    private ViewGroup mParentContainer;
//    private RelativeLayout mPlayerView;
//    private TextureView mVideoView;
//    private Button mMiniPlayBtn;
//    private ImageView mFullBtn;
//    private ImageView mLoadingBar;
//    private ImageView mFrameView;
//    private AudioManager audioManager;
//    private Surface videoSurface;
//
//    private MediaPlayer mediaPlayer;
//    private ADVideoPlayerListener mlistener;
//    private ScreenEventReceiver mScreenReceiver;
//    private ADFrameImageLoadListener mFrameLoadListener;
//
//    /**
//     * data
//     */
//    private String mUrl;
//    private String mFrameURI;
//    private boolean isMute;
//    private int mScreenWidth, mDestationHeight;
//
//    public CustomVideoView(Context context, ViewGroup parentContainer) {
//        super(context);
//        mParentContainer = parentContainer;
//        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
//        initData();
//        initView();
//    }
//
////    private void initData() {
////
////    }
////
////    private void initView() {
////        LayoutInflater inflater = LayoutInflater.from(getContext());
////        inflater.inflate();
////
////    }
//
//
//    public void load() {
//        if (this.mPlayerState != STATE_IDLE) {
//            return;
//        }
//        Log.d(TAG, "load()====MediaPlayer is " + mediaPlayer);
//
//        showLoadingView();
//        try {
//            setCurrentPlayState(STATE_IDLE);
//            checkMediaPlayer();
//            mute(true);//这步是干什么？
//            mediaPlayer.setDataSource(this.mUrl);
//            mediaPlayer.prepareAsync();
//        } catch (Exception e) {
//            stop();
//        }
//
//    }
//
//    public void stop() {
//        Log.d(TAG, "stop()");
//        if (mediaPlayer != null) {
//            mediaPlayer.reset();
//            mediaPlayer.setOnSeekCompleteListener(null);
//            mediaPlayer.stop();//idle状态下不能调用stop吧？
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//        mHandler.removeCallbacksAndMessages(null);
//        setCurrentPlayState(STATE_IDLE);
//        if (mCurrentCount < LOAD_TOTAL_COUNT) {
//            ++mCurrentCount;
//            load();
//        } else {
//            showPauseView(false);
//        }
//
//    }
//
//    private void setCurrentPlayState(int state) {
//        mPlayerState = state;
//    }
//
//    private synchronized void checkMediaPlayer() {
//        if (mediaPlayer == null) {
//            mediaPlayer = createMediaPlayer();
//        }
//    }
//
//    private MediaPlayer createMediaPlayer() {
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.reset();
//        mediaPlayer.setOnPreparedListener(this);
//        mediaPlayer.setOnCompletionListener(this);
//        mediaPlayer.setOnInfoListener(this);
//        mediaPlayer.setOnErrorListener(this);
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        if (videoSurface != null && videoSurface.isValid()) {
//            mediaPlayer.setSurface(videoSurface);
//        } else {
//            stop();
//        }
//        return mediaPlayer;
//    }
//
//    /**
//     * 设置音量
//     * 以下摘自API文档
//     * Sets the volume on this player.
//     * Note that the passed volume values are raw scalars in range 0.0 to 1.0.
//     * UI controls should be scaled logarithmically.
//     * @param mute
//     */
//    public void mute(boolean mute) {
//        isMute = mute;
//        if (mediaPlayer != null && audioManager != null) {
//            float volume = isMute ? 0.0f : 1.0f;
//            mediaPlayer.setVolume(volume, volume);
//        }
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//        Log.d(TAG, "onError()===");
//        return false;
//    }
//
//    @Override
//    protected void onVisibilityChanged(View changedView,
//                                       int visibility) {
//        super.onVisibilityChanged(changedView, visibility);
//
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        Log.d(TAG, "onDetachedFromWindow()");
//    }
//
//    /**
//     * 本来
//     */
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        Log.d(TAG, "onAttachedToWindow()");
//    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        Log.d(TAG, "onSurfaceTextureAvailable()");
//        videoSurface = new Surface(surface);
//        checkMediaPlayer();
//        mediaPlayer.setSurface(videoSurface);//checkMediaPlayer已经调用了，这行不用了吧
//        load();
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//        Log.d(TAG, "onSurfaceTextureSizeChanged()");
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        Log.d(TAG, "onSurfaceTextureDestroyed()");
//        return false;
//    }
//}

