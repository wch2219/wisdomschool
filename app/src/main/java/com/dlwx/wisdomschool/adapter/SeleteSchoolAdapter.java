package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.SubPoiItem;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18/018.
 */

public class SeleteSchoolAdapter extends BaseFastAdapter {
    private ArrayList<PoiItem> pois;

    public SeleteSchoolAdapter(Context ctx, ArrayList<PoiItem> pois) {
        super(ctx);
        this.pois = pois;
    }

    @Override
    public int getCount() {
        return pois.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_seleteschool, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        PoiItem poiItem = pois.get(position);
        vh.tv_name.setText(poiItem.getTitle());
        List<SubPoiItem> subPois = poiItem.getSubPois();

        vh.tv_local.setText(poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_local;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_local = (TextView) rootView.findViewById(R.id.tv_local);
        }

    }
}
