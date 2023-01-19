package com.example.track.model.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.track.R;

public class RightCameraView extends ConstraintLayout {

    private WebView mWebView;
    public RightCameraView(Context context) {
        super(context);
        init(context,null);
    }

    public RightCameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context, AttributeSet attrs){
        View view = inflate(context, R.layout.view_right_camera,this);
        mWebView = view.findViewById(R.id.view_right_camera_web);
        mWebView.loadUrl("https://www.bilibili.com/");
    }

}
