package com.liu.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liu.view.pop.PopActivity;
import com.liu.view.title.QuickPreviewActivity;
import com.liu.view.title.TitleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     findViewById(R.id.button).setOnClickListener(this);
   findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startActivity(new Intent(MainActivity.this, TitleActivity.class));

                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this, PopActivity.class));
                break;
        }
    }
}
