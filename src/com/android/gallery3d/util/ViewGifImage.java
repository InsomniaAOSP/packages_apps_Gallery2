package com.android.gallery3d.util;

import com.android.gallery3d.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewGifImage extends Activity {
    private static final String TAG = "ViewGifImage";

    public static DisplayMetrics dm;

    private ImageView mGifView;
    private String mFileDir;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gif_image);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (getIntent().getAction() != null
                && getIntent().getAction().equals("com.android.gallery3d.VIEW_GIF")) {
            Uri gifUri = getIntent().getData();
            showGifPicture(gifUri);
            return;
        }

        mFileDir = getIntent().getStringExtra("file_dir");
        Uri gifUri = getIntent().getData();
        showGifPicture(gifUri);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mGifView != null) {
            if (mGifView instanceof GIFView) {
                ((GIFView) mGifView).freeMemory();
                mGifView = null;
            }
        }
        super.onDestroy();
    }

    private void showGifPicture(Uri uri) {
        mGifView = new GIFView(this);
        ((LinearLayout) findViewById(R.id.image_absoluteLayout)).addView(mGifView,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (!((GIFView) mGifView).setDrawable(uri)) {
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        super.onConfigurationChanged(newConfig);
    }
}
