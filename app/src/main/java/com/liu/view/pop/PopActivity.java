package com.liu.view.pop;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liu.view.R;
import com.liu.view.title.ComTitleBar;

import java.util.ArrayList;
import java.util.List;

public class PopActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    private ComTitleBar titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        titlebar = $(R.id.titlebar);
        mRecyclerView = $(R.id.dividerRecycler);
        titlebar.setListener(new ComTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ComTitleBar.ACTION_RIGHT_TEXT) {
                    initBottomData();
                }
                // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
                // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
                // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
                // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
                // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
                // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
                // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
                // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
                // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new LinnerItemDivider(LinnerItemDivider.VERTICAL_LIST, Color.RED, 1));
        mAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("数据" + i);
        }
        mAdapter.addAll(strings);
        final LucklyPopopWindow mLucklyPopopWindow = new LucklyPopopWindow(this);

        mLucklyPopopWindow.setViewMargin(false, 20, 0, 0, 0);
        mLucklyPopopWindow.setViewPadding(0, 20, 10, 10);
        //给popupWindow添加数据
        // mLucklyPopopWindow.setData(getResources().getStringArray(R.array.popupArray), new int[]{R.mipmap.add, R.mipmap.delete,R.mipmap.modify,R.mipmap.update});
        mLucklyPopopWindow.setData(getData());

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(final View view, int position) {
                //必须设置宽度
                mLucklyPopopWindow.setWidth(ScreenUtils.dp2px(getApplicationContext(), 160));
                //添加分割线(可选)
                mLucklyPopopWindow.addItemDecoration(LucklyPopopWindow.VERTICAL, Color.GRAY, 1);
                //设置image不显示(可选)
                //mLucklyPopopWindow.setImageDisable(true);
                //设置image的大小(可选)
                mLucklyPopopWindow.setImageSize(20, 20);

                //监听事件
                mLucklyPopopWindow.setOnItemClickListener(new LucklyPopopWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //显示popopWindow
                        mLucklyPopopWindow.dismiss();
                    }
                });

                mLucklyPopopWindow.showAtLocation(getWindow().getDecorView(), view);
            }
        });
    }

    private void initBottomData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("数据" + i);
        }
        mAdapter.addAll(strings);
        LucklyPopopWindow mLucklyPopopWindow = new LucklyPopopWindow(this);

        mLucklyPopopWindow.setViewMargin(true, 40, 40, 10, 10);

        mLucklyPopopWindow.setViewPadding(20, 0, 20, 0);
        //给popupWindow添加数据
        mLucklyPopopWindow.setRadius(18);
        mLucklyPopopWindow.setDarkBackgroundDegree(0.3f);
        //添加数据

        mLucklyPopopWindow.setData(getData());
        //添加分割线
        mLucklyPopopWindow.addItemDecoration(LucklyPopopWindow.VERTICAL, getResources().getColor(android.R.color.darker_gray), 1);

        mLucklyPopopWindow.showInBottom(getWindow().getDecorView());
    }

    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


    private List<LucklyPopopWindow.DataBeans> getData() {
        List<LucklyPopopWindow.DataBeans> list = new ArrayList<>();
        LucklyPopopWindow.DataBeans search = new LucklyPopopWindow.DataBeans();
        search.setData("查询");
        search.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.im_msgs));
        search.setTextColor(getResources().getColor(R.color.colorPrimary));
        list.add(search);


        LucklyPopopWindow.DataBeans add = new LucklyPopopWindow.DataBeans();
        add.setData("增加");
        add.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        add.setTextColor(getResources().getColor(R.color.colorAccent));
        list.add(add);

        LucklyPopopWindow.DataBeans modify = new LucklyPopopWindow.DataBeans();
        modify.setData("修改");
        search.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.im_msgs));
        search.setTextColor(getResources().getColor(R.color.colorPrimary));
        list.add(modify);

        LucklyPopopWindow.DataBeans delete = new LucklyPopopWindow.DataBeans();
        delete.setData("删除");

        add.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        add.setTextColor(getResources().getColor(R.color.colorAccent));
        list.add(delete);

        return list;
    }
}
