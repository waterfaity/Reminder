package com.waterfairy.reminder.manger;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.waterfairy.reminder.application.MyApp;
import com.waterfairy.utils.AssetsUtils;
import com.waterfairy.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2018/2/25
 * des  :音频播放管理器
 */

public class AudioManger {
    private static final String TAG = "AudioManger";
    private static final AudioManger AUDIO_MANGER = new AudioManger();
    private MediaPlayer mediaPlayer;
    private String audioPath;

    private AudioManger() {
    }

    public static AudioManger getInstance() {
        return AUDIO_MANGER;
    }

    /**
     * 开始播放
     */
    public void startAudio() {
        if (mediaPlayer == null) initMediaPlayer();
        mediaPlayer.start();
    }

    /**
     * 停止
     */
    public void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * 初始化配置播放器
     */
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "onPrepared: ");
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.i(TAG, "onCompletion: ");
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Log.i(TAG, "onInfo: " + what + "   " + extra);
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init() {
        initFile();
    }

    /**
     * 闹钟音频文件复制到内存卡
     */
    private void initFile() {
        audioPath = FileUtils.getAPPPath(MyApp.getApp().getApplicationContext(), "audio") + "alarm1.mp3";
        if (!new File(audioPath).exists()) {
            AssetsUtils.copyFile(MyApp.getApp().getApplicationContext(), "audio/alarm1.mp3", audioPath);
            Log.i("test", "init: no exist ");
        } else {
            Log.i("test", "init:exist ");
        }
    }
}
