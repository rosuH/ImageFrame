package com.example.chengwangyong.imageframe;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mrwang.imageframe.ImageFrameHandler;

import java.lang.reflect.Field;

/**
 * @author rosu
 * @date 2018/11/10
 */
public class ControllerActivity extends AppCompatActivity {

    private static final String TAG = "ControllerActivity";

    private Button mStartButton;
    private Button mPauseButton;
    private Button mResumeButton;
    private Button mStopButton;
    private ImageView mImageView;
    int[] resIds;
    private ImageFrameHandler.ResourceHandlerBuilder mHandlerBuilder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        mStartButton = (Button) findViewById(R.id.start);
        mPauseButton = (Button) findViewById(R.id.pause);
        mResumeButton = (Button) findViewById(R.id.resume);
        mStopButton = (Button) findViewById(R.id.stop);
        mImageView = (ImageView) findViewById(R.id.image_view);


        Field[] fields = R.drawable.class.getFields();
        resIds = new int[275];
        for (int i = 0; i < resIds.length; i++) {
            resIds[i] = getResources().getIdentifier(fields[i].getName(), "drawable", getPackageName());
        }

        mHandlerBuilder = new ImageFrameHandler.ResourceHandlerBuilder(getResources(), resIds);
        final ImageFrameHandler handler = mHandlerBuilder
                .setFps(25)
                .setStartIndex(0)
                .setLoop(false)
                .openLruCache(false)
                .setOnImageLoaderListener(new ImageFrameHandler.OnImageLoadListener() {
                    @Override
                    public void onImageLoad(BitmapDrawable drawable) {
                        ViewCompat.setBackground(mImageView, drawable);
                    }

                    @Override
                    public void onPlayFinish() {
                        Log.i(TAG, "onPlayFinish: 结束");
                    }
                })
                .build();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.start();
            }
        });
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.pause();
            }
        });
        mResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.resume();
            }
        });
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.destroy();
            }
        });
    }
}
