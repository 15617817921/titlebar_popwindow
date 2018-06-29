package com.liu.view.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liu.view.R;

import java.util.ArrayList;
import java.util.List;

public class PopWindowsMenu {
    private Activity mContext;
    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;
    private View content;
    private int mDrawable;

    private MenuAdapter mAdapter;
    private List<Menu> menuItemList;

    private static final int DEFAULT_HEIGHT = 480;
    private int popHeight = DEFAULT_HEIGHT;
    private int popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
    private boolean showIcon = true;
    private boolean dimBackground = true;
    private boolean needAnimationStyle = true;

    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private int animationStyle;

    private float alpha = 0.75f;

    public PopWindowsMenu(Activity context,int drawable) {
        this.mContext = context;
        this.mDrawable = drawable;
        init();
    }


    private void init() {
        content = LayoutInflater.from(mContext).inflate(R.layout.trm_popup_menu, null);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.trm_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setBackgroundResource(mDrawable);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        menuItemList = new ArrayList<>();
        mAdapter = new MenuAdapter(mContext, this, menuItemList, showIcon);
    }

    private PopupWindow getPopupWindow() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(content);
        mPopupWindow.setHeight(popHeight);
        mPopupWindow.setWidth(popWidth);
        if (needAnimationStyle) {
            mPopupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
        }

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimBackground) {
                    setBackgroundAlpha(alpha, 1f, 300);
                }
            }
        });

        mAdapter.setData(menuItemList);
        mAdapter.setShowIcon(showIcon);
        mRecyclerView.setAdapter(mAdapter);
        return mPopupWindow;
    }

    public PopWindowsMenu setHeight(int height) {
        if (height <= 0 && height != RecyclerView.LayoutParams.MATCH_PARENT
                && height != RecyclerView.LayoutParams.WRAP_CONTENT) {
            this.popHeight = DEFAULT_HEIGHT;
        } else {
            this.popHeight = height;
        }
        return this;
    }

    public PopWindowsMenu setWidth(int width) {
        if (width <= 0 && width != RecyclerView.LayoutParams.MATCH_PARENT) {
            this.popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            this.popWidth = width;
        }
        return this;
    }

    public PopWindowsMenu setBackground (Drawable drawable) {

        return this;
    }

    /**
     * 是否显示菜单图标
     *
     * @param show
     * @return
     */
    public PopWindowsMenu showIcon(boolean show) {
        this.showIcon = show;
        return this;
    }

    /**
     * 添加单个菜单
     *
     * @param item
     * @return
     */
    public PopWindowsMenu addMenuItem(Menu item) {
        menuItemList.add(item);
        return this;
    }

    /**
     * 添加多个菜单
     *
     * @param list
     * @return
     */
    public PopWindowsMenu addMenuList(List<Menu> list) {
        menuItemList.addAll(list);
        return this;
    }

    /**
     * 是否让背景变暗
     *
     * @param b
     * @return
     */
    public PopWindowsMenu dimBackground(boolean b) {
        this.dimBackground = b;
        return this;
    }

    /**
     * 否是需要动画
     *
     * @param need
     * @return
     */
    public PopWindowsMenu needAnimationStyle(boolean need) {
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     *
     * @param style
     * @return
     */
    public PopWindowsMenu setAnimationStyle(int style) {
        this.animationStyle = style;
        return this;
    }

    public PopWindowsMenu setOnMenuItemClickListener(PopWindowsMenu.OnMenuItemClickListener listener) {
        mAdapter.setOnMenuItemClickListener(listener);
        return this;
    }

    public PopWindowsMenu showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
        return this;
    }

    public PopWindowsMenu showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            if (dimBackground) {
                setBackgroundAlpha(1f, alpha, 240);
            }
        }
        return this;
    }

    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                mContext.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }

    /*适配器*/
    class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.TRMViewHolder> {
        private Context mContext;
        private List<Menu> menuItemList;
        private boolean showIcon;
        private PopWindowsMenu mPopWindowsMenu;
        private PopWindowsMenu.OnMenuItemClickListener onMenuItemClickListener;

        public MenuAdapter(Context context, PopWindowsMenu PopWindowsMenu, List<Menu> menuItemList, boolean show) {
            this.mContext = context;
            this.mPopWindowsMenu = PopWindowsMenu;
            this.menuItemList = menuItemList;
            this.showIcon = show;
        }

        public void setData(List<Menu> data) {
            menuItemList = data;
            notifyDataSetChanged();
        }

        public void setShowIcon(boolean showIcon) {
            this.showIcon = showIcon;
            notifyDataSetChanged();
        }

        @Override
        public MenuAdapter.TRMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.trm_item_popup_menu_list, parent, false);
            return new TRMViewHolder(view);
        }

  

        @Override
        public void onBindViewHolder(MenuAdapter.TRMViewHolder holder, int position) {
            final Menu menuItem = menuItemList.get(position);
            if (showIcon) {
                holder.icon.setVisibility(View.VISIBLE);
                int resId = menuItem.getIcon();
                holder.icon.setImageResource(resId < 0 ? 0 : resId);
            } else {
                holder.icon.setVisibility(View.GONE);
            }
            holder.text.setText(menuItem.getText());

            if (position == 0) {
                holder.container.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_top_pressed));
            } else if (position == menuItemList.size() - 1) {
                holder.container.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_bottom_pressed));
            } else {
                holder.container.setBackgroundDrawable(addStateDrawable(mContext, -1, R.drawable.trm_popup_middle_pressed));
            }
            final int pos = holder.getAdapterPosition();
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null) {
                        mPopWindowsMenu.dismiss();
                        onMenuItemClickListener.onMenuItemClick(pos);
                    }
                }
            });
        }

        private StateListDrawable addStateDrawable(Context context, int normalId, int pressedId) {
            StateListDrawable sd = new StateListDrawable();
            Drawable normal = normalId == -1 ? null : context.getResources().getDrawable(normalId);
            Drawable pressed = pressedId == -1 ? null : context.getResources().getDrawable(pressedId);
            sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
            sd.addState(new int[]{}, normal);
            return sd;
        }

        @Override
        public int getItemCount() {
            return menuItemList == null ? 0 : menuItemList.size();
        }

        class TRMViewHolder extends RecyclerView.ViewHolder {
            ViewGroup container;
            ImageView icon;
            TextView text;

            TRMViewHolder(View itemView) {
                super(itemView);
                container = (ViewGroup) itemView;
                icon = (ImageView) itemView.findViewById(R.id.trm_menu_item_icon);
                text = (TextView) itemView.findViewById(R.id.trm_menu_item_text);
            }
        }

        public void setOnMenuItemClickListener(PopWindowsMenu.OnMenuItemClickListener listener) {
            this.onMenuItemClickListener = listener;
        }
    }

    /*实体类*/
    public static class Menu {
        private String id;
        private int icon;
        private String text;

        public Menu() {}

        public Menu(String text) {
            this.text = text;
        }

        public Menu(int iconId, String text) {
            this.icon = iconId;
            this.text = text;
        }

        public Menu(String id, int iconId, String text) {
            this.id = id;
            this.icon = iconId;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIcon() {
            return icon;

        }
        public void setIcon(int iconId) {
            this.icon = iconId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
}
}

