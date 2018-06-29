package com.liu.view.title;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liu.view.R;


/**
 * Created by liufei on 2017/8/29.
 */

public class QuickPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_preview);
        ((ComTitleBar) findViewById(R.id.titlebar)).setListener(new ComTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ComTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
        ((ComTitleBar) findViewById(R.id.titlebar_3)).showCenterProgress();

    }

//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        AppUtils.StatusBarLightMode(getWindow());
//        AppUtils.transparencyBar(getWindow());
//        KeyboardConflictCompat.assistWindow(getWindow());
//    }
}
