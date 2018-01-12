package com.dlwx.wisdomschool.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.utiles.Content;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 表情栏
 */
public class EmojiconFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.gv_list)
    GridView gv_list;
    Unbinder unbinder;
    private int[] icons;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_emojicon;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
       icons = Content.icons;
        gv_list.setAdapter(new EmoAdapter(ctx));
    }
    private int pos;
    @Override
    protected void initListener() {
        gv_list.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        Glide.with(ctx).asBitmap().load(icons[i]).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                EmoInterface.checkEmoInterface.backEmo(resource,i);
            }
        });


    }

    private class EmoAdapter extends BaseFastAdapter {
        public EmoAdapter(Context ctx) {
            super(ctx);

        }

        @Override
        public int getCount() {

                return icons.length;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.item_emos, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }

                Glide.with(ctx).load(icons[position]).into(vh.iv_pic);


            return convertView;
        }

        private class ViewHolder {
            public View rootView;
            public ImageView iv_pic;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            }

        }
    }

}
