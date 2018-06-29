package com.liu.view.title;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.liu.view.R;
import com.liu.view.pop.LucklyPopopWindow;
import com.liu.view.pop.PopWindowsMenu;
import com.liu.view.pop.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView lv;
    private ComTitleBar titlebar;
    private LucklyPopopWindow mLucklyPopopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        lv = (ListView) findViewById(R.id.listview);
        titlebar = (ComTitleBar) findViewById(R.id.titlebar);
        titlebar.showStatusBar(false);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{
                "快速预览",
                "1、左边TextView + 中间文字",
                "2、左边ImageButton + 中间文字(带进度条)",
                "3、左边自定义Layout + 中间文字",
                "4、中间文字 + 右边TextView",
                "5、中间文字 + 右边ImageButton",
                "6、中间文字 + 右边自定义Layout",
                "7、中间跑马灯效果 + 右边TextView",
                "8、中间添加副标题",
                "9、中间自定义Layout + 右边自定义Layout",
                "10、中间搜索框",
                "11、中间搜索框 + 两侧自定义Layout"
        }));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(TitleActivity.this, QuickPreviewActivity.class));
                } else  {
//                    if (position == 1){
//                    menu(view);
//                }else {
//                  autopop(view);
                    Intent intent = new Intent(TitleActivity.this, OthererActivity.class);
                    intent.putExtra("position", position - 1);
                    startActivity(intent);
                }
            }
        });
    }

    private void autopop(View view) {
        mLucklyPopopWindow = new LucklyPopopWindow(this);
//        mLucklyPopopWindow.setViewMargin(false, 20, 0, 0, 0);
        mLucklyPopopWindow.setViewPadding(0, 10, 0, 0);
        //给popupWindow添加数据
        // mLucklyPopopWindow.setData(getResources().getStringArray(R.array.popupArray), new int[]{R.mipmap.add, R.mipmap.delete,R.mipmap.modify,R.mipmap.update});
        mLucklyPopopWindow.setData(getData());


                //必须设置宽度
                mLucklyPopopWindow.setWidth(ScreenUtils.dp2px(getApplicationContext(),160));
                //添加分割线(可选)
                mLucklyPopopWindow.addItemDecoration(LucklyPopopWindow.VERTICAL, Color.GRAY, 1);
                //设置image不显示(可选)
                //mLucklyPopopWindow.setImageDisable(true);
                //设置image的大小(可选)
                mLucklyPopopWindow.setImageSize(20, 20);
        mLucklyPopopWindow.setRadius(0);
                //监听事件
                mLucklyPopopWindow.setOnItemClickListener(new LucklyPopopWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(TitleActivity.this, "位置"+position, Toast.LENGTH_SHORT).show();
                        //显示popopWindow

                        mLucklyPopopWindow.dismiss();
                    }
                });

                mLucklyPopopWindow.showAtLocation(getWindow().getDecorView(), view);
//        mLucklyPopopWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,-10);
    }
    private List<LucklyPopopWindow.DataBeans> getData(){
        List<LucklyPopopWindow.DataBeans> list=new ArrayList<>();
        LucklyPopopWindow.DataBeans  search=new LucklyPopopWindow.DataBeans();
        search.setData("查询");
        search.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.im_msgs));
        search.setTextColor(getResources().getColor(R.color.colorPrimary));
        list.add(search);


        LucklyPopopWindow.DataBeans add=new LucklyPopopWindow.DataBeans();
        add.setData("增加");
        search.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.im_msgs));
        search.setTextColor(getResources().getColor(R.color.colorPrimary));
        add.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        add.setTextColor(getResources().getColor(R.color.colorAccent));
        list.add(add);

        LucklyPopopWindow.DataBeans modify=new LucklyPopopWindow.DataBeans();
        modify.setData("修改");
        modify.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.im_msgs));
        modify.setTextColor(getResources().getColor(R.color.colorPrimary));
        list.add(modify);

        LucklyPopopWindow.DataBeans delete=new LucklyPopopWindow.DataBeans();
        delete.setData("删除");
        delete.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        delete.setTextColor(getResources().getColor(R.color.colorAccent));
        list.add(delete);

        return list;
    }
    private void menu(View view) {
        PopWindowsMenu mTopRightMenu = new PopWindowsMenu(TitleActivity.this,R.drawable.trm_popup_top_pressed);
        List<PopWindowsMenu.Menu> menuItems = new ArrayList<>();
        menuItems.add(new PopWindowsMenu.Menu(R.mipmap.ic_launcher, "发起多人聊天"));
        menuItems.add(new PopWindowsMenu.Menu(R.mipmap.ic_launcher_round, "加好友"));
        menuItems.add(new PopWindowsMenu.Menu(R.mipmap.im_msgs, "扫一扫"));
        mTopRightMenu
                .setHeight(280)     //默认高度480
//                .setWidth(320)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .addMenuItem(new PopWindowsMenu.Menu(R.mipmap.ic_launcher, "面对面快传"))
                .addMenuItem(new PopWindowsMenu.Menu(R.mipmap.im_msgs, "付款"))
                .setOnMenuItemClickListener(new PopWindowsMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        Toast.makeText(TitleActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .showAsDropDown(view, -225, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
