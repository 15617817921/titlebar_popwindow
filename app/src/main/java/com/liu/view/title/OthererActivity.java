package com.liu.view.title;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.liu.view.R;

public class OthererActivity extends AppCompatActivity {

    private double maxAlphaEffectHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 0:
                setContentView(R.layout.content_left_text);
                break;
            case 1:
                setContentView(R.layout.content_left_button);

                break;
            case 2:
                setContentView(R.layout.content_left_custom_layout);
                break;
            case 3:
                setContentView(R.layout.content_right_text);
                break;
            case 4:
                setContentView(R.layout.content_right_button);
                break;
            case 5:
                setContentView(R.layout.content_right_custom_layout);
                break;
            case 6:
                setContentView(R.layout.content_center_text_marquee);
                break;
            case 7:
                setContentView(R.layout.content_center_subtext);
                break;
//            case 8:
//                setContentView(R.layout.content_center_custom_layout);
//                initSmartTab();
//                break;
            case 9:
                setContentView(R.layout.content_center_search_view);
                break;
            case 10:
                setContentView(R.layout.content_all_custom);
                break;
        }


        final ComTitleBar titleBar = (ComTitleBar) findViewById(R.id.titlebar);

        titleBar.setDoubleClickListener(new ComTitleBar.OnTitleBarDoubleClickListener() {
            @Override
            public void onClicked(View v) {
                Toast.makeText(OthererActivity.this, "Titlebar double clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        if (position == 1) {
            titleBar.showCenterProgress();
        }
    }



//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        KeyboardConflictCompat.assistWindow(getWindow());
//        AppUtils.StatusBarLightMode(getWindow());
//        AppUtils.transparencyBar(getWindow());
//    }
}
