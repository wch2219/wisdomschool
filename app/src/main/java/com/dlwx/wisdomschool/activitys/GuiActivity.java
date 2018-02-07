package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuiActivity extends AppCompatActivity {

    @BindView(R.id.vp_view)
    ViewPager vpView;
    private int[] pics = {R.mipmap.icon_gui1, R.mipmap.icon_gui2, R.mipmap.icon_gui3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui);
        ButterKnife.bind(this);
        vpView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            ViewHolder vh;
            if (view == null) {
                view = LayoutInflater.from(GuiActivity.this).inflate(R.layout.item_gui, null);
                vh = new ViewHolder(view);
                view.setTag(vh);
            }else{
                vh = (ViewHolder) view.getTag();
            }
            vh.iv_pic.setImageResource(pics[position]);
            if (position == pics.length-1) {
                vh.btn_start.setVisibility(View.VISIBLE);
            }else{
                vh.btn_start.setVisibility(View.GONE);
            }
            vh.btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GuiActivity.this, LoginInActivity.class));
                    finish();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private class ViewHolder {
            public View rootView;
            public ImageView iv_pic;
            public Button btn_start;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
                this.btn_start = (Button) rootView.findViewById(R.id.btn_start);
            }

        }
    }
}
